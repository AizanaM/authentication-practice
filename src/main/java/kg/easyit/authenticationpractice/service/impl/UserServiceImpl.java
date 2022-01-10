package kg.easyit.authenticationpractice.service.impl;

import kg.easyit.authenticationpractice.config.PasswordGenerator;
import kg.easyit.authenticationpractice.exceptions.UserNotFoundException;
import kg.easyit.authenticationpractice.mapper.UserMapper;
import kg.easyit.authenticationpractice.model.dto.UserDto;
import kg.easyit.authenticationpractice.model.entity.Authority;
import kg.easyit.authenticationpractice.model.entity.User;
import kg.easyit.authenticationpractice.repository.UserRepository;
import kg.easyit.authenticationpractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordGenerator passwordGenerator;

    @Override
    public UserDto create(UserDto userDto) {

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email: " + userDto.getEmail() +  " is already in use.");
        }

        if (userDto.getUsername() == null || userDto.getUsername().trim().equals("")) {
            userDto.setUsername(userDto.getEmail().substring(0, userDto.getEmail().indexOf('@')));
        }

        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username: " + userDto.getUsername() + " is already in use.");
        }

        User user = User
                .builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .username(userDto.getUsername())
                .build();

        userRepository.save(user);
        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    public UserDto find(Long id) {
        return UserMapper.INSTANCE
                .toDto(userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("User with id=" + id + " is not found.")));
    }

    @Override
    public UserDto update(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto delete(Long id) {
        return null;
    }

    @Override
    public List<Authority> getAllAuthorities() {
        return Stream.of(Authority.values()).collect(Collectors.toList());
    }

    @Override
    public String refreshPassword(String email) {
        User user = userRepository.findByEmail(email).map(user1 -> {
            String oldPassword = user1.getPassword();
            String newPassword = passwordEncoder.encode(passwordGenerator.generatePassword(10));
            user1.setPassword(newPassword);
            return userRepository.save(user1);
        }).orElseThrow(() -> new RuntimeException("Email: " + email + " is not found."));

        return user.getPassword();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username: " + username + " is not found."));
    }
}
