package kg.easyit.authenticationpractice.service;

import kg.easyit.authenticationpractice.model.dto.RoleDto;
import kg.easyit.authenticationpractice.model.entity.Authority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<Authority> getAuthorities();
    RoleDto create(RoleDto roleDto);
}
