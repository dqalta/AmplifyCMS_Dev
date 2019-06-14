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
public class DtoSize implements Serializable {

    private int id;
    private int idMetricsSystem;
    private String description;
    private double length;
    private double depth;
    private double width;
    private int unitsPerSq2;
    private int unitsPerSf2;
    private Date created;
    private String createdBy;
    private Date modified;
    private String modifiedBy;
    private Boolean active;

    public DtoSize() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
  /**
     * @return the idCollectionCategory
     */
    public int getIdMetricsSystem() {
        return idMetricsSystem;
    }

    public void setIdMetricsSystem(int idMetricsSystem) {
        this.idMetricsSystem = idMetricsSystem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLength() {
        return length;
    }

   
    public void setLength(double length) {
        this.length = length;
    }

   
    public double getDepth() {
        return depth;
    }

  
    public void setDepth(double depth) {
        this.depth = depth;
    }

   
    public double getWidth() {
        return width;
    }

  
    public void setWidth(double width) {
        this.width = width;
    }
    public Date getCreated() {
        return created;
    }
    public int getUnitsPerSq2() {
        return unitsPerSq2;
    }


    public void setUnitsPerSq2(int unitsPerSq2) {
        this.unitsPerSq2 = unitsPerSq2;
    }

   
    public int getUnitsPerSf2() {
        return unitsPerSf2;
    }

   
    public void setUnitsPerSf2(int unitsPerSf2) {
        this.unitsPerSf2 = unitsPerSf2;
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
