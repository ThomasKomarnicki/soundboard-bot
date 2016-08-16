package xyz.doglandia.soundboard.text;

import xyz.doglandia.soundboard.model.statistics.MultiStatItem;
import xyz.doglandia.soundboard.model.statistics.StatItem;
import xyz.doglandia.soundboard.text.formatting.Table;

import java.util.*;

/**
 * Created by tdk10 on 7/30/2016.
 */
public class DiscordTextFormatter {



    public String formatTableStatistic(String title, List<MultiStatItem> sortedStatItems){

        List<String> headers = getValueHeadersForStats(sortedStatItems);
        headers.add(0, "Users");

        Table table = createMultiColumnTable(sortedStatItems, headers);
        table.setHeaders(headers);
        return  "  __**"+title+"**__\n\n"+
                "```"+table+"```";
    }

    private Table createMultiColumnTable(List<MultiStatItem> sortedStatItems, List<String> headers){
        List<String> userNames = new ArrayList<>();

        Map<String, Table.Column> columns = new HashMap<>();
        columns.put("Users", new Table.Column(userNames));

        for(int i = 1; i < headers.size(); i++){
            columns.put(headers.get(i), new Table.Column());

        }


        for(int i =0; i < sortedStatItems.size(); i++){
            columns.get("Users").getContent().add(sortedStatItems.get(i).getUserName());

            for(String key : sortedStatItems.get(i).getStatKeys()){
                columns.get(key).getContent().add(String.valueOf(sortedStatItems.get(i).getValue(key)));
            }

        }

        List<Table.Column> sortedColumns = new ArrayList<>();
        for(int i = 0; i < headers.size(); i++){
            sortedColumns.add(columns.get(headers.get(i)));
        }


        return new Table(sortedColumns);
    }

    private List<String> getValueHeadersForStats(List<MultiStatItem> sortedStatItems){

        HashSet<String> columns = new HashSet<>();

        for(MultiStatItem multiStatItem : sortedStatItems){
            columns.addAll(multiStatItem.getStatKeys());
        }

        List<String> headers = new ArrayList<>();
        headers.addAll(columns);
        Collections.sort(headers);

        return headers;
    }





}
