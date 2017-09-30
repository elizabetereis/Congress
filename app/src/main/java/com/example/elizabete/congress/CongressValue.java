package com.example.elizabete.congress;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by elizabete on 24/09/17.
 */

public class CongressValue implements Serializable {

    private int mId;
    private String mName;
    private String mSubmissionDeadline;
    private String mReviewDeadline;

    public CongressValue(){

    }

    public int getId() {

        return mId;
    }

    public void setId(int id) {

        this.mId = id;
    }

    public String getName() {

        return mName;
    }

    public void setName(String name) {

        this.mName = name;
    }

    public String getSubmissionDeadline() {

        return mSubmissionDeadline;
    }

    public void setSubmissionDeadline(String submissionDeadline) {

        this.mSubmissionDeadline = submissionDeadline;
    }

    public String getReviewDeadline() {

        return mReviewDeadline;
    }

    public void setReviewDeadline(String reviewDeadline) {

        this.mReviewDeadline = reviewDeadline;
    }
}
