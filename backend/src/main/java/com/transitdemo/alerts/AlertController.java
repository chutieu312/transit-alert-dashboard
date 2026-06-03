package com.transitdemo.alerts;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    public Page<Alert> list(
            @RequestParam(required = false) AlertStatus status,
            @RequestParam(required = false) String routeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return alertService.findAll(status, routeId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
    }

    @GetMapping("/summary")
    public Map<String, Long> summary() {
        return alertService.summary();
    }

    @GetMapping("/{id}")
    public Alert getById(@PathVariable String id) {
        return alertService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Alert> create(@Valid @RequestBody AlertRequest req) {
        Alert created = alertService.create(req);
        return ResponseEntity.created(URI.create("/api/alerts/" + created.getId())).body(created);
    }

    @PatchMapping("/{id}/resolve")
    public Alert resolve(@PathVariable String id) {
        return alertService.resolve(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        alertService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
