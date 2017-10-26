package com.techneekfactory.popularcars.popularcars.extras;

/**
 * Created by arafat on 17/08/17.
 */

public class SpecialOffers {

    private int offerID;
    private String offerTitle, image;

    public int getOfferID() {
        return offerID;
    }

    public void setOfferID(int offerID) {
        this.offerID = offerID;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public SpecialOffers(int offerID, String offerTitle, String image) {

        this.offerID = offerID;
        this.offerTitle = offerTitle;
        this.image = image;
    }
}
