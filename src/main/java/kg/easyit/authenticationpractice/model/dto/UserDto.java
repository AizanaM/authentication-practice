package kg.easyit.authenticationpractice.model.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
   Long id;
   String firstName;
   String lastName;
   String email;
   String password;
   String username;
}
