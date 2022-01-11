package kg.easyit.authenticationpractice.controller;

import kg.easyit.authenticationpractice.model.dto.RoleDto;
import kg.easyit.authenticationpractice.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/role")
public class RoleController {

    private final RoleService roleService;

    @PreAuthorize("hasAuthority(PERMISSIONS_READ)")
    @PostMapping("/get-all-authorities")
    public ResponseEntity<?> getAllAuthorities() {
        try{
            log.info("Getting all authorities");
            return ResponseEntity.ok(roleService.getAuthorities());
        }catch (RuntimeException ex){
            log.error("Failed getting all authorities");
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }


    @PreAuthorize("hasAnyAuthority(AUTHORITY_READ)")
    @PostMapping("/read")
    public ResponseEntity<?> create(RoleDto roleDto) {
        try{
            log.info("Role creating");
            return ResponseEntity.ok(roleService.create(roleDto));
        } catch (RuntimeException ex){
            log.error("Role creating failed");
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }
}
