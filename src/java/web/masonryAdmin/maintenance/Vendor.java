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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sql.masonryAdmin.maintenance.DtoVendor;
import sql.masonryAdmin.maintenance.DtoVendorAddress;
import sql.masonryAdmin.maintenance.DtoVendorContact; //handles de second tab data
import sql.masonryAdmin.maintenance.MaintenanceSQL;
import util.Fechas;
import web.sesion.ORMUtil;
import web.util.CombosMaintenance;
import web.util.KeyCombos;
import web.util.KeyCombosString;

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
    private boolean existVendor;
    //Variables de la pantalla
    private ArrayList<DtoVendor> vendors = new ArrayList<>();//Variable con la lista de datos
    private ArrayList<DtoVendorContact> vendorsContacts = new ArrayList<>();//Variable con la lista de datos
    private ArrayList<DtoVendorAddress> vendorsAddress = new ArrayList<>();//Variable con la lista de datos


    //Handles the postal codes 
   // private ArrayList<KeyCombosPostalCodes> postalCodes = new ArrayList<>();//Variable con la lista de datos
    private ArrayList<KeyCombos> types = new ArrayList<>();
    private ArrayList<KeyCombosString> provincePostalCodes = new ArrayList<>();
    private ArrayList<KeyCombosString> cities = new ArrayList<>();
    private ArrayList<KeyCombosString> postalCodes = new ArrayList<>();


    //vendors vars
    private String id;
    private String vname;
    private boolean active;
    private String idEdit;
    private String idAddress;
    // vendor contact vars
    private int idContact;
    private String description;
    private String type;
    // vendor address details
    private String province;
    private String city;
    //vendors address vars
    private int idPostalCode;
    private String postalCode;
    
    public Vendor() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        if (session.get("en-sesion") != null) {
            sesionActiva = true;
            mdk = ORMUtil.getSesionCMS().openSession();
            usuario = String.valueOf(session.get("user"));
            permiso = true; //AdmConsultas.getPermiso(o2c, "ADMINISTRACIÓN", "Encargados", usuario);            
            menu = "";//AdmConsultas.menuUsuario(o2c, usuario);
            chargeSelect();
            
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
    //controls tabs navigation
    public boolean isExistVendor() {
        return existVendor;
    }

    public void setExistVendor(boolean existVendor) {
        this.existVendor = existVendor;
    }

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
//refactor of vendor contact vars

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public ArrayList<KeyCombos> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<KeyCombos> types) {
        this.types = types;
    }

    public ArrayList<DtoVendorContact> getVendorsContacts() {
        return vendorsContacts;
    }

    public void setVendorsContacts(ArrayList<DtoVendorContact> vendorsContacts) {
        this.vendorsContacts = vendorsContacts;
    }


    public ArrayList<KeyCombosString> getProvincePostalCodes() {
        return provincePostalCodes;
    }

  
    public void setProvincePostalCodes(ArrayList<KeyCombosString> provincePostalCodes) {
        this.provincePostalCodes = provincePostalCodes;
    }

    public int getIdContact() {
        return idContact;
    }

    public void setIdContact(int idContact) {
        this.idContact = idContact;
    }
   public ArrayList<KeyCombosString> getCities() {
        return cities;
    }


    public void setCities(ArrayList<KeyCombosString> cities) {
        this.cities = cities;
    }

  
    public ArrayList<KeyCombosString> getPostalCodes() {
        return postalCodes;
    }

    public void setPostalCodes(ArrayList<KeyCombosString> postalCodes) {
        this.postalCodes = postalCodes;
    }
    public String getCity() {
        return city;
    }


    public void setCity(String city) {
        this.city = city;
    }

   
    public int getIdPostalCode() {
        return idPostalCode;
    }


    public void setIdPostalCode(int idPostalCode) {
        this.idPostalCode = idPostalCode;
    }
    
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    ////Set and get of the subtable Vendor Address
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
                save();
                break;
            }
            case 2: {
                readForUpdate();
                break;
            }
            case 3: {
                saveContact();
                break;
            }
            case 4: {
                deleteContact();
                break;
            }
            case 5: {
                activeContact();
                break;
            }
             case 6: {
                saveAddress();
                break;
            }
              case 7: {
                activeAddress();
                break;
            }
                case 8: {
                deleteAddress();
                break;
            }
        }
        chargeTables();
