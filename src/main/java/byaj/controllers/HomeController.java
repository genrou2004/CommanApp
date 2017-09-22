package byaj.controllers;

import byaj.models.*;
import byaj.repositories.*;
import byaj.services.GeoApiContextContainer;
import byaj.services.UserApplicationService;
import byaj.services.UserService;
import byaj.validators.AddressValidator;
import byaj.validators.AddressValidator;
import byaj.validators.CampValidator;
import byaj.validators.UserApplicationValidator;
import byaj.validators.UserValidator;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditor;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by student on 7/17/17.
 */
@Controller
public class HomeController {

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserApplicationValidator userApplicationValidator;

    @Autowired
    private CampValidator campValidator;

    @Autowired
    private byaj.services.UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CampRepository campRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private GeoApiContextContainer geoApiContextContainer;


    @Autowired
    private UserApplicationRepository userApplicationRepository;

    @Autowired
    private AddressValidator addressValidator;

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private UserApplicationService userApplicationService;
    
    @Autowired
    private AcceptRepository acceptRepository;
    
    @Autowired
    private EnableRepository enableRepository;

    @Autowired
    private CampBuilderRepository campBuilderRepository;


    @RequestMapping("/")
    public String home(Model model, Principal principal){
       // model.addAttribute("search", new Search());

        if(principal==null){

        }else{
            userRepository.findByUsername(principal.getName()).setUrl("/");
            userRepository.save(userRepository.findByUsername(principal.getName()));
        }
        return "base";
    }

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String showRegistrationPage(Model model){
      //  model.addAttribute("search", new Search());
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
     //   model.addAttribute("search", new Search());

        model.addAttribute("user", user);
        userValidator.validate(user, result);

        if (result.hasErrors()) {
            return "register";
        } else {
            if (user.getRoleSettings().toUpperCase().equals("SUPERADMIN")) {
                userService.saveSuperAdmin(user);
            }
            if (user.getRoleSettings().toUpperCase().equals("ADMIN")) {
                userService.saveAdmin(user);
            }
            if (user.getRoleSettings().toUpperCase().equals("DIRECTOR")) {
                userService.saveDirector(user);
            }
            if (user.getRoleSettings().toUpperCase().equals("USER")) {
                userService.saveUser(user);
                model.addAttribute("message", "User Account Successfully Created");
            }
            model.addAttribute("message", "User Account Successfully Created");
        }
        return "redirect:/";
    }
    @RequestMapping("/login")
    public String login(Model model) {
    //    model.addAttribute("search", new Search());
        return "login";
    }
    @GetMapping("/camp")
    public String campForm(Model model, Principal principal){

        model.addAttribute("user", userRepository.findByUsername(principal.getName()).getUsername());

        if(campRepository.findByUsername(principal.getName())!=null)
        {
            return "redirect:/edit/editcamp";
        }

        else {
            model.addAttribute("camp", new Camp());

            userRepository.findByUsername(principal.getName()).setUrl("/camp");
            userRepository.save(userRepository.findByUsername(principal.getName()));

            model.addAttribute("address", new Address());
            return "camp";
        }
    }

    @PostMapping("/camp")
    public String createCamp(@Valid Camp camp, BindingResult bindingResult2, @Valid Address address, BindingResult bindingResult, @RequestParam(name="streetAddress", required=true) String streetAddress,@RequestParam(name="city", required=true) String city, @RequestParam(name="state", required=true) String state,@RequestParam(name="zipCode", required=true) int zipCode, Principal principal, Model model) {


        address.setStreetAddress(streetAddress);
        address.setCity(city);
        address.setState(state);
        address.setZipCode(zipCode);
        model.addAttribute("address",address);
        model.addAttribute("camp", camp);
        addressValidator.validate(address,bindingResult);
        if (bindingResult.hasErrors()) {
            return "camp";
        }
        addressValidator.validateAddress(address,bindingResult,geoApiContextContainer);
        if (bindingResult.hasErrors()) {
            return "camp";
        }



        campValidator.validate(camp, bindingResult2);
        if (bindingResult2.hasErrors()) {
            return "camp";
        }
        else{
            User user=userRepository.findByUsername(principal.getName());
            camp.setDirectorName(user.getFullName());
            camp.setDirectorId(user.getId());
            camp.setUsername(user.getUsername());
            campRepository.save(camp);
        }

        address.setOwnerType("camp");
        address.setOwnerId(campRepository.findByUsername(principal.getName()).getId());
        addressRepository.save(address);
        campRepository.findByUsername(principal.getName()).setAddressId(addressRepository.findByOwnerIdAndOwnerType(campRepository.findByUsername(principal.getName()).getId(),"camp").getId());
        campRepository.save(campRepository.findByUsername(principal.getName()));
        return "redirect:"+userRepository.findByUsername(principal.getName()).getUrl();
    }

