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
/*This java file defines every constructor to be used as a DTO from an object*/
    public class DtoVendorsPending implements Serializable {

    private int idVendorRegister;
    private String companyName;
    private String vname;
    private String phoneNumber;
    private String webSite;
    private String city;
    private String email;
   private String password;

    public DtoVendorsPending() {
    }



    
    public String getCompanyName() {
        return companyName;
    }

    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }

  
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

  
    public String getWebSite() {
        return webSite;
    }

    
    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

   
    public String getCity() {
        return city;
    }

   
    public void setCity(String city) {
        this.city = city;
    }

   
    public String getEmail() {
        return email;
    }

   
    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getVname() {
        return vname;
    }


    public void setVname(String vname) {
        this.vname = vname;
    }

    public int getIdVendorRegister() {
        return idVendorRegister;
    }

 
    public void setIdVendorRegister(int idVendorRegister) {
        this.idVendorRegister = idVendorRegister;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

  

}
