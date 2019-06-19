/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.masonryAdmin.queries.ajax;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Session;
import web.sesion.ORMUtil;
import web.util.CombosMaintenance;
import web.util.KeyCombosString;

/**
 *
 * @author CR104978
 */
public class SelectCities extends ActionSupport implements SessionAware {

    //SESSION
    Map session;
    //ORM
    Session mdk;
    //CAMPOS
    boolean sesionActiva = true;
    String  province;
    //VARIABLES
    String html;
    InputStream inputStream;

    //CONSTRUCTOR
    public SelectCities() {
        session = ActionContext.getContext().getSession();
        mdk = ORMUtil.getSesionCMS().openSession();
    }

    //SET
    @Override
    public void setSession(Map session) {
        this.session = session;
    }
 

    public void setProvince(String province) {
        this.province = province;
    }

 

    //GET
    public Map getSession() {
        return session;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public String execute() throws IOException {
        html = datos();
        inputStream = IOUtils.toInputStream(html, "UTF-8");

        return SUCCESS;
    }

    public String datos() {
        StringBuilder data = new StringBuilder();
        if (sesionActiva == true) {
            ArrayList<KeyCombosString> combos;
            combos = CombosMaintenance.getCitiesPostalCodes(mdk, province);
            KeyCombosString c;
            for (int i = 0; i < combos.size(); i++) {
                c = combos.get(i);
                data.append("<option value=\"").append(c.getDescription()).append("\">").append(c.getDescription()).append("</option>");
            }
        }
        return data.toString();
    }

  
 
}