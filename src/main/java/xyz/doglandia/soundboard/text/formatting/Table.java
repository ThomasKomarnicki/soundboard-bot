package xyz.doglandia.soundboard.text.formatting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tdk10 on 7/30/2016.
 */
public class Table {

    private List<Column> columns;
    private List<String> headers;

    private int[] maxColumnSizes;

    public Table(List<Column> columns) {
        this.columns = columns;
    }

//    public Table(List<Column> columns) {
//        this.columns = new ArrayList<>();
//        for (Column column : columns){
//            this.columns.add(column);
//        }
//    }

    public static class Column{
        List<String> content;

        public Column(){
            content = new ArrayList<>();
        }

        public Column(List<String> content) {
            this.content = content;
        }

        public List<String> getContent() {
            return content;
        }


    }

    @Override
    public String toString() {
        maxColumnSizes = new int[columns.size()];

        // get sizes of columns
        for(int i = 0; i < columns.size(); i++){
            Column column = columns.get(i);
            int size = 0;
            for(String entry : column.getContent()){
                if(entry.length() > size){
                    size = entry.length();
                }
            }

            if(headers != null) {
                if (headers.get(i).length() > size) {
                    size = headers.get(i).length();
                }
            }

            maxColumnSizes[i] = size;
        }

        StringBuilder tableBuilder = new StringBuilder();
        int maxRows = getMaxRows();

        for(int i = 0; i < headers.size(); i++){
            FormattingUtil.addWithSpaces(tableBuilder, headers.get(i), maxColumnSizes[i]);
            tableBuilder.append(" | ");
        }
        tableBuilder.append('\n');

        for(int i = 0; i < headers.size(); i++){
            FormattingUtil.addRepeatedChar(tableBuilder,'-', maxColumnSizes[i]);
            tableBuilder.append(" | ");
        }
        tableBuilder.append('\n');

        for(int j = 0; j < maxRows; j++) {

            for (int i = 0; i < columns.size(); i++) {
                Column column = columns.get(i);

                if(j < column.getContent().size()) {
                    String content = column.getContent().get(j);
                    FormattingUtil.addWithSpaces(tableBuilder, content, maxColumnSizes[i]);
                }else{
                    FormattingUtil.addWithSpaces(tableBuilder, "", maxColumnSizes[i]);
                }

                tableBuilder.append(" | ");
            }
            tableBuilder.append('\n');

        }

        return tableBuilder.toString();

    }

//    public void setHeaders(String... headers){
//        if(headers.length != columns.size()){
//            throw new RuntimeException("headers length is not the same as columns length");
//        }
//        this.headers = headers;
//    }

    public void setHeaders(List<String> headers){
        if(headers.size() != columns.size()){
            throw new RuntimeException("headers length is not the same as columns length");
        }
        this.headers = headers;
    }

    private int getMaxRows() {
        int max = 0;
        for(int i = 0; i < columns.size(); i++){
            Column column = columns.get(i);
            if(column.getContent().size() > max){
                max = column.getContent().size();
            }
        }

        return max;
    }
}
