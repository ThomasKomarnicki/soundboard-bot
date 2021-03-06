package xyz.doglandia.soundboard.util;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tdk10 on 7/22/2016.
 */
public class Util {

    public static IGuild getGuildFromUserMessage(IMessage message){
        try{
            return message.getGuild();
        }catch (UnsupportedOperationException e){
            // silently fail
        }

        String authorId = message.getAuthor().getID();
        for(IGuild guild : message.getClient().getGuilds()){
            IUser user = guild.getUserByID(authorId);
            if(user != null){
                if(user.getConnectedVoiceChannels().size() >= 1){
                    return user.getConnectedVoiceChannels().get(0).getGuild();
                }
            }
        }

        return null;

    }

    public static String fromArrayWithUnderscores(String[] params, int start, int end){
        StringBuilder builder = new StringBuilder();
        end = Math.min(params.length,end);
        for(int i = start; i < end; i++){
            builder.append(params[i]);
            if(i != end-1) {
                builder.append("_");
            }

        }
        return builder.toString();
    }

    public static String stringFromArray(String[] params, int start, int end){
        StringBuilder builder = new StringBuilder();
        end = Math.min(params.length,end);
        for(int i = start; i < end; i++){
            builder.append(params[i]);
            if(i != end-1) {
                builder.append(" ");
            }

        }
        return builder.toString();
    }

    public static List<String> ListRoleNames(List<IRole> roles){
        return roles.stream().map(IRole::getName).collect(Collectors.toList());
    }
}
