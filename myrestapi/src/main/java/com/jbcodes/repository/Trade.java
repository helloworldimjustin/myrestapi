package com.jbcodes.repository;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Trade {

    private int id;
    private double profitLoss;
    private int shares;
    private String date;


    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public double getProfitLoss(){
        return profitLoss;
    }

    public void setProfitLoss(double profitLoss){
        this.profitLoss = profitLoss;
    }

    public int getShares(){
        return shares;

    }

    public void setShares(int shares){
        this.shares = shares;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    @Override
    public String toString(){
        return "trade object: "+this.id+" | "+this.profitLoss+" | "+this.shares+" | "+this.date;
    }


}
