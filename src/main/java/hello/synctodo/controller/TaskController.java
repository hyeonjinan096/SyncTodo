package hello.synctodo.controller;

import hello.synctodo.dto.TaskDTO;
import hello.synctodo.service.JoinService;
import hello.synctodo.service.TaskService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/task")
@Log4j2
public class TaskController {

    private final TaskService taskservice;

    public TaskController(TaskService taskservice) {
        this.taskservice = taskservice;
    }


    @ApiOperation(value = "Post Task", notes = "할일 등록하기")
    @PostMapping
    public ResponseEntity<String> registerTask(@RequestBody TaskDTO taskDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );
            return ResponseEntity.ok("redirect:/task/register");
        }
        log.info(taskDTO);
        Long taskId = taskservice.register(taskDTO);

        redirectAttributes.addFlashAttribute("result", taskId);

        return ResponseEntity.ok("redirect:/task"+taskId);

    }

    @ApiOperation(value = "Modify Task", notes = "할일 수정하기")
    @PostMapping("/modify/{taskId}")
    public ResponseEntity<String> modifyTask(@PathVariable Long taskId, TaskDTO taskDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );
            return ResponseEntity.ok("redirect:/task/register");
        }

        taskservice.modify(taskId, taskDTO);

        return ResponseEntity.ok("redirect:/task"+taskId);


    }


    @ApiOperation(value = "Read Task", notes = "할일 조회하기")
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> readTask(@PathVariable Long taskId) {

        TaskDTO taskDTO = taskservice.read(taskId);

        return ResponseEntity.ok(taskDTO);

    }

    @ApiOperation(value = "Delete Task", notes = "할일 삭제하기")
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {

        taskservice.delete(taskId);

        return ResponseEntity.ok("delete!");
    }



}
