package be.reaktika.weatherstation.action;

import be.reaktika.weatherstation.domain.aggregations.WeatherStationAggregation;
import com.akkaserverless.javasdk.ServiceCallRef;
import com.akkaserverless.javasdk.SideEffect;
import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Empty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** An action. */
public class WeatherStationDataConsumeServiceAction extends AbstractWeatherStationDataConsumeServiceAction {

  private final Logger logger = LoggerFactory.getLogger(WeatherStationDataConsumeServiceAction.class);

  private final ServiceCallRef<WeatherStationAggregation.AddToAggregationCommand> extremesAggregator;
  private final ServiceCallRef<WeatherStationAggregation.AddToAggregationCommand> geoCodingAggregator;


  public WeatherStationDataConsumeServiceAction(ActionCreationContext ctx) {
    this.extremesAggregator = ctx.serviceCallFactory().lookup("be.reaktika.weatherstation.domain.aggregations.WeatherStationExtremesEntityService","RegisterData", WeatherStationAggregation.AddToAggregationCommand.class);
    this.geoCodingAggregator = ctx.serviceCallFactory().lookup("be.reaktika.weatherstation.domain.geocoding.GeoCodingEntityService","RegisterData", WeatherStationAggregation.AddToAggregationCommand.class);
    logger.info("created WeatherStationDataConsumeServiceAction");
  }

  /** Handler for "DispatchWeatherStationData". */
  @Override
  public Effect<Empty> dispatchWeatherStationData(WeatherStationToTopic.WeatherStationData data) {
    logger.info("dispatching data to aggregators");
    var extremesCommand =  WeatherStationAggregation.AddToAggregationCommand.newBuilder()
            .setType(WeatherStationAggregation.AggregationType.EXTREMES)
            .setWeatherdata(data);
    var countryCommand = WeatherStationAggregation.AddToAggregationCommand.newBuilder()
            .setType(WeatherStationAggregation.AggregationType.COUNTRY)
            .setWeatherdata(data);

    return effects().reply(Empty.getDefaultInstance())
            .addSideEffect(SideEffect.of(extremesAggregator.createCall(extremesCommand.build())))
            .addSideEffect(SideEffect.of(geoCodingAggregator.createCall(countryCommand.build())));

  }
}
