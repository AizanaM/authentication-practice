package kg.easyit.authenticationpractice.mapper;

import kg.easyit.authenticationpractice.model.dto.RoleDto;
import kg.easyit.authenticationpractice.model.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper extends BaseMapper<Role, RoleDto> {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

}
