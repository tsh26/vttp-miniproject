package vttp.ssf.miniproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusArrival {
    private String serviceNo;
    private String estimatedArrival;
    private String load;
    private String feature;
    private String type;

    @Override
    public String toString() {
        return "BusArrival{" +
                "serviceNo='" + serviceNo + '\'' +
                ", estimatedArrival='" + estimatedArrival + '\'' +
                ", load='" + load + '\'' +
                ", feature='" + feature + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
