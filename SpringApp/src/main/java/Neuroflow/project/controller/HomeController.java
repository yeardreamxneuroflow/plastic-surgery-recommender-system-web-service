package Neuroflow.project.controller;

import Neuroflow.project.dto.request.MemberJoinDto;
import Neuroflow.project.service.CodeService;
import Neuroflow.project.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Controller
public class HomeController {

    private final CodeService codeService;
    private final MemberService memberService;

    public HomeController(CodeService codeService, MemberService memberService) {
        this.codeService = codeService;
        this.memberService = memberService;
    }

    @GetMapping("index")
    public String index(Principal principal, final Model model){
        model.addAttribute("username",principal.getName());
        return "index";
    }

    @GetMapping("join")
    public String join() {
        return "join";
    }

    @PostMapping("/join")
    public String postMemberJoin(@ModelAttribute MemberJoinDto memberJoinDto) throws SQLException {
        memberJoinDto.setId(codeService.getSequenceNumber("NNM"));
        memberJoinDto.setCreated_at(LocalDateTime.now());

        if(memberService.memberSave(memberJoinDto)) {
            return "index";
        } else {
            return "join";
        }
    }
}
