package com.training.netcol_collector.task;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Component
@Log
public class TaskExecutor {
    private final ThreadPoolExecutor executor;

    public TaskExecutor() {
        executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        log.info(String.format("Init. Available Processors: %d", Runtime.getRuntime().availableProcessors()));
    }

    public Task executeTask(Task task) {
        log.info(String.format("Got new Task: id='%s'", task.getId()));

        task.setStatus(TaskStatus.INPROGRESS);
        executor.execute(task);

        return task;
    }

    public void stopTasks() {
        executor.shutdown();
    }
}