    @GetMapping("/user")
    public String userForm(Model model, Principal principal){
       // model.addAttribute("user", userRepository.findByUsername(principal.getName()).getUsername());
        model.addAttribute("userApplication", new UserApplication());
        userRepository.findByUsername(principal.getName()).setUrl("/user");
        userRepository.save(userRepository.findByUsername(principal.getName()));

        model.addAttribute("address",new Address());


        return "userapplication";
    }
    @PostMapping("/user")
    public String createUserApplication(@Valid UserApplication userApplication, Model model,BindingResult bindingResult2,@Valid Address address, BindingResult bindingResult, @RequestParam(name="streetAddress", required=true) String streetAddress, @RequestParam(name="city", required=true) String city,@RequestParam(name="state", required=true) String state,@RequestParam(name="zipCode", required=true) int zipCode, Principal principal) {

        address.setStreetAddress(streetAddress);
        address.setCity(city);
        address.setState(state);
        address.setZipCode(zipCode);
        model.addAttribute("address",new Address());
        addressValidator.validate(address,bindingResult);
        if (bindingResult.hasErrors()) {
            return "userapplication";
        }
        addressValidator.validateAddress(address,bindingResult,geoApiContextContainer);
        if (bindingResult.hasErrors()) {
            return "userapplication";
        }



        userApplicationValidator.validate(userApplication, bindingResult2);
        if (bindingResult2.hasErrors()) {
            return "userapplication";
        }
        else{
            userApplication.setUsername(principal.getName());
            User user=userRepository.findByUsername(principal.getName());
            userApplication.setUserId(user.getId());
            userApplication.setFullName(user.getFullName());
            userApplication.setGradDate(userApplication.createDate(userApplication.getGradYear(),userApplication.getGradMonth(),userApplication.getGradDay()));
            userApplicationRepository.save(userApplication);
        }

        address.setOwnerType("user");
        address.setOwnerId(userRepository.findByUsername(principal.getName()).getId());
        addressRepository.save(address);
        userApplicationRepository.findByUsername(principal.getName()).setAddressId(addressRepository.findByOwnerIdAndOwnerType(userRepository.findByUsername(principal.getName()).getId(),"user").getId());
        userApplicationRepository.save(userApplicationRepository.findByUsername(principal.getName()));
        return "redirect:"+userRepository.findByUsername(principal.getName()).getUrl();
    }

