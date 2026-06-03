package com.transitdemo.routes;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends MongoRepository<Route, String> {
    Optional<Route> findByRouteNumber(String routeNumber);
    List<Route> findAllByActiveTrue();
    boolean existsByRouteNumber(String routeNumber);
}
