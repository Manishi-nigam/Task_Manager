package com.taskmanager.controller;

import com.taskmanager.dto.TaskRequestDto;
import com.taskmanager.dto.TaskResponseDto;
import com.taskmanager.entity.Status;
import com.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task Management API", description = "Endpoints for Task creation, retrieval, updates, and deletion.")
public class TaskController {

    private final TaskService taskService;


    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @PostMapping
    @Operation(
            summary = "Create a new Task",
            description = "Creates a new task in the database. Title and Status are mandatory fields."
    )
    @ApiResponse(responseCode = "201", description = "Task created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid payload or validation failed")
    public ResponseEntity<TaskResponseDto> createTask(
            @Valid @RequestBody TaskRequestDto taskRequestDto) {
        TaskResponseDto createdTask = taskService.createTask(taskRequestDto);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }


    @GetMapping
    @Operation(
            summary = "Get all Tasks (with Pagination and Filter)",
            description = "Retrieves a paginated list of tasks. Results can be optionally filtered by Status (PENDING, COMPLETED)."
    )
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    public ResponseEntity<Page<TaskResponseDto>> getAllTasks(
            @Parameter(description = "Filter tasks by status (PENDING or COMPLETED)")
            @RequestParam(value = "status", required = false) Status status,
            
            @Parameter(description = "Page number (zero-based index, defaults to 0)")
            @RequestParam(value = "page", defaultValue = "0") int page,
            
            @Parameter(description = "Number of items per page (defaults to 10)")
            @RequestParam(value = "size", defaultValue = "10") int size,
            
            @Parameter(description = "Sorting field (defaults to 'id')")
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            
            @Parameter(description = "Sorting direction (asc or desc, defaults to 'asc')")
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        
        Page<TaskResponseDto> tasks = taskService.getAllTasks(status, page, size, sortBy, sortDir);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Task by ID",
            description = "Retrieves details of a single task using its unique database identifier."
    )
    @ApiResponse(responseCode = "200", description = "Task retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Task not found with the given ID")
    public ResponseEntity<TaskResponseDto> getTaskById(
            @Parameter(description = "The unique ID of the Task to fetch")
            @PathVariable("id") Long id) {
        TaskResponseDto task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing Task",
            description = "Updates the title, description, and status of an existing task specified by ID."
    )
    @ApiResponse(responseCode = "200", description = "Task updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid payload or validation failed")
    @ApiResponse(responseCode = "404", description = "Task not found with the given ID")
    public ResponseEntity<TaskResponseDto> updateTask(
            @Parameter(description = "The unique ID of the Task to update")
            @PathVariable("id") Long id,
            @Valid @RequestBody TaskRequestDto taskRequestDto) {
        TaskResponseDto updatedTask = taskService.updateTask(id, taskRequestDto);
        return ResponseEntity.ok(updatedTask);
    }


    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Task by ID",
            description = "Deletes a task record permanently from the database using its unique identifier."
    )
    @ApiResponse(responseCode = "204", description = "Task deleted successfully (No Content)")
    @ApiResponse(responseCode = "404", description = "Task not found with the given ID")
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "The unique ID of the Task to delete")
            @PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
