package byaj.controllers;

import byaj.models.Address;
import byaj.models.Camp;
import byaj.models.User;
import byaj.repositories.AddressRepository;
import byaj.repositories.CampRepository;
import byaj.repositories.UserRepository;
import byaj.services.GeoApiContextContainer;
import byaj.validators.AddressValidator;
import byaj.validators.CampValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by student on 7/19/17.
 */

@Controller
@RequestMapping("/edit")
public class EditController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CampRepository campRepository;

    @Autowired
    private CampValidator campValidator;

    @Autowired
    private AddressValidator addressValidator;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private GeoApiContextContainer geoApiContextContainer;

    @RequestMapping("/")
    public String returnHome(Principal principal)
    {

        return "redirect:"+userRepository.findByUsername(principal.getName()).getUrl();
    }


    @GetMapping("/editcamp")
    public String editCampform(Principal principal, Model model)
    {
        if(campRepository.findByUsername(principal.getName())==null){
            return "redirect:/camp";
        }
        model.addAttribute("message","Camp is created successfully");

        Camp camp=campRepository.findByUsername(principal.getName());
        Address address=addressRepository.findByOwnerId(camp.getId());
        model.addAttribute("address",address);
        model.addAttribute("camp",camp);
        User current=userRepository.findByUsername(principal.getName());
        current.setUrl("/edit/editcamp");
        userRepository.save(current);

        return "editCamp";
    }
    @PostMapping("/editcamp")
    public String editCampSave(@Valid Camp camp, BindingResult bindingResult2, @Valid Address address, BindingResult bindingResult, @RequestParam(name="streetAddress", required=true) String streetAddress, @RequestParam(name="city", required=true) String city, @RequestParam(name="state", required=true) String state, @RequestParam(name="zipCode", required=true) int zipCode, Principal principal, Model model)
    {

        address.setStreetAddress(streetAddress);
        address.setCity(city);
        address.setState(state);
        address.setZipCode(zipCode);
        model.addAttribute("address",address);
        model.addAttribute("camp", camp);
        addressValidator.validate(address,bindingResult);
        if (bindingResult.hasErrors()) {
            return "editCamp";
        }
        addressValidator.validateAddress(address,bindingResult,geoApiContextContainer);
        if (bindingResult.hasErrors()) {
            return "editCamp";
        }


        campValidator.validate(camp, bindingResult2);
        if (bindingResult2.hasErrors()) {
            return "editCamp";
        }

        Camp realCamp=campRepository.findByUsername(principal.getName());

        camp.setId(realCamp.getId());
        camp.setAddressId(realCamp.getAddressId());
        camp.setDirectorId(realCamp.getDirectorId());
        camp.setDirectorName(realCamp.getDirectorName());
        camp.setUsername(realCamp.getUsername());
        address.setOwnerType("camp");
        address.setOwnerId(camp.getId());;
        address.setId(camp.getAddressId());

        addressRepository.save(address);
        campRepository.save(camp);




        return "redirect:"+userRepository.findByUsername(principal.getName()).getUrl();
    }


}
