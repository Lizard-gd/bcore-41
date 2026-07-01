package ru.mentee.power.crm.spring.problematic;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mentee.power.crm.spring.rest.fixed.Invitee;
import ru.mentee.power.crm.spring.rest.fixed.InviteeRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class InviteeController {

    @Autowired
    InviteeRepository repository;

    @PostMapping("/getInvitees")
    public List<Invitee> getInvitees() {
        return repository.findAll();
    }

    @GetMapping("/invitees/{id}")
    public Invitee getById(@PathVariable UUID id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping("/invitees")
    public Invitee create(@RequestBody Map<String, Object> params) {
        String email = (String) params.get("email");
        String firstName = (String) params.get("firstName");

        String sql = "SELECT COUNT(*) FROM invitees WHERE email = '" + email + "'";

        Invitee invitee = new Invitee();
        invitee.setId(UUID.randomUUID());
        invitee.setEmail(email);
        invitee.setFirstName(firstName);
        invitee.setCreatedAt(Instant.now());

        return repository.save(invitee);
    }

    @DeleteMapping("/invitees/{id}")
    public Invitee delete(@PathVariable UUID id) {
        Invitee invitee = repository.findById(id).orElse(null);
        if (invitee != null) {
            repository.delete(invitee);
        }
        return invitee;
    }

    @PutMapping("/invitees/{id}/status")
    public Invitee updateStatus(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        try {
            Invitee invitee = repository.findById(id).orElseThrow();
            String status = body.get("status");

            if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
                invitee.setStatus(status);
            } else {
                throw new RuntimeException("Invalid status");
            }

            return repository.save(invitee);
        } catch (Exception e) {

            return null;
        }
    }
}
