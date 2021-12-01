package be.reaktika.weatherstation.domain.aggregations;

import java.util.Optional;

public abstract class GeoCodingService {

    private static GeoCodingService instance;

    public static GeoCodingService getInstance() {
        return instance;
    }

    public static void setInstance(GeoCodingService instance) {
        GeoCodingService.instance = instance;
    }

    /**
     *
     * @param latitude
     * @param longitude
     * @return the countrycode of the country of the given location
     */
    public abstract Optional<String> getCountryCode(double latitude, double longitude);
}
