package banquemisr.challenge05.controller;

import banquemisr.challenge05.model.Task;
import banquemisr.challenge05.model.TaskDto;
import banquemisr.challenge05.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        taskService.createTask(taskDto);
        return new ResponseEntity<>(taskDto, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/task/{id}", method = RequestMethod.PUT)
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto, @PathVariable Long id) {
        taskService.updateTask(id, taskDto);
        return new ResponseEntity<>(taskDto, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @RequestMapping(value = "/task/{id}", method = RequestMethod.GET)
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/task/search/{title}", method = RequestMethod.GET)
    public ResponseEntity<List<TaskDto>> searchTaskByTitle(@PathVariable String title) {
        return new ResponseEntity<>(taskService.searchTaskByTitle(title), HttpStatus.OK);
    }

    @RequestMapping(value = "/task/{id}", method = RequestMethod.DELETE)
    public void deleteTaskById(@PathVariable Long id) {
        taskService.deleteTaskById(id);
    }
}
