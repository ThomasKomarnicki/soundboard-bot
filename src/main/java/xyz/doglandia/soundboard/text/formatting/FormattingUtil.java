package xyz.doglandia.soundboard.text.formatting;

/**
 * Created by tdk10 on 7/30/2016.
 */
public class FormattingUtil {

    public static void addWithSpaces(StringBuilder builder, String content, int spaceMax){
        if(content.length() > spaceMax){
            throw new RuntimeException("content length cannot be larger than spaceMax");
        }
        int spacesToAdd = spaceMax - content.length();
        builder.append(content);
        for(int i = 0; i < spacesToAdd; i++){
            builder.append(' ');
        }
    }

    public static void addRepeatedChar(StringBuilder builder, char c, int count){
        for(int i = 0; i < count; i++){
            builder.append(c);
        }
    }
}
