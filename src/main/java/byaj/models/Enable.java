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
public class Enable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@NotNull
    //@Min(1)
    private int enableID;

    private String enableValue;

    private String enableType;

    private String enableAuthor;

    @Column(columnDefinition="integer default -1")
    private int enableUser;

    private Date enableDate=new Date();


    public int getEnableID() {
        return enableID;
    }

    /*public void setMateID(int enableID) {
        this.enableID = enableID;
    }*/
    public String getEnableValue() {
        return enableValue;
    }

    public void setEnableValue (String enableValue) {
        this.enableValue = enableValue;
    }

    public String getEnableType() {
        return enableType;
    }

    public void setEnableType (String enableType) {
        this.enableType = enableType;
    }

    public String getEnableAuthor() {
        return enableAuthor;
    }

    public void setEnableAuthor (String enableAuthor) {
        this.enableAuthor = enableAuthor;
    }

    public int getEnableUser() {
        return enableUser;
    }

    public void setEnableUser (int enableUser) {
        this.enableUser = enableUser;
    }

    public Date getEnableDate() {
        return enableDate;
    }


    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy hh:mm a zzzz", Locale.US);
        return format.format(enableDate);
    }
}