    @GetMapping("/applicants")
    public String potentialApplicants(Model model, Principal principal){
        ArrayList<User> users = new ArrayList();
        ArrayList<UserApplication> applications = new ArrayList();
        ArrayList <Address> campAddresses = new ArrayList();
        ArrayList <Address> userAddresses = new ArrayList();
        List <Address> addressByOwner = addressRepository.findAllByOrderByOwnerIdDesc();
        Address myCampAddress=new Address();
        Address myUserAddress=new Address();
        for (int count = 0; count < addressByOwner.size(); count++){
            if (addressByOwner.get(count).getOwnerType().toLowerCase().equals("user")){
                userAddresses.add(addressByOwner.get(count));
            }
        }
        for (int count = 0; count < addressByOwner.size(); count++){
            if (addressByOwner.get(count).getOwnerType().toLowerCase().equals("camp")){
                campAddresses.add(addressByOwner.get(count));
            }
        }
        for(int count=0; count<campAddresses.size(); count++){
            if(campRepository.findByUsername(principal.getName()).getAddressId()==campAddresses.get(count).getId()){
                myCampAddress=campAddresses.get(count);
            }
        }
            String[] origins={myCampAddress.googleAddress()};

        for(int count = 0; count < userAddresses.size(); count++){

            String[] destinations={userAddresses.get(count).googleAddress()};
            double distance=0;
            try {
                DistanceMatrix results= DistanceMatrixApi.getDistanceMatrix(geoApiContextContainer.getContext(),origins,destinations).await();
                for(int i=0;i<results.destinationAddresses.length;i++) {
                    distance=results.rows[0].elements[i].distance.inMeters/1609.34;
                }
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(distance<=100){
                applications.add(userApplicationRepository.findByAddressId(userAddresses.get(count).getId()));
                users.add(userRepository.findById(userApplicationRepository.findByAddressId(userAddresses.get(count).getId()).getUserId()));
            }
        }

        model.addAttribute("applicants", applications);
        model.addAttribute("users", users);
        model.addAttribute("camp", campRepository.findByUsername(principal.getName()));
        userRepository.findByUsername(principal.getName()).setUrl("/applicants");
        userRepository.save(userRepository.findByUsername(principal.getName()));


        return "applicants";
    }

    @GetMapping("/camps")
    public String nearbyCamps(Model model, Principal principal){
        //ArrayList<User> users = new ArrayList();
        ArrayList<User> directors = new ArrayList();
        //ArrayList<UserApplication> applications = new ArrayList();
        ArrayList<Camp> camps = new ArrayList();
        ArrayList <Address> campAddresses = new ArrayList();
        ArrayList <Address> userAddresses = new ArrayList();
        List <Address> addressByOwner = addressRepository.findAllByOrderByOwnerIdDesc();
        Address myCampAddress=new Address();
        Address myUserAddress=new Address();
        for (int count = 0; count < addressByOwner.size(); count++){
            if (addressByOwner.get(count).getOwnerType().toLowerCase().equals("user")){
                userAddresses.add(addressByOwner.get(count));
            }
        }
        for (int count = 0; count < addressByOwner.size(); count++){
            if (addressByOwner.get(count).getOwnerType().toLowerCase().equals("camp")){
                campAddresses.add(addressByOwner.get(count));
            }
        }
        for(int count=0; count<userAddresses.size(); count++){
            if(userApplicationRepository.findByUsername(principal.getName()).getAddressId()==userAddresses.get(count).getId()){
                myUserAddress=userAddresses.get(count);
            }
        }
        String[] origins={myUserAddress.googleAddress()};

        for(int count = 0; count < campAddresses.size(); count++){

            String[] destinations={campAddresses.get(count).googleAddress()};

            double distance=0;
            try {
                DistanceMatrix results= DistanceMatrixApi.getDistanceMatrix(geoApiContextContainer.getContext(),origins,destinations).await();
                for(int i=0;i<results.destinationAddresses.length;i++) {
                    distance=results.rows[0].elements[i].distance.inMeters/1609.34;
                }
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(distance<=100){
                if(campRepository.findByAddressId(campAddresses.get(count).getId()).isEnable()) {
                    camps.add(campRepository.findByAddressId(campAddresses.get(count).getId()));
                    directors.add(userRepository.findById(campRepository.findByAddressId(campAddresses.get(count).getId()).getDirectorId()));
                }
            }
        }

        model.addAttribute("camps", camps);
        model.addAttribute("directors", directors);
        userRepository.findByUsername(principal.getName()).setUrl("/camps");
        userRepository.save(userRepository.findByUsername(principal.getName()));




        return "camps";
    }

    @PostMapping("/search/applicants")
    public String searchApplicants(@Valid Search search, BindingResult bindingResult, Model model, Principal principal){
        if (bindingResult.hasErrors()) {
            System.out.println("search");
            return "redirect:"+userRepository.findByUsername(principal.getName()).getUrl();
        }

        //Assumes searchValue is levelOfEdu
        ArrayList<User> users = new ArrayList();
        ArrayList<UserApplication> applications = new ArrayList();
        ArrayList <Address> campAddresses = new ArrayList();
        ArrayList <Address> userAddresses = new ArrayList();
        List <Address> addressByOwner = addressRepository.findAllByOrderByOwnerIdDesc();
        Address myCampAddress=new Address();
        Address myUserAddress=new Address();
        for (int count = 0; count < addressByOwner.size(); count++){
            if (addressByOwner.get(count).getOwnerType().toLowerCase().equals("user")){
                userAddresses.add(addressByOwner.get(count));
            }
        }
        for (int count = 0; count < addressByOwner.size(); count++){
            if (addressByOwner.get(count).getOwnerType().toLowerCase().equals("camp")){
                campAddresses.add(addressByOwner.get(count));
            }
        }
        for(int count=0; count<campAddresses.size(); count++){
            if(campRepository.findByUsername(principal.getName()).getAddressId()==campAddresses.get(count).getId()){
                myCampAddress=campAddresses.get(count);
            }
        }
        String[] origins={myCampAddress.googleAddress()};

        for(int count = 0; count < userAddresses.size(); count++){

            String[] destinations={userAddresses.get(count).googleAddress()};

            double distance=0;
            try {
                DistanceMatrix results= DistanceMatrixApi.getDistanceMatrix(geoApiContextContainer.getContext(),origins,destinations).await();
                for(int i=0;i<results.destinationAddresses.length;i++) {
                    distance=results.rows[0].elements[i].distance.inMeters/1609.34;
                }
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(distance<=100){
                if(userApplicationRepository.findAllByLevelOfEduContainingOrderByUserIdDesc(search.getSearchValue()).contains(userApplicationRepository.findByAddressId(userAddresses.get(count).getId()))){
                applications.add(userApplicationRepository.findByAddressId(userAddresses.get(count).getId()));
                users.add(userRepository.findById(userApplicationRepository.findByAddressId(userAddresses.get(count).getId()).getUserId()));
            }
            }
        }
        search.setSearchAuthor(userRepository.findByUsername(principal.getName()).getUsername());
        search.setSearchUser(userRepository.findByUsername(principal.getName()).getId());
        model.addAttribute("applicants", applications);
        model.addAttribute("users", users);
        model.addAttribute("camp", campRepository.findByUsername(principal.getName()));
        userRepository.findByUsername(principal.getName()).setUrl("/search/applicants");
        userRepository.save(userRepository.findByUsername(principal.getName()));


        return "applicants";
    }



    @PostMapping("/search/camps")
    public String searchCamps(@Valid Search search, BindingResult bindingResult, Model model, Principal principal){
        if (bindingResult.hasErrors()) {
            System.out.println("search");
            return "redirect:"+userRepository.findByUsername(principal.getName()).getUrl();
        }

        //ArrayList<User> users = new ArrayList();
        ArrayList<User> directors = new ArrayList();
        //ArrayList<UserApplication> applications = new ArrayList();
        ArrayList<Camp> camps = new ArrayList();
        ArrayList <Address> campAddresses = new ArrayList();
        ArrayList <Address> userAddresses = new ArrayList();
        List <Address> addressByOwner = addressRepository.findAllByOrderByOwnerIdDesc();
        Address myCampAddress=new Address();
        Address myUserAddress=new Address();
        for (int count = 0; count < addressByOwner.size(); count++){
            if (addressByOwner.get(count).getOwnerType().toLowerCase().equals("user")){
                userAddresses.add(addressByOwner.get(count));
            }
        }
        for (int count = 0; count < addressByOwner.size(); count++){
            if (addressByOwner.get(count).getOwnerType().toLowerCase().equals("camp")){
                campAddresses.add(addressByOwner.get(count));
            }
        }
        for(int count=0; count<userAddresses.size(); count++){
            if(userApplicationRepository.findByUsername(principal.getName()).getAddressId()==userAddresses.get(count).getId()){
                myUserAddress=userAddresses.get(count);
            }
        }
        String[] origins={myUserAddress.googleAddress()};

        for(int count = 0; count < campAddresses.size(); count++){

            String[] destinations={campAddresses.get(count).googleAddress()};

            double distance=0;
            try {
                DistanceMatrix results= DistanceMatrixApi.getDistanceMatrix(geoApiContextContainer.getContext(),origins,destinations).await();
                for(int i=0;i<results.destinationAddresses.length;i++) {
                    distance=results.rows[0].elements[i].distance.inMeters/1609.34;
                }
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(distance<=100){
                if(campRepository.findAllByDescriptionContainingOrderByDirectorIdDesc(search.getSearchValue()).contains(campRepository.findByAddressId(userAddresses.get(count).getId()))) {
                    if(campRepository.findByAddressId(campAddresses.get(count).getId()).isEnable()) {
                        camps.add(campRepository.findByAddressId(campAddresses.get(count).getId()));
                        directors.add(userRepository.findById(campRepository.findByAddressId(campAddresses.get(count).getId()).getDirectorId()));
                    }
                }
            }
        }
        search.setSearchAuthor(userRepository.findByUsername(principal.getName()).getUsername());
        search.setSearchUser(userRepository.findByUsername(principal.getName()).getId());
        searchRepository.save(search);
        model.addAttribute("camps", camps);
        model.addAttribute("directors", directors);
        userRepository.findByUsername(principal.getName()).setUrl("/search/camps");
        userRepository.save(userRepository.findByUsername(principal.getName()));




        return "camps";
    }

    @GetMapping("/search/applicants")
    public String redirectSearchApplicants(Model model, Principal principal){

        if(!userRepository.findByUsername(principal.getName()).getUrl().equals("/search/applicants")){
            return "redirect:"+userRepository.findByUsername(principal.getName()).getUrl();
        }
        Search search = searchRepository.findBySearchAuthorOrderBySearchDateDesc(principal.getName());

        //Assumes searchValue is levelOfEdu
        ArrayList<User> users = new ArrayList();
        ArrayList<UserApplication> applications = new ArrayList();
        ArrayList <Address> campAddresses = new ArrayList();
        ArrayList <Address> userAddresses = new ArrayList();
        List <Address> addressByOwner = addressRepository.findAllByOrderByOwnerIdDesc();
        Address myCampAddress=new Address();
        Address myUserAddress=new Address();
        for (int count = 0; count < addressByOwner.size(); count++){
            if (addressByOwner.get(count).getOwnerType().toLowerCase().equals("user")){
                userAddresses.add(addressByOwner.get(count));
            }
        }
        for (int count = 0; count < addressByOwner.size(); count++){
            if (addressByOwner.get(count).getOwnerType().toLowerCase().equals("camp")){
                campAddresses.add(addressByOwner.get(count));
            }
        }
        for(int count=0; count<campAddresses.size(); count++){
            if(campRepository.findByUsername(principal.getName()).getAddressId()==campAddresses.get(count).getId()){
                myCampAddress=campAddresses.get(count);
            }
        }
        String[] origins={myCampAddress.googleAddress()};

        for(int count = 0; count < userAddresses.size(); count++){

            String[] destinations={userAddresses.get(count).googleAddress()};

            double distance=0;
            try {
                DistanceMatrix results= DistanceMatrixApi.getDistanceMatrix(geoApiContextContainer.getContext(),origins,destinations).await();
                for(int i=0;i<results.destinationAddresses.length;i++) {
                    distance=results.rows[0].elements[i].distance.inMeters/1609.34;
                }
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(distance<=100){
                if(userApplicationRepository.findAllByLevelOfEduContainingOrderByUserIdDesc(search.getSearchValue()).contains(userApplicationRepository.findByAddressId(userAddresses.get(count).getId()))){
                    applications.add(userApplicationRepository.findByAddressId(userAddresses.get(count).getId()));
                    users.add(userRepository.findById(userApplicationRepository.findByAddressId(userAddresses.get(count).getId()).getUserId()));
                }
            }
        }
        search.setSearchAuthor(userRepository.findByUsername(principal.getName()).getUsername());
        search.setSearchUser(userRepository.findByUsername(principal.getName()).getId());
        searchRepository.save(search);
        model.addAttribute("applicants", applications);
        model.addAttribute("users", users);
        model.addAttribute("camp", campRepository.findByUsername(principal.getName()));
        userRepository.findByUsername(principal.getName()).setUrl("/search/applicants");
        userRepository.save(userRepository.findByUsername(principal.getName()));


        return "applicants";
    }



    @GetMapping("/search/camps")
    public String redirectSearchCamps(Model model, Principal principal){
        if(!userRepository.findByUsername(principal.getName()).getUrl().equals("/search/camps")){
            return "redirect:"+userRepository.findByUsername(principal.getName()).getUrl();
        }
        Search search = searchRepository.findBySearchAuthorOrderBySearchDateDesc(principal.getName());

        //ArrayList<User> users = new ArrayList();
        ArrayList<User> directors = new ArrayList();
        //ArrayList<UserApplication> applications = new ArrayList();
        ArrayList<Camp> camps = new ArrayList();
        ArrayList <Address> campAddresses = new ArrayList();
        ArrayList <Address> userAddresses = new ArrayList();
        List <Address> addressByOwner = addressRepository.findAllByOrderByOwnerIdDesc();
        Address myCampAddress=new Address();
        Address myUserAddress=new Address();
        for (int count = 0; count < addressByOwner.size(); count++){
            if (addressByOwner.get(count).getOwnerType().toLowerCase().equals("user")){
                userAddresses.add(addressByOwner.get(count));
            }
        }
        for (int count = 0; count < addressByOwner.size(); count++){
            if (addressByOwner.get(count).getOwnerType().toLowerCase().equals("camp")){
                campAddresses.add(addressByOwner.get(count));
            }
        }
        for(int count=0; count<userAddresses.size(); count++){
            if(userApplicationRepository.findByUsername(principal.getName()).getAddressId()==userAddresses.get(count).getId()){
                myUserAddress=userAddresses.get(count);
            }
        }
        String[] origins={myUserAddress.googleAddress()};

        for(int count = 0; count < campAddresses.size(); count++){

            String[] destinations={campAddresses.get(count).googleAddress()};

            double distance=0;
            try {
                DistanceMatrix results= DistanceMatrixApi.getDistanceMatrix(geoApiContextContainer.getContext(),origins,destinations).await();
                for(int i=0;i<results.destinationAddresses.length;i++) {
                    distance=results.rows[0].elements[i].distance.inMeters/1609.34;
                }
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(distance<=100){
                if(campRepository.findAllByDescriptionContainingOrderByDirectorIdDesc(search.getSearchValue()).contains(campRepository.findByAddressId(userAddresses.get(count).getId()))) {
                    if(campRepository.findByAddressId(userAddresses.get(count).getId()).isEnable()) {
                        camps.add(campRepository.findByAddressId(userAddresses.get(count).getId()));
                        directors.add(userRepository.findById(campRepository.findByAddressId(userAddresses.get(count).getId()).getDirectorId()));
                    }
                }
            }
        }

        model.addAttribute("camps", camps);
        model.addAttribute("directors", directors);
        userRepository.findByUsername(principal.getName()).setUrl("/search/camps");
        userRepository.save(userRepository.findByUsername(principal.getName()));




        return "camps";
    }
    @PostMapping("/accept")
    public String acceptToCamp(@Valid Accept accept, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:" + userRepository.findByUsername(principal.getName()).getUrl();
        }
        UserApplication userAppInQuestion = userApplicationRepository.findById(Integer.parseInt(accept.getAcceptValue()));
        Camp myCamp = campRepository.findByUsername(principal.getName());
        if (accept.getAcceptType().toLowerCase().equals("accept")) {
            userApplicationService.acceptCamp(myCamp, userAppInQuestion);
            userApplicationService.unrejectCamp(myCamp, userAppInQuestion);
        }
        if (accept.getAcceptType().toLowerCase().equals("reject")) {
            userApplicationService.unacceptCamp(myCamp, userAppInQuestion);
            userApplicationService.rejectCamp(myCamp, userAppInQuestion);
        }
        accept.setAcceptUser(userRepository.findByUsername(principal.getName()).getId());
        accept.setAcceptAuthor(userRepository.findByUsername(principal.getName()).getUsername());
        acceptRepository.save(accept);
        return "redirect:" + userRepository.findByUsername(principal.getName()).getUrl();
    }

    @PostMapping("/enable")
    public String enableCamp(@Valid Enable enable, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:" + userRepository.findByUsername(principal.getName()).getUrl();
        }
        Camp campInQuestion = campRepository.findById(Integer.parseInt(enable.getEnableValue()));
        if (enable.getEnableType().toLowerCase().equals("enable")) {
            campInQuestion.setEnable(true);
        }
        if (enable.getEnableType().toLowerCase().equals("disable")) {
            campInQuestion.setEnable(false);
        }
        enable.setEnableUser(userRepository.findByUsername(principal.getName()).getId());
        enable.setEnableAuthor(userRepository.findByUsername(principal.getName()).getUsername());
        enableRepository.save(enable);
        campRepository.save(campInQuestion);
        return "redirect:" + userRepository.findByUsername(principal.getName()).getUrl();
    }

    @GetMapping("/generate/applicants")
    public String generateApplicants(CampBuilder campBuilder, Model model, Principal principal){
        ArrayList<User> users = new ArrayList();
        ArrayList<UserApplication> applications = new ArrayList();
        ArrayList <Address> campAddresses = new ArrayList();
        ArrayList <Address> userAddresses = new ArrayList();
        List <Address> addressByOwner = addressRepository.findAllByOrderByOwnerIdDesc();
        Address myCampAddress=new Address();
        Address myUserAddress=new Address();
        for (int count = 0; count < addressByOwner.size(); count++){
            if (addressByOwner.get(count).getOwnerType().toLowerCase().equals("user")){
                userAddresses.add(addressByOwner.get(count));
            }
        }
        for (int count = 0; count < addressByOwner.size(); count++){
            if (addressByOwner.get(count).getOwnerType().toLowerCase().equals("camp")){
                campAddresses.add(addressByOwner.get(count));
            }
        }
        for(int count=0; count<campAddresses.size(); count++){
            if(campRepository.findByDirectorId(Integer.parseInt(campBuilder.getCampBuilderValue())).getAddressId()==campAddresses.get(count).getId()){
                myCampAddress=campAddresses.get(count);
            }
        }
        String[] origins={myCampAddress.googleAddress()};

        for(int count = 0; count < userAddresses.size(); count++){

            String[] destinations={userAddresses.get(count).googleAddress()};
            ArrayList<String> distances=new ArrayList<>();
            try {
                DistanceMatrix results= DistanceMatrixApi.getDistanceMatrix(geoApiContextContainer.getContext(),origins,destinations).await();
                for(int i=0;i<results.destinationAddresses.length;i++) {
                    distances.add("from "+results.originAddresses[0]+" to "+results.destinationAddresses[i]+" is "+(results.rows[0].elements[i].distance.inMeters/1609.34)+" miles");
                }
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if((Float.parseFloat(distances.get(0))*0.000621371)<=100){
                applications.add(userApplicationRepository.findByAddressId(userAddresses.get(count).getId()));
                users.add(userRepository.findById(userApplicationRepository.findByAddressId(userAddresses.get(count).getId()).getUserId()));
            }
        }

        campBuilder.setCampBuilderAuthor(principal.getName());
        campBuilder.setCampBuilderUser(userRepository.findByUsername(principal.getName()).getId());
        campBuilderRepository.save(campBuilder);
        model.addAttribute("applicants", applications);
        model.addAttribute("users", users);
        model.addAttribute("camp", campRepository.findByAddressId(myCampAddress.getId()));
        userRepository.findByUsername(principal.getName()).setUrl("/applicants");
        userRepository.save(userRepository.findByUsername(principal.getName()));


        return "applicants";
    }

 /*   @RequestMapping("/enable")
    public String enableCamps(Model model, Principal principal){

        //ArrayList<User> users = new ArrayList();
        ArrayList<User> directors = new ArrayList();
        //ArrayList<UserApplication> applications = new ArrayList();
        ArrayList<Camp> camps = new ArrayList();
        ArrayList <Address> campAddresses = new ArrayList();
        ArrayList <Address> userAddresses = new ArrayList();
        List <Address> addressByOwner = addressRepository.findAllByOrderByOwnerIdDesc();
        Address myCampAddress=new Address();
        Address myUserAddress=new Address();
        for (int count = 0; count < addressByOwner.size(); count++){
            if (addressByOwner.get(count).getOwnerType().toLowerCase().equals("user")){
                userAddresses.add(addressByOwner.get(count));
            }
        }
        for (int count = 0; count < addressByOwner.size(); count++){
            if (addressByOwner.get(count).getOwnerType().toLowerCase().equals("camp")){
                campAddresses.add(addressByOwner.get(count));
            }
        }

        for(int count = 0; count < campAddresses.size(); count++){
            camps.add(campRepository.findByAddressId(userAddresses.get(count).getId()));
            directors.add(userRepository.findById(campRepository.findByAddressId(userAddresses.get(count).getId()).getDirectorId()));
        }

        model.addAttribute("camps", camps);
        model.addAttribute("directors", directors);
        userRepository.findByUsername(principal.getName()).setUrl("/search/camps");
        userRepository.save(userRepository.findByUsername(principal.getName()));




        return "camps";
}*/

 
}
