package ori.loproject.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ori.loproject.domain.members.Member;
import ori.loproject.dto.MemberDto;
import ori.loproject.service.MemberService;

import java.util.Map;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        String uri = request.getHeader("Referer");

        if (uri != null && !uri.contains("/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }

        return "member/login";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginWarning", "아이디 혹은 비밀번호를 확인해주세요.");
        return "member/login";
    }

    @GetMapping(value = "/join")
    public String signUp(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/join";
    }

    @PostMapping(value = "join")
    public String signUp(@Valid MemberDto memberDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "member/join";
        }
        try {
            Member member = Member.createMember(memberDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/join";
        }
        return "redirect:/";
    }

    @GetMapping(value = "mypage")
    public String memberInfo(Model model) {
        Member member;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        if(currentUserName.equals("anonymousUser")){
            System.out.println("비회원 입니다.");
            return "redirect:/";
        }

        member = memberService.findByEmail(currentUserName);
        model.addAttribute("memberinfo", member);

        return "member/memberinfo";
    }
}
