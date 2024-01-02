package vttp.ssf.miniproject.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusArrival {
    private String serviceNo;
    private LocalDateTime estimatedArrival;
    private String load;
    private String feature;
    private String type;
    private String busOrder;
    private Long minutesUntilArrival;

    // Constructor includes method to calculate minutes until arrival.
    public BusArrival(String serviceNo, LocalDateTime estimatedArrival, String load, String feature, String type, String busOrder) {
        this.serviceNo = serviceNo;
        this.estimatedArrival = estimatedArrival;
        this.load = load;
        this.feature = feature;
        this.type = type;
        this.busOrder = busOrder;
        calculateMinutesUntilArrival();
    }

    // Method to calculate minutes until arrival
    public void calculateMinutesUntilArrival() {
        if (estimatedArrival != null) {
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Singapore"));
            minutesUntilArrival = ChronoUnit.MINUTES.between(now, estimatedArrival);
        } else {
            minutesUntilArrival = null; // Handle the case when estimatedArrival is null
        }
    }

    @Override
    public String toString() {
        return "BusArrival{" +
                "serviceNo='" + serviceNo + '\'' +
                ", estimatedArrival='" + estimatedArrival + '\'' +
                ", load='" + load + '\'' +
                ", feature='" + feature + '\'' +
                ", type='" + type + '\'' +
                ", minutesUntilArrival=" + minutesUntilArrival +
                '}';
    }
}
