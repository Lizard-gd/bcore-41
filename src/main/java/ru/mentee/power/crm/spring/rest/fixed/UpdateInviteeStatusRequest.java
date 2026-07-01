package ru.mentee.power.crm.spring.rest.fixed;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateInviteeStatusRequest {

  @NotBlank(message = "Статус обязателен")
  private String status;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
