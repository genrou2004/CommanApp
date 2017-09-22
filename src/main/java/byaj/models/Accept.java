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
public class Accept {
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


    public int getAcceptID() {
        return acceptID;
    }

    /*public void setMateID(int acceptID) {
        this.acceptID = acceptID;
    }*/
    public String getAcceptValue() {
        return acceptValue;
    }

    public void setAcceptValue (String acceptValue) {
        this.acceptValue = acceptValue;
    }

    public String getAcceptType() {
        return acceptType;
    }

    public void setAcceptType (String acceptType) {
        this.acceptType = acceptType;
    }

    public String getAcceptAuthor() {
        return acceptAuthor;
    }

    public void setAcceptAuthor (String acceptAuthor) {
        this.acceptAuthor = acceptAuthor;
    }

    public int getAcceptUser() {
        return acceptUser;
    }

    public void setAcceptUser (int acceptUser) {
        this.acceptUser = acceptUser;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }


    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy hh:mm a zzzz", Locale.US);
        return format.format(acceptDate);
    }
}