package byaj.controllers;

import byaj.models.Camp;
import byaj.models.EmailInfo;
import byaj.models.User;
import byaj.repositories.CampRepository;
import byaj.repositories.UserRepository;
import com.google.common.collect.Lists;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

/**
 * Created by student on 7/18/17.
 */
@Controller
@RequestMapping("/email")
public class EmailController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private CampRepository campRepository;

    @Autowired
    public EmailService emailService;

    @RequestMapping("/")
    public String returnHome(Principal principal)
    {

        return "redirect:"+userRepository.findByUsername(principal.getName()).getUrl();
    }
    @PostMapping("/acceptreject")
    public String acceptRejectApplicant(@ModelAttribute EmailInfo email, Principal principal)
    {
        User user=userRepository.findById(Integer.parseInt(email.getEmailValue()));
        User director=userRepository.findByUsername(principal.getName());
        Camp camp=campRepository.findByUsername(principal.getName());
        String message;
        String subject="Status update on Bootcamp Application";
        if(email.getEmailType().equals("accept"))
        {
            message="Dear "+user.getFullName()+". We are happy to inform you that you have been selected to participate in "+camp.getTitle()+
                    "\n if you have any questions about this, please contact "+camp.getDirectorName()+" at "+director.getEmail()+". Thank You.";
            sendEmailWithoutTemplating(user,message,subject);


        }
        if(email.getEmailType().equals("reject"))
        {
            message="Dear "+user.getFullName()+". We regret to inform you that you have not been selected to participate in "+camp.getTitle()+
                    ". If you have any questions about this, please contact "+camp.getDirectorName()+" at "+director.getEmail()+". Thank You.";
            sendEmailWithoutTemplating(user,message,subject);
        }





        return "redirect:"+userRepository.findByUsername(principal.getName()).getUrl();
    }



    @PostMapping("/contact")
    public String contactForm(@ModelAttribute EmailInfo email, Model model, Principal principal)
    {

        User user=userRepository.findById(Integer.parseInt(email.getEmailValue()));
        User current=userRepository.findByUsername(principal.getName());


        model.addAttribute("current",current);
        if(user.getRoleSettings().equals("DIRECTOR"))
        {
            model.addAttribute("director",user);
        }
        if(user.getRoleSettings().equals("USER"))
        {
            model.addAttribute("student",user);
        }
        String message=new String();
        model.addAttribute("message",message);

        return "contactForm";
    }

    @PostMapping("/contactdirector")
    public String contactDirector(@ModelAttribute String message,@ModelAttribute User director,@ModelAttribute User current)
    {
        String subject="Message from Director:"+current.getFirstName()+" "+current.getLastName()+" ("+current.getUsername()+")";
        sendEmailWithoutTemplating(director,message,subject);


        return "base";
    }
    @PostMapping("/contactstudent/")
    public String contactStudent(@ModelAttribute String message,@ModelAttribute User student,@ModelAttribute User current)
    {
        String subject="Message from Applicant:"+current.getFirstName()+" "+current.getLastName()+" ("+current.getUsername()+")";
        sendEmailWithoutTemplating(student,message,subject);
        return "base";
    }

    public void sendEmailWithoutTemplating(User to,String message,String subject){
        final Email email;
        try {
            email = DefaultEmail.builder()
                    .from(new InternetAddress("thecommonappz@gmail.com", "The Common Appz"))
                    .to(Lists.newArrayList(new InternetAddress(to.getEmail(), to.getFirstName()+" "+to.getLastName()+" ("+to.getUsername()+")")))
                    .subject(subject)
                    .body(message)
                    .encoding("UTF-8").build();
            emailService.send(email);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
