package ru.mentee.power.crm.spring.rest.fixed;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.power.crm.spring.exception.EmailAlreadyExistsException;
import ru.mentee.power.crm.spring.exception.EntityNotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class InviteeService {

  private final InviteeRepository repository;
  private final InviteeMapper mapper;

  public Page<InviteeResponse> getAll(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return repository.findAll(pageable).map(mapper::toResponse);
  }

  public InviteeResponse getById(UUID id) {
    Invitee invitee = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Invitee not found with id: " + id));
    return mapper.toResponse(invitee);
  }

  public InviteeResponse create(CreateInviteeRequest request) {
    if (repository.existsByEmail(request.getEmail())) {
      throw new EmailAlreadyExistsException("Email already exists: " + request.getEmail());
    }
    Invitee invitee = mapper.toEntity(request);
    Invitee saved = repository.save(invitee);
    return mapper.toResponse(saved);
  }

  public void delete(UUID id) {
    Invitee invitee = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Invitee not found with id: " + id));
    repository.delete(invitee);
  }

  public InviteeResponse updateStatus(UUID id, InviteeStatus newStatus) {
    Invitee invitee = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Invitee not found with id: " + id));
    invitee.setStatus(newStatus);
    Invitee updated = repository.save(invitee);
    return mapper.toResponse(updated);
  }
}