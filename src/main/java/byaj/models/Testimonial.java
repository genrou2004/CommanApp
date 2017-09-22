package byaj.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by student on 7/17/17.
 */
@Entity
public class Testimonial {

    private int userId;

    private int campId;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="testimonial_id")
    private int id;

    @Column(name = "testimonial_date")
    private Date testimonialDate=new Date();

    @Lob
    @Type(type = "text")
    private String testimonialText;

    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy hh:mm a zzzz", Locale.US);
        return format.format(testimonialDate);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCampId() {
        return campId;
    }

    public void setCampId(int campId) {
        this.campId = campId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTestimonialDate() {
        return testimonialDate;
    }

    public void setTestimonialDate(Date testimonialDate) {
        this.testimonialDate = testimonialDate;
    }

    public String getTestimonialText() {
        return testimonialText;
    }

    public void setTestimonialText(String testimonialText) {
        this.testimonialText = testimonialText;
    }

}
