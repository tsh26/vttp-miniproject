package vttp.ssf.miniproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp.ssf.miniproject.model.BusArrival;
import vttp.ssf.miniproject.service.BusApiService;

@RestController
@RequestMapping("/api")
public class BusApiController {

    @Autowired
    private BusApiService busApiSvc;

    @GetMapping("/bus-info/{busStopCode}")
    public List<BusArrival> getBusInfo(@PathVariable int busStopCode) {
        // Fetch and return bus information as a REST response
        return busApiSvc.getBusStopInfo(busStopCode);
    }
}