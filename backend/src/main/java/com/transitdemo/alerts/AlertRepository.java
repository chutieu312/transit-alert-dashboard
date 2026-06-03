package com.transitdemo.alerts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AlertRepository extends MongoRepository<Alert, String> {

    Page<Alert> findAllByStatus(AlertStatus status, Pageable pageable);

    Page<Alert> findAllByAffectedRouteId(String routeId, Pageable pageable);

    Page<Alert> findAllByStatusAndAffectedRouteId(AlertStatus status, String routeId, Pageable pageable);

    long countByStatus(AlertStatus status);

    List<Alert> findTop5ByStatusOrderByCreatedAtDesc(AlertStatus status);
}
