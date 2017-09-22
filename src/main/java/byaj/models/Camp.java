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
public class Camp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="camp_id")
    private int id;

    private int directorId;

    private String directorName;

    private String username;

    private int startYear;

    private int startMonth;

    private int startDay;

    private int endYear;

    private int endMonth;

    private int endDay;

    private Date startDate;

    private Date endDate;

    private String title;

    private int addressId;

    @Lob
    @Type(type = "text")
    private String description;

    @Lob
    @Type(type = "text")
    private String testimonials;

    private boolean enable;

    @Lob
    @Type(type = "text")
    private String directorDescription;

    private String campEmail;

    @Column(name = "camp_date")
    private Date campDate=new Date();

    @ManyToMany(mappedBy = "accepted")
    private Collection<UserApplication> acceptThem;

    @ManyToMany(mappedBy = "rejected")
    private Collection<UserApplication> rejectThem;

    public String getFormatCampDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy hh:mm a zzzz", Locale.US);
        return format.format(campDate);
    }

    public String getFormatStartDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy", Locale.US);
        return format.format(startDate);
    }
    public String getFormatEndDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy", Locale.US);
        return format.format(endDate);
    }


    public Date createDate(int year,int month,int day)
    {
        GregorianCalendar cal=new GregorianCalendar(year,(month-1),day);
        return cal.getTime();

    }



    public Date getCampDate() {
        return campDate;
    }

    public void setCampDate(Date campDate) {
        this.campDate = campDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getEndDay() {
        return endDay;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getDirectorDescription() {
        return directorDescription;
    }

    public void setDirectorDescription(String directorDescription) {
        this.directorDescription = directorDescription;
    }

    public String getCampEmail() {
        return campEmail;
    }

    public void setCampEmail(String campEmail) {
        this.campEmail = campEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<UserApplication> getAcceptThem() {
        return acceptThem;
    }

    public void setAcceptThem(Collection<UserApplication> acceptThem) {
        this.acceptThem = acceptThem;
    }

    public Collection<UserApplication> getRejectThem() {
        return rejectThem;
    }

    public void setRejectThem(Collection<UserApplication> rejectThem) {
        this.rejectThem = rejectThem;
    }

    public boolean acceptThemContains(UserApplication userApplication){
        if(acceptThem.contains(userApplication)){
            return true;
        }
        else{
            return false;
        }
    }


    public boolean rejectThemContains(UserApplication userApplication){
        if(rejectThem.contains(userApplication)){
            return true;
        }
        else{
            return false;
        }
    }

    public String getTestimonials() {
        return testimonials;
    }

    public void setTestimonials(String testimonials) {
        this.testimonials = testimonials;
    }
}
