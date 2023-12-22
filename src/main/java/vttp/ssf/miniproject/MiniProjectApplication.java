package vttp.ssf.miniproject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.ssf.miniproject.model.BusArrival;
import vttp.ssf.miniproject.model.BusStopCode;
import vttp.ssf.miniproject.service.BusApiService;

@SpringBootApplication
public class MiniProjectApplication implements CommandLineRunner {

@Autowired
private BusApiService busApiSvc;

	public static void main(String[] args) {
		SpringApplication.run(MiniProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		List<BusStopCode> codes = busApiSvc.getBusStopCode();
		System.out.println(">>>>>>>>>> Bus Stop Code: " + codes);

		int busStopCode = 27409;

        // Get bus arrivals for the specified busStopCode
        List<BusArrival> busArrivals = busApiSvc.getBusStopInfo(busStopCode);

        // Print the contents of busArrivals
        System.out.println("******************** \n Bus Arrivals for Bus Stop Code " + busStopCode + ": \n ********************");

		// System.out.println(busArrivals);

        for (BusArrival busArrival : busArrivals) {
            System.out.println(busArrival);
        }
    }
}