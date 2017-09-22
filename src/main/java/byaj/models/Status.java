package byaj.models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by student on 7/17/17.
 */
@Entity
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="status_id")
    private int id;

    private int campId;

    private int userAppId;

    private int userId;

    private String statusInfo;

    @Column(name = "status_date")
    private Date statusDate =new Date();

    private Date gradDate;

    public String getFormatStatusDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy hh:mm a zzzz", Locale.US);
        return format.format(statusDate);
    }

    public String getFormatGradDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy hh:mm a zzzz", Locale.US);
        return format.format(gradDate);
    }

    public int getCampId() {
        return campId;
    }

    public void setCampId(int campId) {
        this.campId = campId;
    }

    public int getUserAppId() {
        return userAppId;
    }

    public void setUserAppId(int userAppId) {
        this.userAppId = userAppId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String isApproval() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Date getGradDate() {
        return gradDate;
    }

    public void setGradDate(Date gradDate) {
        this.gradDate = gradDate;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusInfo() {
        return statusInfo;
    }
}
