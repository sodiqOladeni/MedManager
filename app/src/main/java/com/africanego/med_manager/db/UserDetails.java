package com.africanego.med_manager.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.provider.BaseColumns;

/**
 * Created by ${sodiqOladeni} on 3/25/2018.
 */

@Entity(tableName = UserDetails.TABLE_NAME)
@OnConflictStrategy()
public class UserDetails {

    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String TABLE_NAME = "user_details";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private long id;

    @ColumnInfo(name = "user_firstname")
    private String firstname;

    @ColumnInfo(name = "user_lastname")
    private String lastname;

    @ColumnInfo(name = "user_username")
    private String username;

    @ColumnInfo(name = "user_email")
    private String email;

    @ColumnInfo(name = "user_mobile")
    private String mobile;

    @ColumnInfo(name = "user_dob")
    private String dob;

    @ColumnInfo(name = "user_gender")
    private String gender;

    @ColumnInfo(name = "user_marital_status")
    private String marital_status;

    @ColumnInfo(name = "user_profile_picture")
    private String profile_picture;

    @ColumnInfo(name = "user_password")
    private String password;


    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public String getPassword() {
        return password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
