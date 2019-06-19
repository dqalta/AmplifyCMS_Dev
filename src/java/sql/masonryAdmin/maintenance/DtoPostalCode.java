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
public class DtoPostalCode implements Serializable {

    private int id;
    private String postalCode;
    private String city;
    private String province;     
    private Boolean active;

    public DtoPostalCode() {
    }

 
    public int getId() {
        return id;
    }

   
    public void setId(int id) {
        this.id = id;
    }

 
    public String getPostalCode() {
        return postalCode;
    }


    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }


    public String getCity() {
        return city;
    }


    public void setCity(String city) {
        this.city = city;
    }

 
    public String getProvince() {
        return province;
    }

 
    public void setProvince(String province) {
        this.province = province;
    }

    

    public Boolean getActive() {
        return active;
    }

 
    public void setActive(Boolean active) {
        this.active = active;
    }

  

}
