package be.reaktika.weatherstation.action;

import org.junit.Test;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class WeatherStationDataConsumeServiceActionTest {

  @Test
  public void exampleTest() {
    WeatherStationDataConsumeServiceActionTestKit testKit = WeatherStationDataConsumeServiceActionTestKit.of(WeatherStationDataConsumeServiceAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void dispatchWeatherStationDataTest() {
    WeatherStationDataConsumeServiceActionTestKit testKit = WeatherStationDataConsumeServiceActionTestKit.of(WeatherStationDataConsumeServiceAction::new);
    // ActionResult<Empty> result = testKit.dispatchWeatherStationData(WeatherStationToTopic.WeatherStationData.newBuilder()...build());
  }

}
