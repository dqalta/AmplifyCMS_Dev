/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.masonryAdmin.maintenance;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sql.masonryAdmin.maintenance.DtoColor;
import sql.masonryAdmin.maintenance.DtoGallery;
import sql.masonryAdmin.maintenance.DtoGalleryQuery;
import sql.masonryAdmin.maintenance.DtoPhoto;
import sql.masonryAdmin.maintenance.DtoPhotoQuery;
import sql.masonryAdmin.maintenance.MaintenanceSQL;
import util.Fechas;
import web.sesion.ORMUtil;
import web.util.CombosMaintenance;
import web.util.KeyCombos;

/**
 *
 * @author CR104978
 */
public class Gallery extends ActionSupport implements SessionAware {

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

    //Variables de la pantallaprivate 
    ArrayList<DtoGalleryQuery> galleries = new ArrayList<>();//Variable con la lista de datos
    ArrayList<DtoPhotoQuery> galleriesPhotos = new ArrayList<>();//Variable con la lista de datos

    private ArrayList<KeyCombos> manufacturers = new ArrayList<>();
    private ArrayList<KeyCombos> collections = new ArrayList<>();

    //Variables del mantenimiento
    private int id;
    String description;
    private int[] collection;
    private int[] manufacturer;
    boolean active;
    int idEdit;
    private boolean existGallery;

    //Archivo  
    int idPhoto;
    private File[] photo;
    private String[] photoContentType;
    private String[] photoFileName;

