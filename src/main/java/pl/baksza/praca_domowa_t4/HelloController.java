package pl.baksza.praca_domowa_t4;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String get(Model model){
      model.addAttribute("name","Adek");
      return "AllCarsForm";
    }
}
