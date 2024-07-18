package banquemisr.challenge05.service;

import banquemisr.challenge05.model.Task;
import banquemisr.challenge05.model.TaskDto;
import banquemisr.challenge05.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TaskService {
    @Autowired
    private TaskRepository repository;

    public void createTask(TaskDto taskDto) {
        Task task = toEntity(taskDto);
        if(!repository.exists(Example.of(task))) {
            repository.save(task);
        }
    }

    public List<TaskDto> getAllTasks() {
        List<Task> tasks = repository.findAll();
        List<TaskDto> taskDtos = new ArrayList<>();
        tasks.stream().forEach(task -> taskDtos.add(toDto(task)));
        return taskDtos;
    }

    public TaskDto getTaskById(Long id) {
        Optional<Task> task = repository.findById(id);
        return toDto(task.orElseThrow());
    }

    public void updateTask(Long id, TaskDto taskDto) {
        Optional<Task> task = repository.findById(id);
        TaskDto taskDto1 = toDto(task.orElseThrow());
        taskDto1.setTitle(taskDto.getTitle());
        taskDto1.setDurationInHours(taskDto.getDurationInHours());
        repository.save(toEntity(taskDto1));
    }

    private TaskDto toDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle(task.getTitle());
        taskDto.setDurationInHours(task.getDurationInHours());
        return taskDto;
    }

    public void deleteTaskById(Long id) {
        repository.delete(repository.findById(id).orElseThrow());
    }

    private Task toEntity(TaskDto taskDto) {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDurationInHours(taskDto.getDurationInHours());
        return task;
    }

    public List<TaskDto> searchTaskByTitle(String title) {
        List<Task> tasks = repository.findByTitleContaining(title);
        return tasks.stream().map(task -> toDto(task)).collect(Collectors.toList());
    }
}
