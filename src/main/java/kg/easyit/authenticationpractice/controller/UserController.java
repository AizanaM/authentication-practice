package kg.easyit.authenticationpractice.controller;

import kg.easyit.authenticationpractice.model.dto.UserDto;
import kg.easyit.authenticationpractice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private final UserService userService;

    @PreAuthorize("hasAuthority('USER_CREATE')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserDto userDto) {
        try {
            log.info("Creating user.");
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userDto));
        } catch (RuntimeException ex) {
            log.error("User creation failed. User with such email=" + userDto.getEmail() +
                    " or username=" + userDto.getUsername() + " already exists.");
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @PostMapping("/get/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            log.info("Reading user with id=" + id);
            return ResponseEntity.ok(userService.find(id));
        } catch (RuntimeException ex) {
            log.error("User reading failed. User with id=" + id + " is not found.");
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserDto userDto) {
        try {
            log.info("Updating user.");
            return ResponseEntity.ok(userService.update(userDto));
        } catch (RuntimeException ex) {
            log.error("User updating failed. User with id=" + userDto.getId() + " is not found.");
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PutMapping("/refresh-password")
    public ResponseEntity<?> refreshPassword(@RequestBody String email) {
        try {
            log.info("Refreshing user password.");
            return ResponseEntity.ok(userService.refreshPassword(email));
        } catch (RuntimeException ex) {
            log.error("User password refreshing failed. User with email=" + email + " is not found.");
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            log.info("Deleting user.");
            return ResponseEntity.ok(userService.delete(id));
        } catch (RuntimeException ex) {
            log.error("User deleting failed. User with id=" + id + " is not found.");
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

}
