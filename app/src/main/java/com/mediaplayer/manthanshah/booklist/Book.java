package com.mediaplayer.manthanshah.booklist;

public class Book {

    private String mTitle;
    private String mAuthor;
    private String mDescription;
    private String mBuyLink;

    public Book(String mTitle, String mAuthor, String mDescription, String mBuyLink) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mDescription = mDescription;
        this.mBuyLink = mBuyLink;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmBuyLink() {
        return mBuyLink;
    }
}
