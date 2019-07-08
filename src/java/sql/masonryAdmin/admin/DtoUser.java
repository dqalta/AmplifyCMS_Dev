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

    int id;
    String codeUser;
    String guid;
    String fullName;
    String email;
    String passwordUser;
    boolean menuAdmin;
    boolean menuProdAdmin;
    boolean menuProdComp;
    Date created;
    String createdBy;
    Date modified;
    String modifiedBy;
    boolean active;
    Date lastLogin;
    
    public DtoUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeUser() {
        return codeUser;
    }

    public void setCodeUser(String codeUser) {
        this.codeUser = codeUser;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
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

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
