package Model;

public class ReportMonthType {
    private String month;
    private String type;
    private int count;

    /**
     * Constructor for reportmonthtype model
     * @param month
     * @param type
     * @param count
     */
    public ReportMonthType(String month, String type, int count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
