/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql.masonryAdmin.maintenance;

import java.io.Serializable;

/**
 *
 * @author CR104978
 */
public class DtoPhotoQuery implements Serializable {

    int id;
    int idGallery;
    String photoFileName;
    String photo;

    public DtoPhotoQuery() {
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
