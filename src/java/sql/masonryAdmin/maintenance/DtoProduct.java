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
public class DtoProduct implements Serializable {

    private int id;
    private String pname;
    private int idStyle;
    private int idTexture;
    private int idPackageType;
    private int idMaterial;
    private int idSubMaterial;
    private int idManufacturer;
    private int idSize;
    private String slug;
    private String description;
    private String sku;
    private String palletWeight;
    private boolean canSellLayer;
    private int unitsLayer;
    private int layersPallet;
    private int unitsPallet;
    private boolean hasCorner;
    private String linearFeetCorner;
    private String sqftPerPackageType;
    private int qtyOfUnitsPerPackageType;
    private Date created;
    private String createdBy;
    private Date modified;
    private String modifiedBy;
    private Boolean active;

    public DtoProduct() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getIdStyle() {
        return idStyle;
    }

    public void setIdStyle(int idStyle) {
        this.idStyle = idStyle;
    }

    public int getIdTexture() {
        return idTexture;
    }

    public void setIdTexture(int idTexture) {
        this.idTexture = idTexture;
    }

    public int getIdPackageType() {
        return idPackageType;
    }

    public void setIdPackageType(int idPackageType) {
        this.idPackageType = idPackageType;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public int getIdSubMaterial() {
        return idSubMaterial;
    }

    public void setIdSubMaterial(int idSubMaterial) {
        this.idSubMaterial = idSubMaterial;
    }

    public int getIdManufacturer() {
        return idManufacturer;
    }

    public void setIdManufacturer(int idManufacturer) {
        this.idManufacturer = idManufacturer;
    }

    public int getIdSize() {
        return idSize;
    }

    public void setIdSize(int idSize) {
        this.idSize = idSize;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPalletWeight() {
        return palletWeight;
    }

    public void setPalletWeight(String palletWeight) {
        this.palletWeight = palletWeight;
    }

    public boolean isCanSellLayer() {
        return canSellLayer;
    }

    public void setCanSellLayer(boolean canSellLayer) {
        this.canSellLayer = canSellLayer;
    }

    public int getUnitsLayer() {
        return unitsLayer;
    }

    public void setUnitsLayer(int unitsLayer) {
        this.unitsLayer = unitsLayer;
    }

    public int getLayersPallet() {
        return layersPallet;
    }

    public void setLayersPallet(int layersPallet) {
        this.layersPallet = layersPallet;
    }

    public int getUnitsPallet() {
        return unitsPallet;
    }

    public void setUnitsPallet(int unitsPallet) {
        this.unitsPallet = unitsPallet;
    }

    public boolean isHasCorner() {
        return hasCorner;
    }

    public void setHasCorner(boolean hasCorner) {
        this.hasCorner = hasCorner;
    }

    public String getLinearFeetCorner() {
        return linearFeetCorner;
    }

    public void setLinearFeetCorner(String linearFeetCorner) {
        this.linearFeetCorner = linearFeetCorner;
    }

    public String getSqftPerPackageType() {
        return sqftPerPackageType;
    }

    public void setSqftPerPackageType(String sqftPerPackageType) {
        this.sqftPerPackageType = sqftPerPackageType;
    }

    public int getQtyOfUnitsPerPackageType() {
        return qtyOfUnitsPerPackageType;
    }

    public void setQtyOfUnitsPerPackageType(int qtyOfUnitsPerPackageType) {
        this.qtyOfUnitsPerPackageType = qtyOfUnitsPerPackageType;
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
