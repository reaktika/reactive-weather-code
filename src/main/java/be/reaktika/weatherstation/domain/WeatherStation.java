/* This code was generated by Akka Serverless tooling.
 * As long as this file exists it will not be re-generated.
 * You are free to make changes to this file.
 */
package be.reaktika.weatherstation.domain;

import be.reaktika.weatherstation.domain.WeatherStationDomain.*;
import com.akkaserverless.javasdk.eventsourcedentity.EventSourcedEntityContext;
import com.google.protobuf.Empty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** An event sourced entity. */
public class WeatherStation extends AbstractWeatherStation {

  private final Logger logger = LoggerFactory.getLogger(WeatherStation.class);

  public static final double MAX_LAT_ABS = 85.;
  public static final double MAX_LON_ABS = 180.;

  @SuppressWarnings("unused")
  private final String entityId;

  public WeatherStation(EventSourcedEntityContext context) {
    this.entityId = context.entityId();
  }

  @Override
  public WeatherStationState emptyState() {
    return WeatherStationState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> registerStation(WeatherStationState currentState, StationRegistrationCommand command) {
    logger.info("registering station " + command);
    if (Math.abs(command.getLatitude()) > MAX_LAT_ABS || Math.abs(command.getLongitude()) > MAX_LON_ABS){
      return effects().error(String.format("latitude or longitude are invalid: %f, %f",command.getLongitude(), command.getLongitude()));
    }
    var event = StationRegistered.newBuilder()
            .setStationName(command.getStationName())
            .setStationId(command.getStationId())
            .setLongitude(command.getLongitude())
            .setLatitude(command.getLatitude()).build();

    logger.info("emitting StationRegistered event");

    return effects()
            .emitEvent(event)
            .thenReply(newState -> Empty.getDefaultInstance());

  }

  @Override
  public Effect<Empty> publishTemperatureReport(WeatherStationState currentState, StationTemperatureCommand command) {
    logger.info("publishing temperature " + command);
    var eventBuilder = TemperaturesCelciusAdded.newBuilder()
            .setStationId(command.getStationId());
    command.getTempMeasurementsList().forEach(t -> eventBuilder.addTemperature(Temperature.newBuilder()
            .setMeasurementTime(t.getMeasurementTime())
            .setTemperatureCelcius(t.getTemperatureCelcius())
            .build()));

    logger.info("emitting TemperaturesCelciusAdded event");

    return effects()
            .emitEvent(eventBuilder.build())
            .thenReply(newState -> Empty.getDefaultInstance());

  }

  @Override
  public Effect<Empty> publishWindspeedReport(WeatherStationState currentState, StationWindspeedCommand command) {
    logger.info("publishing windspeed " + command);
    var eventBuilder = WindspeedsAdded.newBuilder().setStationId(command.getStationId());
    command.getWindspeedMeasurementsList().forEach(m -> eventBuilder.addWindspeed(Windspeed.newBuilder()
            .setMeasurementTime(m.getMeasurementTime())
            .setWindspeedMPerS(m.getWindspeedMPerS())
            .build()));

    logger.info("emitting WindspeedsAdded event");
    return effects()
            .emitEvent(eventBuilder.build())
            .thenReply(newStata -> Empty.getDefaultInstance());
  }



  @Override
  public WeatherStationState stationRegistered(WeatherStationState currentState, StationRegistered event) {
    logger.info("station registered");
    var newState = WeatherStationState.newBuilder(currentState)
            .setStationId(event.getStationId())
            .setStationName(event.getStationName())
            .setLatitude(event.getLatitude())
            .setLongitude(event.getLongitude());
    return newState.build();
  }
  @Override
  public WeatherStationState temperaturesCelciusAdded(WeatherStationState currentState, TemperaturesCelciusAdded event) {
    logger.info("temperatures added " + event);
    return currentState;
  }
  @Override
  public WeatherStationState windspeedsAdded(WeatherStationState currentState, WindspeedsAdded event) {
    logger.info("windspeeds added " + event);
    return currentState;
  }

}