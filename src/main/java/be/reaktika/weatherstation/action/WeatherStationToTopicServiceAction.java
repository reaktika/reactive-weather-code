package be.reaktika.weatherstation.action;

import be.reaktika.weatherstation.action.WeatherStationToTopic.WeatherStationData;
import be.reaktika.weatherstation.action.WeatherStationToTopic.WeatherStationTemperatures;
import be.reaktika.weatherstation.action.WeatherStationToTopic.WeatherStationWindspeeds;
import be.reaktika.weatherstation.domain.WeatherStationDomain;
import com.akkaserverless.javasdk.action.ActionCreationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** An action. */
public class WeatherStationToTopicServiceAction extends AbstractWeatherStationToTopicServiceAction {

  private final Logger logger = LoggerFactory.getLogger(WeatherStationToTopicServiceAction.class);

  public WeatherStationToTopicServiceAction(ActionCreationContext creationContext) {}

  /** Handler for "PublishStationRegistered". */
  @Override
  public Effect<WeatherStationToTopic.WeatherStationData> publishStationRegistered(WeatherStationDomain.StationRegistered event) {
    logger.info("publishing registered station to topic");
    var data =  WeatherStationData.newBuilder()
            .setStationId(event.getStationId())
            .setLatitude(event.getLatitude())
            .setLongitude(event.getLongitude())
            .setStationName(event.getStationName())
            .build();
    logger.info("publishing " + data);
    return effects().reply(data);
  }

  /** Handler for "PublishTemperatureRegistered". */
  @Override
  public Effect<WeatherStationToTopic.WeatherStationData> publishTemperatureRegistered(WeatherStationDomain.TemperaturesCelciusAdded event) {
    logger.info("publishing registered temperatures to topic");
    var builder = WeatherStationData.newBuilder().setStationId(event.getStationId());
    event.getTemperatureList().forEach(t -> {
      var tempBuilder = WeatherStationTemperatures
              .newBuilder()
              .setTemperatureCelcius(t.getTemperatureCelcius())
              .setMeasurementTime(t.getMeasurementTime());
      builder.addTemperatures(tempBuilder.build());
    });
    var data = builder.build();
    logger.info("publishing " + data);
    return effects().reply(data);
  }

  /** Handler for "PublishWindspeedRegistered". */
  @Override
  public Effect<WeatherStationToTopic.WeatherStationData> publishWindspeedRegistered(WeatherStationDomain.WindspeedsAdded event) {
    logger.info("publishing registered windspeeds to topic");
    var builder = WeatherStationData.newBuilder()
            .setStationId(event.getStationId());
    event.getWindspeedList().forEach(w -> {
      var windspeedBuilder = WeatherStationWindspeeds
              .newBuilder()
              .setWindspeedMPerS(w.getWindspeedMPerS())
              .setMeasurementTime(w.getMeasurementTime());
      builder.addWindspeeds(windspeedBuilder.build());
    });
    var data = builder.build();
    logger.info("publishing " + data);
    return effects().reply(data);
  }
}
