/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.home;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Session;
import web.sesion.ORMUtil;

/**
 *
 * @author CR104978
 */
public class Home extends ActionSupport implements SessionAware {

    HttpServletRequest request;
    Map session;
    boolean sesionActiva;
    String usuario;
    String menu;

    public Home() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        if (session.get("en-sesion") != null) {
            sesionActiva = true;
            usuario = String.valueOf(session.get("user"));
        } else {
            sesionActiva = false;
        }
    }

    //SET
    @Override
    public void setSession(Map session) {
        this.session = session;
    }

    //GET
    public boolean getSesionActiva() {
        return sesionActiva;
    }

    public Map getSession() {
        return session;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    @Override
    public String execute() {
        if (session.get("en-sesion") != null) {
            //ConsultasAdmin.registroBitacora(staging, String.valueOf(session.get("usuario")), "Inicio", "Carga página.");           
        }
        return SUCCESS;
    }

}
