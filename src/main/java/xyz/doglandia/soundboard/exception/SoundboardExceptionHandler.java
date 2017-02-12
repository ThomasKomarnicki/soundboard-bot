package xyz.doglandia.soundboard.exception;

import com.google.gson.JsonObject;
import okhttp3.*;
import xyz.doglandia.soundboard.analytics.Analytics;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by tdk10 on 2/10/2017.
 */
public class SoundboardExceptionHandler {

    public static SoundboardExceptionHandler Instance = new SoundboardExceptionHandler();

    public void handleException(Exception e){
        OkHttpClient client = new OkHttpClient();
        JsonObject obj = new JsonObject();

        StringWriter writer = new StringWriter(1950);
        e.printStackTrace(new PrintWriter(writer));

        int len = 0;
        String stacktrace = writer.toString();
        String[] splits = stacktrace.split("\n");
        for(int i = 0; i < Math.min(6, splits.length); i++){
            len += splits[i].length();
        }

        obj.addProperty("content", stacktrace.substring(0,len));

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), obj.toString());
        Request request = new Request.Builder()
                .url("https://discordapp.com/api/webhooks/279821182839029760/fDCRX7e50zgRTrgE_E8m_d92BvB8MBDZAJiFTaja381_qowEtqfyBG10c11S4BtOzAp-")
                .post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                System.out.println(response.toString());
                System.out.println(response.body().string());
                response.body().close();
            }
        });

        Analytics.sendException(e);
    }
}
