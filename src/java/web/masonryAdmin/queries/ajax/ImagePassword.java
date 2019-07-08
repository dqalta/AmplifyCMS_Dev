/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.masonryAdmin.queries.ajax;

import static com.mchange.v2.c3p0.impl.C3P0Defaults.password;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Session;
import util.Imagen;
import web.sesion.ORMUtil;

/**
 *
 * @author CR104978
 */
public class ImagePassword extends ActionSupport implements SessionAware {

    //SESSION
    Map session;
    //ORM
    Session mdk;
    //CAMPOS
    boolean sesionActiva = true;
    String ps;
    //VARIABLES
    InputStream inputStream;

    //CONSTRUCTOR
    public ImagePassword() {
        session = ActionContext.getContext().getSession();
       
    }

    //SET
    @Override
    public void setSession(Map session) {
        this.session = session;
    }

    public void setPs(String ps) {
        this.ps = ps;
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
        if (Imagen.generar(ps)) {
            try {
                inputStream = new FileInputStream(new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "password" + ".png"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return SUCCESS;
    }
}
