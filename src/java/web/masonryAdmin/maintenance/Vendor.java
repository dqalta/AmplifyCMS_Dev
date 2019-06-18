/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.masonryAdmin.maintenance;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sql.masonryAdmin.maintenance.DtoVendor;
import sql.masonryAdmin.maintenance.MaintenanceSQL;
import util.Fechas;
import web.sesion.ORMUtil;

/**
 *
 * @author CR104978
 */
public class Vendor extends ActionSupport implements SessionAware {

    HttpServletRequest request;//Variable donde se crea el objeto de la petición
    Map session;//Variable que guarda la sesión del Tomcat

    // Variables del Hibernate-ORM
    Session mdk; //Variable de la conexión a la base de datos

    //Variables del Controlador Struts2 Servidor<->JSP
    //Variables de validaciones
    int accion; //Acción donde se guardar la función a llamar en el submit Ejemplo: 0=Insert, 1=Select...
    boolean sesionActiva = true; //Guardo el estado de la sesión del usuario en el tomcat
    boolean permiso;//Guardo si tiene o no permiso de ingresar a la pantalla 
    String usuario;//Código del usuario logueado
    String menu;//String de los permisos del menu 
    String mensajes = "";//Variable para cargar el texto del resultado de las validaciones o acciones
    boolean mensaje;//Variable bandera para saber si se muestra o no el mensaje

    //Variables de la pantalla
    private ArrayList<DtoVendor> vendors = new ArrayList<>();//Variable con la lista de datos

    //Variables del mantenimiento
    private String id;
    private String vname;
    private boolean active;
    private String idEdit;

    public Vendor() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        if (session.get("en-sesion") != null) {
            sesionActiva = true;
            mdk = ORMUtil.getSesionCMS().openSession();
            usuario = String.valueOf(session.get("user"));
            permiso = true; //AdmConsultas.getPermiso(o2c, "ADMINISTRACIÓN", "Encargados", usuario);            
            menu = "";//AdmConsultas.menuUsuario(o2c, usuario);
        } else {
            sesionActiva = false;
        }
    }

    //SET GET DEFAULT
    public Map getSession() {
        return session;
    }

    @Override
    public void setSession(Map session) {
        this.session = session;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public int getAccion() {
        return accion;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }

    public boolean getSesionActiva() {
        return sesionActiva;
    }

    public void setSesionActiva(boolean sesionActiva) {
        this.sesionActiva = sesionActiva;
    }

    public boolean getPermiso() {
        return permiso;
    }

    public void setPermiso(boolean permiso) {
        this.permiso = permiso;
    }

    public boolean getMensaje() {
        return mensaje;
    }

    public void setMensaje(boolean mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensajes() {
        return mensajes;
    }

    public void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    //SET GET CUSToMIZED
  
     public String getVname() {
        return vname;
    }
  public void setVname(String vname) {
        this.vname = vname;
    }

   public ArrayList<DtoVendor> getVendors() {
        return vendors;
    }
  public void setVendors(ArrayList<DtoVendor> vendors) {
        this.vendors = vendors;
    }
  public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getIdEdit() {
        return idEdit;
    }

     public void setIdEdit(String idEdit) {
        this.idEdit = idEdit;
    }




    ////////
    @Override
    public String execute() {
        if (permiso == true) {
            process();
            mdk.close();//Cerrar la conexión de la base de datos SIEMPRE
        }
        return SUCCESS;
    }

    //METODOS ADICIONALES
    public void process() {
        switch (accion) {
            case 1:
                save();
                break;
            case 2:
                readForUpdate();
                break;
        }
        chargeVendors();
    }

    public void clearFields() {
        id = "";
        vname = "";
        accion = 0;
        idEdit="";
        
        setActive(false);
    }

    public boolean validateFields() {
        boolean flag = true;
        mensajes = "";
        mensaje = false;
        //VALIDAR QUE CAMPOS NO SEAN BLANCOS NI NULOS
        if ((vname == null) || (vname.isEmpty())) {
            mensajes = mensajes + "danger<>Error<>Please complete field 'Name of vendor'.|";
            flag = false;
        }
        if (!flag) {
            mensaje = flag;
        }
        return flag;
    }

    public void save() {
        if (getIdEdit().equals("")) {
            insert();
        } else {
            update();
        }
    }

    public void chargeVendors() {
        setVendors(MaintenanceSQL.getVendors(mdk));
    }

    public void insert() {
        if (validateFields()) {//Valido los campos del formulario
            Transaction tn = null;//Inicializo la transacción de la BD en null
            try {
                tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

                DtoVendor m = new DtoVendor();//Creo un objeto del tipo style

                //Setting the fields, including id -is not auto incremental
                m.setId(id);
                m.setVname(vname);
                m.setCreated(Fechas.ya());
                m.setCreatedBy(usuario);
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);
                m.setActive(isActive());//Lo puse en true porque se me olvidó crear el check en el formulario, en la noche hacemos eso jajaja

                MaintenanceSQL.saveVendor(mdk, m);
                //AdmConsultas.bitacora(o2c, usuario, "Encargado guardado Tipo: " + tipo + ", Codigo: " + codigo);

                tn.commit();// Hago Commit a la transacción para guardar el registro
                clearFields();
                mensajes = mensajes + "info<>Information<>Vendor saved successfully.";
                mensaje = true;

            } catch (HibernateException x) {
                //AdmConsultas.error(o2c, x.getMessage());
                // mensajes = mensajes + "danger<>Error<>Error al guardar encargados: " + codigo + ": " + ExceptionUtils.getMessage(x) + ".";
                mensajes = mensajes + "danger<>Error<>Error.|";
                mensaje = true;
                if (tn != null) {//Si hay error y el transacción es distinto de null, es porque la transacción existe, entoncs hago rollback
                    tn.rollback();
                }
            }
            mensaje = true;
        }
    }

    public void update() {
        if (validateFields()) {
            Transaction tn = null;
            try {
                tn = mdk.beginTransaction();
                DtoVendor m = MaintenanceSQL.getVendor(mdk, getIdEdit());
                if (m != null) {                   
                    m.setVname(vname);
                    m.setModified(Fechas.ya());
                    m.setModifiedBy(usuario);
                    m.setActive(isActive());//Lo puse en true porque se me olvidó crear el check en el formulario, en la noche hacemos eso jajaja

                    MaintenanceSQL.updateVendor(mdk, m);
                    // AdmConsultas.bitacora(o2c, usuario, "Encargado modificado Tipo: " + tipo + ", Codigo: " + codigo);

                    tn.commit();
                    clearFields();
                    mensajes = mensajes + "info<>Information<>Vendor modified successfully.";
                    mensaje = true;
                } else {
                    insert();
                }
            } catch (HibernateException x) {
                //AdmConsultas.error(o2c, x.getMessage());
                // mensajes = mensajes + "danger<>Error<>Error al modificar encargados: " + codigo + ": " + ExceptionUtils.getMessage(x) + ".";
                mensajes = mensajes + "danger<>Error<>Error.|";
                mensaje = true;
                if (tn != null) {
                    tn.rollback();
                }
            }
            mensaje = true;
        }
    }

    public void readForUpdate() {
        DtoVendor m = MaintenanceSQL.getVendor(mdk, getIdEdit());
        if (m != null) {
            id= m.getId();
            vname = m.getVname();
            setActive((boolean) m.getActive());
        } else {
            mensajes = mensajes + "danger<>Error<>Vendor does not exist.";
            mensaje = true;
        }
    }
}