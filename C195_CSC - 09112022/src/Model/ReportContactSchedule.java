package Model;

import java.sql.Date;

public class ReportContactSchedule {
    private int contact;
    private int appID;
    private String appTitle;
    private String appType;
    private Date appStartDate;
    private Date appEndDate;
    private int custID;

    public ReportContactSchedule(int contact, int appID, String appTitle, String appType, Date appStartDate, Date appEndDate, int custID) {
        this.contact = contact;
        this.appID = appID;
        this.appTitle = appTitle;
        this.appType = appType;
        this.appStartDate = appStartDate;
        this.appEndDate = appEndDate;
        this.custID = custID;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public Date getAppStartDate() {
        return appStartDate;
    }

    public void setAppStartDate(Date appStartDate) {
        this.appStartDate = appStartDate;
    }

    public Date getAppEndDate() {
        return appEndDate;
    }

    public void setAppEndDate(Date appEndDate) {
        this.appEndDate = appEndDate;
    }

    public int getCustID() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID = custID;
    }
}
