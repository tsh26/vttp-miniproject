package vttp.ssf.miniproject.model;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusStop {

    @Pattern(regexp = "\\d{5}", message = "Enter a 5-digit bus stop code.")
    private String busStopCode;

    private String roadName;

    private String description;
}
