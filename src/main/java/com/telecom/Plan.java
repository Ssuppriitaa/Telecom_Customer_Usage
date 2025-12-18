package main.java.com.telecom;

public class Plan {
    private int plan_id;
    private double monthly_fee;
    private int data_limit_gb;
    private int call_limit_minutes;
    private int duration;

    public Plan() {
    }

    public Plan(double rate, int data, int duration, int callLimit) {
        this.monthly_fee = rate;
        this.data_limit_gb = data;
        this.call_limit_minutes = callLimit;
        this.duration = duration;
    }

    public Plan(int id, double rate, int data, int duration, int callLimit) {
        this.plan_id = id;
        this.monthly_fee = rate;
        this.data_limit_gb = data;
        this.call_limit_minutes = callLimit;
        this.duration = duration;
    }

    // Getters and Setters
    public int getId() {
        return plan_id;
    }

    public void setId(int id) {
        this.plan_id = id;
    }

    public double getRate() {
        return monthly_fee;
    }

    public void setRate(double rate) {
        this.monthly_fee = rate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getData_limit_gb() {
        return data_limit_gb;
    }

    public void setData_limit_gb(int data_limit_gb) {
        this.data_limit_gb = data_limit_gb;
    }

    public int getCall_limit_minutes() {
        return call_limit_minutes;
    }

    public void setCall_limit_minutes(int call_limit_minutes) {
        this.call_limit_minutes = call_limit_minutes;
    }

    @Override
    public String toString() {
        return "Plan{id=" + plan_id + ", rate=" + monthly_fee + ", Data limit(gb)=" + data_limit_gb
                + ", Call limit(min)=" + call_limit_minutes + ", duration=" + duration + "}";
    }

}
/* By : Name: Suprita Thakur
     Email: thakursuprita30@gmail.com */