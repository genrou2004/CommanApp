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
public class CampBuilder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@NotNull
    //@Min(1)
    private int acceptID;

    private String acceptValue;

    private String acceptType;

    private String acceptAuthor;

    @Column(columnDefinition="integer default -1")
    private int acceptUser;

    private Date acceptDate=new Date();


    public int getCampBuilderID() {
        return acceptID;
    }

    /*public void setMateID(int acceptID) {
        this.acceptID = acceptID;
    }*/
    public String getCampBuilderValue() {
        return acceptValue;
    }

    public void setCampBuilderValue (String acceptValue) {
        this.acceptValue = acceptValue;
    }

    public String getCampBuilderType() {
        return acceptType;
    }

    public void setCampBuilderType (String acceptType) {
        this.acceptType = acceptType;
    }

    public String getCampBuilderAuthor() {
        return acceptAuthor;
    }

    public void setCampBuilderAuthor (String acceptAuthor) {
        this.acceptAuthor = acceptAuthor;
    }

    public int getCampBuilderUser() {
        return acceptUser;
    }

    public void setCampBuilderUser (int acceptUser) {
        this.acceptUser = acceptUser;
    }

    public Date getCampBuilderDate() {
        return acceptDate;
    }


    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy hh:mm a zzzz", Locale.US);
        return format.format(acceptDate);
    }
}