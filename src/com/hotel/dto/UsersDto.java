package com.hotel.dto;

public class UsersDto {

    private String userId;
    private String userName;
    private boolean isPasswordChange;
    private String roleCode;

    public UsersDto() {
    }

    public UsersDto(String userId, String userName, boolean isPasswordChange, String roleCode) {
        this.userId = userId;
        this.userName = userName;
        this.isPasswordChange = isPasswordChange;
        this.roleCode = roleCode;
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

    public boolean isPasswordChange() {
        return isPasswordChange;
    }

    public void setPasswordChange(boolean passwordChange) {
        isPasswordChange = passwordChange;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Override
    public String toString() {
        return "UsersDto{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", isPasswordChange='" + isPasswordChange + '\'' +
                ", roleCode='" + roleCode + '\'' +
                '}';
    }
}
