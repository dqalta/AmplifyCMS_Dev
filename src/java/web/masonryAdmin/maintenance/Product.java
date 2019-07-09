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
import sql.masonryAdmin.maintenance.DtoCollection;
import sql.masonryAdmin.maintenance.DtoManufacturer;
import sql.masonryAdmin.maintenance.DtoProduct;
import sql.masonryAdmin.maintenance.DtoSize;
import sql.masonryAdmin.maintenance.MaintenanceSQL;
import util.Fechas;
import util.Numeros;
import web.sesion.ORMUtil;
import web.util.CombosMaintenance;
import web.util.KeyCombos;

/**
 *
 * @author CR104978
 */
public class Product extends ActionSupport implements SessionAware {

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
    private ArrayList<DtoProduct> products = new ArrayList<>();//Variable con la lista de datos
    private ArrayList<KeyCombos> styles = new ArrayList<>();
    private ArrayList<KeyCombos> textures = new ArrayList<>();
    private ArrayList<KeyCombos> packageTypes = new ArrayList<>();
    private ArrayList<KeyCombos> materials = new ArrayList<>();
    private ArrayList<KeyCombos> subMaterials = new ArrayList<>();
    private ArrayList<KeyCombos> manufacturers = new ArrayList<>();
    private ArrayList<KeyCombos> sizes = new ArrayList<>();
    private ArrayList<KeyCombos> colors = new ArrayList<>();
    private ArrayList<KeyCombos> collections = new ArrayList<>();

    //Variables del mantenimiento
    int id;
    private String pname;
    private String sku;
    private String description;
    private int idStyle;
    private int idTexture;
    private int idPackageType;
    private int idMaterial;
    private int idSubMaterial;
    private int idManufacturer;
    private int idSize;
    private int[] color;
    private int[] collection;
    private String slug;
    private String palletWeight;
    private boolean canSellLayer;
    private int unitsLayer;
    private int layersPallet;
    private int unitsPallet;
    private boolean hasCorner;
    private String linearFeetCorner;
    private String sqftPerPackageType;
    private int qtyOfUnitsPerPackageType;
    boolean active;
    int idEdit;

