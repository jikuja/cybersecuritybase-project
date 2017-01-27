package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.repository.SignupRepository;

@Controller
public class UnsubscribeController {
    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping(value = "/unsubscribe/{id}", method = RequestMethod.GET)
    public String unsubscribe(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("name", signupRepository.getOne(id).getName());
        model.addAttribute("add", signupRepository.getOne(id).getAddress());
        return "unsubscribe";
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.POST)
    public String unsubscribePost(@RequestParam("id") Long id, Model model) {
        model.addAttribute("id", id);
        signupRepository.delete(id);
        return "unsubscribePost";
    }
}
