package be.reaktika.weatherstation.domain;

import com.google.protobuf.Timestamp;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class WeatherStationTest {

    @Test
    public void registerStationTest() {
        WeatherStationTestKit testKit = WeatherStationTestKit.of(WeatherStation::new);
        var command = WeatherStationDomain.StationRegistrationCommand.newBuilder()
                .setStationId("stationId")
                .setStationName("name")
                .setLatitude(10)
                .setLongitude(20).build();
        var registerStationResult = testKit.registerStation(command);

        var event = registerStationResult.getNextEventOfType(WeatherStationDomain.StationRegistered.class);

        assertEquals("stationId",event.getStationId());
        assertEquals("stationId",testKit.getState().getStationId());
        assertEquals("name", testKit.getState().getStationName());

    }

    @Test
    public void publishTemperatureReportTest() {
        WeatherStationTestKit testKit = WeatherStationTestKit.of(WeatherStation::new);

        var command = WeatherStationDomain.StationTemperatureCommand.newBuilder()
                .setStationId("stationId")
                    .addTempMeasurements(WeatherStationDomain.TemperatureMeasurements.newBuilder().setTemperatureCelcius(10).setMeasurementTime(Timestamp.newBuilder().build()))
                    .addTempMeasurements(WeatherStationDomain.TemperatureMeasurements.newBuilder().setTemperatureCelcius(20).setMeasurementTime(Timestamp.newBuilder().build()))
                .build();
        var result = testKit.publishTemperatureReport(command);


        WeatherStationDomain.TemperaturesCelciusAdded expected = WeatherStationDomain.TemperaturesCelciusAdded.newBuilder()
                    .setStationId("stationId")
                .addTemperature(WeatherStationDomain.Temperature.newBuilder()
                        .setTemperatureCelcius(10)
                        .setMeasurementTime(Timestamp.newBuilder().build()).build())
                .addTemperature(WeatherStationDomain.Temperature.newBuilder()
                        .setTemperatureCelcius(20)
                        .setMeasurementTime(Timestamp.newBuilder().build()).build())
                .build();
        var event = result.getNextEventOfType(WeatherStationDomain.TemperaturesCelciusAdded.class);
        assertEquals(expected.getStationId(), event.getStationId());
        assertEquals(expected.getTemperatureCount(), event.getTemperatureCount());
        assertEquals(expected.getTemperatureList().get(0).getTemperatureCelcius(),event.getTemperatureList().get(0).getTemperatureCelcius(), 0.001);
        assertEquals(expected.getTemperatureList().get(0).getMeasurementTime(),event.getTemperatureList().get(0).getMeasurementTime());
        assertEquals(expected.getTemperatureList().get(1).getTemperatureCelcius(),event.getTemperatureList().get(1).getTemperatureCelcius(), 0.001);

    }

    @Test
    public void publishWindspeedReportTest() {
        WeatherStationTestKit testKit = WeatherStationTestKit.of(WeatherStation::new);

        var result = testKit.publishWindspeedReport(WeatherStationDomain.StationWindspeedCommand.newBuilder()
                .setStationId("stationId")
                .addWindspeedMeasurements(WeatherStationDomain.WindspeedMeasurement.newBuilder()
                        .setWindspeedMPerS(10)
                        .setMeasurementTime(Timestamp.newBuilder().build()).build())
                .addWindspeedMeasurements(WeatherStationDomain.WindspeedMeasurement.newBuilder()
                        .setWindspeedMPerS(20)
                        .setMeasurementTime(Timestamp.newBuilder().build()).build())
                .build());

        var event = result.getNextEventOfType(WeatherStationDomain.WindspeedsAdded.class);

        WeatherStationDomain.WindspeedsAdded expected = WeatherStationDomain.WindspeedsAdded.newBuilder()
                .setStationId("stationId")
                .addWindspeed(WeatherStationDomain.Windspeed.newBuilder()
                        .setWindspeedMPerS(10)
                        .setMeasurementTime(Timestamp.newBuilder().build()).build())
                .addWindspeed(WeatherStationDomain.Windspeed.newBuilder()
                        .setWindspeedMPerS(20)
                        .setMeasurementTime(Timestamp.newBuilder().build()).build())
                .build();
        
        assertEquals(expected.getStationId(), expected.getStationId());
        assertEquals(expected.getWindspeedList().get(0).getWindspeedMPerS(), expected.getWindspeed(0).getWindspeedMPerS(), 0.001);
        assertEquals(expected.getWindspeedList().get(0).getMeasurementTime(), expected.getWindspeed(0).getMeasurementTime());
        assertEquals(expected.getWindspeedList().get(1).getWindspeedMPerS(), expected.getWindspeed(1).getWindspeedMPerS(), 0.001);
        assertEquals(expected.getWindspeedList().get(1).getMeasurementTime(), expected.getWindspeed(1).getMeasurementTime());
    }

}