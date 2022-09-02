package com.hotel.input;

public class UserInput {

    private String userId;
    private String userName;
    private String roleCode;
    private String password;
    private String createdBy;

    public UserInput() {
    }

    public UserInput(String userId, String userName, String roleCode, String password, String createdBy) {
        this.userId = userId;
        this.userName = userName;
        this.roleCode = roleCode;
        this.password = password;
        this.createdBy = createdBy;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "UserInput{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", roleCode='" + roleCode + '\'' +
                ", password='" + password + '\'' +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
