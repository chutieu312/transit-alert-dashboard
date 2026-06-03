package com.transitdemo.common;

import com.transitdemo.alerts.*;
import com.transitdemo.routes.Route;
import com.transitdemo.routes.RouteRepository;
import com.transitdemo.stops.Stop;
import com.transitdemo.stops.StopRepository;
import com.transitdemo.users.User;
import com.transitdemo.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

/**
 * Seeds demo data on first startup if the database is empty.
 * Safe to run multiple times — checks for existing data before inserting.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final StopRepository stopRepository;
    private final AlertRepository alertRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void seed() {
        if (userRepository.count() > 0) {
            log.info("DataSeeder: data already exists, skipping.");
            return;
        }
        log.info("DataSeeder: seeding demo data...");
        seedUsers();
        List<Route> routes = seedRoutes();
        seedStops(routes);
        seedAlerts(routes);
        log.info("DataSeeder: done. Login as operator@transit.demo / demo1234");
    }

    private void seedUsers() {
        userRepository.saveAll(List.of(
                User.builder()
                        .email("admin@transit.demo")
                        .password(passwordEncoder.encode("demo1234"))
                        .fullName("Admin User")
                        .role(User.Role.ADMIN)
                        .createdAt(Instant.now())
                        .build(),
                User.builder()
                        .email("operator@transit.demo")
                        .password(passwordEncoder.encode("demo1234"))
                        .fullName("Jane Operator")
                        .role(User.Role.OPERATOR)
                        .createdAt(Instant.now())
                        .build(),
                User.builder()
                        .email("viewer@transit.demo")
                        .password(passwordEncoder.encode("demo1234"))
                        .fullName("Bob Viewer")
                        .role(User.Role.VIEWER)
                        .createdAt(Instant.now())
                        .build()
        ));
    }

    private List<Route> seedRoutes() {
        return routeRepository.saveAll(List.of(
                Route.builder().routeNumber("22").name("Mission Street").type("BUS")
                        .description("Mission District to Downtown via Mission St")
                        .active(true).createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                Route.builder().routeNumber("38").name("Geary Boulevard").type("BUS")
                        .description("Ocean Beach to Downtown via Geary Blvd")
                        .active(true).createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                Route.builder().routeNumber("N").name("Judah Metro").type("METRO")
                        .description("Ocean Beach to Caltrain via Market St Tunnel")
                        .active(true).createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                Route.builder().routeNumber("1").name("California Street").type("BUS")
                        .description("Ferry Building to 33rd Ave via California St")
                        .active(true).createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                Route.builder().routeNumber("14").name("Mission Express").type("BUS")
                        .description("Daly City to Downtown Express")
                        .active(false).createdAt(Instant.now()).updatedAt(Instant.now()).build()
        ));
    }

    private void seedStops(List<Route> routes) {
        Route route22 = routes.get(0);
        Route routeN  = routes.get(2);

        stopRepository.saveAll(List.of(
                Stop.builder().routeId(route22.getId()).stopCode("MSN-01")
                        .name("Mission St & 24th St").latitude(37.7525).longitude(-122.4183)
                        .sequenceOrder(1).createdAt(Instant.now()).build(),
                Stop.builder().routeId(route22.getId()).stopCode("MSN-02")
                        .name("Mission St & 16th St").latitude(37.7647).longitude(-122.4194)
                        .sequenceOrder(2).createdAt(Instant.now()).build(),
                Stop.builder().routeId(route22.getId()).stopCode("MSN-03")
                        .name("Mission St & Market St").latitude(37.7749).longitude(-122.4194)
                        .sequenceOrder(3).createdAt(Instant.now()).build(),
                Stop.builder().routeId(routeN.getId()).stopCode("N-01")
                        .name("Ocean Beach").latitude(37.7749).longitude(-122.5094)
                        .sequenceOrder(1).createdAt(Instant.now()).build(),
                Stop.builder().routeId(routeN.getId()).stopCode("N-02")
                        .name("Irving & 9th Ave").latitude(37.7648).longitude(-122.4671)
                        .sequenceOrder(2).createdAt(Instant.now()).build(),
                Stop.builder().routeId(routeN.getId()).stopCode("N-03")
                        .name("Church & Market").latitude(37.7647).longitude(-122.4286)
                        .sequenceOrder(3).createdAt(Instant.now()).build()
        ));
    }

    private void seedAlerts(List<Route> routes) {
        Route route22 = routes.get(0);
        Route route38 = routes.get(1);
        Route routeN  = routes.get(2);

        alertRepository.saveAll(List.of(
                Alert.builder()
                        .title("Signal failure at Mission & 16th")
                        .description("Traffic signal malfunction causing 10–15 min delays on Route 22 southbound.")
                        .severity(AlertSeverity.HIGH)
                        .status(AlertStatus.ACTIVE)
                        .affectedRouteId(route22.getId())
                        .affectedRouteName(route22.getName())
                        .createdByEmail("operator@transit.demo")
                        .createdAt(Instant.now().minusSeconds(3600))
                        .build(),
                Alert.builder()
                        .title("Detour via Van Ness due to street fair")
                        .description("Route 38 rerouted via Van Ness Ave between Turk and Ellis. Expect 5 min delay.")
                        .severity(AlertSeverity.MEDIUM)
                        .status(AlertStatus.ACTIVE)
                        .affectedRouteId(route38.getId())
                        .affectedRouteName(route38.getName())
                        .createdByEmail("operator@transit.demo")
                        .createdAt(Instant.now().minusSeconds(7200))
                        .build(),
                Alert.builder()
                        .title("Track maintenance between Church and Castro")
                        .description("N Judah single-tracking between Church St and Castro St station. 15 min delays expected.")
                        .severity(AlertSeverity.HIGH)
                        .status(AlertStatus.ACTIVE)
                        .affectedRouteId(routeN.getId())
                        .affectedRouteName(routeN.getName())
                        .createdByEmail("admin@transit.demo")
                        .createdAt(Instant.now().minusSeconds(1800))
                        .build(),
                Alert.builder()
                        .title("Overhead wire repair — resolved")
                        .description("Overhead wire repair at Market & Castro completed. Service restored.")
                        .severity(AlertSeverity.HIGH)
                        .status(AlertStatus.RESOLVED)
                        .affectedRouteId(routeN.getId())
                        .affectedRouteName(routeN.getName())
                        .createdByEmail("operator@transit.demo")
                        .createdAt(Instant.now().minusSeconds(86400))
                        .resolvedAt(Instant.now().minusSeconds(79200))
                        .build()
        ));
    }
}
