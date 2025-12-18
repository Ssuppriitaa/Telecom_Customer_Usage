package main.java.com.telecom;//

public class UsageRecord {
    private int usage_id;
    private int customer_id;
    private java.util.Date month_year;
    private int data_used_gb;
    private int call_used_min;
    private double dataLeft;
    private int daysLeft;

    public UsageRecord() {}

    public UsageRecord(double dataLeft, int daysLeft) {
        this.dataLeft = dataLeft;
        this.daysLeft = daysLeft;
    }

    // Getters and Setters
    public double getDataLeft() {
        return dataLeft;
    }

    public void setDataLeft(double dataLeft) {
        this.dataLeft = dataLeft;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    @Override
    public String toString() {
        return "UsageRecord{dataLeft=" + dataLeft + ", daysLeft=" + daysLeft + ", usage_id=" + usage_id + ", customer_id=" + customer_id + ", month_year=" + month_year + ", data_used_gb=" + data_used_gb + ", call_used_min=" + call_used_min + "}";
    }
}
/* By : Name: Suprita Thakur
     Email: thakursuprita30@gmail.com */