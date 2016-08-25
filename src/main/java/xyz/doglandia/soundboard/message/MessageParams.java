package xyz.doglandia.soundboard.message;

import sx.blah.discord.handle.obj.IMessage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tdk10 on 8/21/2016.
 */
public class MessageParams {

    public enum Type{
        NONE, ADD_SOUNDBOARD, ADD_CLIP, PLAY_CLIP, HELP, STOP_AUDIO, JOIN_CHANNEL
    }

    public static class Keys{
        public static final String TYPE = "type";
        public static final String HELP_PARAM = "help_param";
        public static final String SOUNDBOARD_NAME = "soundboard_name";
        public static final String CLIP_NAME = "clip_name";
        public static final String CLIP_URL = "clip_url";
        public static final String CHANNEL_NAME = "channel_name";
    }

    private Type type;
//    private String[] params;

    private Map<String, String> params;

    public MessageParams(IMessage message) {
        params = new HashMap<>();
        parseMessage(message);
    }

    private void parseMessage(IMessage message){
        String[] messageParams = message.getContent().split(" ");

        if(messageParams.length >= 2){
            if(messageParams[0].startsWith("!") && messageParams[0].length() > 1){

                String helpParam =  messageParams[1];
                if (messageParams[0].equalsIgnoreCase("!help")){
                    type = Type.HELP;
                    params.put(Keys.TYPE, "help");
                    params.put(Keys.HELP_PARAM, helpParam);
//                    params = Arrays.copyOfRange(messageParams,0,messageParams.length);
                    return;
                }
                else if(messageParams[0].equalsIgnoreCase("!add")){


                    type = Type.ADD_CLIP;
                    params.put(Keys.SOUNDBOARD_NAME, messageParams[1]);
                    params.put(Keys.CLIP_NAME, messageParams[2]);
                    params.put(Keys.CLIP_URL, message.getAttachments().get(0).getUrl());
                    return;

                }
                else if(messageParams.length >= 3 && messageParams[0].equalsIgnoreCase("!create") && messageParams[1].equalsIgnoreCase("soundboard")){
                    type = Type.ADD_SOUNDBOARD;
                    params.put(Keys.SOUNDBOARD_NAME, messageParams[2]);
                    return;
                }else {

                    type = Type.PLAY_CLIP;
                    params.put(Keys.SOUNDBOARD_NAME, messageParams[0].substring(1, messageParams[0].length()));
                    params.put(Keys.CLIP_NAME, messageParams[1]);
                    return;
                }

            }else{
                if(messageParams[0].equalsIgnoreCase("join") && messageParams[1].length() > 0){
                    type = Type.JOIN_CHANNEL;
                    params.put(Keys.CHANNEL_NAME, messageParams[1]);
                    return;
//                    handleJoin(message, messageParams);
                }
            }
        }else if(messageParams.length == 1){
            if(messageParams[0].equalsIgnoreCase("stop")){
                type = Type.STOP_AUDIO;
                return;
//                audioDispatcher.stopAllAudio(message);
            }
        }
        type = Type.NONE;
    }

    public String getParam(String key){
        return params.get(key);
    }

    public boolean hasParam(String key){
        return params.containsKey(key);
    }

    public Type getType() {
        return type;
    }
}
