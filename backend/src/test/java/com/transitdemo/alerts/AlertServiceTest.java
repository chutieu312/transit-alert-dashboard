package com.transitdemo.alerts;

import com.transitdemo.common.NotFoundException;
import com.transitdemo.routes.Route;
import com.transitdemo.routes.RouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlertServiceTest {

    @Mock private AlertRepository alertRepository;
    @Mock private RouteRepository routeRepository;
    @InjectMocks private AlertService alertService;

    private Route route;
    private Alert activeAlert;

    @BeforeEach
    void setUp() {
        route = Route.builder()
                .id("route-1")
                .routeNumber("22")
                .name("Mission Street")
                .type("BUS")
                .active(true)
                .build();

        activeAlert = Alert.builder()
                .id("alert-1")
                .title("Signal failure")
                .severity(AlertSeverity.HIGH)
                .status(AlertStatus.ACTIVE)
                .affectedRouteId("route-1")
                .affectedRouteName("Mission Street")
                .createdByEmail("operator@transit.demo")
                .createdAt(Instant.now())
                .build();

        // Set security context so AlertService can call getAuthentication().getName()
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("operator@transit.demo", null, List.of()));
    }

    @Test
    void create_savesAlertWithActiveStatus() {
        when(routeRepository.findById("route-1")).thenReturn(Optional.of(route));
        when(alertRepository.save(any(Alert.class))).thenAnswer(inv -> inv.getArgument(0));

        AlertRequest req = new AlertRequest("Signal failure", "Delay on Mission", AlertSeverity.HIGH, "route-1");
        Alert saved = alertService.create(req);

        assertThat(saved.getStatus()).isEqualTo(AlertStatus.ACTIVE);
        assertThat(saved.getAffectedRouteName()).isEqualTo("Mission Street");
        assertThat(saved.getCreatedByEmail()).isEqualTo("operator@transit.demo");
        verify(alertRepository).save(any(Alert.class));
    }

    @Test
    void create_throwsNotFound_whenRouteDoesNotExist() {
        when(routeRepository.findById("bad-id")).thenReturn(Optional.empty());
        AlertRequest req = new AlertRequest("title", null, AlertSeverity.LOW, "bad-id");

        assertThatThrownBy(() -> alertService.create(req))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void resolve_setsStatusToResolvedAndSetsResolvedAt() {
        when(alertRepository.findById("alert-1")).thenReturn(Optional.of(activeAlert));
        when(alertRepository.save(any(Alert.class))).thenAnswer(inv -> inv.getArgument(0));

        Alert resolved = alertService.resolve("alert-1");

        assertThat(resolved.getStatus()).isEqualTo(AlertStatus.RESOLVED);
        assertThat(resolved.getResolvedAt()).isNotNull();
    }

    @Test
    void resolve_isIdempotent_whenAlreadyResolved() {
        activeAlert.setStatus(AlertStatus.RESOLVED);
        when(alertRepository.findById("alert-1")).thenReturn(Optional.of(activeAlert));

        Alert result = alertService.resolve("alert-1");
        assertThat(result.getStatus()).isEqualTo(AlertStatus.RESOLVED);
        verify(alertRepository, never()).save(any());
    }

    @Test
    void summary_returnsCounts() {
        when(alertRepository.countByStatus(AlertStatus.ACTIVE)).thenReturn(3L);
        when(alertRepository.countByStatus(AlertStatus.RESOLVED)).thenReturn(7L);

        Map<String, Long> summary = alertService.summary();

        assertThat(summary.get("ACTIVE")).isEqualTo(3L);
        assertThat(summary.get("RESOLVED")).isEqualTo(7L);
    }

    @Test
    void findAll_withStatusFilter_callsCorrectRepository() {
        Page<Alert> mockPage = new PageImpl<>(List.of(activeAlert));
        when(alertRepository.findAllByStatus(eq(AlertStatus.ACTIVE), any())).thenReturn(mockPage);

        Page<Alert> result = alertService.findAll(AlertStatus.ACTIVE, null, PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        verify(alertRepository).findAllByStatus(eq(AlertStatus.ACTIVE), any());
    }
}
