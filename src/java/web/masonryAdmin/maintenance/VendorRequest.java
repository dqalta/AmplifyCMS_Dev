/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.masonryAdmin.maintenance;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sql.masonryAdmin.admin.AdminSQL;
import sql.masonryAdmin.maintenance.DtoString;
import sql.masonryAdmin.maintenance.DtoVendor;
import sql.masonryAdmin.maintenance.DtoVendorAddress;
import sql.masonryAdmin.maintenance.DtoVendorAddressQuery;
import sql.masonryAdmin.maintenance.DtoVendorContact; //handles de second tab data
import sql.masonryAdmin.maintenance.DtoVendorUser;
import sql.masonryAdmin.maintenance.DtoVendorsPending;
import sql.masonryAdmin.maintenance.MaintenanceSQL;
import util.Fechas;
import util.Generales;
import util.UtilSecurity;
import web.cycle.SendMails;
import web.sesion.ORMUtil;
import web.util.CombosMaintenance;
import web.util.KeyCombos;
import web.util.KeyCombosString;

/**
 *
 * @author CR104978
 */
public class VendorRequest extends ActionSupport implements SessionAware {

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
    boolean flag = false;
    int vendorsPending;
    //Variables de la pantalla
    private ArrayList<DtoVendorsPending> vendorsPendings = new ArrayList<>();//Variable con la lista de datos
    private ArrayList<DtoVendor> vendors = new ArrayList<>();//Variable con la lista de datos
    private ArrayList<DtoVendorAddress> vendorsAddress = new ArrayList<>();//must be used to move the address from temporary table
    //vars of the activation 
    private int idVendorRegister;
    private String companyName;
    private String vname;
    private String phoneNumber;
    private String webSite;
    private String city;
    private String email;
    private String password;

    //vendor vars
    //vendors vars
    private String id;
    private boolean active;
    private int idEdit;
    private String idAddress;
    // vendor address details
    private int idPostalCode;
    //vendor user vars
    private String codeVendorUser;

