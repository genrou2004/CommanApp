package byaj.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by student on 6/27/17.
 */
@Entity
public class EmailInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@NotNull
    //@Min(1)
    private int emailID;

    private String emailValue;

    private String emailType;

    private String emailAuthor;

    @Column(columnDefinition="integer default -1")
    private int emailUser;

    private Date emailDate=new Date();


    public int getEmailID() {
        return emailID;
    }

    /*public void setMateID(int emailID) {
        this.emailID = emailID;
    }*/
    public String getEmailValue() {
        return emailValue;
    }

    public void setEmailValue (String emailValue) {
        this.emailValue = emailValue;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType (String emailType) {
        this.emailType = emailType;
    }

    public String getEmailAuthor() {
        return emailAuthor;
    }

    public void setEmailAuthor (String emailAuthor) {
        this.emailAuthor = emailAuthor;
    }

    public int getEmailUser() {
        return emailUser;
    }

    public void setEmailUser (int emailUser) {
        this.emailUser = emailUser;
    }

    public Date getEmailDate() {
        return emailDate;
    }


    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy hh:mm a zzzz", Locale.US);
        return format.format(emailDate);
    }
}