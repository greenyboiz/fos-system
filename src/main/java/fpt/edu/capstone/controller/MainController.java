//package fpt.edu.capstone.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Controller
//@CrossOrigin(origins = "*", maxAge = 3600)
//public class MainController {
//
//    @RequestMapping("/chat")
//    public String index(HttpServletRequest request, Model model) {
//        String username = (String) request.getSession().getAttribute("username");
//
//        if (username == null || username.isEmpty()) {
//            return "redirect:/lg";
//        }
//        model.addAttribute("username", username);
//
//        return "chat";
//    }
//
//    @RequestMapping(path = "/lg", method = RequestMethod.GET)
//    public String showLoginPage() {
//        return "login";
//    }
//
//    @RequestMapping(path = "/test", method = RequestMethod.GET)
//    public String showTestPage() {
//        return "test";
//    }
//
//    @RequestMapping(path = "/lg", method = RequestMethod.POST)
//    public String doLogin(HttpServletRequest request, @RequestParam(defaultValue = "") String username) {
//        username = username.trim();
//
//        if (username.isEmpty()) {
//            return "login";
//        }
//        request.getSession().setAttribute("username", username);
//
//        return "redirect:/chat";
//    }
//
//    @RequestMapping(path = "/logout")
//    public String logout(HttpServletRequest request) {
//        request.getSession(true).invalidate();
//
//        return "redirect:/lg";
//    }
//
//}
