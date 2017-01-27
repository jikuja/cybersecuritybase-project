package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sec.project.repository.SignupRepository;

@Controller
public class AdminController {
    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping(value = "/admin")
    public String admin(Authentication authentication) {
        return "admin";
    }

    @RequestMapping(value = "/admin/list")
    public String list(Model model, Authentication authentication) {
        model.addAttribute("list", signupRepository.findAll());
        return "list";
    }
}
