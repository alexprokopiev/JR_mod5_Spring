package org.alexprokopiev.mapper;

import org.alexprokopiev.model.command.TaskCommand;
import org.alexprokopiev.model.dto.TaskDto;
import org.alexprokopiev.model.entity.TaskEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TaskMapper extends BaseMapper<TaskEntity, TaskCommand, TaskDto> {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Override
    TaskDto mapToDTO(TaskEntity taskEntity);

    @Override
    TaskEntity mapToEntity(TaskCommand taskCommand);
}
