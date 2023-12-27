package vttp.ssf.miniproject.service;

import java.util.ArrayList;
import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.ssf.miniproject.model.BusStopCode;
// import vttp.ssf.miniproject.repo.BusRepository;

@Service
public class BusUserService {
    
    // @Autowired
    // private BusRepository busRepo;

    List<BusStopCode> busStopBookmark = new ArrayList<>();

    // public BusUserService() {
    //     if (busStopBookmark == null) {
    //         busStopBookmark = new ArrayList<BusStopCode>();
    //     }
    // }

    // adds a BusStopCode object to the bookmark list.
    public void addBusStop(BusStopCode busStops) {
        busStopBookmark.add(busStops);

        System.out.println(busStopBookmark);
    }

    // removes a bus stop from the bookmark list based on the provided bus stop code.
    public void removeBusStop(String busStopCode) {
        busStopBookmark.removeIf(busStops -> busStops.getBusStopCode().equals(busStopCode));
    }

    // returns the entire bookmark list.
    public List<BusStopCode> getAllBusStops() {
        return busStopBookmark;
    }
    
}
