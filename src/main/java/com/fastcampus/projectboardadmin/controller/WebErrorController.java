package com.fastcampus.projectboardadmin.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebErrorController implements ErrorController {

    @RequestMapping("/error")
    public String error(
            Model model
            , HttpServletResponse response
    ){
        String viewName = "";
        String responseSTATUS = Integer.toString(response.getStatus());

        model.addAttribute("responseSTATUS", responseSTATUS);

        if(responseSTATUS.substring(1).equals("4")) {
            viewName = "error/4xx";
        }else {
            viewName = "error/5xx";
        }

        return viewName;
    }
}
