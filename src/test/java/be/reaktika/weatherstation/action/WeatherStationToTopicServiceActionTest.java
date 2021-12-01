package be.reaktika.weatherstation.action;

import org.junit.Test;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class WeatherStationToTopicServiceActionTest {

  @Test
  public void exampleTest() {
    WeatherStationToTopicServiceActionTestKit testKit = WeatherStationToTopicServiceActionTestKit.of(WeatherStationToTopicServiceAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void publishStationRegisteredTest() {
    WeatherStationToTopicServiceActionTestKit testKit = WeatherStationToTopicServiceActionTestKit.of(WeatherStationToTopicServiceAction::new);
    // ActionResult<WeatherStationToTopic.WeatherStationData> result = testKit.publishStationRegistered(WeatherStationDomain.StationRegistered.newBuilder()...build());
  }

  @Test
  public void publishTemperatureRegisteredTest() {
    WeatherStationToTopicServiceActionTestKit testKit = WeatherStationToTopicServiceActionTestKit.of(WeatherStationToTopicServiceAction::new);
    // ActionResult<WeatherStationToTopic.WeatherStationData> result = testKit.publishTemperatureRegistered(WeatherStationDomain.TemperaturesCelciusAdded.newBuilder()...build());
  }

  @Test
  public void publishWindspeedRegisteredTest() {
    WeatherStationToTopicServiceActionTestKit testKit = WeatherStationToTopicServiceActionTestKit.of(WeatherStationToTopicServiceAction::new);
    // ActionResult<WeatherStationToTopic.WeatherStationData> result = testKit.publishWindspeedRegistered(WeatherStationDomain.WindspeedsAdded.newBuilder()...build());
  }

}
