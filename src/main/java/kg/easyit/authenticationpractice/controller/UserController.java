package kg.easyit.authenticationpractice.controller;

import kg.easyit.authenticationpractice.model.dto.UserDto;
import kg.easyit.authenticationpractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// где логгер?
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private final UserService userService;

    @PreAuthorize("hasAuthority(USER_CREATE)")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserDto userDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userDto));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasAuthority(USER_READ)")
    @PostMapping("/get-user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.find(id));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasAuthority(USER_UPDATE)")
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserDto userDto) {
        try {
            return ResponseEntity.ok(userService.update(userDto));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasAuthority(USER_UPDATE)")
    @PutMapping("/refresh-password")
    public ResponseEntity<?> refreshPassword(@RequestBody String email) {
        try {
            return ResponseEntity.ok(userService.refreshPassword(email));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

}