    public Gallery() {
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

    public int getAccion() {
        return accion;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }

    public int getVendorsPending() {
        return vendorsPending;
    }

    public void setVendorsPending(int vendorsPending) {
        this.vendorsPending = vendorsPending;
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
    //// Set and get from DTO's
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<KeyCombos> getManufacturers() {
        return manufacturers;
    }

    public ArrayList<KeyCombos> getCollections() {
        return collections;
    }

    public void setManufacturers(ArrayList<KeyCombos> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public void setCollections(ArrayList<KeyCombos> collections) {
        this.collections = collections;
    }

    public boolean isExistGallery() {
        return existGallery;
    }

    public void setExistGallery(boolean existGallery) {
        this.existGallery = existGallery;
    }

    public int[] getCollection() {
        return collection;
    }

    public void setCollection(int[] collection) {
        this.collection = collection;
    }

    public int[] getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(int[] manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
            case 0:
                chargeGalleries();
                break;
            case 1:
                saveGallery();
                break;
            case 2:
                readForUpdate();
                break;
        }
        chargeGalleries();
        chargeCombos();
        existGallery = MaintenanceSQL.getGallery(mdk, id) != null;
    }

    public void readForUpdate() {
        DtoGallery m = MaintenanceSQL.getGallery(mdk, idEdit);
        if (m != null) {
            idEdit = m.getId();
            description = m.getDescription();
            manufacturer = MaintenanceSQL.getGalleryManufacturer(mdk, idEdit);
            collection = MaintenanceSQL.getGalleryCollection(mdk, idEdit);
            chargeCombos();
            chargeGalleryPhotos();
        } else {
            mensajes = mensajes + "danger<>Error<>Gallery does not exist.";
            mensaje = true;
        }
    }

    public void chargeGalleries() {
        ArrayList<DtoGalleryQuery> galleriesBack = new ArrayList<>();//Variable con la lista de datos
        galleriesBack = MaintenanceSQL.getGalleries(mdk);
        ArrayList<DtoPhoto> galleryPhotos = new ArrayList<>();//Variable con la lista de datos
        for (int i = 0; i < galleriesBack.size(); i++) {
            galleryPhotos = MaintenanceSQL.getGalleryPhotos(mdk, galleriesBack.get(i).getId());
            galleriesBack.get(i).setQuantity(galleryPhotos.size());
            galleriesBack.get(i).setPhoto("http://3.15.28.209:8080/MasonryCMS/masonryAdmin/queries/ajax/see-photo.mdk?token=" + galleryPhotos.get(0).getId());
        }
        galleries = galleriesBack;
    }

    public void chargeGalleryPhotos() {
        ArrayList<DtoPhotoQuery> galleriesBack = new ArrayList<>();//Variable con la lista de datos
        galleriesBack = MaintenanceSQL.getGalleryPhotosQuery(mdk,idEdit);
        for (int i = 0; i < galleriesBack.size(); i++) {
            galleriesBack.get(i).setPhoto("http://3.15.28.209:8080/MasonryCMS/masonryAdmin/queries/ajax/see-photo.mdk?token=" + galleriesBack.get(i).getId());
        }
        galleriesPhotos = galleriesBack;
    }

    public void clearFields() {
        id = 0;
        description = "";
        accion = 0;
        idEdit = 0;
        active = false;
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
        if ((collection == null) || (collection.length == 0)) {
            mensajes = mensajes + "danger<>Error<>Please complete field 'Collection'.|";
            flag = false;
        }
        if ((manufacturer == null) || (manufacturer.length == 0)) {
            mensajes = mensajes + "danger<>Error<>Please complete field 'Manufacturer'.|";
            flag = false;
        }
        if ((photo == null) || (photo.length == 0)) {
            mensajes = mensajes + "danger<>Error<>Please select almost one photo.|";
            flag = false;
        }
        if (!flag) {
            mensaje = true;
        }
        return flag;
    }

    public void chargeCombos() {
        manufacturers = CombosMaintenance.getManufacturers(mdk);
        if (manufacturer != null) {
            collections = CombosMaintenance.getCollectionsByManufacturers(mdk, manufacturer);
        }
        //collections = CombosMaintenance.getCollections(mdk);
    }

    public void uploadPhotos() {
        Transaction tn = null;
        for (int i = 0; i < photo.length; i++) {
            try {
                if (photo[i] != null) {
                    tn = mdk.beginTransaction();
                    DtoPhoto p;
                    p = new DtoPhoto();
                    p.setIdGallery(id);
                    p.setPhotoFileName(photoFileName[i]);
                    p.setPhotoContentType(photoContentType[i]);
                    MaintenanceSQL.saveGalleryPhoto(mdk, p, photo[i]);

                    tn.commit();
                    accion = 0;

                } else {
                    mensajes = mensajes + "danger<>Error<>El archivo " + photoFileName[i] + " no existe.|";
                    mensaje = true;
                }
            } catch (HibernateException x) {
                System.out.println("Error Adjunto: " + x.getMessage());
                x.printStackTrace();
                mensajes = mensajes + "danger<>Error<>Error al adjuntar documento: " + ExceptionUtils.getMessage(x) + ".|";
                mensaje = true;
                if (tn != null) {
                    tn.rollback();
                }
                mensaje = true;
            }
        }
        mensajes = mensajes + "info<>Informaci\u00f3n<>Photos uploaded successfully.|";
        mensaje = true;
        accion = 3;
    }

    public void saveGallery() {
        if (validateFields()) {//Valido los campos del formulario
            Transaction tn = null;//Inicializo la transacción de la BD en null
            try {
                tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

                DtoGallery m = new DtoGallery();//Creo un objeto del tipo color                
                //Seteo los datos del objeto excepto el id por que es Auto Incremental
                m.setDescription(description);
                m.setCreated(Fechas.ya());
                m.setCreatedBy(usuario);
                m.setModified(Fechas.ya());
                m.setModifiedBy(usuario);

                MaintenanceSQL.saveGallery(mdk, m);
                //AdmConsultas.bitacora(o2c, usuario, "Encargado guardado Tipo: " + tipo + ", Codigo: " + codigo);

                tn.commit();// Hago Commit a la transacción para guardar el registro
                existGallery = true;
                id = MaintenanceSQL.lastIdGallery(mdk, usuario);

                saveGalleryManufacturer();
                saveGalleryCollection();
                uploadPhotos();

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

    public void saveGalleryManufacturer() {
        Transaction tn = null;//Inicializo la transacción de la BD en null
        try {
            tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

            for (int i = 0; i < manufacturer.length; i++) {
                MaintenanceSQL.saveGalleryManufacturer(mdk, id, manufacturer[i]);
            }

            //AdmConsultas.bitacora(o2c, usuario, "Encargado guardado Tipo: " + tipo + ", Codigo: " + codigo);
            tn.commit();// Hago Commit a la transacción para guardar el registro

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

    public void saveGalleryCollection() {
        Transaction tn = null;//Inicializo la transacción de la BD en null
        try {
            tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

            for (int i = 0; i < collection.length; i++) {
                MaintenanceSQL.saveGalleryCollection(mdk, id, collection[i]);
            }

            //AdmConsultas.bitacora(o2c, usuario, "Encargado guardado Tipo: " + tipo + ", Codigo: " + codigo);
            tn.commit();// Hago Commit a la transacción para guardar el registro

            mensajes = mensajes + "info<>Information<>Gallery saved successfully.|";
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

    public File[] getPhoto() {
        return photo;
    }

    public void setPhoto(File[] photo) {
        this.photo = photo;
    }

    public String[] getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String[] photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String[] getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String[] photoFileName) {
        this.photoFileName = photoFileName;
    }

    public ArrayList<DtoGalleryQuery> getGalleries() {
        return galleries;
    }

    public void setGalleries(ArrayList<DtoGalleryQuery> galleries) {
        this.galleries = galleries;
    }

    public int getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(int idPhoto) {
        this.idPhoto = idPhoto;
    }

    public ArrayList<DtoPhotoQuery> getGalleriesPhotos() {
        return galleriesPhotos;
    }

    public void setGalleriesPhotos(ArrayList<DtoPhotoQuery> galleriesPhotos) {
        this.galleriesPhotos = galleriesPhotos;
    }

    public int getIdEdit() {
        return idEdit;
    }

    public void setIdEdit(int idEdit) {
        this.idEdit = idEdit;
    }

}
