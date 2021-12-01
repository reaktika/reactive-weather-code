package be.reaktika.weatherstation.ports;

import be.reaktika.weatherstation.domain.aggregations.GeoCodingService;
import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class OpenCageGeoCodingService extends GeoCodingService {

    private final Logger logger = LoggerFactory.getLogger(OpenCageGeoCodingService.class);
    private final JOpenCageGeocoder geocoder;

    public OpenCageGeoCodingService(Config config){
        logger.info("creating OpenCageGeoCodingService with env " + System.getenv("OPENCAGE_API_KEY"));
        String apiKey = config.getString("reactiveweather.geocode.opencage.apikey");
        geocoder = new JOpenCageGeocoder(apiKey);
    }

    @Override
    public Optional<String> getCountryCode(double latitude, double longitude) {
        try{
            JOpenCageReverseRequest request = new JOpenCageReverseRequest(latitude, longitude);
            JOpenCageResponse response = geocoder.reverse(request);
            return Optional.ofNullable(response.getFirstComponents().getCountryCode());
        }catch (Exception e){
            logger.error("failed to getCountryCode", e);
            return Optional.empty();
        }

    }
}