    public Product() {
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

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public int getAccion() {
        return accion;
    }

    public int getVendorsPending() {
        return vendorsPending;
    }

    public void setVendorsPending(int vendorsPending) {
        this.vendorsPending = vendorsPending;
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
    public ArrayList<DtoProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<DtoProduct> products) {
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEdit() {
        return idEdit;
    }

    public void setIdEdit(int idEdit) {
        this.idEdit = idEdit;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getIdStyle() {
        return idStyle;
    }

    public void setIdStyle(int idStyle) {
        this.idStyle = idStyle;
    }

    public int getIdTexture() {
        return idTexture;
    }

    public void setIdTexture(int idTexture) {
        this.idTexture = idTexture;
    }

    public int getIdPackageType() {
        return idPackageType;
    }

    public void setIdPackageType(int idPackageType) {
        this.idPackageType = idPackageType;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public int getIdSubMaterial() {
        return idSubMaterial;
    }

    public void setIdSubMaterial(int idSubMaterial) {
        this.idSubMaterial = idSubMaterial;
    }

    public int getIdManufacturer() {
        return idManufacturer;
    }

    public void setIdManufacturer(int idManufacturer) {
        this.idManufacturer = idManufacturer;
    }

    public int getIdSize() {
        return idSize;
    }

    public void setIdSize(int idSize) {
        this.idSize = idSize;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPalletWeight() {
        return palletWeight;
    }

    public void setPalletWeight(String palletWeight) {
        this.palletWeight = palletWeight;
    }

    public boolean isCanSellLayer() {
        return canSellLayer;
    }

    public void setCanSellLayer(boolean canSellLayer) {
        this.canSellLayer = canSellLayer;
    }

    public int getUnitsLayer() {
        return unitsLayer;
    }

    public void setUnitsLayer(int unitsLayer) {
        this.unitsLayer = unitsLayer;
    }

    public int getLayersPallet() {
        return layersPallet;
    }

    public void setLayersPallet(int layersPallet) {
        this.layersPallet = layersPallet;
    }

    public int getUnitsPallet() {
        return unitsPallet;
    }

    public void setUnitsPallet(int unitsPallet) {
        this.unitsPallet = unitsPallet;
    }

    public boolean isHasCorner() {
        return hasCorner;
    }

    public void setHasCorner(boolean hasCorner) {
        this.hasCorner = hasCorner;
    }

    public String getLinearFeetCorner() {
        return linearFeetCorner;
    }

    public void setLinearFeetCorner(String linearFeetCorner) {
        this.linearFeetCorner = linearFeetCorner;
    }

    public String getSqftPerPackageType() {
        return sqftPerPackageType;
    }

    public void setSqftPerPackageType(String sqftPerPackageType) {
        this.sqftPerPackageType = sqftPerPackageType;
    }

    public int getQtyOfUnitsPerPackageType() {
        return qtyOfUnitsPerPackageType;
    }

    public void setQtyOfUnitsPerPackageType(int qtyOfUnitsPerPackageType) {
        this.qtyOfUnitsPerPackageType = qtyOfUnitsPerPackageType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ArrayList<KeyCombos> getStyles() {
        return styles;
    }

    public void setStyles(ArrayList<KeyCombos> styles) {
        this.styles = styles;
    }

    public ArrayList<KeyCombos> getTextures() {
        return textures;
    }

    public void setTextures(ArrayList<KeyCombos> textures) {
        this.textures = textures;
    }

    public ArrayList<KeyCombos> getPackageTypes() {
        return packageTypes;
    }

    public void setPackageTypes(ArrayList<KeyCombos> packageTypes) {
        this.packageTypes = packageTypes;
    }

    public ArrayList<KeyCombos> getMaterials() {
        return materials;
    }

    public void setMaterials(ArrayList<KeyCombos> materials) {
        this.materials = materials;
    }

    public ArrayList<KeyCombos> getSubMaterials() {
        return subMaterials;
    }

    public void setSubMaterials(ArrayList<KeyCombos> subMaterials) {
        this.subMaterials = subMaterials;
    }

    public ArrayList<KeyCombos> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(ArrayList<KeyCombos> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public ArrayList<KeyCombos> getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList<KeyCombos> sizes) {
        this.sizes = sizes;
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    public int[] getCollection() {
        return collection;
    }

    public void setCollection(int[] collection) {
        this.collection = collection;
    }

    public ArrayList<KeyCombos> getColors() {
        return colors;
    }

    public void setColors(ArrayList<KeyCombos> colors) {
        this.colors = colors;
    }

    public ArrayList<KeyCombos> getCollections() {
        return collections;
    }

    public void setCollections(ArrayList<KeyCombos> collections) {
        this.collections = collections;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
            case 0:
                palletWeight = "0";
                linearFeetCorner = "0";
                sqftPerPackageType = "0";
                break;
            case 1:
                save();
                break;
            case 2:
                readForUpdate();
                break;
        }
        chargeProducts();
    }

    public void clearFields() {
        id = 0;
        accion = 0;
        idEdit = 0;
        active = false;
    }

    public void chargeSelect() {
        styles = CombosMaintenance.getStyles(mdk);
        textures = CombosMaintenance.getTextures(mdk);
        packageTypes = CombosMaintenance.getPackageTypes(mdk);
        materials = CombosMaintenance.getMaterials(mdk);
        subMaterials = CombosMaintenance.getSubMaterials(mdk, idMaterial);
        manufacturers = CombosMaintenance.getManufacturers(mdk);
        sizes = CombosMaintenance.getSizes(mdk);

        colors = CombosMaintenance.getColors(mdk);
        int[] arr = new int[1];
        arr[0] = idManufacturer;
        collections = CombosMaintenance.getCollectionsByManufacturers(mdk, arr);
        //collections = CombosMaintenance.getCollections(mdk);
    }

    public boolean validateFields() {
        boolean flag = true;
        mensajes = "";
        mensaje = false;
        //VALIDAR QUE CAMPOS NO SEAN BLANCOS NI NULOS
        if ((sku == null) || (sku.isEmpty())) {
            mensajes = mensajes + "danger<>Error<>Please complete field 'SKU'.|";
            flag = false;
        }
        if ((collection.length == 0) || (collection == null)) {
            mensajes = mensajes + "danger<>Error<>Select at least one 'Collection'.|";
            flag = false;
        }
        if ((pname == null) || (pname.isEmpty())) {
            mensajes = mensajes + "danger<>Error<>Please complete field 'Name'.|";
            flag = false;
        }
        if ((description == null) || (description.isEmpty())) {
            mensajes = mensajes + "danger<>Error<>Please complete field 'Description'.|";
            flag = false;
        }
        if ((color.length == 0) || (color == null)) {
            mensajes = mensajes + "danger<>Error<>Select at least one 'Color'.|";
            flag = false;
        }
        if (idMaterial == 0) {
            mensajes = mensajes + "danger<>Error<>Select one 'Material'.|";
            flag = false;
        }
        if (idSubMaterial == 0) {
            mensajes = mensajes + "danger<>Error<>Select one 'Sub Material'.|";
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

    public void chargeProducts() {
        products = MaintenanceSQL.getProducts(mdk);
    }

    public void insert() {
        if (validateFields()) {//Valido los campos del formulario
            Transaction tn = null;//Inicializo la transacción de la BD en null
            try {
                tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

                if (MaintenanceSQL.getProductBySKU(mdk, sku) == null) {

                    DtoProduct m = new DtoProduct();//Creo un objeto del tipo Manufacturer

                    //Seteo los datos del objeto excepto el id por que es Auto Incremental
                    DtoManufacturer ma = MaintenanceSQL.getManufacturer(mdk, idManufacturer);
                    DtoCollection co = MaintenanceSQL.getCollection(mdk, collection[0]);
                    DtoSize si = MaintenanceSQL.getSize(mdk, idSize);

                    slug = ((ma != null ? ma.getDescription() : " ") + (co != null ? co.getDescription() : " ") + (si != null ? si.getDescription() : " "));

                    m.setSku(sku);

                    m.setIdManufacturer(idManufacturer);
                    m.setPname(pname);
                    m.setDescription(description);
                    m.setIdStyle(idStyle);
                    m.setIdTexture(idTexture);
                    m.setIdPackageType(idPackageType);
                    m.setIdSize(idSize);
                    m.setHasCorner(hasCorner);
                    m.setCanSellLayer(canSellLayer);
                    m.setIdMaterial(idMaterial);
                    m.setIdSubMaterial(idSubMaterial);
                    m.setPalletWeight(Numeros.numero(palletWeight));
                    m.setUnitsPallet(unitsPallet);
                    m.setLayersPallet(layersPallet);
                    m.setUnitsLayer(unitsLayer);
                    m.setLinearFeetCorner(Numeros.numero(linearFeetCorner));
                    m.setSqftPerPackageType(Numeros.numero(sqftPerPackageType));
                    m.setQtyOfUnitsPerPackageType(qtyOfUnitsPerPackageType);
                    m.setSlug(slug);
                    m.setCreated(Fechas.ya());
                    m.setCreatedBy(usuario);
                    m.setModified(Fechas.ya());
                    m.setModifiedBy(usuario);
                    m.setActive(active);//Lo puse en true porque se me olvidó crear el check en el formulario, en la noche hacemos eso jajaja

                    MaintenanceSQL.saveProduct(mdk, m);

                    idEdit = MaintenanceSQL.lastIdProduct(mdk, usuario);

                    //Guardar Collections
                    //Guardar Color
                    //AdmConsultas.bitacora(o2c, usuario, "Encargado guardado Tipo: " + tipo + ", Codigo: " + codigo);
                    tn.commit();// Hago Commit a la transacción para guardar el registro

                    saveProductCollections();
                    saveProductColors();

                    mensajes = mensajes + "info<>Information<>Product saved successfully.";
                    mensaje = true;
                    accion = 5;
                } else {
                    mensajes = mensajes + "danger<>Error<>SKU already exists.";
                    mensaje = true;
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
    }

    public void saveProductCollections() {
        Transaction tn = null;//Inicializo la transacción de la BD en null
        try {
            tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

            for (int i = 0; i < collection.length; i++) {
                MaintenanceSQL.saveProductCollection(mdk, idEdit, collection[i]);
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

    public void saveProductColors() {
        Transaction tn = null;//Inicializo la transacción de la BD en null
        try {
            tn = mdk.beginTransaction();//Inicializo la transacción de la DB 

            for (int i = 0; i < color.length; i++) {
                MaintenanceSQL.saveProductColor(mdk, idEdit, color[i]);
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

    public void update() {
        if (validateFields()) {
            Transaction tn = null;
            try {
                tn = mdk.beginTransaction();
                DtoCollection m = MaintenanceSQL.getCollection(mdk, idEdit);
                if (m != null) {

                    m.setDescription(pname);
                    m.setModified(Fechas.ya());
                    m.setModifiedBy(usuario);
                    m.setActive(active);//Lo puse en true porque se me olvidó crear el check en el formulario, en la noche hacemos eso jajaja

                    MaintenanceSQL.updateCollection(mdk, m);
                    // AdmConsultas.bitacora(o2c, usuario, "Encargado modificado Tipo: " + tipo + ", Codigo: " + codigo);

                    tn.commit();
                    clearFields();
                    mensajes = mensajes + "info<>Information<>Collection modified successfully.";
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
        DtoProduct m = MaintenanceSQL.getProduct(mdk, idEdit);
        if (m != null) {
            idEdit = m.getId();
            pname = m.getDescription();
            idStyle = m.getIdSize();
            idTexture = m.getIdTexture();
            idPackageType = m.getIdPackageType();
            idMaterial = m.getIdMaterial();
            chargeSelect();
            idSubMaterial = m.getIdSubMaterial();
            slug = m.getSlug();
            description = m.getDescription();
            idManufacturer = m.getIdManufacturer();
            idSize = m.getIdSize();
            sku = m.getSku();
            palletWeight = String.valueOf(m.getPalletWeight());
            canSellLayer = m.isCanSellLayer();
            unitsPallet = m.getUnitsPallet();
            layersPallet = m.getLayersPallet();
            unitsLayer = m.getUnitsLayer();
            hasCorner = m.isHasCorner();
            linearFeetCorner = String.valueOf(m.getLinearFeetCorner());
            sqftPerPackageType = String.valueOf(m.getSqftPerPackageType());
            qtyOfUnitsPerPackageType = m.getQtyOfUnitsPerPackageType();
            active = m.getActive();

            //color = MaintenanceSQL.getColors(mdk, idEdit);
        } else {
            mensajes = mensajes + "danger<>Error<>Product does not exist.";
            mensaje = true;
        }
    }

}
