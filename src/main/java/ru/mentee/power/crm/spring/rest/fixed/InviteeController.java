package ru.mentee.power.crm.spring.rest.fixed;

import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@ConditionalOnProperty(name = "invitee.enabled", havingValue = "true")
@RestController
@RequestMapping("/api/invitees")
public class InviteeController {

  private final InviteeService inviteeService;

  public InviteeController(InviteeService inviteeService) {
    this.inviteeService = inviteeService;
  }

  @GetMapping
  public ResponseEntity<Page<InviteeResponse>> getAll(
      @PageableDefault(size = 20) Pageable pageable) {
    Page<InviteeResponse> page = inviteeService.getAll(pageable);
    return ResponseEntity.ok(page);
  }

  @GetMapping("/{id}")
  public ResponseEntity<InviteeResponse> getById(@PathVariable UUID id) {
    InviteeResponse response = inviteeService.getById(id);
    return ResponseEntity.ok(response);
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
      @PathVariable UUID id, @Valid @RequestBody UpdateInviteeStatusRequest request) {
    InviteeResponse updated = inviteeService.updateStatus(id, request.getStatus());
    return ResponseEntity.ok(updated);
  }
}
