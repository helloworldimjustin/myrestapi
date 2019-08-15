package com.jbcodes.repository;

import java.util.Date;

public class ProfitLoss {

    float pl;

    Date date;

    public ProfitLoss(){}

    public ProfitLoss(float pl){
        this.pl =pl;
    }
    public ProfitLoss(float pl, Date date){
        this.pl =pl;
        this.date = date;
    }

    void setProfitLoss(float pl){
        this.pl = pl;
    }

    void setDate(Date date){
        this.date = date;
    }

    float getProfitLoss(){
        return pl;
    }
}
