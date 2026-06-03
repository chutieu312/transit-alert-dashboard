package com.transitdemo.routes;

import com.transitdemo.common.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RouteServiceTest {

    @Mock private RouteRepository routeRepository;
    @InjectMocks private RouteService routeService;

    private Route route22;

    @BeforeEach
    void setUp() {
        route22 = Route.builder()
                .id("route-1")
                .routeNumber("22")
                .name("Mission Street")
                .type("BUS")
                .active(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    @Test
    void findById_returnsRoute() {
        when(routeRepository.findById("route-1")).thenReturn(Optional.of(route22));
        Route found = routeService.findById("route-1");
        assertThat(found.getRouteNumber()).isEqualTo("22");
    }

    @Test
    void findById_throwsNotFound_whenMissing() {
        when(routeRepository.findById("bad-id")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> routeService.findById("bad-id"))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Route not found");
    }

    @Test
    void create_setsActiveTrue_andSaves() {
        when(routeRepository.save(any(Route.class))).thenAnswer(inv -> inv.getArgument(0));
        RouteRequest req = new RouteRequest("44", "Haight Street", "Haight to Downtown", "BUS");

        Route created = routeService.create(req);

        assertThat(created.isActive()).isTrue();
        assertThat(created.getRouteNumber()).isEqualTo("44");
        verify(routeRepository).save(any(Route.class));
    }

    @Test
    void findAllActive_delegatesToRepository() {
        when(routeRepository.findAllByActiveTrue()).thenReturn(List.of(route22));
        List<Route> actives = routeService.findAllActive();
        assertThat(actives).hasSize(1);
        verify(routeRepository).findAllByActiveTrue();
    }

    @Test
    void delete_removesRoute() {
        when(routeRepository.findById("route-1")).thenReturn(Optional.of(route22));
        routeService.delete("route-1");
        verify(routeRepository).delete(route22);
    }
}
