package com.example.bookstore.models;

public class User {
    private String id;
    private String fullName, phoneNumber, dateOfBirth, country, userName, password, profileImgUrl;
    private String email;

    public User(String id, String fullName, String phoneNumber, String dateOfBirth,
                String country, String userName, String password, String profileImgUrl, String email) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.userName = userName;
        this.password = password;
        this.profileImgUrl = profileImgUrl;
        this.email = email;
    }

    public User() {
    }
    public User(String userName, String password,String email){
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
    public User(String fullName, String phoneNumber, String dateOfBirth, String country,
                String userName, String password, String profileImgUrl, String email) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.userName = userName;
        this.password = password;
        this.profileImgUrl = profileImgUrl;
        this.email = email;
    }
    public User(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }
}
