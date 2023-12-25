package vttp.ssf.miniproject.service;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.ssf.miniproject.model.BusArrival;
import vttp.ssf.miniproject.model.BusStopCode;

@Service
public class BusApiService {

    @Value("${lta.apiKey}")
    private String apiKey;

    public List<BusArrival> getBusStopInfo(int busStopCode) {

        try {
            String url = UriComponentsBuilder
                    .fromUriString("http://datamall2.mytransport.sg/ltaodataservice/BusArrivalv2")
                    .queryParam("BusStopCode", busStopCode)
                    .toUriString();

            System.out.println("******************** \n Request URL: \n ********************" + url); // Print the
                                                                                                      // request URL for
            // Create a HTTP GET request
            // GET
            // http://datamall2.mytransport.sg/ltaodataservice/BusArrivalv2?BusStopCode=27409
            RequestEntity<Void> req = RequestEntity
                    .get(url)
                    .header("AccountKey", apiKey)
                    .build();

            // Use RestTemplate to send the request and receive the response as as string
            RestTemplate template = new RestTemplate();
            ResponseEntity<String> resp = template.exchange(req, String.class);

            // Get the response body (JSON data)
            String payload = resp.getBody();
            System.out.println("******************** \n Response payload: \n ********************" + payload); // Print

            // Parse the JSON data using JsonReader
            JsonReader reader = Json.createReader(new StringReader(payload));

            // NOTE THAT THE JSON STRUCTURE IS A JSON OBJECT
            JsonObject result = reader.readObject();
            JsonArray services = result.getJsonArray("Services");

            System.out.println("******************** \n JsonArray services: \n ********************" + services);

            List<BusArrival> busArrivals = new ArrayList<>();

            // JsonValue is an abstract class, will need to cast it into a concrete
            // subclass, in this case the JsonObject.
            for (JsonValue serviceValue : services) {
                JsonObject service = (JsonObject) serviceValue;

                busArrivals.add(createBusArrival(service, "NextBus"));
                busArrivals.add(createBusArrival(service, "NextBus2"));
                busArrivals.add(createBusArrival(service, "NextBus3"));
            }

            return busArrivals;

        } catch (HttpServerErrorException e) {
            System.err.println("******************** \n API Error Response: \n ********************"
                    + e.getResponseBodyAsString());
            throw e;
        }
    }

    private BusArrival createBusArrival(JsonObject service, String busOrder) {
        String serviceNo = service.getString("ServiceNo");
        String estimatedArrivalString = service.getJsonObject(busOrder).getString("EstimatedArrival");
        String load = service.getJsonObject(busOrder).getString("Load");
        String feature = service.getJsonObject(busOrder).getString("Feature");
        String type = service.getJsonObject(busOrder).getString("Type");

        LocalDateTime estimatedArrival = null;
        if (estimatedArrivalString != null && !estimatedArrivalString.isEmpty()) {
            estimatedArrival = LocalDateTime.parse(estimatedArrivalString, DateTimeFormatter.ISO_DATE_TIME);
        }

        return new BusArrival(serviceNo, estimatedArrival, load, feature, type, busOrder);
    }

    private List<BusStopCode> codes = null;

    public List<BusStopCode> getBusStopCode() {
        // Check if the Bus Stop Codes have been fetched before
        if (codes == null) {
            // Construct the URL for API request:
            // http://datamall2.mytransport.sg/ltaodataservice/BusStops

            String url = UriComponentsBuilder
                    .fromUriString("http://datamall2.mytransport.sg/ltaodataservice/BusStops")
                    .toUriString();

            // Create an HTTP GET request
            // GET http://datamall2.mytransport.sg/ltaodataservice/BusStops
            RequestEntity<Void> req = RequestEntity
                    .get(url)
                    .header("AccountKey", apiKey)
                    .build();

            // Use RestTemplate to send the request and receives the response as a string
            RestTemplate template = new RestTemplate();
            ResponseEntity<String> resp = template.exchange(req, String.class);

            // Get the response body (JSON data)
            String payload = resp.getBody();

            // Parse the JSON data using JsonReader
            JsonReader reader = Json.createReader(new StringReader(payload));

            // NOTE THAT THE JSON STRUCTURE IS A JSON OBJECT
            JsonObject result = reader.readObject();
            JsonArray arr = result.getJsonArray("value");

            // Map each JSON object to a BusStopCode object
            codes = arr.stream()
                    .map(j -> j.asJsonObject()) // Maps each JSON object in the stream to a JSON object
                    .map(o -> {
                        // Extract information from the JSON object
                        String busStopCode = o.getString("BusStopCode");
                        // int busStopCode = Integer.valueOf(o.getString("BusStopCode"));
                        String roadName = o.getString("RoadName", "No road name");
                        String description = o.getString("Description", "No description");

                        return new BusStopCode(busStopCode, roadName, description);
                    })
                    .toList();
        }
        // busUserSvc.setBusStopCodes(codes);

        return codes;
    }

}
