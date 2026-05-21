package com.scut.industrial_software.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class TaskDirectoryManager {

    private final Path tasksRootPath;

    public TaskDirectoryManager(@Value("${files.tasks.path}") String tasksRootPath) {
        if (!StringUtils.hasText(tasksRootPath)) {
            throw new IllegalStateException("files.tasks.path is required");
        }
        this.tasksRootPath = Paths.get(tasksRootPath).normalize();
    }

    public Path createTaskDirectories(Integer userId, Integer taskId) throws IOException {
        if (userId == null || taskId == null) {
            throw new IllegalArgumentException("userId and taskId are required");
        }
        Path taskRoot = tasksRootPath.resolve("user_" + userId).resolve("task_" + taskId);
        Files.createDirectories(taskRoot.resolve("input"));
        Files.createDirectories(taskRoot.resolve("output"));
        Files.createDirectories(taskRoot.resolve("logs"));
        Files.createDirectories(taskRoot.resolve("scripts"));
        return taskRoot;
    }
}

