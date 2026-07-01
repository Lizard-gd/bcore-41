package ru.mentee.power.crm.spring.rest.fixed;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@ConditionalOnProperty(name = "invitee.enabled", havingValue = "true")
@Repository
public interface InviteeRepository extends JpaRepository<Invitee, UUID> {
  boolean existsByEmail(String email);
}
