package xyz.doglandia.soundboard.util;

import java.util.List;

/**
 * Created by tdk10 on 8/13/2016.
 */
public class SqlUtil {

    public static String stringListToValue(List<String> strings){
        StringBuilder builder = new StringBuilder();
        builder.append("'{");

        for(int i = 0; i < strings.size(); i++){
            builder.append("\""+strings.get(i)+"\"");
            if(i != strings.size()-1) {
                builder.append(",");
            }

        }





        builder.append("}'");
        return builder.toString();
    }
}
