package com.base7.jd.birdapp.datamodels;

/**
 * Created by jd on 28-Feb-17.
 */

public class Buyer {
    private String buyerName, buyerContactNo, buyerAddress, buyerBoughtBirdId;

    public Buyer() {
    }

    public Buyer(String buyerName, String buyerContactNo, String buyerAddress, String buyerBoughtBirdId) {
        this.buyerName = buyerName;
        this.buyerContactNo = buyerContactNo;
        this.buyerAddress = buyerAddress;
        this.buyerBoughtBirdId = buyerBoughtBirdId;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public void setBuyerContactNo(String buyerContactNo) {
        this.buyerContactNo = buyerContactNo;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public void setBuyerBoughtBirdId(String buyerBoughtBirdId) {
        this.buyerBoughtBirdId = buyerBoughtBirdId;
    }


    public String getBuyerName() {
        return buyerName;
    }

    public String getBuyerContactNo() {
        return buyerContactNo;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public String getBuyerBoughtBirdId() {
        return buyerBoughtBirdId;
    }

}
