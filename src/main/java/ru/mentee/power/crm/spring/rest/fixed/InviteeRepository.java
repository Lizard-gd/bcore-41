package ru.mentee.power.crm.spring.rest.fixed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InviteeRepository extends JpaRepository<Invitee, UUID> {
  boolean existsByEmail(String email);
}