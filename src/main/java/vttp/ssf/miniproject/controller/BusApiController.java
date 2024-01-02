package vttp.ssf.miniproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp.ssf.miniproject.model.BusArrival;
import vttp.ssf.miniproject.model.BusStop;
import vttp.ssf.miniproject.service.BusApiService;
import vttp.ssf.miniproject.service.BusUserService;

@RestController
@RequestMapping("/api")
public class BusApiController {

    @Autowired
    private BusApiService busApiSvc;

    @Autowired
    private BusUserService busUserSvc;

    // Fetch Bus Stop information in real-time
    @GetMapping("/bus-info/{busStopCode}")
    public List<BusArrival> getBusInfo(@PathVariable int busStopCode) {
        // Fetch and return bus information as a REST response
        return busApiSvc.getBusStopInfo(busStopCode);
    }

    // Fetch user's bookmarks
    @GetMapping("/user/{username}/bookmarks")
    public List<BusStop> getUserBookmarks(@PathVariable String username) {

        return busUserSvc.getAllBusStops(username);
    }

}