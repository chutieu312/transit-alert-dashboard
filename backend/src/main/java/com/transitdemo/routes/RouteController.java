package com.transitdemo.routes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping
    public Page<Route> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return routeService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/active")
    public List<Route> listActive() {
        return routeService.findAllActive();
    }

    @GetMapping("/{id}")
    public Route getById(@PathVariable String id) {
        return routeService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Route> create(@Valid @RequestBody RouteRequest req) {
        Route created = routeService.create(req);
        return ResponseEntity.created(URI.create("/api/routes/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public Route update(@PathVariable String id, @Valid @RequestBody RouteRequest req) {
        return routeService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        routeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
