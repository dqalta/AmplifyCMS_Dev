/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.masonryAdmin.admin;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
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
import sql.masonryAdmin.admin.DtoEmail;
import sql.masonryAdmin.admin.DtoUser;
import sql.masonryAdmin.maintenance.DtoColor;
import sql.masonryAdmin.maintenance.DtoVendorContact;
import sql.masonryAdmin.maintenance.MaintenanceSQL;
import util.Emails;
import util.Fechas;
import util.Generales;
import util.UtilSecurity;
import web.sesion.ORMUtil;
import web.util.CombosAdmin;
import web.util.KeyCombos;

/**
 *
 * @author CR104978
 */
public class User extends ActionSupport implements SessionAware {

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
    ArrayList<DtoUser> users = new ArrayList<>();//Variable con la lista de datos
    ArrayList<KeyCombos> roles = new ArrayList<>();

    //Variables del mantenimiento
    String code;
    String fullName;
    String email;
    boolean active;
    int idEdit;
    String[] rol;
    boolean menuAdmin;
    boolean menuProdAdmin;
    boolean menuProdComp;

    public User() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        if (session.get("en-sesion") != null) {
            sesionActiva = true;
            mdk = ORMUtil.getSesionCMS().openSession();
            usuario = String.valueOf(session.get("user"));
            permiso = true; //AdmConsultas.getPermiso(o2c, "ADMINISTRACIÓN", "Encargados", usuario);            
            menu = "";//AdmConsultas.menuUsuario(o2c, usuario);
            chargeSelect();
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

    public ArrayList<KeyCombos> getRoles() {
        return roles;
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
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String[] getRol() {
        return rol;
    }

    public void setRol(String[] rol) {
        this.rol = rol;
    }

    public boolean isMenuAdmin() {
        return menuAdmin;
    }

    public void setMenuAdmin(boolean menuAdmin) {
        this.menuAdmin = menuAdmin;
    }

    public boolean isMenuProdAdmin() {
        return menuProdAdmin;
    }

    public void setMenuProdAdmin(boolean menuProdAdmin) {
        this.menuProdAdmin = menuProdAdmin;
    }

    public boolean isMenuProdComp() {
        return menuProdComp;
    }

    public int getVendorsPending() {
        return vendorsPending;
    }

    public void setVendorsPending(int vendorsPending) {
        this.vendorsPending = vendorsPending;
    }

    public void setMenuProdComp(boolean menuProdComp) {
        this.menuProdComp = menuProdComp;
    }

    public ArrayList<DtoUser> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<DtoUser> users) {
        this.users = users;
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
            case 3:
                activeUser();
                break;
        }
        chargeUsers();
    }

