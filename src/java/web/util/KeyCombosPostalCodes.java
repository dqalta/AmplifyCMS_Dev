/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web.util;
//load data  from category and create a array
public class KeyCombosPostalCodes {

    private int id;
    private String postalCode;
    private String province;
    private String city;
    

    public KeyCombosPostalCodes(int id, String postalCode) {
        this.id = id;
        this.postalCode = postalCode;
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

 
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }


    public void setCity(String city) {
        this.city = city;
    }


}
