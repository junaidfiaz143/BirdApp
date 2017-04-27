package com.base7.jd.birdapp.datamodels;

/**
 * Created by jd on 20-Jan-17.
 */

public class Relation {
    private String maleId, femaleId, clutchId, noOfEggs;

    public Relation() {

    }

    public Relation(String maleId, String femaleId, String clutchId, String noOfEggs){
        this.maleId = maleId;
        this.femaleId = femaleId;
        this.clutchId = clutchId;
        this.noOfEggs = noOfEggs;
    }

    public void setMaleId(String maleId){
        this.maleId = maleId;
    }

    public void setFemaleId(String femaleId) {
        this.femaleId = femaleId;
    }

    public void setClutchId(String clutchId) {
        this.clutchId = clutchId;
    }

    public void setNoOfEggs(String noOfEggs) {
        this.noOfEggs = noOfEggs;
    }

    ////GETTERS////

    public String getMaleId(){
        return maleId;
    }

    public String getFemaleId() {
        return femaleId;
    }

    public String getClutchId() {
        return clutchId;
    }

    public String getNoOfEggs() {
        return noOfEggs;
    }
}
