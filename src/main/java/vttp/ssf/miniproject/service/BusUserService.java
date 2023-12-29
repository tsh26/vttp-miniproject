package vttp.ssf.miniproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import vttp.ssf.miniproject.model.BusStop;

@Service
public class BusUserService {
    
    List<BusStop> busStopBookmark = new ArrayList<>();

    // Adds a BusStop object to the bookmark list.
    public void addBusStop(BusStop busStops) {
        busStopBookmark.add(busStops);

        System.out.println(busStopBookmark);
    }

    // Removes a bus stop from the bookmark list based on the provided bus stop code.
    public void removeBusStop(String busStopCode) {
        busStopBookmark.removeIf(busStops -> busStops.getBusStopCode().equals(busStopCode));
    }

    // Returns the entire bookmark list.
    public List<BusStop> getAllBusStops() {
        return busStopBookmark;
    }
    
}
