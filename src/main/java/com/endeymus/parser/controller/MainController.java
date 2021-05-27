package com.endeymus.parser.controller;

import com.endeymus.parser.model.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mark Shamray
 */
@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("")
    public String index() {
        return "main/index";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound404(){
        return "main/404";
    }

    @GetMapping("document/api")
    public String documentAPI() {
        return "main/documentAPI";
    }

}
