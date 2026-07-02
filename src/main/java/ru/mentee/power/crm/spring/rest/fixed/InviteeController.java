package ru.mentee.power.crm.spring.rest.fixed;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/invitees")
public class InviteeController {

  private final InviteeService inviteeService;

  public InviteeController(InviteeService inviteeService) {
    this.inviteeService = inviteeService;
  }

  @GetMapping
  public ResponseEntity<Page<InviteeResponse>> getAll(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "20") int size) {
    return ResponseEntity.ok(inviteeService.getAll(page, size));
  }

  @GetMapping("/{id}")
  public ResponseEntity<InviteeResponse> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(inviteeService.getById(id));
  }

  @PostMapping
  public ResponseEntity<InviteeResponse> create(@Valid @RequestBody CreateInviteeRequest request) {
    InviteeResponse created = inviteeService.create(request);
    URI location = URI.create("/api/invitees/" + created.id());
    return ResponseEntity.status(HttpStatus.CREATED)
            .header(HttpHeaders.LOCATION, location.toString())
            .body(created);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    inviteeService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/status")
  public ResponseEntity<InviteeResponse> updateStatus(
          @PathVariable UUID id,
          @Valid @RequestBody UpdateInviteeStatusRequest request) {
    InviteeResponse updated = inviteeService.updateStatus(id, request.getStatus());
    return ResponseEntity.ok(updated);
  }
}