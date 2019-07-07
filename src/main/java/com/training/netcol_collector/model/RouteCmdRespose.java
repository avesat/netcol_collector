package com.training.netcol_collector.model;

import com.training.netcol_collector.task.TaskStatus;
import lombok.Data;

@Data
public class RouteCmdRespose {
    private TaskStatus status;
}
