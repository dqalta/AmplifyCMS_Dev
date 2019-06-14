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
import sql.masonryAdmin.maintenance.DtoSize;
import sql.masonryAdmin.maintenance.MaintenanceSQL;
import util.Fechas;
import web.sesion.ORMUtil;
import web.util.CombosMaintenance;
import web.util.KeyCombos;
import util.Numeros;

/**
 *
 * @author CR104978
 */
public class Size extends ActionSupport implements SessionAware {

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
    private ArrayList<DtoSize> sizes = new ArrayList<>();//Variable con la lista de datos
    private ArrayList<KeyCombos> units = new ArrayList<>();

    //Variables del mantenimiento
    private int id;
    private int idMetricsSystem;
    private String description;
    private String length;
    private String depth;
    private String width;
    private boolean active;
    private int idEdit;

    public Size() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        if (session.get("en-sesion") != null) {
            sesionActiva = true;
            mdk = ORMUtil.getSesionCMS().openSession();
            usuario = String.valueOf(session.get("user"));
            permiso = true; //AdmConsultas.getPermiso(o2c, "ADMINISTRACIÓN", "Encargados", usuario);            
            menu = "";//AdmConsultas.menuUsuario(o2c, usuario);
            chargeSelect(); // fill the select with the categories
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
     /**
     * @return the sizes
     */
    public ArrayList<DtoSize> getSizes() {
        return sizes;
    }

    /**
     * @param sizes the sizes to set
     */
    public void setSizes(ArrayList<DtoSize> sizes) {
        this.sizes = sizes;
    }

    /**
     * @return the metricsSystem
     */
    public ArrayList<KeyCombos> getUnits() {
        return units;
    }

    /**
     * @param metricsSystem the metricsSystem to set
     */
    public void setMetricsSystem(ArrayList<KeyCombos> units) {
        this.units = units;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the idMetricsSystem
     */
    public int getIdMetricsSystem() {
        return idMetricsSystem;
    }

    /**
     * @param idMetricsSystem the idMetricsSystem to set
     */
    public void setIdMetricsSystem(int idMetricsSystem) {
        this.idMetricsSystem = idMetricsSystem;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the length
     */
    public String getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(String length) {
        this.length = length;
    }

    /**
     * @return the depth
     */
    public String getDepth() {
        return depth;
    }

    /**
     * @param depth the depth to set
     */
    public void setDepth(String depth) {
        this.depth = depth;
    }

    /**
     * @return the width
     */
    public String getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the idEdit
     */
    public int getIdEdit() {
        return idEdit;
    }

    /**
     * @param idEdit the idEdit to set
     */
    public void setIdEdit(int idEdit) {
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
        chargeSizes();
    }

    public void clearFields() {
        setId(0);
        idMetricsSystem = 0;
        setDescription("");
        setActive(false);
        setLength("");
        setDepth("");
        setWidth("");
        accion = 0;
        setIdEdit(0);
        chargeSelect(); //after clean charge select again 

    }

    public boolean validateFields() {
        boolean flag = true;
        mensajes = "";
        mensaje = false;
        //VALIDAR QUE CAMPOS NO SEAN BLANCOS NI NULOS
        if ((getDescription() == null) || (getDescription().isEmpty())) {
            mensajes = mensajes + "danger<>Error<>Please complete field 'Description'.|";
            flag = false;
        }
        if (idMetricsSystem == 0) {
            mensajes = mensajes + "danger<>Error<>Please select one material.|";
            flag = false;
        }
          if ((getLength () == null) || (getLength().isEmpty())) {
            mensajes = mensajes + "danger<>Error<>Please complete field 'Length'.|";
            flag = false;
        }
            if ((getDepth() == null) || (getDepth().isEmpty())) {
            mensajes = mensajes + "danger<>Error<>Please complete field 'Depth'.|";
            flag = false;
        }
              if ((getWidth() == null) || (getWidth().isEmpty())) {
            mensajes = mensajes + "danger<>Error<>Please complete field 'Width'.|";
            flag = false;
        }
        if (!flag) {
            mensaje = flag;
        }
        return flag;
    }

    public void save() {
        if (getIdEdit() == 0) {
            insert();
        } else {
            update();
        }
    }

    public void chargeSizes() {
        sizes = MaintenanceSQL.getSize(mdk);
    }

    public void chargeSelect() {
        units = CombosMaintenance.getUnits(mdk);
    }

    public void insert() {
        if (validateFields()) {//Valido los campos del formulario
            Transaction tn = null;//Inicializo la transacción de la BD en null
            try {
                tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

                DtoSize m = new DtoSize();//Creo un objeto del tipo size

                //Seteo los datos del objeto excepto el id por que es Auto Incremental
                m.setIdMetricsSystem(idMetricsSystem);
                m.setDescription(getDescription());
                m.setLength(Numeros.numero(length) );
                m.setDepth(Numeros.numero(depth));
                m.setWidth(Numeros.numero(width));
                m.setCreated(Fechas.ya());
                m.setCreatedBy(usuario);
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);
                m.setActive(isActive());//Lo puse en true porque se me olvidó crear el check en el formulario, en la noche hacemos eso jajaja

                MaintenanceSQL.saveSize (mdk, m);
                //AdmConsultas.bitacora(o2c, usuario, "Encargado guardado Tipo: " + tipo + ", Codigo: " + codigo);

                tn.commit();// Hago Commit a la transacción para guardar el registro
                clearFields();
                mensajes = mensajes + "info<>Information<>Size saved successfully.";
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
                DtoSize m = MaintenanceSQL.getSize(mdk, getIdEdit());
                if (m != null) {         
                    m.setDescription(getDescription());
                    m.setIdMetricsSystem(idMetricsSystem);
                    m.setDescription(getDescription());
                    m.setLength(Numeros.numero(length));
                    m.setDepth(Numeros.numero(depth));
                    m.setWidth(Numeros.numero(width));                                
                    m.setModified(Fechas.ya());
                    m.setModifiedBy(usuario);
                    m.setActive(isActive());//Lo puse en true porque se me olvidó crear el check en el formulario, en la noche hacemos eso jajaja

                    MaintenanceSQL.updateSize(mdk, m);
                    // AdmConsultas.bitacora(o2c, usuario, "Encargado modificado Tipo: " + tipo + ", Codigo: " + codigo);

                    tn.commit();
                    clearFields();
                    mensajes = mensajes + "info<>Information<>Size modified successfully.";
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
        DtoSize m = MaintenanceSQL.getSize(mdk, getIdEdit());
        if (m != null) {
            setIdEdit(m.getId());
            idMetricsSystem = m.getIdMetricsSystem();
            setDescription(m.getDescription());
            setLength(String.valueOf(m.getLength()));
            setDepth(String.valueOf(m.getDepth()));
            setWidth(String.valueOf(m.getWidth()));
            setActive(m.getActive());
        } else {
            mensajes = mensajes + "danger<>Error<>Size name does not exist.";
            mensaje = true;
        }
    }

  

}
