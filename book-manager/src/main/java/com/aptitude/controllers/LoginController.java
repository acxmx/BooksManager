package com.aptitude.controllers;


import com.aptitude.domain.Admin;
import com.aptitude.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("login.do")
    public String login(String username, String password,
                        Model model, HttpSession session) {
        // 通过账号和密码查询用户
        Admin admin = adminService.findAdminByName(username, password);
        if (admin != null) {
            // 将Admin对象添加到Session
            session.setAttribute("ADMIN", admin);
            // 跳转到主页面
            return "books.do";
        }
        model.addAttribute("tips", "账号或密码错误，请重新输入！");
        // 返回到登录页面
        return "login";
    }

    @RequestMapping("logout.do")
    public String logout(HttpSession session) {
        session.removeAttribute("ADMIN");
        return "login";
    }

    @RequestMapping(value = "/login")
    public String toLogin (HttpServletRequest request) {
        return "login";
    }

    @RequestMapping("/")
    public String index1(Model model) {
        return "login";
    }

    @RequestMapping("/index")
    public String index2(Model model) {
        return "login";
    }

}
