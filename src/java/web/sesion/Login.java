/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.sesion;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import util.UtilSecurity;

/**
 *
 * @author CR104978
 */
public class Login extends ActionSupport implements SessionAware {

    //variables propias de la sesion
    HttpServletRequest request;
    Map session;
    Session mdk;
    //CAMPOS - html
    boolean parametros = true;//PARAMETROS VALIDOS
    String user, password, guid;
    //VARIABLES - uso en clase
    String mensajes = "";
    boolean mensaje;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    //SET
    @Override
    public void setSession(Map session) {
        this.session = session;
    }

    public String getMensajes() {
        return mensajes;
    }

    public void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }

    public boolean isMensaje() {
        return mensaje;
    }

    public void setMensaje(boolean mensaje) {
        this.mensaje = mensaje;
    }

    //GET
    public Map getSession() {
        return session;
    }

    @Override
    public String execute() {
        mdk.close();
        return SUCCESS;
    }

    @Override
    public void validate() {
        //REQUEST
        request = ServletActionContext.getRequest();
        try {
            session = ActionContext.getContext().getSession();
            mdk = ORMUtil.getSesionCMS().openSession();

            if (guid != null) {
                System.out.println("POR URL");
            } else {
                System.out.println("POR PANTALLA");
            }
            session = ActionContext.getContext().getSession();
            session.put("en-sesion", "true");
            session.put("user", "Daniel");
            session.put("userName", "Danielito");
            session.put("ip", request.getRemoteHost());

            mdk.close();
        } catch (Exception e) {
            addActionError(e.getMessage());
        }
    }

}
