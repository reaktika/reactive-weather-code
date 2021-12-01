package be.reaktika.weatherstation.domain.aggregations;


import com.akkaserverless.javasdk.ServiceCallFactory;
import com.akkaserverless.javasdk.ServiceCallRef;
import com.akkaserverless.javasdk.valueentity.CommandContext;
import org.junit.Before;
import org.mockito.Mockito;

import java.util.Optional;

public class GeoCodingEntityTest {

    private CommandContext context = Mockito.mock(CommandContext.class);
    private ServiceCallFactory factoryMock = Mockito.mock(ServiceCallFactory.class);
    //private ServiceCallRef<WeatherstationGeocoding.CountryMeasurements> measurementsPublisher = Mockito.mock(ServiceCallRef.class);
    //private GeoCodingEntity entity;
/*

    @Before
    public void init(){
        Mockito.reset(context);
        Mockito.reset(factoryMock);
        Mockito.reset(measurementsPublisher);
        Mockito.when(context.serviceCallFactory()).thenReturn(factoryMock);
        Mockito.when(factoryMock.lookup(Mockito.anyString(),Mockito.anyString(), Mockito.eq(WeatherstationGeocoding.CountryMeasurements.class)))
                .thenReturn(measurementsPublisher);

        GeoCodingService.setInstance(new GeoCodingService() {
            @Override
            public Optional<String> getCountryCode(double latitude, double longitude) {
                return Optional.of("countrycode");
            }
        });
    }

 */
/*
    @Test
    public void stationRegistrationShouldGeoCodeLocation(){
        entity = new GeoCodingEntity(WeatherStationAggregation.AggregationType.COUNTRY.name(),context);
        var data = WeatherStationPublish.WeatherStationData.newBuilder()
                .setStationId("id1")
                .setStationName("name1")
                .setLongitude(2.)
                .setLongitude(2.)
                .build();
        var command = WeatherStationAggregation.AddToAggregationCommand.newBuilder()
                .setWeatherdata(data)
                .build();

        entity.registerData(command,context);

        var argumentCaptor = ArgumentCaptor.forClass(WeatherstationGeocoding.GeoCodingState.class);
        Mockito.verify(context).updateState(argumentCaptor.capture());

        Assert.assertEquals("countrycode",argumentCaptor.getValue().getStationIdToCountryOrThrow("id1"));
    }

    @Test
    public void temperatureRegistration(){
        entity = new GeoCodingEntity(WeatherStationAggregation.AggregationType.COUNTRY.name(),context);
        var temperatures = WeatherStationPublish.WeatherStationTemperatures
                .newBuilder()
                .setMeasurementTime(Timestamp.newBuilder().build())
                .setTemperatureCelcius(10)
                .build();

        var data = WeatherStationPublish.WeatherStationData.newBuilder()
                .setStationId("id1")
                .addTemperatures(temperatures)
                .build();
        var command = WeatherStationAggregation.AddToAggregationCommand.newBuilder()
                .setWeatherdata(data)
                .build();
        var currentState = WeatherstationGeocoding.GeoCodingState.newBuilder()
                .putStationIdToCountry("id1","be")
                .build();
        Mockito.when(context.getState()).thenReturn(Optional.of(currentState));
        entity.registerData(command,context);

        var argumentCaptor = ArgumentCaptor.forClass(WeatherstationGeocoding.CountryMeasurements.class);
        Mockito.verify(measurementsPublisher).createCall(argumentCaptor.capture());

        Assert.assertEquals(10,argumentCaptor.getValue().getTemperaturesList().get(0).getMeasuredTemperature(),0.001);
        Assert.assertEquals("id1",argumentCaptor.getValue().getTemperaturesList().get(0).getStationId());
        Assert.assertEquals("be",argumentCaptor.getValue().getCountry());

    }

 */

}
