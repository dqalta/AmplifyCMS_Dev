/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.masonryAdmin.admin;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sql.masonryAdmin.admin.DtoRol;
import sql.masonryAdmin.maintenance.DtoCollection;
import sql.masonryAdmin.maintenance.MaintenanceSQL;
import util.Fechas;
import web.sesion.ORMUtil;

/**
 *
 * @author CR104978
 */
public class Rol extends ActionSupport implements SessionAware {

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
    private ArrayList<DtoRol> rols = new ArrayList<>();//Variable con la lista de datos

    //Variables del mantenimiento
    String description;
    int idEdit;

    //Administration
    boolean user, rol, audit;

    //Product Administration
    boolean product, collection, material, subMaterial;

    //Product Components
    boolean manufacturer, packageType, metricSystem, style, size, texture, color;

    public Rol() {
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

    //SET GET CUSTUMIZED
    public ArrayList<DtoRol> getRols() {
        return rols;
    }

    public void setRols(ArrayList<DtoRol> rols) {
        this.rols = rols;
    }

    public int getIdEdit() {
        return idEdit;
    }

    public void setIdEdit(int idEdit) {
        this.idEdit = idEdit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    public boolean isRol() {
        return rol;
    }

    public void setRol(boolean rol) {
        this.rol = rol;
    }

    public boolean isAudit() {
        return audit;
    }

    public void setAudit(boolean audit) {
        this.audit = audit;
    }

    public boolean isProduct() {
        return product;
    }

    public void setProduct(boolean product) {
        this.product = product;
    }

    public boolean isCollection() {
        return collection;
    }

    public void setCollection(boolean collection) {
        this.collection = collection;
    }

    public boolean isMaterial() {
        return material;
    }

    public void setMaterial(boolean material) {
        this.material = material;
    }

    public boolean isSubMaterial() {
        return subMaterial;
    }

    public void setSubMaterial(boolean subMaterial) {
        this.subMaterial = subMaterial;
    }

    public boolean isManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(boolean manufacturer) {
        this.manufacturer = manufacturer;
    }

    public boolean isPackageType() {
        return packageType;
    }

    public void setPackageType(boolean packageType) {
        this.packageType = packageType;
    }

    public boolean isMetricSystem() {
        return metricSystem;
    }

    public void setMetricSystem(boolean metricSystem) {
        this.metricSystem = metricSystem;
    }

    public boolean isStyle() {
        return style;
    }

    public void setStyle(boolean style) {
        this.style = style;
    }

    public boolean isSize() {
        return size;
    }

    public void setSize(boolean size) {
        this.size = size;
    }

    public boolean isTexture() {
        return texture;
    }

    public void setTexture(boolean texture) {
        this.texture = texture;
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

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
        chargeRols();
    }

    public void clearFields() {
        accion = 0;
        idEdit = 0;
        description = "";
        user = false;
        rol = false;
        audit = false;
        product = false;
        collection = false;
        material = false;
        subMaterial = false;
        manufacturer = false;
        packageType = false;
        metricSystem = false;
        style = false;
        size = false;
        texture = false;
        color = false;
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

    public void chargeRols() {
        rols = MaintenanceSQL.getRols(mdk);
    }

    public void insert() {
        if (validateFields()) {//Valido los campos del formulario
            Transaction tn = null;//Inicializo la transacción de la BD en null
            try {
                tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

                DtoRol m = new DtoRol();//Creo un objeto del tipo Manufacturer

                //Seteo los datos del objeto excepto el id por que es Auto Incremental
                m.setDescription(description);

                m.setCreated(Fechas.ya());
                m.setCreatedBy(usuario);
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);

                MaintenanceSQL.saveRol(mdk, m);
                //AdmConsultas.bitacora(o2c, usuario, "Encargado guardado Tipo: " + tipo + ", Codigo: " + codigo);

                tn.commit();// Hago Commit a la transacción para guardar el registro
                saveRolDetails(MaintenanceSQL.getLastIdRol(mdk, usuario));
                clearFields();
                mensajes = mensajes + "info<>Information<>Rol saved successfully.";
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

    public void saveRolDetails(int _idRol) {
        Transaction tn = null;
        try {
            tn = mdk.beginTransaction();
            int idRol = _idRol;
            MaintenanceSQL.deleteRolDetails(mdk, idRol);
            Date ya = Fechas.ya();

            //Administration
            MaintenanceSQL.saveRolDetail(mdk, idRol, "ADMINISTRATION", "user", user, usuario, ya);
            MaintenanceSQL.saveRolDetail(mdk, idRol, "ADMINISTRATION", "rol", rol, usuario, ya);
            MaintenanceSQL.saveRolDetail(mdk, idRol, "ADMINISTRATION", "audit", audit, usuario, ya);

            //Product Components
            MaintenanceSQL.saveRolDetail(mdk, idRol, "PRODUCTCOMPONENTS", "product", product, usuario, ya);
            MaintenanceSQL.saveRolDetail(mdk, idRol, "PRODUCTCOMPONENTS", "collection", collection, usuario, ya);
            MaintenanceSQL.saveRolDetail(mdk, idRol, "PRODUCTCOMPONENTS", "material", material, usuario, ya);
            MaintenanceSQL.saveRolDetail(mdk, idRol, "PRODUCTCOMPONENTS", "subMaterial", subMaterial, usuario, ya);

            //Product Administration
            MaintenanceSQL.saveRolDetail(mdk, idRol, "PRODUCTADMINISTRATION", "manufacturer", manufacturer, usuario, ya);
            MaintenanceSQL.saveRolDetail(mdk, idRol, "PRODUCTADMINISTRATION", "packageType", packageType, usuario, ya);
            MaintenanceSQL.saveRolDetail(mdk, idRol, "PRODUCTADMINISTRATION", "metricSystem", metricSystem, usuario, ya);
            MaintenanceSQL.saveRolDetail(mdk, idRol, "PRODUCTADMINISTRATION", "style", style, usuario, ya);
            MaintenanceSQL.saveRolDetail(mdk, idRol, "PRODUCTADMINISTRATION", "size", size, usuario, ya);
            MaintenanceSQL.saveRolDetail(mdk, idRol, "PRODUCTADMINISTRATION", "texture", texture, usuario, ya);
            MaintenanceSQL.saveRolDetail(mdk, idRol, "PRODUCTADMINISTRATION", "color", color, usuario, ya);

            tn.commit();
        } catch (HibernateException x) {
            mensajes = mensajes + "danger<>Error<>Error at save rol detail: " + ExceptionUtils.getMessage(x) + ".";
            mensaje = true;
            if (tn != null) {
                tn.rollback();
            }
        }
        mensaje = true;
    }

    public void update() {
        if (validateFields()) {
            Transaction tn = null;
            try {
                tn = mdk.beginTransaction();
                DtoRol m = MaintenanceSQL.getRol(mdk, idEdit);
                if (m != null) {

                    m.setDescription(description);
                    m.setModified(Fechas.ya());
                    m.setModifiedBy(usuario);

                    MaintenanceSQL.updateRol(mdk, m);
                    // AdmConsultas.bitacora(o2c, usuario, "Encargado modificado Tipo: " + tipo + ", Codigo: " + codigo);
                    tn.commit();
                    saveRolDetails(m.getId());
                    clearFields();
                    mensajes = mensajes + "info<>Information<>Rol modified successfully.";
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
        DtoRol m = MaintenanceSQL.getRol(mdk, idEdit);
        if (m != null) {
            idEdit = m.getId();
            description = m.getDescription();

            //Administration
            user = MaintenanceSQL.getRolDetail(mdk, idEdit, "ADMINISTRATION", "user");
            rol = MaintenanceSQL.getRolDetail(mdk, idEdit, "ADMINISTRATION", "rol");
            audit = MaintenanceSQL.getRolDetail(mdk, idEdit, "ADMINISTRATION", "audit");

            //Product Components            
            product = MaintenanceSQL.getRolDetail(mdk, idEdit, "PRODUCTCOMPONENTS", "product");
            collection = MaintenanceSQL.getRolDetail(mdk, idEdit, "PRODUCTCOMPONENTS", "collection");
            material = MaintenanceSQL.getRolDetail(mdk, idEdit, "PRODUCTCOMPONENTS", "material");
            subMaterial = MaintenanceSQL.getRolDetail(mdk, idEdit, "PRODUCTCOMPONENTS", "subMaterial");

            //Product Components            
            manufacturer = MaintenanceSQL.getRolDetail(mdk, idEdit, "PRODUCTADMINISTRATION", "manufacturer");
            packageType = MaintenanceSQL.getRolDetail(mdk, idEdit, "PRODUCTADMINISTRATION", "packageType");
            metricSystem = MaintenanceSQL.getRolDetail(mdk, idEdit, "PRODUCTADMINISTRATION", "metricSystem");
            style = MaintenanceSQL.getRolDetail(mdk, idEdit, "PRODUCTADMINISTRATION", "style");
            size = MaintenanceSQL.getRolDetail(mdk, idEdit, "PRODUCTADMINISTRATION", "size");
            texture = MaintenanceSQL.getRolDetail(mdk, idEdit, "PRODUCTADMINISTRATION", "texture");
            color = MaintenanceSQL.getRolDetail(mdk, idEdit, "PRODUCTADMINISTRATION", "color");

        } else {
            mensajes = mensajes + "danger<>Error<>Rol does not exist.";
            mensaje = true;
        }
    }

}
