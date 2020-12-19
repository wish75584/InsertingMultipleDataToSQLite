package com.stubborn.insertingmultipledatatosqlite;

import com.google.firebase.firestore.FieldValue;

public class Modal {
    String database_date, id, sms_body;
    FieldValue createdAt;

    public Modal(String database_date, String id, String sms_body, FieldValue createdAt) {
        this.database_date = database_date;
        this.id = id;
        this.sms_body = sms_body;
        this.createdAt = createdAt;
    }

    public String getDatabase_date() {
        return database_date;
    }

    public void setDatabase_date(String database_date) {
        this.database_date = database_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSms_body() {
        return sms_body;
    }

    public void setSms_body(String sms_body) {
        this.sms_body = sms_body;
    }

    public FieldValue getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(FieldValue createdAt) {
        this.createdAt = createdAt;
    }
}


