package vttp.ssf.miniproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusStopCode {
    private int busStopCode;
    private String roadName;
    private String description;
}
