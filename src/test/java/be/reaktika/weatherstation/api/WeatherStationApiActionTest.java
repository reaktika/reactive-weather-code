package be.reaktika.weatherstation.api;

import org.junit.Test;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class WeatherStationApiActionTest {

  @Test
  public void exampleTest() {
    WeatherStationApiActionTestKit testKit = WeatherStationApiActionTestKit.of(WeatherStationApiAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void registerStationTest() {
    WeatherStationApiActionTestKit testKit = WeatherStationApiActionTestKit.of(WeatherStationApiAction::new);
    // ActionResult<Empty> result = testKit.registerStation(WeatherStationService.StationRegistrationRequest.newBuilder()...build());
  }

  @Test
  public void publishTemperatureReportTest() {
    WeatherStationApiActionTestKit testKit = WeatherStationApiActionTestKit.of(WeatherStationApiAction::new);
    // ActionResult<Empty> result = testKit.publishTemperatureReport(WeatherStationService.StationTemperaturePublishRequest.newBuilder()...build());
  }

  @Test
  public void publishWindspeedReportTest() {
    WeatherStationApiActionTestKit testKit = WeatherStationApiActionTestKit.of(WeatherStationApiAction::new);
    // ActionResult<Empty> result = testKit.publishWindspeedReport(WeatherStationService.StationWindspeedPublishRequest.newBuilder()...build());
  }

}
