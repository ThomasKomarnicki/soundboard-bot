package xyz.doglandia.soundboard;

/**
 * Created by tdk10 on 7/16/2016.
 */
public class BotRunner {

    public static void main(String[] args) {

        String env = "dev";
        if(args.length > 0) {
            env = args[0];
        }

        BotEnvironment.init(env);

        SoundboardBot bot = new SoundboardBot(BotEnvironment.getInstance().getToken());
    }
}
