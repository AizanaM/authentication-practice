package kg.easyit.authenticationpractice.service.impl;

import kg.easyit.authenticationpractice.mapper.RoleMapper;
import kg.easyit.authenticationpractice.model.dto.RoleDto;
import kg.easyit.authenticationpractice.model.entity.Authority;
import kg.easyit.authenticationpractice.repository.RoleRepository;
import kg.easyit.authenticationpractice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Authority> getAuthorities() {
        return Stream.of(Authority.values()).collect(Collectors.toList());
    }

    @Override
    public RoleDto create(RoleDto roleDto) {
        if (roleRepository.existsByRoleName(roleDto.getRoleName())) {
            throw new RuntimeException("Role name: " + roleDto.getRoleName() + " already exists.");
        }
        return RoleMapper.INSTANCE
                .toDto(roleRepository
                        .save(RoleMapper.INSTANCE.toEntity(roleDto)));
    }
}
