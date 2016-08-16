package xyz.doglandia.soundboard.stats;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageList;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;
import xyz.doglandia.soundboard.model.statistics.CountStatistic;
import xyz.doglandia.soundboard.model.statistics.MultiCountStatistic;
import xyz.doglandia.soundboard.util.MessageListIterator;

import java.util.regex.Pattern;

/**
 * Created by tdk10 on 7/26/2016.
 */
public class ChannelStatistics {

    private static final String[] LINKS_REGEX = {
            "http(?:s?):\\/\\/(?:www\\.)?youtu(?:be\\.com\\/watch\\?v=|\\.be\\/)([\\w\\-\\_]*)(&(amp;)?\u200C\u200B[\\w\\?\u200C\u200B=]*)?", // youtube
            ".*imgur\\.com", // imgur
            ".*reddit\\.com", //reddit
    };

    private IChannel channel;

    public ChannelStatistics(IChannel channel){
        this.channel = channel;

    }


    public CountStatistic getStatsForPhrase(String phrase){
        phrase = phrase.trim();
        CountStatistic statistic = new CountStatistic("Number of times \""+phrase+"\" was used");

        MessageList messageList = channel.getMessages();
        for(int i = 0; i < messageList.size(); i++){
            IMessage message = messageList.get(i);
            if(message.getContent().contains(phrase)){
                statistic.addOrIncrement(message.getAuthor().getName());
            }
            if(i == 99){
                try {
                    messageList.load(100);
                } catch (RateLimitException e) {
                    e.printStackTrace();
                }
                i = 0;
            }
        }


        return statistic;
    }

    public CountStatistic getStatsForMessageCount(){
        CountStatistic statistic = new CountStatistic("Number of messages sent");
        int total = 1;

        MessageList messageList = channel.getMessages();


        MessageListIterator iterator = new MessageListIterator(messageList);


        for(IMessage message : iterator){
            statistic.addOrIncrement(message.getAuthor().getName());
            System.out.println(message.getAuthor().getName() + " : " + message.getContent() + "(" + total++ + ")");
        }

        return statistic;
    }

    public MultiCountStatistic getStatsForLinks(){
        MultiCountStatistic statistic = new MultiCountStatistic("Number and types of links");

        MessageList messageList = channel.getMessages();

        MessageListIterator iterator = new MessageListIterator(messageList);

        for(IMessage message : iterator){

            if(Pattern.matches(LINKS_REGEX[0], message.getContent())){
                statistic.addOrIncrement(message.getAuthor().getName(), "Youtube");
            }else if(Pattern.matches(LINKS_REGEX[1], message.getContent())){
                statistic.addOrIncrement(message.getAuthor().getName(), "Imgur");
            }else if(Pattern.matches(LINKS_REGEX[2], message.getContent())){
                statistic.addOrIncrement(message.getAuthor().getName(), "Reddit");
            }

        }

        return statistic;
    }




}
