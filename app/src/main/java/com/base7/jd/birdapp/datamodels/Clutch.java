package com.base7.jd.birdapp.datamodels;

/**
 * Created by jd on 21-Jan-17.
 */

public class Clutch {

    private String maleId, femaleId, clutchId, noOfEggs, noOfFertilizeEggs;

    public Clutch() {
    }

    public Clutch(String maleId, String femaleId, String clutchId, String noOfEggs, String noOfFertilizeEggs) {
        this.maleId = maleId;
        this.femaleId = femaleId;
        this.clutchId = clutchId;
        this.noOfEggs = noOfEggs;
        this.noOfFertilizeEggs = noOfFertilizeEggs;
    }

    public void setMaleId(String maleId) {
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

    public void setNoOfFertilizeEggs(String noOfFertilizeEggs) {
        this.noOfFertilizeEggs = noOfFertilizeEggs;
    }

    ////GETTERS////

    public String getMaleId() {
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

    public String getNoOfFertilizeEggs() {
        return noOfFertilizeEggs;
    }
}
