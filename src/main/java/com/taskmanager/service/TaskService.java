package com.taskmanager.service;

import com.taskmanager.dto.TaskRequestDto;
import com.taskmanager.dto.TaskResponseDto;
import com.taskmanager.entity.Status;
import org.springframework.data.domain.Page;


public interface TaskService {


    TaskResponseDto createTask(TaskRequestDto taskRequestDto);

    Page<TaskResponseDto> getAllTasks(Status status, int page, int size, String sortBy, String sortDir);


    TaskResponseDto getTaskById(Long id);

    TaskResponseDto updateTask(Long id, TaskRequestDto taskRequestDto);

    void deleteTask(Long id);
}
