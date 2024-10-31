package org.alexprokopiev.service;

import org.alexprokopiev.mapper.TaskMapper;
import org.alexprokopiev.model.command.TaskCommand;
import org.alexprokopiev.model.dto.TaskDto;
import org.alexprokopiev.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = TaskMapper.INSTANCE;
    }

    public Page<TaskDto> findAll(Pageable pageable) {
        List<TaskDto> users = taskRepository.findAll(pageable).stream()
                .map(taskMapper::mapToDTO)
                .toList();
        return new PageImpl<>(users, pageable, users.size());
    }

    public long findPageCount(Page<TaskDto> page) {
        long totalCount = taskRepository.count();
        int size = page.getSize();
        return totalCount%size == 0 ? totalCount/size : totalCount/size + 1;
    }

    @Transactional
    public TaskDto create(TaskCommand taskCommand) {
        return Optional.of(taskCommand)
                .map(taskMapper::mapToEntity)
                .map(taskRepository::save)
                .map(taskMapper::mapToDTO)
                .orElseThrow();
    }

    @Transactional
    public boolean deleteById(Integer id) {
        return taskRepository.findById(id)
                .map(entity -> {
                    taskRepository.delete(entity);
                    taskRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public Optional<TaskDto> update(Integer id, TaskCommand taskCommand) {
        return taskRepository.findById(id)
                .map(entity -> {
                    if (!taskCommand.getDescription().isEmpty()
                            && !taskCommand.getDescription().equals(entity.getDescription())) {
                        entity.setDescription(taskCommand.getDescription());
                    }
                    if (!taskCommand.getStatus().name().isEmpty()
                            && !taskCommand.getStatus().name().equals(entity.getStatus().name())) {
                        entity.setStatus(taskCommand.getStatus());
                    }
                    return entity;
                })
                .map(taskRepository::saveAndFlush)
                .map(taskMapper::mapToDTO);
    }
}