    public VendorRequest() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        if (session.get("en-sesion") != null) {
            sesionActiva = true;
            mdk = ORMUtil.getSesionCMS().openSession();
            usuario = String.valueOf(session.get("user"));
            permiso = true; //AdmConsultas.getPermiso(o2c, "ADMINISTRACIÓN", "Encargados", usuario);            
            menu = "";//AdmConsultas.menuUsuario(o2c, usuario);  
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

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public int getVendorsPending() {
        return vendorsPending;
    }

    public void setVendorsPending(int vendorsPending) {
        this.vendorsPending = vendorsPending;
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
    public int getIdVendorRegister() {
        return idVendorRegister;
    }

    public void setIdVendorRegister(int idVendorRegister) {
        this.idVendorRegister = idVendorRegister;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<DtoVendorsPending> getVendorsPendings() {
        return vendorsPendings;
    }

    public void setVendorsPending(ArrayList<DtoVendorsPending> vendorsPendings) {
        this.vendorsPendings = vendorsPendings;
    }

    ///get and set for vendors
    public String getId() {
        return id;
    }

    public void setIdvendor(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getIdEdit() {
        return idEdit;
    }

    public void setIdEdit(int idEdit) {
        this.idEdit = idEdit;
    }

    public String getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(String idAddress) {
        this.idAddress = idAddress;
    }

    public ArrayList<DtoVendorAddress> getVendorsAddress() {
        return vendorsAddress;
    }

    public void setVendorsAddress(ArrayList<DtoVendorAddress> vendorsAddress) {
        this.vendorsAddress = vendorsAddress;
    }

    public int getIdPostalCode() {
        return idPostalCode;
    }

    public void setIdPostalCode(int idPostalCode) {
        this.idPostalCode = idPostalCode;
    }

    /**
     * @return the vendors
     */
    public ArrayList<DtoVendor> getVendors() {
        return vendors;
    }

    public void setVendors(ArrayList<DtoVendor> vendors) {
        this.vendors = vendors;
    }

    public String getCodeVendorUser() {
        return codeVendorUser;
    }

    public void setCodeVendorUser(String codeVendorUser) {
        this.codeVendorUser = codeVendorUser;
    }

    ////////
    @Override
    public String execute() { //the class start here
        if (permiso == true) {
            process();

            mdk.close();//Cerrar la conexión de la base de datos SIEMPRE
        }
        return SUCCESS;
    }

    //METODOS ADICIONALES
    public void process() {
        switch (accion) {
            case 1: {

                break;
            }
            case 2: {
                activateAccount();
                break;
            }
            case 3: {
                flag = true;
                deleteVendorRegister(flag);
                break;
            }

        }
        chargeTables();
    }

    public void chargeTables() {

        vendorsPendings = MaintenanceSQL.getVendorsPending(mdk);

    }

    public void readForUpdate() {

        DtoVendorsPending m = MaintenanceSQL.getVendorPending(mdk, idEdit);
        if (m != null) {
            idVendorRegister = m.getIdVendorRegister();
            companyName = m.getCompanyName();
            vname = m.getVname();
            phoneNumber = m.getPhoneNumber();
            webSite = m.getWebSite();
            city = m.getCity();
            email = m.getEmail();
            password = m.getPassword();
        } else {
            mensajes = mensajes + "danger<>Error<>Vendor account does not exist.";
            mensaje = true;
        }
    }

    public void saveVendorAccount() {
        Transaction tn = null;//Inicializo la transacción de la BD en null
        try {
            DtoVendorsPending p = MaintenanceSQL.getVendorPending(mdk, idEdit);

            if (p != null) {
                tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

                DtoVendor m = new DtoVendor();//Creo un objeto del tipo style

                String guid = Generales.generateCode(p.getVname());
                guid = guid + "-" + String.format("%03d", AdminSQL.getConsecutive(mdk, "codeVendor"));

                m.setId(guid);
                id = guid;
                m.setVname(p.getVname());
                m.setCreated(Fechas.ya());
                m.setCreatedBy(usuario);
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);
                m.setActive(true);//Lo puse en true porque se me olvidó crear el check en el formulario, en la noche hacemos eso jajaja

                MaintenanceSQL.saveVendor(mdk, m);

                AdminSQL.incrementConsecutive(mdk, "codeVendor");
                //AdmConsultas.bitacora(o2c, usuario, "Encargado guardado Tipo: " + tipo + ", Codigo: " + codigo);

                tn.commit();

                saveVendorAddress();

            }
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

    public void saveVendorAddress() {
        Transaction tn = null;//Inicializo la transacción de la BD en null
        try {
            DtoVendorsPending v = MaintenanceSQL.getVendorPending(mdk, idEdit);

            if (v != null) {

                tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

                DtoVendorAddress m = new DtoVendorAddress();//Creo un objeto del tipo style

                //Setting the fields, including id -is not auto incremental                                  
                m.setIdVendor(id);
                idPostalCode = MaintenanceSQL.getIdPostalCodesAV(mdk, v.getCity());
                m.setIdPostalCode(idPostalCode);
                m.setDescription("");
                m.setCreated(Fechas.ya());
                m.setCreatedBy(usuario);
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);
                m.setActive(true);//Lo puse en true porque se me olvidó crear el check en el formulario, en la noche hacemos eso jajaja

                MaintenanceSQL.saveVendorAddress(mdk, m);
                //AdmConsultas.bitacora(o2c, usuario, "Encargado guardado Tipo: " + tipo + ", Codigo: " + codigo);
                tn.commit();// Hago Commit a la transacción para guardar el registro

                saveVendorEmail();

            }
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

    public void saveVendorEmail() {
        Transaction tn = null;//Inicializo la transacción de la BD en null
        try {

            DtoVendorsPending v = MaintenanceSQL.getVendorPending(mdk, idEdit);

            if (v != null) {

                tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

                DtoVendorContact m = new DtoVendorContact();//Creo un objeto del tipo style      
                //Setting the fields, including id -is not auto incremental
                m.setIdVendor(id);
                m.setDescription(v.getEmail());
                m.setType("Email");
                m.setCreated(Fechas.ya());
                m.setCreatedBy(usuario);
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);
                m.setActive(true);//Lo puse en true porque se me olvidó crear el check en el formulario, en la noche hacemos eso jajaja

                MaintenanceSQL.saveVendorContact(mdk, m);
                //AdmConsultas.bitacora(o2c, usuario, "Encargado guardado Tipo: " + tipo + ", Codigo: " + codigo);

                tn.commit();// Hago Commit a la transacción para guardar el registro          
                saveVendorPhoneNumber();
            }
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

    public void saveVendorPhoneNumber() {
        Transaction tn = null;//Inicializo la transacción de la BD en null
        try {

            DtoVendorsPending v = MaintenanceSQL.getVendorPending(mdk, idEdit);

            if (v != null) {
                tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

                DtoVendorContact m = new DtoVendorContact();//Creo un objeto del tipo style      
                //Setting the fields, including id -is not auto incremental
                m.setIdVendor(id);
                m.setDescription(v.getPhoneNumber());
                m.setType("PhoneNumber");
                m.setCreated(Fechas.ya());
                m.setCreatedBy(usuario);
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);
                m.setActive(true);//Lo puse en true porque se me olvidó crear el check en el formulario, en la noche hacemos eso jajaja

                MaintenanceSQL.saveVendorContact(mdk, m);
                //AdmConsultas.bitacora(o2c, usuario, "Encargado guardado Tipo: " + tipo + ", Codigo: " + codigo);

                tn.commit();// Hago Commit a la transacción para guardar el registro          
                saveVendorWebSite();
            }
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

    public void saveVendorWebSite() {
        Transaction tn = null;//Inicializo la transacción de la BD en null
        try {

            DtoVendorsPending v = MaintenanceSQL.getVendorPending(mdk, idEdit);

            if (v != null) {

                tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

                DtoVendorContact m = new DtoVendorContact();//Creo un objeto del tipo style      
                //Setting the fields, including id -is not auto incremental
                m.setIdVendor(id);
                m.setDescription(v.getWebSite());
                m.setType("Web Site");
                m.setCreated(Fechas.ya());
                m.setCreatedBy(usuario);
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);
                m.setActive(true);//Lo puse en true porque se me olvidó crear el check en el formulario, en la noche hacemos eso jajaja

                MaintenanceSQL.saveVendorContact(mdk, m);
                //AdmConsultas.bitacora(o2c, usuario, "Encargado guardado Tipo: " + tipo + ", Codigo: " + codigo);

                tn.commit();// Hago Commit a la transacción para guardar el registro          
                saveVendorCompany();
            }
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

    public void saveVendorCompany() {
        Transaction tn = null;//Inicializo la transacción de la BD en null
        try {
            tn = mdk.beginTransaction();//Inicializo la transacción de la DB 
            DtoVendorsPending v = MaintenanceSQL.getVendorPending(mdk, idEdit);

            if (v != null) {

                DtoVendorContact m = new DtoVendorContact();//Creo un objeto del tipo style      
                //Setting the fields, including id -is not auto incremental
                m.setIdVendor(id);
                m.setDescription(v.getCompanyName());
                m.setType("Company");
                m.setCreated(Fechas.ya());
                m.setCreatedBy(usuario);
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);
                m.setActive(true);//Lo puse en true porque se me olvidó crear el check en el formulario, en la noche hacemos eso jajaja

                MaintenanceSQL.saveVendorContact(mdk, m);
                //AdmConsultas.bitacora(o2c, usuario, "Encargado guardado Tipo: " + tipo + ", Codigo: " + codigo);

                tn.commit();// Hago Commit a la transacción para guardar el registro          
                createVendorAccount();

            }
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

    public void createVendorAccount() {
        Transaction tn = null;//Always starts the database in null state
        try {

            DtoVendorsPending p = MaintenanceSQL.getVendorPending(mdk, idEdit);

            if (p != null) {

                tn = mdk.beginTransaction();//Begins Database transaction

                DtoVendorUser m = new DtoVendorUser();//Creates a vendor user DTO
                int consecutiveVendor;

                consecutiveVendor = AdminSQL.getConsecutiveVendor(mdk, "codeVendorUser", id);

                if (consecutiveVendor == 0) {
                    AdminSQL.saveConsecutiveVendor(mdk, "codeVendorUser", id);
                    consecutiveVendor = 1;
                }
                String code_ = Generales.generateCode(p.getVname());
                code_ = code_ + String.format("%03d", consecutiveVendor);
                codeVendorUser = code_;

                m.setCodeVendorUser(codeVendorUser);
                m.setNickName("");
                m.setFullName(p.getVname());
                m.setEmail(p.getEmail());
                //m.setPasswordVendorUser(UtilSecurity.encript(UtilSecurity.randomPassword(3, 2, 1, 2)));
                m.setPasswordVendorUser(p.getPassword());
                m.setIdVendor(id);
                m.setCreated(Fechas.ya());
                m.setCreatedBy(usuario);
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);
                m.setActive(active);
                m.setStatusVendorUser("Approved");
                //Set id roles by vendor user
                AdminSQL.saveVendorUser(mdk, m);
                AdminSQL.incrementConsecutiveVendor(mdk, "codeVendorUser", id);
                tn.commit();// Hago Commit a la transacción para guardar el registro
                assignVendorRol(codeVendorUser);

            }
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

    public void assignVendorRol(String codeVendorUser) {

        Transaction tn = null;
        try {
            tn = mdk.beginTransaction();
            DtoVendorUser p = AdminSQL.getVendorUser(mdk, codeVendorUser);
            if (p != null) {

                AdminSQL.saveVendorUserRol(mdk, 19, p.getCodeVendorUser(), p.getIdVendor());
                tn.commit();
                assignVendorRolDetails(AdminSQL.getLastIdVendorRol(mdk, usuario));

            }
        } catch (HibernateException x) {
            mensajes = mensajes + "danger<>Error<>Assign the role was unable " + codeVendorUser + ": " + ExceptionUtils.getMessage(x) + ".";
            mensaje = true;
            if (tn != null) {
                tn.rollback();
            }
        }
    }

    public void assignVendorRolDetails(int _idRol) {

        Transaction tn = null;
        try {
            tn = mdk.beginTransaction();
            DtoVendorUser c = AdminSQL.getVendorUser(mdk, codeVendorUser);
            if (c != null) {

                int idRol = _idRol;

                Date ya = Fechas.ya();

                //Administration
                AdminSQL.saveVendorRolDetail(mdk, idRol, "ADMINISTRATION", "user", true, usuario, ya, c.getCodeVendorUser());
                AdminSQL.saveVendorRolDetail(mdk, idRol, "ADMINISTRATION", "rol", true, usuario, ya, c.getCodeVendorUser());
                AdminSQL.saveVendorRolDetail(mdk, idRol, "ADMINISTRATION", "audit", true, usuario, ya, c.getCodeVendorUser());

                //VendorStructure
                AdminSQL.saveVendorRolDetail(mdk, idRol, "PRODUCST&ORDERS", "My products", true, usuario, ya, c.getCodeVendorUser());
                AdminSQL.saveVendorRolDetail(mdk, idRol, "PRODUCST&ORDERS", "Shipping Rules", true, usuario, ya, c.getCodeVendorUser());
                AdminSQL.saveVendorRolDetail(mdk, idRol, "PRODUCST&ORDERS", "Shipping Codes", true, usuario, ya, c.getCodeVendorUser());
                AdminSQL.saveVendorRolDetail(mdk, idRol, "PRODUCST&ORDERS", "Orders", true, usuario, ya, c.getCodeVendorUser());
                AdminSQL.saveVendorRolDetail(mdk, idRol, "PRODUCST&ORDERS", "Inventory", true, usuario, ya, c.getCodeVendorUser());

                tn.commit();
                flag = false;
                deleteVendorRegister(flag);
            }
        } catch (HibernateException x) {
            mensajes = mensajes + "danger<>Error<>Error at save rol detail: " + ExceptionUtils.getMessage(x) + ".";
            mensaje = true;
            if (tn != null) {
                tn.rollback();
            }
        }
        mensaje = true;

    }

    public void sendLinks() {
        Transaction tn = null;
        try {
            DtoVendorsPending p = MaintenanceSQL.getVendorPending(mdk, idEdit);

            if (p != null) {

                SendMails activation = new SendMails();
                System.out.println(p.getEmail());
                //activation.sendMail(p.getEmail());
                mensajes = mensajes + "info<>Account activated successfully.";
                mensaje = true;
            }
        } catch (HibernateException x) {
            mensajes = mensajes + "danger<>Error<>Email not sent" + ExceptionUtils.getMessage(x) + ".";
            mensaje = true;
            if (tn != null) {
                tn.rollback();
            }
        }

    }

    public void deleteVendorRegister(boolean flag) {
        Transaction tn = null;
        try {
            DtoVendorsPending p = MaintenanceSQL.getVendorPending(mdk, idEdit);

            if (p != null) {

                tn = mdk.beginTransaction();
                System.out.println(flag);
                MaintenanceSQL.deleteVendorsPending(mdk, p.getIdVendorRegister());
                tn.commit();
                if (flag) {
                    chargeTables();
                    mensajes = mensajes + "danger<>Error<>Account rejected successfully";
                    mensaje = true;

                } else {
                    sendLinks();
                }
            }
        } catch (HibernateException x) {
            mensajes = mensajes + "danger<>Error<>Delete process not permitted" + ExceptionUtils.getMessage(x) + ".";
            mensaje = true;
            if (tn != null) {
                tn.rollback();
            }
        }
    }

    public void activateAccount() {
        DtoVendorsPending v = MaintenanceSQL.getVendorPending(mdk, idEdit);
        if (v != null) {
            saveVendorAccount();
            //  assignVendorRol(codeVendorUser);
            //  assignVendorRolDetails();
            // sendLinks();
            //  deleteVendorRegister(); //good
            chargeTables();
        }

    }

}
