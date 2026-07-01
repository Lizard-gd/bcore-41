package ru.mentee.power.crm.spring.rest.fixed;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InviteeMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  Invitee toEntity(CreateInviteeRequest request);

  InviteeResponse toResponse(Invitee invitee);
}
