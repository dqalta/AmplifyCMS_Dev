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
public class DtoPhoto implements Serializable {

    private int id;
    private int idGallery;
    private String photoFileName;
    private String photoContentType;

    public DtoPhoto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdGallery() {
        return idGallery;
    }

    public void setIdGallery(int idGallery) {
        this.idGallery = idGallery;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

}
