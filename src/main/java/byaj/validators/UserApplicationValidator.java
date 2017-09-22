package byaj.validators;

import byaj.models.User;
import byaj.models.UserApplication;
import byaj.repositories.UserApplicationRepository;
import byaj.repositories.UserRepository;
import org.hibernate.validator.internal.engine.groups.ValidationOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import org.springframework.validation.Validator;


@Component
public class UserApplicationValidator implements Validator {

    @Autowired
    UserApplicationRepository userApplicationRepository;

    public boolean supports(Class<?> clazz){
        return UserApplication.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors){
        UserApplication userApplication = (UserApplication) target;

        String description = userApplication.getDescription();
        String levelOfEdu = userApplication.getLevelOfEdu();
        int gradYear = userApplication.getGradYear();
        int gradMonth = userApplication.getGradMonth();
        int gradDay = userApplication.getGradDay();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "userApplication.description.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "levelOfEdu", "userApplication.levelOfEdu.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gradYear", "userApplication.gradYear.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gradMonth", "userApplication.gradMonth.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gradDay", "userApplication.gradDay.empty");

        if(description.length() < 5){
            errors.rejectValue("description","userApplication.description.tooShort");
        }

//Checks GradDate

        if(gradDay < 0 || gradDay > 31){
            errors.rejectValue("gradDay","userApplication.gradDay.outOfBounds");
        }
        else{

        }


        //Month Input

        //System.out.println("Enter the gradMonth you were born (ex. January 2, 2017 = 1): ");
        //checks if letters are input

        //checks if the gradDay is valid
        if(gradMonth < 1 || gradMonth > 13){
            errors.rejectValue("gradMonth","userApplication.gradMonth.outOfBounds");
        }
        //checks if the gradDay exists in this gradMonth
        else if(((gradDay>30)&&gradMonth == 4)||((gradDay>30)&&gradMonth == 6)||((gradDay>30)&&gradMonth == 9)||((gradDay>30)&&gradMonth == 11)||((gradDay>29)&&gradMonth == 2)){
            errors.rejectValue("gradDay","userApplication.gradDay.outOfBoundsForMonth");
        }
        else{

        }
        //Year Input

        //System.out.println("Enter the gradYear you were born (ex. January 2, 2017 = 2017): ");
        //checks if letters are input

        //checks if the user has given a date that hasn't occurred yet
        if(gradYear<0){
            errors.rejectValue("gradYear","userApplication.gradYear.outOfBounds");
        }
        else if(((gradYear%4) != 0) && (gradDay > 28) && gradMonth == 2){
            errors.rejectValue("gradDay","userApplication.gradDay.outOfBoundsForMonthOfFebruary");
        }
        else{

        }


    }
}
