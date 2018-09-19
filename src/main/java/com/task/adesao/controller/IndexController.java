package com.task.adesao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by marcus on 17/09/18.
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "upload";
    }

}
