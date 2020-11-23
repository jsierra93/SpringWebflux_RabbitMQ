package co.com.jsierra.webfluxrabbitmq.health;

import lombok.*;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Status {
    private String status;
}
