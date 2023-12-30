package vttp.ssf.miniproject.model;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    
    // private String userId = UUID.randomUUID().toString();
    
    @NotEmpty(message = "Please enter your username")
    private String username;
}
