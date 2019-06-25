/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql.masonryAdmin.admin;

import java.util.Date;

/**
 *
 * @author CR104978
 */
public class DtoUser {

    private int id;
    private String code;
    private String nickName;
    private String fullName;
    private String email;
    private String password;
    private boolean menuAdmin;
    private boolean menuProdAdmin;
    private boolean menuProdComp;
    private Date created;
    private String createdBy;
    private Date modified;
    private String modifiedBy;
    private boolean active;
    private String status;

    public DtoUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isMenuAdmin() {
        return menuAdmin;
    }

    public void setMenuAdmin(boolean menuAdmin) {
        this.menuAdmin = menuAdmin;
    }

    public boolean isMenuProdAdmin() {
        return menuProdAdmin;
    }

    public void setMenuProdAdmin(boolean menuProdAdmin) {
        this.menuProdAdmin = menuProdAdmin;
    }

    public boolean isMenuProdComp() {
        return menuProdComp;
    }

    public void setMenuProdComp(boolean menuProdComp) {
        this.menuProdComp = menuProdComp;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}