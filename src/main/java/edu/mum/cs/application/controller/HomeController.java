package edu.mum.cs.application.controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by orifjon9 on 7/5/2017.
 */

@RestController
public class HomeController {

    @RequestMapping("/")
    public String index() {
        return "<a href=\"/students\">students list</a> <br> <a href=\"/courses\">courses list</a>";
    }

}
