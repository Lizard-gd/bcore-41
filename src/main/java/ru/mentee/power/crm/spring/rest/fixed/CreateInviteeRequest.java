package ru.mentee.power.crm.spring.rest.fixed;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateInviteeRequest {

  @NotBlank(message = "Email обязателен")
  @Email(message = "Некорректный формат email")
  private String email;

  @NotBlank(message = "Имя обязательно")
  @Size(min = 2, max = 30, message = "Имя должно быть от 2 до 30 символов")
  private String firstName;
}