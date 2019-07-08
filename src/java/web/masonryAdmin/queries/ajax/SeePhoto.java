/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.masonryAdmin.queries.ajax;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Session;
import sql.masonryAdmin.maintenance.MaintenanceSQL;
import util.Imagen;
import util.Numeros;
import web.sesion.ORMUtil;

/**
 *
 * @author CR104978
 */
public class SeePhoto extends ActionSupport implements SessionAware {

    //SESSION
    Map session;
    //ORM
    Session mdk;
    //CAMPOS
    boolean sesionActiva = true;
    String token;
    //VARIABLES
    InputStream inputStream;

    //CONSTRUCTOR
    public SeePhoto() {
        session = ActionContext.getContext().getSession();
        mdk = ORMUtil.getSesionCMS().openSession();
    }

    //SET
    @Override
    public void setSession(Map session) {
        this.session = session;
    }

    public void setToken(String token) {
        this.token = token;
    }

    //GET
    public Map getSession() {
        return session;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public String execute() throws FileNotFoundException {       
        inputStream = MaintenanceSQL.getProductPhoto(mdk,Numeros.entero(token));
        mdk.close();
        return SUCCESS;
        
    }
}
