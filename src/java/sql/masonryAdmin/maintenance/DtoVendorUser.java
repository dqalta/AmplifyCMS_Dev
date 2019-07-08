/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql.masonryAdmin.maintenance;

import java.util.Date;

/**
 *
 * @author CR104978
 */
public class DtoVendorUser {

    private int id;
    private String codeVendorUser;
    private String nickName;
    private String fullName;
    private String email;
    private String passwordVendorUser;
    private String idVendor;
//    private boolean menuAdmin;
//    private boolean menuProdAdmin;
    private Date created;
    private String createdBy;
    private Date modified;
    private String modifiedBy;
    private boolean active;
    private String statusVendorUser;

    public DtoVendorUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeVendorUser() {
        return codeVendorUser;
    }

    public void setCodeVendorUser(String codeVendorUser) {
        this.codeVendorUser = codeVendorUser;
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

    public String getPasswordVendorUser() {
        return passwordVendorUser;
    }

    public void setPasswordVendorUser(String passwordVendorUser) {
        this.passwordVendorUser= passwordVendorUser;
    }
//
//    public boolean isMenuAdmin() {
//        return menuAdmin;
//    }
//
//    public void setMenuAdmin(boolean menuAdmin) {
//        this.menuAdmin = menuAdmin;
//    }
//
//    public boolean isMenuProdAdmin() {
//        return menuProdAdmin;
//    }
//
//    public void setMenuProdAdmin(boolean menuProdAdmin) {
//        this.menuProdAdmin = menuProdAdmin;
//    }

 

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

    public String getStatusVendorUser() {
        return statusVendorUser;
    }

    public void setStatusVendorUser(String statusVendorUser) {
        this.statusVendorUser = statusVendorUser;
    }

 
    public String getIdVendor() {
        return idVendor;
    }


    public void setIdVendor(String idVendor) {
        this.idVendor = idVendor;
    }
}
