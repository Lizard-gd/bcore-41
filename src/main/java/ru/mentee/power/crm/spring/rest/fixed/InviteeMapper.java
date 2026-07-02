package ru.mentee.power.crm.spring.rest.fixed;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InviteeMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "status", expression = "java(InviteeStatus.NEW)")
  Invitee toEntity(CreateInviteeRequest request);

  InviteeResponse toResponse(Invitee invitee);
}