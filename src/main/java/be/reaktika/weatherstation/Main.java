package be.reaktika.weatherstation;


import be.reaktika.weatherstation.api.WeatherStationApiAction;
import be.reaktika.weatherstation.domain.WeatherStation;
import be.reaktika.weatherstation.view.WeatherStationOverallAverageView;
import com.akkaserverless.javasdk.AkkaServerless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class Main {


    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static AkkaServerless createAkkaServerless() {
        return AkkaServerlessFactory.withComponents(
                WeatherStation::new,
                WeatherStationApiAction::new,
                WeatherStationOverallAverageView::new);

    }


    public static void main(String[] args) throws Exception {
        LOG.info("starting the Akka Serverless service");
        createAkkaServerless().start().toCompletableFuture().get();
    }
}