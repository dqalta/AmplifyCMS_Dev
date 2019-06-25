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
public class DtoGallery implements Serializable {

    private int id;
    private String description;
    private String createdBy;
    private Date created;

    public DtoGallery() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
