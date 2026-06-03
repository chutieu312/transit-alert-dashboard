package com.transitdemo.routes;

import com.transitdemo.common.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;

    public Page<Route> findAll(Pageable pageable) {
        return routeRepository.findAll(pageable);
    }

    public List<Route> findAllActive() {
        return routeRepository.findAllByActiveTrue();
    }

    public Route findById(String id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Route not found: " + id));
    }

    public Route create(RouteRequest req) {
        Route route = Route.builder()
                .routeNumber(req.routeNumber())
                .name(req.name())
                .description(req.description())
                .type(req.type())
                .active(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        return routeRepository.save(route);
    }

    public Route update(String id, RouteRequest req) {
        Route route = findById(id);
        route.setRouteNumber(req.routeNumber());
        route.setName(req.name());
        route.setDescription(req.description());
        route.setType(req.type());
        route.setUpdatedAt(Instant.now());
        return routeRepository.save(route);
    }

    public void delete(String id) {
        Route route = findById(id);
        routeRepository.delete(route);
    }
}
