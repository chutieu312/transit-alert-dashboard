package com.transitdemo.stops;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routes/{routeId}/stops")
@RequiredArgsConstructor
public class StopController {

    private final StopRepository stopRepository;

    @GetMapping
    public List<Stop> list(@PathVariable String routeId) {
        return stopRepository.findAllByRouteIdOrderBySequenceOrderAsc(routeId);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(@PathVariable String routeId) {
        return ResponseEntity.ok(stopRepository.countByRouteId(routeId));
    }
}
