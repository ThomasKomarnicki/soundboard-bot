package xyz.doglandia.soundboard.analytics;


import com.brsanthu.googleanalytics.EventHit;
import com.brsanthu.googleanalytics.ExceptionHit;
import com.brsanthu.googleanalytics.GoogleAnalytics;
import xyz.doglandia.soundboard.util.Sensitive;

public class Analytics {

    private static GoogleAnalytics ga = new GoogleAnalytics(Sensitive.GA_TRACKING);

    public static void sendEvent(EventHit eventHit){
        ga.postAsync(eventHit);

    }

    public static void sendException(Exception e){
        ga.postAsync(new ExceptionHit(e.getMessage()));
    }

}
