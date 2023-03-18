package Model;

public class Report {
    long appointmentDate;
    String appointmentType;
    long appointmentTotal;

    /**
     * Constructor for report model
     * @param appointmentDate
     * @param appointmentType
     * @param appointmentTotal
     */
    public Report(long appointmentDate, String appointmentType, long appointmentTotal) {
        this.appointmentDate = appointmentDate;
        this.appointmentType = appointmentType;
        this.appointmentTotal = appointmentTotal;
    }

    public Report(){
    }

    /**
     * Get appointment type from database
     * @return appointmentType
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * Set appointment type from database
     * @param appointmentType
     */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    /**
     * Get appointment date from database
     * @return appointmentDate
     */
    public long getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Set appointment date from database
     * @param appointmentDate
     */
    public void setAppointmentDate(long appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * Get appointment total in database
     * @return
     */
    public long getAppointmentTotal() {
        return appointmentTotal;
    }

    public void setAppointmentTotal(long appointmentTotal) {
        this.appointmentTotal = appointmentTotal;
    }

    public String toString(){
        return String.valueOf(appointmentDate);
    }
}