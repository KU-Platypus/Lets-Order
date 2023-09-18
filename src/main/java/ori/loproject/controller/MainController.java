package ori.loproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ori.loproject.domain.members.Member;
import ori.loproject.service.MemberService;

/*
@RequiredArgsConstructor
@Controller
public class MainController {
    private final MemberService memberService;

    @GetMapping("/1")
    public String mainPage(Model model){
        Member member;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        if(currentUserName.equals("anonymousUser")){
            return "main/main_page";
        }

        member = memberService.findByEmail(currentUserName);
        model.addAttribute("member", member);
        return "main/main_page";
    }
}
*/