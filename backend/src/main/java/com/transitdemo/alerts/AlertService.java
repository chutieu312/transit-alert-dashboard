package com.transitdemo.alerts;

import com.transitdemo.common.NotFoundException;
import com.transitdemo.routes.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;
    private final RouteRepository routeRepository;

    public Page<Alert> findAll(AlertStatus status, String routeId, Pageable pageable) {
        if (status != null && routeId != null) {
            return alertRepository.findAllByStatusAndAffectedRouteId(status, routeId, pageable);
        }
        if (status != null) {
            return alertRepository.findAllByStatus(status, pageable);
        }
        if (routeId != null) {
            return alertRepository.findAllByAffectedRouteId(routeId, pageable);
        }
        return alertRepository.findAll(pageable);
    }

    public Alert findById(String id) {
        return alertRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Alert not found: " + id));
    }

    public Alert create(AlertRequest req) {
        String routeName = routeRepository.findById(req.affectedRouteId())
                .map(r -> r.getName())
                .orElseThrow(() -> new NotFoundException("Route not found: " + req.affectedRouteId()));

        String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();

        Alert alert = Alert.builder()
                .title(req.title())
                .description(req.description())
                .severity(req.severity())
                .status(AlertStatus.ACTIVE)
                .affectedRouteId(req.affectedRouteId())
                .affectedRouteName(routeName)
                .createdByEmail(createdBy)
                .createdAt(Instant.now())
                .build();
        return alertRepository.save(alert);
    }

    public Alert resolve(String id) {
        Alert alert = findById(id);
        if (alert.getStatus() == AlertStatus.RESOLVED) {
            return alert; // idempotent
        }
        alert.setStatus(AlertStatus.RESOLVED);
        alert.setResolvedAt(Instant.now());
        return alertRepository.save(alert);
    }

    public void delete(String id) {
        Alert alert = findById(id);
        alertRepository.delete(alert);
    }

    public Map<String, Long> summary() {
        return Map.of(
                "ACTIVE", alertRepository.countByStatus(AlertStatus.ACTIVE),
                "RESOLVED", alertRepository.countByStatus(AlertStatus.RESOLVED)
        );
    }
}
