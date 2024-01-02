package vttp.ssf.miniproject.model;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    
    // private String userId = UUID.randomUUID().toString();
    
    @NotEmpty(message = "Please enter your username.")
    @Size(min = 3, max = 20, message = "Min 3 characters, Max 20 characters.")
    private String username;
}
