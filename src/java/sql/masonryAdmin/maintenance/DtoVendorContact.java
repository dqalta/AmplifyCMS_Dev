/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql.masonryAdmin.maintenance;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author CR104978
 */
/*This java file defines every constructor to be used as a DTO from an object*/
public class DtoVendorContact implements Serializable {

    private int id;
    private String idVendor;
    private String description;
    private String type;
    private Date created;
    private String createdBy;
    private Date modified;
    private String modifiedBy;
    private Boolean active;

    public DtoVendorContact() {
    }

 
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getIdVendor() {
        return idVendor;
    }

    public void setIdVendor(String idVendor) {
        this.idVendor = idVendor;
    }

  
    public String getDescription() {
        return description;
    }

  
    public void setDescription(String description) {
        this.description = description;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
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


    public Boolean getActive() {
        return active;
    }

  
    public void setActive(Boolean active) {
        this.active = active;
    }

 
    
  


}
