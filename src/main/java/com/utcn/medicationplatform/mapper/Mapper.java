package com.utcn.medicationplatform.mapper;

public interface Mapper<Entity, Dto> {

    Entity dtoToEntity(Dto dto);

    Dto entityToDto(Entity entity);

}