    public void activeUser() {
        Transaction tn = null;
        try {
            tn = mdk.beginTransaction();
            DtoUser m = AdminSQL.getUser(mdk, idEdit);
            if (m != null) {
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);
                m.setActive(!m.isActive());
                AdminSQL.updateUser(mdk, m);
                // AdmConsultas.bitacora(o2c, usuario, "Encargado modificado Tipo: " + tipo + ", Codigo: " + codigo);

                tn.commit();

                mensajes = mensajes + "info<>Information<>Status of the User modified successfully.";
                mensaje = true;
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

    public void readForUpdate() {
        DtoUser m = AdminSQL.getUser(mdk, idEdit);
        if (m != null) {
            idEdit = m.getId();
            code = m.getCodeUser();
            fullName = m.getFullName();
            email = m.getEmail();
            active = m.isActive();
            menuAdmin = m.isMenuAdmin();
            menuProdAdmin = m.isMenuProdAdmin();
            menuProdComp = m.isMenuProdComp();
            rol = AdminSQL.getUserRols(mdk, m.getCodeUser()).split(",");
        } else {
            mensajes = mensajes + "danger<>Error<>User does not exist.";
            mensaje = true;
        }
    }

    public void chargeSelect() {
        roles = CombosAdmin.roles(mdk);
    }

    public void clearFields() {
        accion = 0;
        idEdit = 0;
        code = "";
        fullName = "";
        email = "";
        active = true;
        menuAdmin = false;
        menuProdAdmin = false;
        menuProdComp = false;
        rol = null;
        chargeSelect();
    }

    public boolean validateFields() {
        boolean flag = true;
        mensajes = "";
        mensaje = false;
        //VALIDAR QUE CAMPOS NO SEAN BLANCOS NI NULOS
        if ((fullName == null) || (fullName.isEmpty())) {
            mensajes = mensajes + "danger<>Error<>Please complete field 'Full Name'.|";
            flag = false;
            mensaje = true;

        }

        if ((email == null) || (email.isEmpty())) {
            mensajes = mensajes + "danger<>Error<>Please complete field 'Email'.|";
            flag = false;
            mensaje = true;
        }
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");

        Matcher mather = pattern.matcher(email);
        if (!mather.find()) {
            mensajes = mensajes + "danger<>Error<>The Email hasn't a valid text format.|";
            flag = false;
            mensaje = true;

        }

        if (rol.length == 0) {
            mensajes = mensajes + "danger<>Error<>Select one 'Rol'.|";
            flag = false;
            mensaje = true;
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

    public void chargeUsers() {
        users = AdminSQL.getUsers(mdk);
    }

    public void update() {
        if (validateFields()) {
            Transaction tn = null;
            try {
                tn = mdk.beginTransaction();
                DtoUser p = AdminSQL.getUser(mdk, idEdit);
                if (p != null) {

                    p.setFullName(fullName);
                    p.setEmail(email);
                    p.setMenuAdmin(menuAdmin);
                    p.setMenuProdAdmin(menuProdAdmin);
                    p.setMenuProdComp(menuProdComp);
                    p.setModified(Fechas.ya());
                    p.setModifiedBy(usuario);
                    p.setActive(active);

                    AdminSQL.updateUser(mdk, p);

                    tn.commit();

                    saveRols(p.getCodeUser());
                    clearFields();
                    mensajes = mensajes + "info<>Informaci\u00f3n<>User " + code + " modified successfully .";
                    mensaje = true;
                } else {
                    save();
                }
            } catch (Exception e) {
                mensajes = mensajes + "danger<>Error<>Error ." + e.getMessage() + "|";
                mensaje = true;
                if (tn != null) {
                    tn.rollback();
                }
            }
            mensaje = true;
        }
    }

    public void insert() {
        if (validateFields()) {//Valido los campos del formulario
            Transaction tn = null;//Inicializo la transacción de la BD en null
            try {
                tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

                DtoUser m = new DtoUser();//Creo un objeto del tipo Manufacturer

                String code_ = Generales.generateCode(fullName);
                code_ = code_ + "-" + String.format("%03d", AdminSQL.getConsecutive(mdk, "codeUser"));
                code = code_;

                String guid = RandomStringUtils.randomAlphanumeric(40);
                boolean flag = (AdminSQL.getUserByGuid(mdk, guid) != null);

                while (flag) {
                    guid = RandomStringUtils.randomAlphanumeric(40);
                    flag = (AdminSQL.getUserByGuid(mdk, guid) != null);
                }

                m.setCodeUser(code);
                m.setGuid(guid);
                m.setFullName(fullName);
                m.setEmail(email);
                m.setPasswordUser(UtilSecurity.encript(UtilSecurity.randomPassword(3, 2, 1, 2)));
                m.setMenuAdmin(menuAdmin);
                m.setMenuProdAdmin(menuProdAdmin);
                m.setMenuProdComp(menuProdComp);
                m.setCreated(Fechas.ya());
                m.setCreatedBy(usuario);
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);
                m.setActive(active);

                AdminSQL.saveUser(mdk, m);

                AdminSQL.incrementConsecutive(mdk, "codeUser");

                //CORREO
                DtoEmail mail = new DtoEmail();
                mail.setRecipients(email);
                mail.setSubject("Masonry CMS invitation");
                String htmlEmail = "";
                htmlEmail = htmlEmail.concat(Emails.head());
                htmlEmail = htmlEmail.concat(Emails.body("", "", "", "", "", ""));
                htmlEmail = htmlEmail.concat(Emails.footer(""));
                mail.setBody(htmlEmail);
                mail.setStatus("PENDING");
                mail.setCreated(Fechas.ya());
                mail.setAttempts(0);
                mail.setObservations("");
                mail.setType("UserInvitation");

                AdminSQL.saveEmail(mdk, mail);

                tn.commit();

                saveRols(code);
                clearFields();

                //clearFields();
                mensajes = mensajes + "info<>Information<>User saved successfully.";
                mensaje = true;

            } catch (Exception e) {
                //AdmConsultas.error(o2c, x.getMessage());
                // mensajes = mensajes + "danger<>Error<>Error al guardar encargados: " + codigo + ": " + ExceptionUtils.getMessage(x) + ".";
                mensajes = mensajes + "danger<>Error<>Error." + e.getMessage() + "|";
                mensaje = true;
                System.out.println(e.getMessage());
                if (tn != null) {//Si hay error y el transacción es distinto de null, es porque la transacción existe, entoncs hago rollback
                    tn.rollback();
                }
            }
            mensaje = true;
        }
    }

    public void saveRols(String code) {
        Transaction tn = null;
        try {
            tn = mdk.beginTransaction();
            DtoUser p = AdminSQL.getUser(mdk, code);
            if (p != null) {

                AdminSQL.deleteUserRol(mdk, code);

                for (int i = 0; i < rol.length; i++) {
                    AdminSQL.saveUserRol(mdk, code, rol[i]);
                }
                tn.commit();
            } else {
                save();
            }
        } catch (Exception e) {
            //AdmConsultas.error(o2c, e.getMessage())
            mensajes = mensajes + "danger<>Error<>Error." + e.getMessage() + "|";
            mensaje = true;
            if (tn != null) {
                tn.rollback();
            }
        }
    }

}
