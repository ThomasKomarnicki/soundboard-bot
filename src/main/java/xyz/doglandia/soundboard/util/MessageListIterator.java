package xyz.doglandia.soundboard.util;

import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.*;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/**
 * Created by tdk10 on 7/31/2016.
 */
public class MessageListIterator implements Iterable<IMessage> {

//    private static final int MESSAGES_RATE_LIMIT = 10; // per second

    private MessageList messageList;



    public interface MessageIteration{
        void onNextMessage(IMessage message);
    }

    public MessageListIterator(MessageList messageList) {
        this.messageList = messageList;
        messageList.setCacheCapacity(0);
        messageList.setCacheCapacity(200);
    }

    public void getAllMessages(MessageIteration messageIteration){

    }

    @Override
    public Iterator<IMessage> iterator() {
        return new MessagesIterator();
    }


    private class MessagesIterator implements Iterator<IMessage>{

        int currentIndex = 0;

        private boolean hasMore = true;

        @Override
        public boolean hasNext() {
            return hasMore || currentIndex < messageList.size();
        }

        @Override
        public IMessage next() {
            if(currentIndex >= messageList.size()){

                RequestBuffer.RequestFuture<Object> future = RequestBuffer.request(new RequestBuffer.IRequest<Object>() {
                    @Override
                    public Object request() throws RateLimitException {

                        messageList.load(100);
                        if(messageList.size() > 100) {
                            removeRange(100);
                        }
                        currentIndex = 0;
                        if(messageList.size() < 100){
                            hasMore = false;
                        }
                        return null;
                    }
                });


                while (!future.isDone()){
                    // block and wait for load()
                }


            }
            return messageList.get(currentIndex++);
        }
    }

    private void removeRange(int count){
        for(int i = 0; i < count; i++){
            messageList.remove(0);
        }
    }
}
