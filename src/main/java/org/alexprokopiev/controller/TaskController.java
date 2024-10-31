package org.alexprokopiev.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.alexprokopiev.model.command.TaskCommand;
import org.alexprokopiev.model.dto.*;
import org.alexprokopiev.model.enums.Status;
import org.alexprokopiev.service.TaskService;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/")
    public String findAll(Model model, Pageable pageable) {
        Page<TaskDto> page = taskService.findAll(pageable);
        page.getTotalElements();
        model.addAttribute("pageCount", taskService.findPageCount(page));
        model.addAttribute("tasks", PageResponse.of(page));
        model.addAttribute("statuses", Status.values());
        return "index";
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(TaskCommand taskCommand) {
        taskService.create(taskCommand);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteById(@PathVariable("id") Integer id) {
        if (!taskService.deleteById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Integer id, TaskCommand taskCommand) {
        return taskService.update(id, taskCommand)
                .map(it -> "redirect:/")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
