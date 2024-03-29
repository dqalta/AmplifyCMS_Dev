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
import sql.masonryAdmin.maintenance.DtoSubMaterial;
import sql.masonryAdmin.maintenance.MaintenanceSQL;
import util.Fechas;
import web.sesion.ORMUtil;
import web.util.CombosMaintenance;
import web.util.KeyCombos;

/**
 *
 * @author CR104978
 */
public class SubMaterial extends ActionSupport implements SessionAware {

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
    int vendorsPending;

    //Variables de la pantalla
    private ArrayList<DtoSubMaterial> subMaterials = new ArrayList<>();//Variable con la lista de datos
    private ArrayList<KeyCombos> materials = new ArrayList<>();

    //Variables del mantenimiento
    int id;
    int idProductMaterial;
    String description;
    boolean active;
    int idEdit;

    public SubMaterial() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        if (session.get("en-sesion") != null) {
            sesionActiva = true;
            mdk = ORMUtil.getSesionCMS().openSession();
            usuario = String.valueOf(session.get("user"));
            permiso = true; //AdmConsultas.getPermiso(o2c, "ADMINISTRACIÓN", "Encargados", usuario);            
            menu = "";//AdmConsultas.menuUsuario(o2c, usuario);
            chargeSelect(); // fill the select with the categories
            vendorsPending = MaintenanceSQL.getPendingVendors(mdk);
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

    public int getVendorsPending() {
        return vendorsPending;
    }

    public void setVendorsPending(int vendorsPending) {
        this.vendorsPending = vendorsPending;
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
    public ArrayList<DtoSubMaterial> getSubMaterials() {
        return subMaterials;
    }

    public void setSubMaterials(ArrayList<DtoSubMaterial> subMaterials) {
        this.subMaterials = subMaterials;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<KeyCombos> getMaterials() {
        return materials;
    }

    public void setMaterials(ArrayList<KeyCombos> materials) {
        this.materials = materials;
    }

    public int getIdEdit() {
        return idEdit;
    }

    public void setIdEdit(int idEdit) {
        this.idEdit = idEdit;
    }

    public int getIdProductMaterial() {
        return idProductMaterial;
    }

    public void setIdProductMaterial(int idProductMaterial) {
        this.idProductMaterial = idProductMaterial;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
        chargeSubMaterials();
    }

    public void clearFields() {
        id = 0;
        idProductMaterial = 0;
        description = "";
        active = false;
        accion = 0;
        idEdit = 0;
        chargeSelect(); //after clean charge select again 

    }

    public boolean validateFields() {
        boolean flag = true;
        mensajes = "";
        mensaje = false;
        //VALIDAR QUE CAMPOS NO SEAN BLANCOS NI NULOS
        if ((description == null) || (description.isEmpty())) {
            mensajes = mensajes + "danger<>Error<>Please complete field 'Description'.|";
            flag = false;
        }
        if (idProductMaterial == 0) {
            mensajes = mensajes + "danger<>Error<>Please select one material.|";
            flag = false;
        }
        if (!flag) {
            mensaje = flag;
        }
        return flag;
    }

    public void save() {
        if (idEdit == 0) {
            insert();
        } else {
            update();
        }
    }

    public void chargeSubMaterials() {
        subMaterials = MaintenanceSQL.getSubMaterials(mdk);
    }

    public void chargeSelect() {
        materials = CombosMaintenance.getMaterials(mdk);
    }

    public void insert() {
        if (validateFields()) {//Valido los campos del formulario
            Transaction tn = null;//Inicializo la transacción de la BD en null
            try {
                tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

                DtoSubMaterial m = new DtoSubMaterial();//Creo un objeto del tipo color

                //Seteo los datos del objeto excepto el id por que es Auto Incremental
                m.setIdProductMaterial(idProductMaterial);
                m.setDescription(description);
                m.setCreated(Fechas.ya());
                m.setCreatedBy(usuario);
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);
                m.setActive(active);//Lo puse en true porque se me olvidó crear el check en el formulario, en la noche hacemos eso jajaja

                MaintenanceSQL.saveSubMaterial(mdk, m);
                //AdmConsultas.bitacora(o2c, usuario, "Encargado guardado Tipo: " + tipo + ", Codigo: " + codigo);

                tn.commit();// Hago Commit a la transacción para guardar el registro
                clearFields();
                mensajes = mensajes + "info<>Information<>SubMaterial saved successfully.";
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
                DtoSubMaterial m = MaintenanceSQL.getSubMaterial(mdk, idEdit);
                if (m != null) {
                    m.setIdProductMaterial(idProductMaterial);
                    m.setDescription(description);
                    m.setModified(Fechas.ya());
                    m.setModifiedBy(usuario);
                    m.setActive(active);//Lo puse en true porque se me olvidó crear el check en el formulario, en la noche hacemos eso jajaja

                    MaintenanceSQL.updateSubMaterial(mdk, m);
                    // AdmConsultas.bitacora(o2c, usuario, "Encargado modificado Tipo: " + tipo + ", Codigo: " + codigo);

                    tn.commit();
                    clearFields();
                    mensajes = mensajes + "info<>Information<>SubMaterial modified successfully.";
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
        DtoSubMaterial m = MaintenanceSQL.getSubMaterial(mdk, idEdit);
        if (m != null) {
            idEdit = m.getId();
            idProductMaterial = m.getIdProductMaterial();
            description = m.getDescription();
            active = m.getActive();
        } else {
            mensajes = mensajes + "danger<>Error<>SubMaterial does not exist.";
            mensaje = true;
        }
    }

}
