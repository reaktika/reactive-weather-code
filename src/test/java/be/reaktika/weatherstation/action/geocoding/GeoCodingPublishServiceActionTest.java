package be.reaktika.weatherstation.action.geocoding;

import org.junit.Test;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class GeoCodingPublishServiceActionTest {

  @Test
  public void exampleTest() {
    GeoCodingPublishServiceActionTestKit testKit = GeoCodingPublishServiceActionTestKit.of(GeoCodingPublishServiceAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void publishMeasurementsTest() {
    GeoCodingPublishServiceActionTestKit testKit = GeoCodingPublishServiceActionTestKit.of(GeoCodingPublishServiceAction::new);
    // ActionResult<WeatherstationGeocoding.CountryMeasurements> result = testKit.publishMeasurements(WeatherstationGeocoding.CountryMeasurements.newBuilder()...build());
  }

}
