package com.example.sdp_bfit.workout;

public class Workout {
    private int Step;
    private Double Cal;
    private Double Dis;
    private String Date;


    //constructor
    public Workout(int Step, Double Cal, Double Dis, String Date) {
        this.Step = Step;
        this.Cal = Cal;
        this.Dis = Dis;
        this.Date = Date;


    }

    public Workout(){

    }

    //setter
    public void setStep(int Step){this.Step = Step;}
    public void setCal(Double Cal){this.Cal = Cal;}
    public void setDis(Double Dis){this.Dis = Dis;}

    //getter
    public int getStep(){return Step;}
    public Double getCal(){return Cal;}
    public Double getDis(){return Dis;}
    public String getDate(){return Date;}


    //TOSTRING
    public String toString(){
        return   "" +
                "Step: " + Step +"\r\n"+
                "Cal: " + Cal +"\r\n"+
                "Dis size: " + Dis +"\r\n"+
                "Date: " + Date  +"\r\n"+
                "";
    }
}
