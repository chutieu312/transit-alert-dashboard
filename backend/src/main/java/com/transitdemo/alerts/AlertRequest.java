package com.transitdemo.alerts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlertRequest(
        @NotBlank String title,
        String description,
        @NotNull AlertSeverity severity,
        @NotBlank String affectedRouteId
) {}
