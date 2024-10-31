package org.alexprokopiev.mapper;

public interface BaseMapper<BaseEntity, BaseCommand, BaseDto> {

    BaseDto mapToDTO(BaseEntity entity);

    BaseEntity mapToEntity(BaseCommand command);
}
