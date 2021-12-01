package be.reaktika.weatherstation.domain.aggregations;

import be.reaktika.weatherstation.action.WeatherStationToTopic;
import com.akkaserverless.javasdk.ServiceCallFactory;
import com.akkaserverless.javasdk.valueentity.CommandContext;
import com.google.protobuf.Timestamp;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class ExtremesEntityTest {
    private CommandContext context = Mockito.mock(CommandContext.class);
    private ServiceCallFactory factoryMock = Mockito.mock(ServiceCallFactory.class);
    //private ExtremesEntity entity;

    @Before
    public void init(){
        Mockito.reset(context);
        Mockito.reset(factoryMock);
        Mockito.when(context.serviceCallFactory()).thenReturn(factoryMock);
    }

    @Test
    public void registerStation(){
        var testkit = WeatherStationExtremesTestKit.of(WeatherStationExtremes::new);

        var data = WeatherStationToTopic.WeatherStationData.newBuilder()
                .setStationId("id1")
                .setStationName("name1")
                .setLongitude(2.)
                .setLongitude(2.)
                .build();
        var command = WeatherStationAggregation.AddToAggregationCommand.newBuilder()
                .setWeatherdata(data)
                .build();
        var result = testkit.registerData(command);
        assertEquals(testkit.getState().getMaxTemperature(), WeatherStationExtremesAggregation.TemperatureRecord.getDefaultInstance());
        assertEquals(testkit.getState().getMaxTemperature(), WeatherStationExtremesAggregation.TemperatureRecord.getDefaultInstance());
    }


    @Test
    public void registerTemperatureOnEmptyState(){
        var testkit = WeatherStationExtremesTestKit.of(WeatherStationExtremes::new);
        var temp1 = WeatherStationToTopic.WeatherStationTemperatures
                .newBuilder()
                .setMeasurementTime(Timestamp.newBuilder().build())
                .setTemperatureCelcius(10)
                .build();
        var temp2 = WeatherStationToTopic.WeatherStationTemperatures
                .newBuilder()
                .setMeasurementTime(Timestamp.newBuilder().build())
                .setTemperatureCelcius(5)
                .build();

        var data1 = WeatherStationToTopic.WeatherStationData.newBuilder()
                .setStationId("id1")
                .addTemperatures(temp1)
                .addTemperatures(temp2)
                .build();
        var command1 = WeatherStationAggregation.AddToAggregationCommand.newBuilder()
                .setWeatherdata(data1)
                .build();

        var result = testkit.registerData(command1);

        assertEquals(10,testkit.getState().getMaxTemperature().getCurrent().getMeasuredTemperature(),0.001);
        assertEquals(5,testkit.getState().getMinTemperature().getCurrent().getMeasuredTemperature(),0.001);
        assertEquals("id1",testkit.getState().getMinTemperature().getCurrent().getStationId());
        assertEquals("id1",testkit.getState().getMaxTemperature().getCurrent().getStationId());


    }

    @Test
    public void updateMaximumAndMinimum(){
        var testkit = WeatherStationExtremesTestKit.of(WeatherStationExtremes::new);
        var max = WeatherStationAggregation.TemperatureMeasurement.newBuilder()
                .setStationId("id1")
                .setMeasuredTemperature(30.)
                .build();
        var min = WeatherStationAggregation.TemperatureMeasurement.newBuilder()
                .setStationId("id1")
                .setMeasuredTemperature(-10)
                .build();


        var temp1 = WeatherStationToTopic.WeatherStationTemperatures
                .newBuilder()
                .setMeasurementTime(Timestamp.newBuilder().build())
                .setTemperatureCelcius(40)
                .build();
        var temp2 = WeatherStationToTopic.WeatherStationTemperatures
                .newBuilder()
                .setMeasurementTime(Timestamp.newBuilder().build())
                .setTemperatureCelcius(-20)
                .build();

        var data = WeatherStationToTopic.WeatherStationData.newBuilder()
                .setStationId("id2")
                .addTemperatures(temp1)
                .addTemperatures(temp2)
                .build();
        var command = WeatherStationAggregation.AddToAggregationCommand.newBuilder()
                .setWeatherdata(data)
                .build();

        testkit.registerData(command);

        assertEquals(40,testkit.getState().getMaxTemperature().getCurrent().getMeasuredTemperature(),0.001);
        assertEquals(-20,testkit.getState().getMinTemperature().getCurrent().getMeasuredTemperature(),0.001);
        assertEquals("id2",testkit.getState().getMaxTemperature().getCurrent().getStationId());
        assertEquals("id2",testkit.getState().getMinTemperature().getCurrent().getStationId());


    }

}
