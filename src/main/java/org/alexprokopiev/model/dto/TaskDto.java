package org.alexprokopiev.model.dto;

import lombok.Value;
import org.alexprokopiev.model.enums.Status;

@Value
public class TaskDto implements BaseDto {

    Integer id;
    String description;
    Status status;
}
