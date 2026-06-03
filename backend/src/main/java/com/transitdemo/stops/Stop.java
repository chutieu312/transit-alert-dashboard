package com.transitdemo.stops;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "stops")
@CompoundIndex(name = "route_stop_idx", def = "{'routeId': 1, 'stopCode': 1}")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Stop {

    @Id
    private String id;

    private String routeId;

    private String stopCode;       // e.g. "MKT-02"

    private String name;           // e.g. "Market St & 4th St"

    private double latitude;
    private double longitude;

    private int sequenceOrder;     // position along the route

    private Instant createdAt;
}
