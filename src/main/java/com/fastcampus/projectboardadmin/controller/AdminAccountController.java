package com.fastcampus.projectboardadmin.controller;

import com.fastcampus.projectboardadmin.dto.reponse.AdminAccountResponse;
import com.fastcampus.projectboardadmin.service.AdminAccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/admin/members")
@Controller
public class AdminAccountController {

    private AdminAccountService adminAccountService;

    @GetMapping
    public String members(
            Model model
            , HttpServletRequest request
    ){
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/members";
    }

    @ResponseBody
    @GetMapping("/api/admin/members")
    public List<AdminAccountResponse> getMembers() {
        return List.of();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    @DeleteMapping("/api/admin/members/{userId}")
    public void delete(@PathVariable String userId){

    }
}
