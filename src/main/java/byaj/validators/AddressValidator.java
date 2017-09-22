package byaj.validators;

import byaj.models.Address;
import byaj.services.GeoApiContextContainer;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.io.IOException;

/**
 * Created by student on 7/18/17.
 */
@Component
public class AddressValidator implements Validator {

    public boolean supports(Class<?> clazz){
        return Address.class.isAssignableFrom(clazz);
    }


    public void validate(Object target, Errors errors)
    {
        Address address=(Address) target;

        String streetAddress=address.getStreetAddress();
        String city=address.getCity();
        String state=address.getState();
        int zipCode= address.getZipCode();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "streetAddress", "streetAddress.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "city.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", "state.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zipCode", "zipCode.empty");

        if(state.length() <2){
            errors.rejectValue("state","state.tooShort");
        }
        if(streetAddress.length() <6){
            errors.rejectValue("streetAddress","streetAddress.tooShort");
        }
        if(zipCode <10000){
            errors.rejectValue("zipCode","zipCode.tooShort");
        }
        if(city.length() <1){
            errors.rejectValue("city","city.tooShort");
        }


        if(city.length() >100){
            errors.rejectValue("city","city.tooLong");
        }
        if(streetAddress.length() >100){
            errors.rejectValue("streetAddress","streetAddress.tooLong");
        }

        if(zipCode >100000){
            errors.rejectValue("zipCode","zipCode.tooLong");
        }
        if(state.length() >2){
            errors.rejectValue("state","state.tooLong");
        }


    }
    public void validateAddress(Object target, Errors errors, GeoApiContextContainer container)
    {

        Address address=(Address) target;

        String streetAddress=address.getStreetAddress();
        String city=address.getCity();
        String state=address.getState();
        int zipCode= address.getZipCode();



        GeocodingResult[] results = new GeocodingResult[0];
        String addressFormat=(streetAddress+", "+city+", "+state+" "+zipCode);
        try {
            results = GeocodingApi.geocode(container.getContext(), addressFormat).await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(results.length==0 || results[0].partialMatch)
        {
            errors.rejectValue("streetAddress","streetAddress.invalidAddress");

        }


    }





}
