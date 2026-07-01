package ru.mentee.power.crm.spring.rest.fixed;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mentee.power.crm.spring.exception.EntityNotFoundException;
import ru.mentee.power.crm.spring.exception.EmailAlreadyExistsException;
import ru.mentee.power.crm.spring.exception.InvalidStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InviteeService {

  private final InviteeRepository repository;
  private final InviteeMapper mapper;

  public Page<InviteeResponse> getAll(Pageable pageable) {
    return repository.findAll(pageable).map(mapper::toResponse);
  }

  public InviteeResponse getById(UUID id) {
    Invitee invitee =
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Invitee not found with id: " + id));
    return mapper.toResponse(invitee);
  }

  public InviteeResponse create(CreateInviteeRequest request) {
    if (repository.existsByEmail(request.getEmail())) {
      throw new EmailAlreadyExistsException("Email already exists: " + request.getEmail());
    }

    Invitee invitee = mapper.toEntity(request);
    invitee.setStatus("NEW");

    Invitee saved = repository.save(invitee);
    return mapper.toResponse(saved);
  }

  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("Invitee not found with id: " + id);
    }
    repository.deleteById(id);
  }

  public InviteeResponse updateStatus(UUID id, String status) {
    Invitee invitee =
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Invitee not found with id: " + id));

    if (!status.equals("ACTIVE") && !status.equals("INACTIVE")) {
      throw new InvalidStatusException("Invalid status: " + status);
    }

    invitee.setStatus(status);
    Invitee updated = repository.save(invitee);
    return mapper.toResponse(updated);
  }
}
