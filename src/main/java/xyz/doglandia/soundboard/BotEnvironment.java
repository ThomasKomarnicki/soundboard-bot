package xyz.doglandia.soundboard;

import xyz.doglandia.soundboard.util.Sensitive;

/**
 * Created by tdk10 on 9/4/2016.
 */
public class BotEnvironment {

    private static final String STAGING_DATABASE_CONNECTION = "jdbc:postgresql://ec2-52-43-81-161.us-west-2.compute.amazonaws.com:5432/";
    private static final String PROD_DATABASE_CONNECTION = "jdbc:postgresql://ec2-52-41-77-215.us-west-2.compute.amazonaws.com:5432/";
    private static final String DEV_DATABASE_CONNECTION = STAGING_DATABASE_CONNECTION;

    private static final String STAGING_S3_FOLDER = "staging";
    private static final String PROD_S3_FOLDER = "production";
    private static final String DEV_S3_FOLDER = STAGING_S3_FOLDER;

    private static BotEnvironment instance;

    public static BotEnvironment getInstance() {
        return instance;
    }

    public static void init(String environmentName){
        instance = new BotEnvironment(environmentName);
    }



    private enum Env{
        Production, Staging, Dev;
    }

    private Env env;


    private BotEnvironment(String environmentName){
        if(environmentName.equalsIgnoreCase("prod")){
            env = Env.Production;
        }else if(environmentName.equalsIgnoreCase("staging")){
            env = Env.Staging;
        }else{
            env = Env.Dev;
        }
    }

    public String getDatabaseConnection(){
        if(env == Env.Production){
            return PROD_DATABASE_CONNECTION;
        }else if(env == Env.Staging){
            return STAGING_DATABASE_CONNECTION;
        }else{
            return DEV_DATABASE_CONNECTION;
        }
    }

    public String[] getDatabaseCreds(){
        return new String[]{"postgres", Sensitive.DB_PASSWORD};
    }

    public String getS3BucketDivision() {
        if(env == Env.Production){
            return PROD_S3_FOLDER;
        }else if(env == Env.Staging){
            return STAGING_S3_FOLDER;
        }else{
            return DEV_S3_FOLDER;
        }
    }


}
