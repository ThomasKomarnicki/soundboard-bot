package xyz.doglandia.soundboard;

import sx.blah.discord.util.DiscordException;

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

        try {
            SoundboardBot bot = new SoundboardBot(BotEnvironment.getInstance().getToken());
        } catch (DiscordException e) {
            System.out.print("caught discord exception");
            e.printStackTrace();
        }
        catch (Exception e){
            System.out.print("caught unhandeled exception");
        }
    }
}
