package net.sourceforge.html5val.examples;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("userFormBean", new UserFormBean());
        return "index.html";
    }

    @RequestMapping(value = "/userSave.do", method = RequestMethod.POST)
    public String userEditSave(
            @Valid UserFormBean userFormBean, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "index.html";
        }
        redirectAttributes.addFlashAttribute("userFormBean", userFormBean);
        return "redirect:user.html";
    }

    @RequestMapping("/user.html")
    public String user() {
        return "user.html";
    }
}
