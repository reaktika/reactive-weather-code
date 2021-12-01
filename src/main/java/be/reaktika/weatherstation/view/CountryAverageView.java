package be.reaktika.weatherstation.view;

import be.reaktika.weatherstation.action.geocoding.GeoCodingDataPublish;
import be.reaktika.weatherstation.view.CountryAverageViewModel.AveragePerCountryState;
import be.reaktika.weatherstation.view.CountryAverageViewModel.CountryAverageRecord;
import com.akkaserverless.javasdk.view.ViewContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class CountryAverageView extends AbstractCountryAverageView {

  private final Logger logger = LoggerFactory.getLogger(CountryAverageView.class);

  public CountryAverageView(ViewContext context) {}

  @Override
  public AveragePerCountryState emptyState() {
    return AveragePerCountryState.getDefaultInstance();
  }


  @Override
  public UpdateEffect<AveragePerCountryState> processCountryMeasurement(AveragePerCountryState state, GeoCodingDataPublish.CountryData countryData) {
    logger.info("updating average per country for " + countryData.getCountry());
    var stateBuilder = AveragePerCountryState.newBuilder(state);
    var thisCountry = state.getAveragesPerCountyList().stream()
            .filter(r -> r.getCountry().equals(countryData.getCountry()))
            .findFirst()
            .orElse(CountryAverageRecord.newBuilder()
                    .setCountry(countryData.getCountry())
                    .build());
    //avgTmp in measurements
    var eventTmpAvg = countryData.getTemperaturesList()
            .stream().mapToDouble(GeoCodingDataPublish.TemperatureData::getMeasuredTemperature)
            .average()
            .orElse(0.);
    //avtWindspeed in measurements
    var eventWsAvg = countryData.getWindspeedsList()
            .stream().mapToDouble(GeoCodingDataPublish.WindspeedData::getMeasuredWindspeed)
            .average()
            .orElse(0.);
    //previous averages
    var oldTempAvg = thisCountry.getNumberOfTemperatureMeasurements() == 0 ? eventTmpAvg : thisCountry.getAverageTempCelcius();
    var currentNbrTemps = thisCountry.getNumberOfTemperatureMeasurements() + countryData.getTemperaturesCount();

    var oldWsAvg = thisCountry.getNumberOfWindMeasurements() == 0 ? eventWsAvg : thisCountry.getAverageWindspeed();
    var currentNbrWs = thisCountry.getNumberOfWindMeasurements() + countryData.getWindspeedsCount();

    var newTmpAvg = oldTempAvg + ((eventTmpAvg - oldTempAvg)/currentNbrTemps);
    var newWsAvg = oldWsAvg + ((eventWsAvg - oldWsAvg)/currentNbrWs);

    var newCountryAvg = CountryAverageRecord.newBuilder(thisCountry)
            .setNumberOfTemperatureMeasurements(currentNbrTemps)
            .setAverageTempCelcius(newTmpAvg)
            .setNumberOfWindMeasurements(currentNbrWs)
            .setAverageWindspeed(newWsAvg);

    //replace old record
    var index = state.getAveragesPerCountyList().indexOf(thisCountry);
    if (index > -1) {
      stateBuilder.setAveragesPerCounty(index,newCountryAvg.build());
    }else {
      stateBuilder.addAveragesPerCounty(newCountryAvg.build());
    }
    var newState = stateBuilder.build();
    logger.info("updating state to " + newState);
    return effects().updateState(newState);
  }
}
