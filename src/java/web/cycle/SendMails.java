/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.cycle;

import java.util.Map;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Session;
import static org.hibernate.criterion.Expression.sql;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import web.sesion.ORMUtil;

/**
 *
 * @author CR108002
 */
public class SendMails implements Job, SessionAware {

    Session mdk;
    Map session;

    public SendMails() {
        mdk = ORMUtil.getSesionCMS().openSession();
    }

    @Override
    public void setSession(Map session) {
        this.session = session;
    }

    public Map getSession() {
        return session;
    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
               
              //  ArrayList<AdmDtoCorreosPendientes> arr = new ArrayList<>();
               // arr = AdmConsultas.getCorreosPendientes(o2c);
               // System.out.println("Cantidad "+arr.size());
               // for (int i = 0; i < arr.size(); i++) {
               //     AdmDtoCorreosPendientes co = arr.get(i);
                    String respuestaEnvio = enviarCorreo();

                 /*   Transaction tn = null;
                    try {
                        tn = o2c.beginTransaction();
                        AdmConsultas.modificarCorreoPendiente(o2c, co.getId(), respuestaEnvio.contains("O2C_Error") ? respuestaEnvio : "ENVIADO", respuestaEnvio.contains("O2C_Error") ? "PENDIENTE" : "ENVIADO");

                        FacDtoFacturaEstados e = new FacDtoFacturaEstados();
                        e = FacConsultas.getFacturaEstado(o2c, co.getClave());
                        e.setEnvioCorreo(respuestaEnvio.contains("O2C_Error") ? "PENDIENTE" : "ENVIADO");                        
                        e.setModificado(Fechas.ya());
                        e.setModificadoPor("SISTEMA");
                        FacConsultas.modificarEstadosFactura(o2c, e);
                        AdmConsultas.bitacora(o2c, "SISTEMA", "Intento de envio de correo estado: " + (respuestaEnvio.contains("O2C_Error") ? "PENDIENTE" : "ENVIADO"));

                        tn.commit();
                    } catch (HibernateException x) {
                        if (tn != null) {
                            tn.rollback();
                        }
                    }
                    System.out.println("Respuesta Envio: " + respuestaEnvio);
                }*/

                mdk.close();
            }
        }
        );
        t.start();
    }

    public String enviarCorreo() {
        String mensaje = "Enviado";
        try {
/*
            Transaction tn = null;
            try {
                tn = o2c.beginTransaction();
                AdmConsultas.modificarIntentoCorreoPendiente(o2c, obj.getId());
                tn.commit();
            } catch (HibernateException x) {
                if (tn != null) {
                    tn.rollback();
                }
            }*/

            HtmlEmail correo = new HtmlEmail();
            correo.setHostName("constructorameco-com.mail.protection.outlook.com");            
            correo.addHeader("X-Priority", "1");

           // String correos[] = new String[obj.getDestinatarios().split(";").length];
         //   correos = obj.getDestinatarios().split(";");
          //  for (int i = 0; i < correos.length; i++) {
          //      correo.addTo(correos[i]);
          //  }
          correo.addTo("dqalta@gmail.com");

            correo.setFrom("no-reply@masonrysupply.com", "Masonry");
           // correo.setSubject(obj.getAsunto());
            correo.setSubject("Prueba");
            correo.setCharset("UTF-8");

            //correo.setHtmlMsg(obj.getCuerpo());
            correo.setHtmlMsg("");

            mensaje = correo.send();
        } catch (EmailException ex) {
            mensaje = "O2C_Error: " + ex.getMessage();
            //Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error de correo", x);
        } finally {
            return mensaje;
        }
    }
}
