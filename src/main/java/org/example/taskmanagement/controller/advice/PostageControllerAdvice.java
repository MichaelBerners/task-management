package org.example.taskmanagement.controller.advice;

import java.net.UnknownServiceException;
import org.example.taskmanagement.domain.exception.EditingException;
import org.example.taskmanagement.domain.exception.TaskNotFoundException;
import org.example.taskmanagement.domain.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostageControllerAdvice {
    @ExceptionHandler(EditingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notValid(EditingException e) {
        return "Ошибка создания задания";
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notValid(TaskNotFoundException e) {
        return "Задание не найдено";
    }

    @ExceptionHandler(UnknownServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notValid(UserNotFoundException e) {
        return "Пользователь не найден";
    }
}