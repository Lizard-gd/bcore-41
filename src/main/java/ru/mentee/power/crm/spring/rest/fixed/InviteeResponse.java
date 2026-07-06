package ru.mentee.power.crm.spring.rest.fixed;

import java.time.LocalDateTime;
import java.util.UUID;

public record InviteeResponse(
        UUID id,
        String email,
        String firstName,
        InviteeStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}