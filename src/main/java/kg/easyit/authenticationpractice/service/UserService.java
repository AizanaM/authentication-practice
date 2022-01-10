package kg.easyit.authenticationpractice.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {

    UserDto create(UserDto userDto);
    UserDto find(Long id);
    UserDto update(UserDto userDto);
    UserDto delete(Long id);

}
