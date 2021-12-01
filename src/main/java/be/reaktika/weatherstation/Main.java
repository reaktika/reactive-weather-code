package be.reaktika.weatherstation;


import be.reaktika.weatherstation.action.WeatherStationDataConsumeServiceAction;
import be.reaktika.weatherstation.action.WeatherStationToTopicServiceAction;
import be.reaktika.weatherstation.action.geocoding.GeoCodingPublishServiceAction;
import be.reaktika.weatherstation.api.WeatherStationApiAction;
import be.reaktika.weatherstation.domain.WeatherStation;
import be.reaktika.weatherstation.domain.aggregations.GeoCodingService;
import be.reaktika.weatherstation.domain.aggregations.WeatherStationExtremes;
import be.reaktika.weatherstation.domain.geocoding.GeoCoding;
import be.reaktika.weatherstation.ports.OpenCageGeoCodingService;
import be.reaktika.weatherstation.view.CountryAverageView;
import be.reaktika.weatherstation.view.WeatherStationExtremesView;
import be.reaktika.weatherstation.view.WeatherStationOverallAverageView;
import com.akkaserverless.javasdk.AkkaServerless;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class Main {


    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static AkkaServerless createAkkaServerless() {
        return AkkaServerlessFactory.withComponents(
                GeoCoding::new,
                WeatherStation::new,
                WeatherStationExtremes::new,
                CountryAverageView::new,
                GeoCodingPublishServiceAction::new,
                WeatherStationApiAction::new,
                WeatherStationDataConsumeServiceAction::new,
                WeatherStationExtremesView::new,
                WeatherStationOverallAverageView::new,
                WeatherStationToTopicServiceAction::new);

    }


    public static void main(String[] args) throws Exception {
        LOG.info("starting the Akka Serverless service");

        //create geocoding implementation.
        GeoCodingService geoCoding = new OpenCageGeoCodingService(ConfigFactory.load());
        GeoCodingService.setInstance(geoCoding);

        createAkkaServerless().start().toCompletableFuture().get();
    }
}