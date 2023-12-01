package org.kosta.starducks;

import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TestCont {


    @GetMapping("/forum")
    public String main() {

        return "forum/forum"; //템플릿 포럼/포럼.html이 출력되는 것

    }
}

