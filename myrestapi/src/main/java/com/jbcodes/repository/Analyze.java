package com.jbcodes.repository;

import java.io.FileReader;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;


public class Analyze {

    private static HashMap<String, ArrayList<ProfitLoss>> data;
    private static int total;
    private static ArrayList<Float> plar = new ArrayList<Float>();

    private String filePath = "/Users/justinbullock/Desktop/Software Engineering/Java Projects/Realized-Table 1.csv";

    private float averagePl;
    private double medianPl;
    private double plRatio;



    public Analyze(){
        this.data = Analyze.extractDataLiveAcct(this.filePath);
        this.averagePl = Analyze.getMedianProfitLoss();
        this.medianPl = Analyze.getMedianProfitLoss();
        this.plRatio = Analyze.getProfitLossRatio();
    }

    public float getAveragePl() {
        return averagePl;
    }

    public double getMedianPl() {
        return medianPl;
    }

    public double getPlRatio() {
        return plRatio;
    }

    public ArrayList<Float> getPlar() {
        return plar;
    }

    // == helper functions  ==

    private static HashMap< String, ArrayList<ProfitLoss> > extractDataLiveAcct(String filePath){
        HashMap<String, ArrayList<ProfitLoss>> data = new HashMap<String, ArrayList<ProfitLoss>>();
        int i;
        String str = "";
        char c;
        boolean qFlag = false;
        int count = 1;
        int errorCount = 0;
        String security = "empty";
        float profitLoss;
        Date date = new Date();
        try {
            FileReader fr = new FileReader(filePath);
            PushbackReader pbr = new PushbackReader(fr);

            while ((i = pbr.read()) != -1) {

                c = (char) i;
                if (!qFlag && (c == ',' || c == '\n')) {

                    if (c == '\n') {
                        count = 0;
                        total++;
                    }

                    if (c == ',') {
                        if (str != "") {

                            //name of security
                            if (count == 1) { security = str; }
                            //date of trade
                            if (count == 6) { date = extractDate(str); }
                            //profit/loss value of security
                            if (count == 8) {
                                profitLoss = Analyze.stringToFloat(str);
                                //if security not in map
                                if (data.get(security) != null) {
                                    data.get(security).add(new ProfitLoss(profitLoss, date));
                                }
                                //if security is in map
                                else {
                                    data.put(security, new ArrayList<ProfitLoss>());
                                    data.get(security).add(new ProfitLoss(profitLoss, date));
                                }
                            }
                        }
                    }

                    count++;
                    str = "";
                    continue;
                }

                if (c == '"') {
                    if (qFlag) {
                        qFlag = false;
                    } else {
                        qFlag = true;
                    }
                }

                str += c;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }
    private HashMap< String, ArrayList<ProfitLoss> > extractDataSimAcct(String filePath){
        HashMap<String, ArrayList<ProfitLoss>> data = new HashMap<String, ArrayList<ProfitLoss>>();
        int i;
        String str = "";
        char c;
        boolean qFlag = false;
        int count = 1;
        int errorCount = 0;
        String security = "empty";
        float profitLoss;
        Date date = new Date();
        try {
            FileReader fr = new FileReader(filePath);
            PushbackReader pbr = new PushbackReader(fr);

            while ((i = pbr.read()) != -1) {

                c = (char) i;
                if (!qFlag && (c == ',' || c == '\n')) {

                    if (c == '\n') {
                        count = 0;
                        total++;
                    }

                    if (c == ',') {
                        if (str != "") {
                            //name of security
                            if (count == 2) {
                                security = str;
                                System.out.println(security);
                            }
                            //profit/loss value of security
                            if (count == 6) {
                                System.out.println(str);
                                profitLoss = this.stringToFloat(str);
                                //if security not in map
                                if (data.get(security) != null) {
                                    data.get(security).add(new ProfitLoss(profitLoss));
                                }
                                //if security is in map
                                else {
                                    data.put(security, new ArrayList<ProfitLoss>());
                                    data.get(security).add(new ProfitLoss(profitLoss));
                                }
                            }
                        }
                    }

                    count++;
                    str = "";
                    continue;
                }

                if (c == '"') {
                    if (qFlag) {
                        qFlag = false;
                    } else {
                        qFlag = true;
                    }
                }

                str += c;
            }
        }catch(Exception e){

        }
        return data;
    }
    private static Date extractDate(String str){

        int day = 0;
        int month = 0;
        int year = 0;
        int count = 0;
        int temp = 0;
        String parseStr = "";
        char[] dateArray = str.toCharArray();

        for(char c : dateArray){
            if(c == '/') {

                temp = Integer.parseInt(parseStr);
                if(count == 0){
                    month = temp;
                    //System.out.print(month+" : ");
                }
                if(count == 1){
                    day = temp;
                    //System.out.print(day+" : ");
                }
                parseStr = "";
                count++;
            }
            else{ parseStr+=c; }
        }
        year = Integer.parseInt(parseStr);
        year +=2000;
        //System.out.print(year);
        //System.out.println();

        return new Date(year, month-1, day);
    }
    private static float stringToFloat(String str){
        float num = 0;

        if( (str.charAt(0) == '(') && (str.charAt(str.length()-1) == ')') ){
            num = -1*( Float.parseFloat( str.substring(1, str.length()-1 ) ) );
        }else{
            num = Float.parseFloat(str);
        }
        return num;
    }

    // == core functions ==

    private float getAverageProfitLoss(){

        float sum = 0;
        total = 0;
        ArrayList<ProfitLoss> temp;
        for(String security : this.data.keySet()){
            temp = this.data.get(security);
            for(ProfitLoss pl : temp ){
                sum+= pl.getProfitLoss();
                total++;
            }
        }

        return sum/total;



    }

    private static float getMedianProfitLoss() {

        if(total > 0){
            ArrayList<ProfitLoss> temp;
            for(String security : Analyze.data.keySet()){
                temp = Analyze.data.get(security);
                for(ProfitLoss pl : temp ){
                    plar.add(pl.getProfitLoss());
                }
            }
        }

        Collections.sort(plar);

        return plar.get(total/2);
    }

    private static double getProfitLossRatio() {

        if(plar.isEmpty()){ return -1; }

        Collections.sort(plar);

        //int firstNonNeg = findFirstNonNegative(plar.size(), 0, plar.size()-1);

        int losses = 0;
        int gains = 0;
        for(float f : plar){
            if(f < 0){ losses+= f; }
            else{ gains+=f; }
        }

        return ((double) gains)/((double) (-1*losses));

    }
}