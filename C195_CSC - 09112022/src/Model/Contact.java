package Model;

public class Contact {
    private int contactID;
    private String contactName;
    private String contactEmail;

    /**
     * Constructor for appointment
     * @param contactID
     * @param contactName
     * @param contactEmail
     */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Gets contact's ID from database.
     * @return contactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets the contact's ID in the database
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Gets contact's name from database.
     * @return contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the contact's name in the database
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Gets contact's email from database.
     * @return contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Sets the contact's email in the database
     * @param contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * Displays the contact's name as a string instead of a reference to the contact object.
     * @return
     */
    @Override
    public String toString(){
        return this.getContactName();
    }
}
