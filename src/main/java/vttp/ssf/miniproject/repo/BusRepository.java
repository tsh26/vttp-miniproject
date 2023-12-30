package vttp.ssf.miniproject.repo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vttp.ssf.miniproject.model.BusStop;

@Repository
public class BusRepository {

    @Autowired
    @Qualifier("busCache") // from RedisConfig @Bean
    private RedisTemplate<String, String> template;

    // Add a BusStop object to the bookmark for the specified user.
    public void addBusStop(String username, BusStop busStop) {
        HashOperations<String, String, String> hashOperations = template.opsForHash();
        String busStopCode = busStop.getBusStopCode();
        String busStopJson = convertBusStopToJson(busStop);
        hashOperations.put(getUserKey(username), busStopCode, busStopJson);
    }

    // Removes a bus stop from the bookmark for the specified user.
    public void removeBusStop(String username, String busStopCode) {
        HashOperations<String, String, String> hashOperations = template.opsForHash();
        hashOperations.delete(getUserKey(username), busStopCode);
    }

    // Returns the entire bookmark for the specified user.
    public List<BusStop> getAllBusStops(String username) {
        HashOperations<String, String, String> hashOperations = template.opsForHash();
        Map<String, String> busStopsMap = hashOperations.entries(getUserKey(username));

        return busStopsMap.values().stream()
                .map(this::convertJsonToBusStop)
                .collect(Collectors.toList());
    }

    private String getUserKey(String username) {
        return "user:" + username;
    }

    // Conversion of BusStop object to JSON string.
    private String convertBusStopToJson(BusStop busStop) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(busStop);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting BusStop to JSON", e);
        }
    }

    // Conversion of JSON string back to BusStop object.
    private BusStop convertJsonToBusStop(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, BusStop.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to BusStop", e);
        }
    }
}