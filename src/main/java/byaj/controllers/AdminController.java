package byaj.controllers;

import byaj.models.Camp;
import byaj.models.User;
import byaj.models.UserApplication;
import byaj.repositories.AddressRepository;
import byaj.repositories.CampRepository;
import byaj.repositories.UserApplicationRepository;
import byaj.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 7/19/17.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private UserApplicationRepository userApplicationRepository;

    @Autowired
    private CampRepository campRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String returnHome(Principal principal)
    {

        return "redirect:"+userRepository.findByUsername(principal.getName()).getUrl();
    }

    @RequestMapping("/viewapplications")
    public String viewApplications(Principal principal,Model model)
    {
        User current=userRepository.findByUsername(principal.getName());
        current.setUrl("/admin/viewapplications");
        userRepository.save(current);

        List<UserApplication> applicationList=(List) userApplicationRepository.findAllByOrderByUserAppDateDesc();
        ArrayList<User> users=new ArrayList<>();
        for(UserApplication application:applicationList)
        {
            users.add(userRepository.findOne(application.getUserId()));
        }
        model.addAttribute("applicants",applicationList);
        model.addAttribute("users",users);
        return "applicants";
    }

    @RequestMapping("/enable")
    public String viewCamps(Principal principal,Model model)
    {
        User current=userRepository.findByUsername(principal.getName());
        current.setUrl("/admin/enable");
        userRepository.save(current);

        List<Camp> campList=(List) campRepository.findAllByOrderByCampDateDesc();
        ArrayList<User> directors=new ArrayList<>();
        for(Camp camp:campList)
        {
            directors.add(userRepository.findOne(camp.getDirectorId()));
        }
        model.addAttribute("camps",campList);
        model.addAttribute("directors",directors);

        return "camps";
    }
    @RequestMapping("/viewcampstudents")
    public String viewCampStudents(@ModelAttribute Camp camp, Principal principal, Model model)
    {
        User current=userRepository.findByUsername(principal.getName());
        current.setUrl("/admin/viewcampstudents");
        userRepository.save(current);

        List<UserApplication> accepted=(List) camp.getAcceptThem();
        List<UserApplication> rejected=(List) camp.getRejectThem();
        List<UserApplication> applicants=accepted;
        applicants.addAll(rejected);
        ArrayList<User> users=new ArrayList<>();
        for(UserApplication application:applicants)
        {
            users.add(userRepository.findOne(application.getUserId()));
        }
        model.addAttribute("applicants",applicants);
        model.addAttribute("users",users);

        return "applicants";
    }


}
