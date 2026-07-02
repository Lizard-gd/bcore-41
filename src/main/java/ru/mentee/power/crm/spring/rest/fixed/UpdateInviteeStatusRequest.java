package ru.mentee.power.crm.spring.rest.fixed;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateInviteeStatusRequest {

  @NotNull(message = "Статус обязателен")
  private InviteeStatus status;
}