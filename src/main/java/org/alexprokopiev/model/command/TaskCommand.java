package org.alexprokopiev.model.command;

import lombok.Data;
import org.alexprokopiev.model.enums.Status;

@Data
public class TaskCommand implements BaseCommand {

    private String description;
    private Status status;
}