existVendor = MaintenanceSQL.getVendor(mdk, id) != null;
    }

    public void clearFields() {
        id = "";
        vname = "";
        accion = 0;
        idEdit = "";

        active = false;
    }

    public void clearFieldsContact() {
        description = "";
        type = "Email";

    }
  public void clearFieldsAddress() {
        description = "";        

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

    public boolean validateFieldsContact() {
        boolean flag = true;
        mensajes = "";
        mensaje = false;
        //VALIDAR QUE CAMPOS NO SEAN BLANCOS NI NULOS
        if ((description == null) || (description.isEmpty())) {
            mensajes = mensajes + "danger<>Error<>Please complete field 'Description Contact'.|";
            flag = false;
            mensaje = true;

        }
        if (type.equals("Email")) {
            Pattern pattern = Pattern.compile("^(.+)@(.+)$");
                                                
            Matcher mather = pattern.matcher(description);
            if (!mather.find()) {
                mensajes = mensajes + "danger<>Error<>The Email hasn't a valid text format.|";
                flag = false;
                mensaje = true;

            }
        }
       
        return flag;
    }
 public boolean validateFieldsAddress() {
        boolean flag = true;
        mensajes = "";
        mensaje = false;
        //VALIDAR QUE CAMPOS NO SEAN BLANCOS NI NULOS
        if ((description == null) || (description.isEmpty())) {
            mensajes = mensajes + "danger<>Error<>Please complete field 'Address Details'.|";
            flag = false;
            mensaje = true;

        }
        if (city.equals("NONE")) {
                   mensajes = mensajes + "danger<>Error<>The postal code must be selected.|";
                flag = false;
                mensaje = true;

            
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

    public void deleteContact() {
        Transaction tn = null;
        try {
            tn = mdk.beginTransaction();

            MaintenanceSQL.deleteVendorContact(mdk, idContact);
            tn.commit();
            mensajes = mensajes + "info<>Information<>Contact deleted successfully";
            mensaje = true;

        } catch (HibernateException x) {
            mensajes = mensajes + "danger<>Error<>Deleted not permitted" + ExceptionUtils.getMessage(x) + ".";
            mensaje = true;
            if (tn != null) {
                tn.rollback();
            }
        }
    }
  public void deleteAddress() {
        Transaction tn = null;
        try {
            tn = mdk.beginTransaction();

            MaintenanceSQL.deleteVendorAddress(mdk, id);
            tn.commit();
            mensajes = mensajes + "info<>Information<>Address deleted successfully";
            mensaje = true;

        } catch (HibernateException x) {
            mensajes = mensajes + "danger<>Error<>Deleted not permitted" + ExceptionUtils.getMessage(x) + ".";
            mensaje = true;
            if (tn != null) {
                tn.rollback();
            }
        }
    }
    public void activeContact() {
        Transaction tn = null;
        try {
            tn = mdk.beginTransaction();
            DtoVendorContact m = MaintenanceSQL.getVendorsContact(mdk, idContact);
            if (m != null) {
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);
                m.setActive(!m.getActive());
                MaintenanceSQL.updateVendorContact(mdk, m);
                // AdmConsultas.bitacora(o2c, usuario, "Encargado modificado Tipo: " + tipo + ", Codigo: " + codigo);

                tn.commit();

                mensajes = mensajes + "info<>Information<>Status of the Contact modified successfully.";
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
        public void activeAddress() {
        Transaction tn = null;
        try {
            tn = mdk.beginTransaction();
            System.out.print("vendedor por parametro"+id);
            DtoVendorAddress m = MaintenanceSQL.getVendorAddress(mdk,id);
            if (m != null) {
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);
                m.setActive(!m.getActive());
                MaintenanceSQL.updateVendorAddress(mdk, m);
                // AdmConsultas.bitacora(o2c, usuario, "Encargado modificado Tipo: " + tipo + ", Codigo: " + codigo);

                tn.commit();

                mensajes = mensajes + "info<>Information<>Status of the Contact modified successfully.";
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

    public void saveContact() {
        if (validateFieldsContact()) {
            Transaction tn = null;//Inicializo la transacción de la BD en null
            try {
                tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

                DtoVendorContact m = new DtoVendorContact();//Creo un objeto del tipo style      
                //Setting the fields, including id -is not auto incremental
                m.setIdVendor(id);
                m.setDescription(description);
                m.setType(type);
                m.setCreated(Fechas.ya());
                m.setCreatedBy(usuario);
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);
                m.setActive(true);//Lo puse en true porque se me olvidó crear el check en el formulario, en la noche hacemos eso jajaja

                MaintenanceSQL.saveVendorContact(mdk, m);
                //AdmConsultas.bitacora(o2c, usuario, "Encargado guardado Tipo: " + tipo + ", Codigo: " + codigo);

                tn.commit();// Hago Commit a la transacción para guardar el registro
                clearFieldsAddress();
                mensajes = mensajes + "info<>Information<>Contact detail saved successfully.";
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
   public void saveAddress() {
        if (validateFieldsAddress()) {
            Transaction tn = null;//Inicializo la transacción de la BD en null
            try {
                tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

                DtoVendorAddress m = new DtoVendorAddress();//Creo un objeto del tipo style
           
                //Setting the fields, including id -is not auto incremental                                  
                m.setIdVendor(id);
                idPostalCode = MaintenanceSQL.getIdPostalCodes(mdk, postalCode);                  
                m.setIdPostalCode(idPostalCode);
                m.setDescription(description);               
                m.setCreated(Fechas.ya());
                m.setCreatedBy(usuario);
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);
                m.setActive(true);//Lo puse en true porque se me olvidó crear el check en el formulario, en la noche hacemos eso jajaja

                MaintenanceSQL.saveVendorAddress(mdk, m);
                //AdmConsultas.bitacora(o2c, usuario, "Encargado guardado Tipo: " + tipo + ", Codigo: " + codigo);

                tn.commit();// Hago Commit a la transacción para guardar el registro
                clearFieldsAddress();
                mensajes = mensajes + "info<>Information<>Vendor's address saved successfully.";
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
    public void chargeTables() {
        vendors = MaintenanceSQL.getVendors(mdk);
        vendorsContacts = MaintenanceSQL.getVendorsContacts(mdk, id);
        System.out.println(id);
        vendorsAddress= MaintenanceSQL.getVendorsAddress(mdk, id);
    }

    public void chargeSelect() {
        types = CombosMaintenance.getTypeContact();
        provincePostalCodes = CombosMaintenance.getProvincePostalCodes(mdk);
        cities = CombosMaintenance.getCitiesPostalCodes(mdk, province); 
        postalCodes= CombosMaintenance.getPostalCodes(mdk, city); 
        
    }
    public void insert() {
        if (validateFields()) {//Valido los campos del formulario
            Transaction tn = null;//Inicializo la transacción de la BD en null
            try {
                tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

                DtoVendor m = new DtoVendor();//Creo un objeto del tipo style

                //Setting the fields, including id -is not auto incremental
                String guid = RandomStringUtils.randomAlphanumeric(15);
                boolean flag = (MaintenanceSQL.getVendor(mdk, guid) != null);

                while (flag) {
                    guid = RandomStringUtils.randomAlphanumeric(15);
                    flag = (MaintenanceSQL.getVendor(mdk, guid) != null);
                }
                m.setId(guid);
                id = guid;
                m.setVname(vname);
                m.setCreated(Fechas.ya());
                m.setCreatedBy(usuario);
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);
                m.setActive(isActive());//Lo puse en true porque se me olvidó crear el check en el formulario, en la noche hacemos eso jajaja

                MaintenanceSQL.saveVendor(mdk, m);
                //AdmConsultas.bitacora(o2c, usuario, "Encargado guardado Tipo: " + tipo + ", Codigo: " + codigo);

                tn.commit();// Hago Commit a la transacción para guardar el registro
                existVendor = true;

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

    public void updateContact() {
        if (validateFields()) {

        }
    }

    public void readForUpdate() {
        DtoVendor m = MaintenanceSQL.getVendor(mdk, getIdEdit());
        if (m != null) {
            id = m.getId();
            vname = m.getVname();
            active = m.getActive();
            existVendor = true;
        } else {
            mensajes = mensajes + "danger<>Error<>Vendor does not exist.";
            mensaje = true;
        }
    }


 



 

 

}
