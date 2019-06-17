/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.sesion;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Session;

/**
 *
 * @author CR108002
 */
public class LogOut extends ActionSupport implements SessionAware {

    Map session;
    Session staging;

    @Override
    public void setSession(Map session) {
        this.session = session;
    }

    public Map getSession() {
        return session;
    }

    @Override
    public String execute() {

        //staging = ORMUtil.getStaging(String.valueOf(session.get("pais"))).openSession();
        session = ActionContext.getContext().getSession();
        //  ConsultasAdmin.registroBitacora(staging, String.valueOf(session.get("usuario")), "Salida", "Cierra sesión.");

        /*session.remove("en-sesion");
        session.remove("usuario");
        session.remove("nombre");
        session.remove("pagina");
        session.remove("proyecto");
        session.remove("pais");
        session.remove("ip");*/
        ((SessionMap) session).invalidate();

        return SUCCESS;
    }

}
