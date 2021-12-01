package be.reaktika.weatherstation.action.geocoding;

import be.reaktika.weatherstation.domain.geocoding.GeoCodingModel;
import com.akkaserverless.javasdk.action.ActionCreationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** An action. */
public class GeoCodingPublishServiceAction extends AbstractGeoCodingPublishServiceAction {
  private final Logger logger = LoggerFactory.getLogger(GeoCodingPublishServiceAction.class);
  public GeoCodingPublishServiceAction(ActionCreationContext creationContext) {
  }

  /** Handler for "PublishMeasurements". */
  @Override
  public Effect<GeoCodingDataPublish.CountryData> publishMeasurements(GeoCodingModel.CountryMeasurements countryMeasurements) {
    logger.info("publisher received measurements");

    var builder = GeoCodingDataPublish.CountryData.newBuilder()
            .setCountry(countryMeasurements.getCountry());

    countryMeasurements.getTemperaturesList().stream()
            .map(t -> GeoCodingDataPublish.TemperatureData.newBuilder()
                    .setMeasuredTemperature(t.getMeasuredTemperature())
                    .setMeasurementTime(t.getMeasurementTime())
                    .build()
            )
            .forEach(d -> builder.addTemperatures(d));
    countryMeasurements.getWindspeedsList().stream()
            .map(s -> GeoCodingDataPublish.WindspeedData.newBuilder()
                    .setMeasuredWindspeed(s.getMeasuredWindspeed())
                    .setMeasurementTime(s.getMeasurementTime())
                    .build()
            )
            .forEach(d -> builder.addWindspeeds(d));
    var toPublish = builder.build();
    logger.info("publishing " + toPublish);
    return effects().reply(toPublish);
  }
}
