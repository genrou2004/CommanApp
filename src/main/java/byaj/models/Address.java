package byaj.models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by student on 7/17/17.
 */
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="address_id")
    private int id;

    private String streetAddress;

    private String city;

    private String state;

    private int zipCode;
    
    private String ownerType;
    
    private int ownerId;

    @Column(name = "address_date")
    private Date addressDate =new Date();

    public Address(String streetAddress, String city, String state, int zipCode){
        this.streetAddress=streetAddress;
        this.city=city;
        this.state=state.toUpperCase();
        this.zipCode=zipCode;
    }
    public Address()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state.toUpperCase();
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public Date getAddressDate() {
        return addressDate;
    }

    public void setAddressDate(Date addressDate) {
        this.addressDate = addressDate;
    }
    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy hh:mm a zzzz", Locale.US);
        return format.format(addressDate);
    }
    
    public String getOwnerType(){
        return ownerType;
    }

    public void setOwnerType(String ownerType){
        this.ownerType = ownerType;
        
    }
    
    public int getOwnerId(){
        return ownerId;
    }

    public void setOwnerId(int ownerId){
        this.ownerId = ownerId;

    }

    public String googleAddress(){
        return String.format("%s, %s, %s %s", streetAddress, city, state, zipCode);
    }

}
