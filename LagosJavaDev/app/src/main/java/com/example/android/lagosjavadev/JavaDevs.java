package com.example.android.lagosjavadev;

/**
 * Created by Kayode Agboola on 12-Sep-17.
 */

public class JavaDevs {

    /** profile image uri of the developer */
    private String mImageUrl;

    /** username of the developer */
    private String mUsername;

    /** Github profile URL of the dev */
    private String mUrl;

    /**
     * Constructs a new JavaDevs object.
     *
     * @param imageUri is the profile image for the dev
     * @param username is the username of the dev
     * @param url is the github profile URL of the dev
     */
    public JavaDevs(String imageUri, String username, String url) {
        mImageUrl = imageUri;
        mUsername = username;
        mUrl = url;
    }

    //returns the profile image of the dev
    public String getImageUri() {
        return mImageUrl;
    }

    //returns the username of the dev
    public String getUsername() {
        return mUsername;
    }

     //Returns the github profile URL of the dev.
    public String getUrl() {
        return mUrl;
    }
}
