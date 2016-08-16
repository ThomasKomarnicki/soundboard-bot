package xyz.doglandia.soundboard.model.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tdk10 on 7/31/2016.
 */
public class MultiStatItem implements Comparable<MultiStatItem> {

    public static final String ALL = "all";

    String userName;
    Map<String, Integer> values;
    boolean trackAll;

    public MultiStatItem(boolean trackAll) {
        this.trackAll = trackAll;
        values = new HashMap<>();
        if(trackAll) {
            values.put(ALL, 1);
        }
    }

    public MultiStatItem(boolean trackAll, String userName, String statKey) {
        this(trackAll);
        this.userName = userName;
        values.put(statKey, 1);
    }

    public void setValue(String key, int value) {
        values.put(key, value);
    }

    public void incrementValue(String key){
        values.put(key, values.get(key)+1);
    }

    public int getValue(String key) {
        return values.get(key);
    }

    public String getUserName() {
        return userName;
    }



    @Override
    public int compareTo(MultiStatItem o) {
        if(trackAll) {
            return Integer.compare(o.getValue(ALL), getValue(ALL));
        }else{
            return Integer.compare(o.getValue("Count"), getValue("Count"));
        }
    }

    public Set<String> getStatKeys(){
        return values.keySet();
    }
}
