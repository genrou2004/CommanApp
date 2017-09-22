package byaj.validators;

import byaj.models.Camp;
import byaj.models.User;
import byaj.repositories.UserRepository;
import org.hibernate.validator.internal.engine.groups.ValidationOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import org.springframework.validation.Validator;

import java.util.Date;


@Component
public class CampValidator implements Validator {

    @Autowired
    UserRepository userRepository;

    public boolean supports(Class<?> clazz){
        return Camp.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors){
        Camp camp = (Camp) target;

        //String directorName = camp.getDirectorName();
        String title = camp.getTitle();
        String description = camp.getDescription();
        String directorDescription = camp.getDirectorDescription();
        int startYear = camp.getStartYear();
        int startMonth = camp.getStartMonth();
        int startDay = camp.getStartDay();
        int endYear = camp.getEndYear();
        int endMonth = camp.getEndMonth();
        int endDay = camp.getEndDay();

        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "directorName", "camp.directorName.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "camp.title.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "camp.description.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "directorDescription", "camp.directorDescription.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startYear", "camp.startYear.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startMonth", "camp.startMonth.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDay", "camp.startDay.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endYear", "camp.endYear.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endMonth", "camp.endMonth.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDay", "camp.endDay.empty");

        if(title.length() < 3){
            errors.rejectValue("title","camp.title.tooShort");
        }

        if(description.length() < 3){
            errors.rejectValue("description","camp.description.tooShort");
        }

        if(directorDescription.length() < 3){
            errors.rejectValue("directorDescription","camp.directorDescription.tooShort");
        }

        //Checks Start Date
        
        if(startDay < 0 || startDay > 31){
            errors.rejectValue("startDay","camp.startDay.outOfBounds");
        }
        else{

        }


        //Month Input

        //System.out.println("Enter the startMonth you were born (ex. January 2, 2017 = 1): ");
        //checks if letters are input

        //checks if the startDay is valid
        if(startMonth < 1 || startMonth > 13){
            errors.rejectValue("startMonth","camp.startMonth.outOfBounds");
        }
        //checks if the startDay exists in this startMonth
        else if(((startDay>30)&&startMonth == 4)||((startDay>30)&&startMonth == 6)||((startDay>30)&&startMonth == 9)||((startDay>30)&&startMonth == 11)||((startDay>29)&&startMonth == 2)){
            errors.rejectValue("startDay","camp.startDay.outOfBoundsForMonth");
        }
        else{

        }
        //Year Input

        //System.out.println("Enter the startYear you were born (ex. January 2, 2017 = 2017): ");
        //checks if letters are input

        //checks if the user has given a date that hasn't occurred yet
        if(startYear<0){
            errors.rejectValue("startYear","camp.startYear.outOfBounds");
        }
        else if(((startYear%4) != 0) && (startDay > 28) && startMonth == 2){
            errors.rejectValue("startDay","camp.startMonth.outOfBoundsForMonthOfFebruary");
        }
        else{

        }
        camp.setStartDate(camp.createDate(startYear, startMonth, startDay));
        //Checks EndDate

        if(endDay < 0 || endDay > 31){
            errors.rejectValue("endDay","camp.endDay.outOfBounds");
        }
        else{

        }


        //Month Input

        //System.out.println("Enter the endMonth you were born (ex. January 2, 2017 = 1): ");
        //checks if letters are input

        //checks if the endDay is valid
        if(endMonth < 1 || endMonth > 13){
            errors.rejectValue("endMonth","camp.endMonth.outOfBounds");
        }
        //checks if the endDay exists in this endMonth
        else if(((endDay>30)&&endMonth == 4)||((endDay>30)&&endMonth == 6)||((endDay>30)&&endMonth == 9)||((endDay>30)&&endMonth == 11)||((endDay>29)&&endMonth == 2)){
            errors.rejectValue("endDay","camp.endDay.outOfBoundsForMonth");
        }
        else{

        }
        //Year Input

        //System.out.println("Enter the endYear you were born (ex. January 2, 2017 = 2017): ");
        //checks if letters are input

        //checks if the user has given a date that hasn't occurred yet
        if(endYear<0){
            errors.rejectValue("endYear","camp.endYear.outOfBounds");
        }
        else if(((endYear%4) != 0) && (endDay > 28) && endMonth == 2){
            errors.rejectValue("endDay","camp.endDay.outOfBoundsForMonthOfFebruary");
        }
        else{

        }

        camp.setEndDate(camp.createDate(endYear, endMonth, endDay));

        if(camp.getStartDate().compareTo(camp.getEndDate())>=0){
            errors.rejectValue("endDay","camp.endDay.lessThanOrEqualToStartDay");
        }

        if(camp.getStartDate().compareTo(new Date())<=0){
            errors.rejectValue("startDay","camp.startDay.lessThanOrEqualToCurrentDay");
        }
        if(camp.getEndDate().compareTo(new Date())<=0){
            errors.rejectValue("endDay","camp.endDay.lessThanOrEqualToCurrentDay");
        }
    }
}
