package vttp.ssf.miniproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.ssf.miniproject.model.BusStop;
import vttp.ssf.miniproject.repo.BusRepository;

@Service
public class BusUserService {

    @Autowired
    private final BusRepository busRepo;

    public BusUserService(BusRepository busRepo) {
        this.busRepo = busRepo;
    }

    // Adds a BusStop object to the bookmark for the specified user.
    public void addBusStop(String username, BusStop busStop) {
        busRepo.addBusStop(username, busStop);
    }

    // Removes a bus stop from the bookmark based on the provided bus stop code for the specified user.
    public void removeBusStop(String username, String busStopCode) {
        busRepo.removeBusStop(username, busStopCode);
    }

    // Returns the entire bookmark list for the specified user.
    public List<BusStop> getAllBusStops(String username) {
        return busRepo.getAllBusStops(username);
    }
}