package com.training.netcol_collector.task;

import com.training.netcol_collector.utils.Collector;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Log
public abstract class Task implements Runnable, Collector {
    private Long id;
    private Long parentId;
    private TaskStatus status = TaskStatus.NEW;
    private String cmd;
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) private List<Task> subTasks = new ArrayList<>();

    protected void addNextTask(Task newTask) {
        newTask.setParentId(id);
        subTasks.add(newTask);
    }

    protected Iterator<Task> iterator() {
        return subTasks.iterator();
    }

    public void collect(InputStream inputStream) {
        log.info("Default collect method");
    }

    public void run() {
        log.info(String.format("Default run method. Task id='%s'", getId()));
    }
}