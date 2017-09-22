package byaj.services;

import byaj.models.Camp;
import byaj.models.User;
import byaj.models.UserApplication;
import byaj.repositories.CampRepository;
import byaj.repositories.RoleRepository;
import byaj.repositories.UserApplicationRepository;
import byaj.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by student on 7/19/17.
 */
@Service
public class UserApplicationService {


    @Autowired
    UserApplicationRepository userApplicationRepository;

    @Autowired
    CampRepository campRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    public UserApplicationService(UserApplicationRepository userApplicationRepository) {
        this.userApplicationRepository = userApplicationRepository;
    }

    public void acceptCamp(Camp camp, UserApplication userApplication){
        //this userApplication is likedCamps other userApplication
        Collection<Camp> acceptedCamps=userApplication.getAccepted();
        if(!userApplication.acceptedContains(camp)) {
            acceptedCamps.add(camp);
            userApplication.setAccepted(acceptedCamps);
        }
        //other userApplication is being likers by this userApplication
        Collection<UserApplication> appsAccepted=camp.getAcceptThem();
        if(!camp.acceptThemContains(userApplication)) {
            appsAccepted.add(userApplication);
            camp.setAcceptThem(appsAccepted);
        }
        campRepository.save(camp);
        userApplicationRepository.save(userApplication);
    }

    public void unacceptCamp(Camp camp, UserApplication userApplication){
        Collection<UserApplication> rejectApplication;

        if( camp.acceptThemContains(userApplication)){
            rejectApplication=camp.getAcceptThem();
            rejectApplication.remove(userApplication);
            camp.setAcceptThem(rejectApplication);
        }
        Collection<Camp> denied;
        if (userApplication.acceptedContains(camp)){
            denied=userApplication.getAccepted();
            denied.remove(camp);
            userApplication.setAccepted(denied);
        }
        userApplicationRepository.save(userApplication);
        campRepository.save(camp);
    }

























    public void rejectCamp(Camp camp, UserApplication userApplication){
        //this userApplication is likedCamps other userApplication
        Collection<Camp> rejectedCamps=userApplication.getRejected();
        if(!userApplication.rejectedContains(camp)) {
            rejectedCamps.add(camp);
            userApplication.setRejected(rejectedCamps);
        }
        //other userApplication is being likers by this userApplication
        Collection<UserApplication> appsRejected=camp.getRejectThem();
        if(!camp.rejectThemContains(userApplication)) {
            appsRejected.add(userApplication);
            camp.setRejectThem(appsRejected);
        }
        campRepository.save(camp);
        userApplicationRepository.save(userApplication);
    }

    public void unrejectCamp(Camp camp, UserApplication userApplication){
        Collection<UserApplication> rejectApplication;

        if( camp.rejectThemContains(userApplication)){
            rejectApplication=camp.getRejectThem();
            rejectApplication.remove(userApplication);
            camp.setRejectThem(rejectApplication);
        }
        Collection<Camp> denied;
        if (userApplication.rejectedContains(camp)){
            denied=userApplication.getRejected();
            denied.remove(camp);
            userApplication.setRejected(denied);
        }
        userApplicationRepository.save(userApplication);
        campRepository.save(camp);
    }
    
    
}
