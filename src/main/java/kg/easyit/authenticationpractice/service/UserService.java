package kg.easyit.authenticationpractice.service;

import kg.easyit.authenticationpractice.model.dto.UserDto;
import kg.easyit.authenticationpractice.model.entity.Authority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    UserDto create(UserDto userDto);
    UserDto find(Long id);
    UserDto update(UserDto userDto);
    UserDto delete(Long id);
    String refreshPassword(String email);

}
