package kg.easyit.authenticationpractice.service.impl;

import com.sun.org.apache.xerces.internal.utils.XMLLimitAnalyzer;
import kg.easyit.authenticationpractice.request.RefreshPasswordRequest;
import kg.easyit.authenticationpractice.util.PasswordGenerator;
import kg.easyit.authenticationpractice.exceptions.UserNotFoundException;
import kg.easyit.authenticationpractice.mapper.UserMapper;
import kg.easyit.authenticationpractice.model.dto.UserDto;
import kg.easyit.authenticationpractice.model.entity.User;
import kg.easyit.authenticationpractice.repository.UserRepository;
import kg.easyit.authenticationpractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

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
                .isActive(true)
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
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
        return UserMapper.INSTANCE.toDto(userRepository
                .findByIdAndIsActiveTrue(userDto.getId())
                .map(user -> {
                    user.setFirstName(userDto.getFirstName());
                    user.setLastName(userDto.getLastName());
                    user.setEmail(userDto.getEmail());
                    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    user.setUsername(userDto.getUsername());
                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException("User with id=" + userDto.getId() + " is not found.")));
    }

    @Override
    public UserDto delete(Long id) {
        return UserMapper.INSTANCE.toDto(userRepository
                .findByIdAndIsActiveTrue(id)
                .map(user -> {user.setIsActive(false);
                return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException("User with id=" + id + " is not found.")));
    }

    @Override
    public String refreshPassword(RefreshPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).map(user1 -> {
            String newPassword = PasswordGenerator.generatePassword();
            // send to email
            user1.setPassword(passwordEncoder.encode(newPassword));
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("easy.it.java@gmail.com");
            message.setTo(user1.getEmail());
            message.setSubject("Forgot your password?");
            message.setText("Your new password is: '" + newPassword + "'");
            javaMailSender.send(message);
            return userRepository.save(user1);
        }).orElseThrow(() -> new RuntimeException("Email: " + request.getEmail() + " is not found."));

        return "Password has been refreshed";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username: " + username + " is not found."));
    }
}
