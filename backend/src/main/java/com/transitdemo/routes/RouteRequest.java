package com.transitdemo.routes;

import jakarta.validation.constraints.NotBlank;

public record RouteRequest(
        @NotBlank String routeNumber,
        @NotBlank String name,
        String description,
        @NotBlank String type   // BUS, METRO, TRAM, FERRY
) {}
