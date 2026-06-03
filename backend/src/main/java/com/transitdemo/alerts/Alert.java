package com.transitdemo.alerts;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "alerts")
@CompoundIndexes({
    @CompoundIndex(name = "status_route_created_idx",
                   def = "{'status': 1, 'affectedRouteId': 1, 'createdAt': -1}")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Alert {

    @Id
    private String id;

    private String title;

    private String description;

    private AlertSeverity severity;   // LOW, MEDIUM, HIGH

    private AlertStatus status;       // ACTIVE, RESOLVED

    private String affectedRouteId;   // FK to routes collection

    private String affectedRouteName; // denormalized for display speed

    private String createdByEmail;

    private Instant createdAt;

    private Instant resolvedAt;
}
