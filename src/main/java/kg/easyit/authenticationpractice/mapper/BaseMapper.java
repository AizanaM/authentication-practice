package kg.easyit.authenticationpractice.mapper;

import java.util.List;

public interface BaseMapper<Entity, Dto> {

    Dto toDto(Entity entity);
    Entity toEntity(Dto dto);
    List<Dto> toDtoList(List<Entity> entityList);
    List<Entity> toEntityList(List<Dto> dtoList);

}
