package kg.easyit.authenticationpractice.mapper;

import kg.easyit.authenticationpractice.model.dto.UserDto;
import kg.easyit.authenticationpractice.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper extends BaseMapper<User, UserDto> {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

}
