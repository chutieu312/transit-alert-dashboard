package com.transitdemo.stops;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StopRepository extends MongoRepository<Stop, String> {
    List<Stop> findAllByRouteIdOrderBySequenceOrderAsc(String routeId);
    long countByRouteId(String routeId);
}
