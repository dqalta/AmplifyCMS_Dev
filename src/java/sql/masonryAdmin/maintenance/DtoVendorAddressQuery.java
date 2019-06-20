/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql.masonryAdmin.maintenance;
import java.io.Serializable;


public class DtoVendorAddressQuery implements Serializable {
    private int id;
    private String description;
    private String postalCode;
    private String city;
    private String province;


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
    
   
   
}
