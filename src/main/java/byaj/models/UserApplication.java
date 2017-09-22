package byaj.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by student on 7/17/17.
 */
@Entity
public class UserApplication {

    private String username;

    private String fullName;

    private int userId;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="application_id")
    private int id;

    @Column(name = "userApp_date")
    private Date userAppDate =new Date();

    @Lob
    @Type(type = "text")
    private String description;

    private String levelOfEdu;

    private int gradYear;

    private int gradMonth;

    private int gradDay;
    
    private Date gradDate;

    private int addressId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="accepted",joinColumns = @JoinColumn(name = "user_application_id"),inverseJoinColumns = @JoinColumn(name = "camp_id"))
    private Collection<Camp> accepted;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="rejected",joinColumns = @JoinColumn(name = "user_application_id"),inverseJoinColumns = @JoinColumn(name = "camp_id"))
    private Collection<Camp> rejected;

    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy hh:mm a zzzz", Locale.US);
        return format.format(userAppDate);
    }

    public String getFormatGradDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy", Locale.US);
        return format.format(gradDate);
    }

    public Date createDate(int year,int month,int day)
    {
        GregorianCalendar cal=new GregorianCalendar(year,(month-1),day);
        return cal.getTime();

    }


    public int getGradYear() {
        return gradYear;
    }

    public void setGradYear(int gradYear) {
        this.gradYear = gradYear;
    }

    public int getGradMonth() {
        return gradMonth;
    }

    public void setGradMonth(int gradMonth) {
        this.gradMonth = gradMonth;
    }

    public int getGradDay() {
        return gradDay;
    }

    public void setGradDay(int gradDay) {
        this.gradDay = gradDay;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getUserAppDate() {
        return userAppDate;
    }

    public void setUserAppDate(Date userAppDate) {
        this.userAppDate = userAppDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevelOfEdu() {
        return levelOfEdu;
    }

    public void setLevelOfEdu(String levelOfEdu) {
        this.levelOfEdu = levelOfEdu;
    }

    public Date getGradDate() {
        return gradDate;
    }

    public void setGradDate(Date gradDate) {
        this.gradDate = gradDate;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public Collection<Camp> getAccepted() {
        return accepted;
    }

    public void setAccepted(Collection<Camp> accepted) {
        this.accepted = accepted;
    }


    public Collection<Camp> getRejected() {
        return rejected;
    }

    public void setRejected(Collection<Camp> rejected) {
        this.rejected = rejected;
    }

    public boolean acceptedContains(Camp camp){
        if(accepted.contains(camp)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean rejectedContains(Camp camp){
        if(rejected.contains(camp)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean decidedContains(Camp camp){
        if(rejected.contains(camp)||acceptedContains(camp)){
            return true;
        }
        else{
            return false;
        }
    }
}
