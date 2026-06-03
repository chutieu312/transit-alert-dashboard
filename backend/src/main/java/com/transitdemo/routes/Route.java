package com.transitdemo.routes;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "routes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Route {

    @Id
    private String id;

    @Indexed(unique = true)
    private String routeNumber;    // e.g. "22", "44X"

    private String name;           // e.g. "Mission Street Express"

    private String description;

    private String type;           // BUS, METRO, TRAM, FERRY

    private boolean active;

    private Instant createdAt;
    private Instant updatedAt;
}
