package xyz.doglandia.soundboard.model.statistics;

import xyz.doglandia.soundboard.text.DiscordTextFormatter;

import java.util.*;

/**
 * Created by tdk10 on 7/31/2016.
 */
public class MultiCountStatistic implements Statistic {

    private String title;

    Map<String, MultiStatItem> items;

    public MultiCountStatistic(String title) {
        this.title = title;

        items = new HashMap<>();
    }

    public void addOrIncrement(String username, String statKey) {
        if(items.containsKey(username)){
            items.get(username).incrementValue(statKey);
        }else{
            items.put(username, new MultiStatItem(true, username, statKey));
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDisplayOutput() {
        DiscordTextFormatter textFormatter = new DiscordTextFormatter();
        return textFormatter.formatTableStatistic(getTitle(), getSortedStatItems());
    }

    private List<MultiStatItem> getSortedStatItems(){
        List<MultiStatItem> sorted = new ArrayList<>();
        sorted.addAll(items.values());

        Collections.sort(sorted);

        return sorted;
    }
}
