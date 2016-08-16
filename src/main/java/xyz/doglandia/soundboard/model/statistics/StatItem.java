package xyz.doglandia.soundboard.model.statistics;

import java.util.Comparator;

/**
 * Created by tdk10 on 7/30/2016.
 */
public class StatItem implements Comparable<StatItem>{

    String userName;
    int value;

    public StatItem() {
        value = 1;
    }

    public StatItem(String userName) {
        this.userName = userName;
        value = 1;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void incrementValue(){
        value++;
    }

    public int getValue() {
        return value;
    }

    public String getUserName() {
        return userName;
    }



    @Override
    public int compareTo(StatItem o) {
        return Integer.compare(o.getValue(), getValue());
    }
}
