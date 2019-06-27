/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.sesion;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
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
    Session cms;
    //CAMPOS - html
    boolean parametros = true;//PARAMETROS VALIDOS
    String usuario, contrasena;
    //VARIABLES - uso en clase
    String nombre;
    String mensajes = "";
    boolean mensaje;

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

    public void setUsuario(String usuario) {
        this.usuario = StringUtils.trimToEmpty(usuario).toUpperCase();
    }

    public void setContrasena(String contrasena) {
        this.contrasena = StringUtils.trimToEmpty(contrasena);
    }

    //GET
    public Map getSession() {
        return session;
    }

    public String getUsuario() {
        return this.usuario;
    }

    @Override
    public String execute() {
        cms.close();
        return SUCCESS;
    }

    @Override
    public void validate() {
        //REQUEST
        request = ServletActionContext.getRequest();
        //VALIDAR QUE CAMPOS NO SEAN BLANCOS NI NULOS
        /* if ((usuario == null) || (usuario.isEmpty() == true)) {
            addFieldError("usuario", "Complete \"Usuario\".");
            parametros = false;
        }
        if ((contrasena == null) || (contrasena.isEmpty() == true)) {
            addFieldError("contrasena", "Complete \"Contrase\u00f1a\".");
            parametros = false;
        }
        if (parametros == true) {*/

        for (int i = 0; i < 10; i++) {
            System.out.println(UtilSecurity.randomPassword(3, 2, 1, 2));
        }
        try {

            session = ActionContext.getContext().getSession();

            cms = ORMUtil.getSesionCMS().openSession();

            usuario = getResultado(cms);

            System.out.println("Resultado de BD: " + usuario);

            session = ActionContext.getContext().getSession();
            session.put("en-sesion", "true");
            session.put("user", "Daniel");
            session.put("userName", "Danielito");
            session.put("ip", request.getRemoteHost());

            //Lógica para pegar Base de Datos
            /* }*/
            cms.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            addActionError(e.getMessage());

        }
    }

    public static String getResultado(Session cms) {
        String valor = "-1";
        Iterator itr = cms.createSQLQuery("SELECT id as valor"
                + " FROM member")
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list().iterator();
        while (itr.hasNext()) {
            valor = String.valueOf(((Map) itr.next()).get("valor"));
        }
        return valor;
    }

}
