package SimplifyPay.application.dtos;

import SimplifyPay.application.annotations.CpfCnpj;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateUserRequest {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Document is required.")
    @CpfCnpj
    private String document;

    @Email
    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;
}
