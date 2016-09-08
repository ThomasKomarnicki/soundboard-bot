package xyz.doglandia.soundboard;

import xyz.doglandia.soundboard.audio.DiscordAudioDispatcher;
import xyz.doglandia.soundboard.audio.management.*;
import xyz.doglandia.soundboard.discord.DiscordEventListener;
import xyz.doglandia.soundboard.message.MessageHandlerImpl;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;
import xyz.doglandia.soundboard.text.DiscordTextDispatcher;

/**
 * Created by tdk10 on 7/16/2016.
 */
public class SoundboardBot {

    private IDiscordClient client;


    public SoundboardBot(String token){

        try {
            client = new ClientBuilder().withToken(token).login();

        } catch (DiscordException e) {
            e.printStackTrace();
        }

//        SoundboardDataCreator soundboardDataCreator = new SoundboardFilesDataCreator(new File("soundboards/"));
        SoundboardController soundboardController = new SoundboardsController();

        DiscordEventListener eventListener = new DiscordEventListener(client, new MessageHandlerImpl(new DiscordAudioDispatcher(), new DiscordTextDispatcher(), soundboardController));
        client.getDispatcher().registerListener(eventListener);



    }


}
