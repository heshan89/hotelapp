package com.hotel.dto;

public class UsersDto {

    private int id;
    private String userId;
    private String userName;
    private boolean isPasswordChange;
    private String roleCode;
    private boolean isAskForPwReset;
    private boolean isEdit;
    private String password;

    public UsersDto() {
    }

    public UsersDto(int id, String userId, String userName, boolean isPasswordChange, String roleCode, boolean isAskForPwReset, boolean isEdit, String password) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.isPasswordChange = isPasswordChange;
        this.roleCode = roleCode;
        this.isAskForPwReset = isAskForPwReset;
        this.isEdit = isEdit;
        this.password = password;
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

    public boolean getIsAskForPwReset() {
        return isAskForPwReset;
    }

    public void setAskForPwReset(boolean askForPwReset) {
        isAskForPwReset = askForPwReset;
    }

    public boolean getIsEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UsersDto{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", isPasswordChange=" + isPasswordChange +
                ", roleCode='" + roleCode + '\'' +
                ", isAskForPwReset=" + isAskForPwReset +
                ", isEdit=" + isEdit +
                ", password='" + password + '\'' +
                '}';
    }
}
