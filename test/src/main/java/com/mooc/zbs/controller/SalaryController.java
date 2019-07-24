package com.mooc.zbs.controller;

import com.mooc.zbs.web.mvc.Controller;
import com.mooc.zbs.web.mvc.RequestMapping;
import com.mooc.zbs.web.mvc.RequestParam;

@Controller
public class SalaryController {

    @RequestMapping("/salary")
    public Integer getSalary(@RequestParam("name") String name, @RequestParam("age") String age) {
        System.out.println(name + "=====" + age);
        return 10000;
    }
}
