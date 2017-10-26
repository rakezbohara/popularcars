package com.techneekfactory.popularcars.popularcars.extras;

/**
 * Created by root on 10/22/17.
 */

public class Makes {

    int makeID;
    String makes;
    String imageUrl;

    public Makes(int makeID, String makes, String imageUrl) {
        this.makeID = makeID;
        this.makes = makes;
        this.imageUrl = imageUrl;
    }

    public int getMakeID() {
        return makeID;
    }

    public void setMakeID(int makeID) {
        this.makeID = makeID;
    }

    public String getMakes() {
        return makes;
    }

    public void setMakes(String makes) {
        this.makes = makes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
