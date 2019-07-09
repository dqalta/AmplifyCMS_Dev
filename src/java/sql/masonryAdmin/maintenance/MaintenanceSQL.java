package sql.masonryAdmin.maintenance;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import sql.masonryAdmin.admin.DtoRol;
import util.Fechas;
import util.Generales;
import util.Numeros;
import web.sesion.ORMUtil;

/**
 *
 * @author CR104978
 */
public class MaintenanceSQL {
//maintenance for manufacturers

    public static int getPendingVendors(Session mdk) {
        int valor = 0;
        Iterator itr = mdk.createSQLQuery("SELECT count(idVendorRegister) as quantity"
                + " FROM vendorRegister")
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list().iterator();
        while (itr.hasNext()) {
            valor = Numeros.entero(String.valueOf(((Map) itr.next()).get("quantity")));
        }
        return valor;
    }

    public static ArrayList<DtoVendorsPending> getVendorsPending(Session mdk) {
        ArrayList<DtoVendorsPending> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " idVendorRegister,"
                + " companyName,"
                + " vname,"
                + " phoneNumber,"
                + " webSite,"
                + " city,"
                + " email"
                + " password"
                + " FROM vendorRegister")
                .setResultTransformer(Transformers.aliasToBean(DtoVendorsPending.class))
                .list().iterator();
        while (itr.hasNext()) {
            a.add((DtoVendorsPending) itr.next());

        }
        return a;
    }

    public static DtoVendorsPending getVendorPending(Session mdk, int id) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " idVendorRegister,"
                + " companyName,"
                + " vname,"
                + " phoneNumber,"
                + " webSite,"
                + " city,"
                + " email,"
                + " password"
                + " FROM vendorRegister"
                + " WHERE idVendorRegister = :idVendorRegister")
                .setParameter("idVendorRegister", id)
                .setResultTransformer(Transformers.aliasToBean(DtoVendorsPending.class))
                .list().iterator();
        DtoVendorsPending m = null;
        while (itr.hasNext()) {
            m = (DtoVendorsPending) itr.next();
        }
        return m;
    }

    public static void deleteVendorsPending(Session mdk, int idVendorRegister) {
        mdk.createNativeQuery("DELETE FROM vendorRegister"
                + " WHERE idVendorRegister = :idVendorRegister")
                .setParameter("idVendorRegister", idVendorRegister)
                .executeUpdate();
    }

    public static int getIdPostalCodesAV(Session mdk, String city) {
        int id = 0;
        Iterator itr = mdk.createSQLQuery("SELECT id"
                + " FROM postalCode"
                + " WHERE city = :city")
                .setParameter("city", city)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list().iterator();
        while (itr.hasNext()) {
            id = Numeros.entero(String.valueOf(((Map) itr.next()).get("id")));
        }

        return id;
    }

    public static InputStream getProductPhoto(Session mdk, int id) {
        Connection c = ORMUtil.getConnection(mdk);
        InputStream is = null;
        try {
            PreparedStatement pst = c.prepareStatement("SELECT photo FROM galleryPhoto"
                    + " WHERE id = ?");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                is = rs.getBinaryStream("photo");
            }
            rs.close();
            pst.close();
            if (is == null) {
                is = MaintenanceSQL.class.getResourceAsStream("/img/no_image_available.png");
            }
        } catch (SQLException x) {
            is = MaintenanceSQL.class.getResourceAsStream("/img/no_image_available.png");
        }
        return is;
    }

    public static void saveProductCollection(Session cga, int idProduct, int idCollection) {
        cga.createNativeQuery("INSERT INTO collectionProduct"
                + " (idProduct, idCollection)"
                + " VALUES"
                + " (:idProduct, :idCollection)")
                .setParameter("idProduct", idProduct)
                .setParameter("idCollection", idCollection)
                .executeUpdate();
    }

    public static void saveProductColor(Session cga, int idProduct, int idColor) {
        cga.createNativeQuery("INSERT INTO productColors"
                + " (idProduct, idColor)"
                + " VALUES"
                + " (:idProduct, :idColor)")
                .setParameter("idProduct", idProduct)
                .setParameter("idColor", idColor)
                .executeUpdate();
    }

    public static int lastIdProduct(Session mdk, String user) {
        int valor = 0;
        Iterator itr = mdk.createSQLQuery("SELECT MAX(id) as valor"
                + " FROM product"
                + " WHERE createdBy = :user")
                .setParameter("user", user)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list().iterator();
        while (itr.hasNext()) {
            valor = Numeros.entero(String.valueOf(((Map) itr.next()).get("valor")));
        }
        return valor;
    }

    public static void saveProduct(Session mdk, DtoProduct m) {
        mdk.createNativeQuery("INSERT INTO product"
                + " (pname, idStyle, idTexture, idPackageType, idMaterial, idSubMaterial, slug, description, idManufacturer, idSize, sku, palletWeight, canSellLayer, unitsPallet, layersPallet, unitsLayer, hasCorner, linearFeetCorner, sqftPerPackageType, qtyOfUnitsPerPackageType, created, createdBy, modified, modifiedBy, active)"
                + " VALUES"
                + " (:pname, :idStyle, :idTexture, :idPackageType, :idMaterial, :idSubMaterial, :slug, :description, :idManufacturer, :idSize, :sku, :palletWeight, :canSellLayer, :unitsPallet, :layersPallet, :unitsLayer, :hasCorner, :linearFeetCorner, :sqftPerPackageType, :qtyOfUnitsPerPackageType, :created, :createdBy, :modified, :modifiedBy, :active)")
                .setParameter("pname", m.getPname())
                .setParameter("idStyle", m.getIdStyle())
                .setParameter("idTexture", m.getIdTexture())
                .setParameter("idPackageType", m.getIdPackageType())
                .setParameter("idMaterial", m.getIdMaterial())
                .setParameter("idSubMaterial", m.getIdSubMaterial())
                .setParameter("slug", m.getSlug())
                .setParameter("description", m.getDescription())
                .setParameter("idManufacturer", m.getIdManufacturer())
                .setParameter("idSize", m.getIdSize())
                .setParameter("sku", m.getSku())
                .setParameter("palletWeight", m.getPalletWeight())
                .setParameter("canSellLayer", m.isCanSellLayer())
                .setParameter("unitsPallet", m.getUnitsPallet())
                .setParameter("layersPallet", m.getLayersPallet())
                .setParameter("unitsLayer", m.getUnitsLayer())
                .setParameter("hasCorner", m.isHasCorner())
                .setParameter("linearFeetCorner", m.getLinearFeetCorner())
                .setParameter("sqftPerPackageType", m.getSqftPerPackageType())
                .setParameter("qtyOfUnitsPerPackageType", m.getQtyOfUnitsPerPackageType())
                .setParameter("created", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static void saveGalleryPhoto(Session mdk, DtoPhoto ep, File photo) {
        mdk.createNativeQuery("INSERT INTO galleryPhoto"
                + " (idGallery,photo,photoFileName,photoContentType)"
                + " VALUES"
                + " (:idGallery,:photo,:photoFileName,:photoContentType)")
                .setParameter("idGallery", ep.getIdGallery())
                .setParameter("photo", Generales.archivoABytes(photo))
                .setParameter("photoFileName", ep.getPhotoFileName())
                .setParameter("photoContentType", ep.getPhotoContentType())
                .executeUpdate();
    }

    public static DtoProduct getProductBySKU(Session mdk, String sku) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " pname,"
                + " idStyle,"
                + " idTexture,"
                + " idPackageType,"
                + " idMaterial,"
                + " idSubMaterial,"
                + " slug,"
                + " description,"
                + " idManufacturer,"
                + " idSize,"
                + " sku,"
                + " palletWeight,"
                + " canSellLayer,"
                + " unitsPallet,"
                + " layersPallet,"
                + " unitsLayer,"
                + " hasCorner,"
                + " linearFeetCorner,"
                + " sqftPerPackageType,"
                + " qtyOfUnitsPerPackageType,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM product"
                + " WHERE sku = :sku")
                .setParameter("sku", sku)
                .setResultTransformer(Transformers.aliasToBean(DtoProduct.class))
                .list().iterator();
        DtoProduct m = null;
        while (itr.hasNext()) {
            m = (DtoProduct) itr.next();
        }
        return m;
    }

    public static DtoGallery getGallery(Session mdk, int id) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " description,"
                + " created,"
                + " createdBy"
                + " FROM gallery"
                + " WHERE id = :id")
                .setParameter("id", id)
                .setResultTransformer(Transformers.aliasToBean(DtoGallery.class))
                .list().iterator();
        DtoGallery m = null;
        while (itr.hasNext()) {
            m = (DtoGallery) itr.next();
        }
        return m;
    }

    public static int lastIdGallery(Session mdk, String user) {
        int valor = 0;
        Iterator itr = mdk.createSQLQuery("SELECT MAX(id) as valor"
                + " FROM gallery"
                + " WHERE createdBy = :user")
                .setParameter("user", user)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list().iterator();
        while (itr.hasNext()) {
            valor = Numeros.entero(String.valueOf(((Map) itr.next()).get("valor")));
        }
        return valor;
    }

    public static void saveGallery(Session cga, DtoGallery m) {
        cga.createNativeQuery("INSERT INTO gallery"
                + " (description,created,createdBy, modified, modifiedBy)"
                + " VALUES"
                + " (:description,:created,:createdBy, :modified, :modifiedBy)")
                .setParameter("description", m.getDescription())
                .setParameter("created", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .executeUpdate();
    }

    public static void saveGalleryManufacturer(Session cga, int idGallery, int idManufacturer) {
        cga.createNativeQuery("INSERT INTO galleryManufacturer"
                + " (idGallery, idManufacturer)"
                + " VALUES"
                + " (:idGallery, :idManufacturer)")
                .setParameter("idGallery", idGallery)
                .setParameter("idManufacturer", idManufacturer)
                .executeUpdate();
    }

    public static void saveGalleryCollection(Session cga, int idGallery, int idCollection) {
        cga.createNativeQuery("INSERT INTO galleryCollection"
                + " (idGallery, idCollection)"
                + " VALUES"
                + " (:idGallery, :idCollection)")
                .setParameter("idGallery", idGallery)
                .setParameter("idCollection", idCollection)
                .executeUpdate();
    }

    public static void saveGallerySize(Session cga, int idGallery, int idSize) {
        cga.createNativeQuery("INSERT INTO gallerySize"
                + " (idGallery, idSize)"
                + " VALUES"
                + " (:idGallery, :idSize)")
                .setParameter("idGallery", idGallery)
                .setParameter("idSize", idSize)
                .executeUpdate();
    }

    public static void saveRolDetail(Session cga, int idMasonryRol, String module, String description, boolean permission, String user, Date ya) {
        if (permission) {
            cga.createNativeQuery("INSERT INTO masonryRolDetail"
                    + " (idMasonryRol, module, description, permission, created, createdBy, modified, modifiedBy)"
                    + " VALUES"
                    + " (:idMasonryRol, :module, :description, :permission, :created, :createdBy, :modified, :modifiedBy)")
                    .setParameter("idMasonryRol", idMasonryRol)
                    .setParameter("module", module)
                    .setParameter("description", description)
                    .setParameter("permission", permission)
                    .setParameter("created", ya)
                    .setParameter("createdBy", user)
                    .setParameter("modified", ya)
                    .setParameter("modifiedBy", user)
                    .executeUpdate();
        }
    }

    public static void deleteRolDetails(Session mdk, int idMasonryRol) {
        mdk.createNativeQuery("DELETE FROM masonryRolDetail"
                + " WHERE idMasonryRol = :idMasonryRol")
                .setParameter("idMasonryRol", idMasonryRol)
                .executeUpdate();
    }

    public static ArrayList<DtoManufacturer> getManufacturers(Session mdk) {
        ArrayList<DtoManufacturer> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM manufacturer")
                .setResultTransformer(Transformers.aliasToBean(DtoManufacturer.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoManufacturer) itr.next());
        }
        return a;
    }

    public static ArrayList<DtoGalleryQuery> getGalleries(Session mdk) {
        ArrayList<DtoGalleryQuery> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT DISTINCT"
                + " G.id,"
                + " G.description,"
                + " '' AS photo,"
                + " G.id AS quantity,"
                + " G.modified"
                + " FROM gallery AS G"
                + " INNER JOIN galleryPhoto P"
                + " ON P.idGallery = G.id")
                .setResultTransformer(Transformers.aliasToBean(DtoGalleryQuery.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoGalleryQuery) itr.next());
        }
        return a;
    }

    public static ArrayList<DtoPhoto> getGalleryPhotos(Session mdk, int idGallery) {
        ArrayList<DtoPhoto> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " idGallery,"
                + " photoFileName,"
                + " photoContentType"
                + " FROM galleryPhoto"
                + " WHERE idGallery = :idGallery")
                .setParameter("idGallery", idGallery)
                .setResultTransformer(Transformers.aliasToBean(DtoPhoto.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoPhoto) itr.next());
        }
        return a;
    }

    public static ArrayList<DtoPhotoQuery> getGalleryPhotosQuery(Session mdk, int idGallery) {
        ArrayList<DtoPhotoQuery> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " idGallery,"
                + " photoFileName,"
                + " '' AS photo"
                + " FROM galleryPhoto"
                + " WHERE idGallery = :idGallery")
                .setParameter("idGallery", idGallery)
                .setResultTransformer(Transformers.aliasToBean(DtoPhotoQuery.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoPhotoQuery) itr.next());
        }
        return a;
    }

    public static ArrayList<DtoCollectionQuery> getCollectionsQuery(Session mdk) {
        ArrayList<DtoCollectionQuery> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " c.id,"
                + " m.description AS manufacturer,"
                + " c.description,"
                + " c.created,"
                + " c.createdBy,"
                + " c.modified,"
                + " c.modifiedBy,"
                + " c.active"
                + " FROM collection c"
                + " INNER JOIN manufacturer m"
                + " ON m.id = c.idManufacturer")
                .setResultTransformer(Transformers.aliasToBean(DtoCollectionQuery.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoCollectionQuery) itr.next());
        }
        return a;
    }

    public static ArrayList<DtoCollection> getCollections(Session mdk) {
        ArrayList<DtoCollection> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " idManufacturer,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM collection")
                .setResultTransformer(Transformers.aliasToBean(DtoCollection.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoCollection) itr.next());
        }
        return a;
    }

    public static ArrayList<DtoCollection> getCollectionsByManufacturers(Session mdk, int[] arr) {
        List<Integer> intList = new ArrayList<>();
        for (int i : arr) {
            intList.add(i);
        }

        ArrayList<DtoCollection> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " idManufacturer,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM collection"
                + " WHERE idManufacturer IN( :manufacturers )")
                .setParameterList("manufacturers", intList)
                .setResultTransformer(Transformers.aliasToBean(DtoCollection.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoCollection) itr.next());
        }
        return a;
    }

    public static ArrayList<DtoPostalCode> getPostalCodes(Session mdk) {
        ArrayList<DtoPostalCode> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " postalCode,"
                + " city,"
                + " province,"
                + " active"
                + " FROM postalCode")
                .setResultTransformer(Transformers.aliasToBean(DtoPostalCode.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoPostalCode) itr.next());
        }
        return a;
    }

    public static ArrayList<DtoString> getProvincePostalCodes(Session mdk) {
        ArrayList<DtoString> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT distinct province as description"
                + " FROM postalCode")
                .setResultTransformer(Transformers.aliasToBean(DtoString.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoString) itr.next());
        }
        return a;
    }

    public static ArrayList<DtoRol> getRols(Session mdk) {
        ArrayList<DtoRol> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy"
                + " FROM masonryRol")
                .setResultTransformer(Transformers.aliasToBean(DtoRol.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoRol) itr.next());
        }
        return a;
    }

    public static DtoManufacturer getManufacturer(Session mdk, int id) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM manufacturer"
                + " WHERE id = :id")
                .setParameter("id", id)
                .setResultTransformer(Transformers.aliasToBean(DtoManufacturer.class))
                .list().iterator();
        DtoManufacturer m = null;
        while (itr.hasNext()) {
            m = (DtoManufacturer) itr.next();
        }
        return m;
    }

    public static ArrayList<DtoProduct> getProducts(Session mdk) {
        ArrayList<DtoProduct> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " pname,"
                + " idStyle,"
                + " idTexture,"
                + " idPackageType,"
                + " idMaterial,"
                + " idSubMaterial,"
                + " slug,"
                + " description,"
                + " idManufacturer,"
                + " idSize,"
                + " sku,"
                + " palletWeight,"
                + " canSellLayer,"
                + " unitsPallet,"
                + " layersPallet,"
                + " unitsLayer,"
                + " hasCorner,"
                + " linearFeetCorner,"
                + " sqftPerPackageType,"
                + " qtyOfUnitsPerPackageType,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM product")
                .setResultTransformer(Transformers.aliasToBean(DtoProduct.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoProduct) itr.next());
        }
        return a;
    }

    public static DtoProduct getProduct(Session mdk, int idProduct) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " pname,"
                + " idStyle,"
                + " idTexture,"
                + " idPackageType,"
                + " idMaterial,"
                + " idSubMaterial,"
                + " slug,"
                + " description,"
                + " idManufacturer,"
                + " idSize,"
                + " sku,"
                + " palletWeight,"
                + " canSellLayer,"
                + " unitsPallet,"
                + " layersPallet,"
                + " unitsLayer,"
                + " hasCorner,"
                + " linearFeetCorner,"
                + " sqftPerPackageType,"
                + " qtyOfUnitsPerPackageType,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM product"
                + " WHERE id = :idProduct").
                setParameter("idProduct", idProduct)
                .setResultTransformer(Transformers.aliasToBean(DtoProduct.class))
                .list().iterator();

        DtoProduct m = null;
        while (itr.hasNext()) {
            m = (DtoProduct) itr.next();
        }
        return m;
    }

    public static DtoCollection getCollection(Session mdk, int id) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " idManufacturer,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM collection"
                + " WHERE id = :id")
                .setParameter("id", id)
                .setResultTransformer(Transformers.aliasToBean(DtoCollection.class))
                .list().iterator();
        DtoCollection m = null;
        while (itr.hasNext()) {
            m = (DtoCollection) itr.next();
        }
        return m;
    }

    public static DtoRol getRol(Session mdk, int id) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy"
                + " FROM masonryRol"
                + " WHERE id = :id")
                .setParameter("id", id)
                .setResultTransformer(Transformers.aliasToBean(DtoRol.class))
                .list().iterator();
        DtoRol m = null;
        while (itr.hasNext()) {
            m = (DtoRol) itr.next();
        }
        return m;
    }

    public static void saveManufacturer(Session mdk, DtoManufacturer m) {
        mdk.createNativeQuery("INSERT INTO manufacturer"
                + " (description, created, createdBy, modified, modifiedBy, active)"
                + " VALUES"
                + " (:description, :created, :createdBy, :modified, :modifiedBy, :active)")
                .setParameter("description", m.getDescription())
                .setParameter("created", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static void saveCollection(Session mdk, DtoCollection m) {
        mdk.createNativeQuery("INSERT INTO collection"
                + " (idManufacturer, description, created, createdBy, modified, modifiedBy, active)"
                + " VALUES"
                + " (:idManufacturer, :description, :created, :createdBy, :modified, :modifiedBy, :active)")
                .setParameter("description", m.getDescription())
                .setParameter("idManufacturer", m.getIdManufacturer())
                .setParameter("created", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static void saveRol(Session mdk, DtoRol m) {
        mdk.createNativeQuery("INSERT INTO masonryRol"
                + " (description, created, createdBy, modified, modifiedBy)"
                + " VALUES"
                + " (:description, :created, :createdBy, :modified, :modifiedBy)")
                .setParameter("description", m.getDescription())
                .setParameter("created", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .executeUpdate();
    }

    public static int getLastIdRol(Session mdk, String user) {
        int result = 0;
        Iterator itr = mdk.createSQLQuery("SELECT MAX(id) as result"
                + " FROM masonryRol"
                + " WHERE createdBy = :user")
                .setParameter("user", user)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list().iterator();
        while (itr.hasNext()) {
            result = Numeros.entero(String.valueOf(((Map) itr.next()).get("result")));
        }
        return result;
    }

    public static void updateManufacturer(Session mdk, DtoManufacturer m) {
        mdk.createNativeQuery("UPDATE manufacturer SET"
                + " description = :description,"
                + " modified = :modified,"
                + " modifiedBy = :modifiedBy,"
                + " active = :active"
                + " WHERE id = :id")
                .setParameter("id", m.getId())
                .setParameter("description", m.getDescription())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static void updateCollection(Session mdk, DtoCollection m) {
        mdk.createNativeQuery("UPDATE collection SET"
                + " description = :description,"
                + " idManufacturer = :idManufacturer,"
                + " modified = :modified,"
                + " modifiedBy = :modifiedBy,"
                + " active = :active"
                + " WHERE id = :id")
                .setParameter("id", m.getId())
                .setParameter("idManufacturer", m.getIdManufacturer())
                .setParameter("description", m.getDescription())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static boolean getRolDetail(Session mdk, int idMasonryRol, String module, String description) {
        boolean permiso = false;
        Iterator itr = mdk.createSQLQuery("SELECT permission"
                + " FROM masonryRolDetail"
                + " WHERE idMasonryRol = :idMasonryRol"
                + " AND module = :module"
                + " AND description = :description")
                .setParameter("idMasonryRol", idMasonryRol)
                .setParameter("module", module)
                .setParameter("description", description).list().iterator();
        while (itr.hasNext()) {
            permiso = (Boolean) itr.next();
        }
        return permiso;
    }

    public static void updateRol(Session mdk, DtoRol m) {
        mdk.createNativeQuery("UPDATE masonryRol SET"
                + " description = :description,"
                + " modified = :modified,"
                + " modifiedBy = :modifiedBy"
                + " WHERE id = :id")
                .setParameter("id", m.getId())
                .setParameter("description", m.getDescription())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .executeUpdate();
    }
//maintenance for packagetypes

    public static ArrayList<DtoPackageType> getPackageTypes(Session mdk) {
        ArrayList<DtoPackageType> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM productPackage")
                .setResultTransformer(Transformers.aliasToBean(DtoPackageType.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoPackageType) itr.next());
        }
        return a;
    }

    public static DtoPackageType getPackageType(Session mdk, int id) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM productPackage"
                + " WHERE id = :id")
                .setParameter("id", id)
                .setResultTransformer(Transformers.aliasToBean(DtoPackageType.class))
                .list().iterator();
        DtoPackageType m = null;
        while (itr.hasNext()) {
            m = (DtoPackageType) itr.next();
        }
        return m;
    }

    public static void savePackageType(Session mdk, DtoPackageType m) {
        mdk.createNativeQuery("INSERT INTO productPackage"
                + " (description, created, createdBy, modified, modifiedBy, active)"
                + " VALUES"
                + " (:description, :created, :createdBy, :modified, :modifiedBy, :active)")
                .setParameter("description", m.getDescription())
                .setParameter("created", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static void updatePackageType(Session mdk, DtoPackageType m) {
        mdk.createNativeQuery("UPDATE productPackage SET"
                + " description = :description,"
                + " modified = :modified,"
                + " modifiedBy = :modifiedBy,"
                + " active = :active"
                + " WHERE id = :id")
                .setParameter("id", m.getId())
                .setParameter("description", m.getDescription())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }
////Maintenance for styles

    public static ArrayList<DtoStyle> getStyles(Session mdk) {
        ArrayList<DtoStyle> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM productStyle")
                .setResultTransformer(Transformers.aliasToBean(DtoStyle.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoStyle) itr.next());
        }
        return a;
    }

    public static DtoStyle getStyles(Session mdk, int id) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM productStyle"
                + " WHERE id = :id")
                .setParameter("id", id)
                .setResultTransformer(Transformers.aliasToBean(DtoStyle.class))
                .list().iterator();
        DtoStyle m = null;
        while (itr.hasNext()) {
            m = (DtoStyle) itr.next();
        }
        return m;
    }

    public static void saveStyles(Session mdk, DtoStyle m) {
        mdk.createNativeQuery("INSERT INTO productStyle"
                + " (description, created, createdBy, modified, modifiedBy, active)"
                + " VALUES"
                + " (:description, :created, :createdBy, :modified, :modifiedBy, :active)")
                .setParameter("description", m.getDescription())
                .setParameter("created", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static void updateStyles(Session mdk, DtoStyle m) {
        mdk.createNativeQuery("UPDATE productStyle SET"
                + " description = :description,"
                + " modified = :modified,"
                + " modifiedBy = :modifiedBy,"
                + " active = :active"
                + " WHERE id = :id")
                .setParameter("id", m.getId())
                .setParameter("description", m.getDescription())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }
////Maintenance for texture

    public static ArrayList<DtoTexture> getTextures(Session mdk) {
        ArrayList<DtoTexture> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM productTexture")
                .setResultTransformer(Transformers.aliasToBean(DtoTexture.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoTexture) itr.next());
        }
        return a;
    }

    public static DtoTexture getTextures(Session mdk, int id) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM productTexture"
                + " WHERE id = :id")
                .setParameter("id", id)
                .setResultTransformer(Transformers.aliasToBean(DtoTexture.class))
                .list().iterator();
        DtoTexture m = null;
        while (itr.hasNext()) {
            m = (DtoTexture) itr.next();
        }
        return m;
    }

    public static void saveTextures(Session mdk, DtoTexture m) {
        mdk.createNativeQuery("INSERT INTO productTexture"
                + " (description, created, createdBy, modified, modifiedBy, active)"
                + " VALUES"
                + " (:description, :created, :createdBy, :modified, :modifiedBy, :active)")
                .setParameter("description", m.getDescription())
                .setParameter("created", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static void updateTextures(Session mdk, DtoTexture m) {
        mdk.createNativeQuery("UPDATE productTexture SET"
                + " description = :description,"
                + " modified = :modified,"
                + " modifiedBy = :modifiedBy,"
                + " active = :active"
                + " WHERE id = :id")
                .setParameter("id", m.getId())
                .setParameter("description", m.getDescription())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }
////Maintenance for colors

    public static ArrayList<DtoColor> getColors(Session mdk) {
        ArrayList<DtoColor> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM productColor")
                .setResultTransformer(Transformers.aliasToBean(DtoColor.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoColor) itr.next());
        }
        return a;
    }

    public static DtoColor getColors(Session mdk, int id) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM productColor"
                + " WHERE id = :id")
                .setParameter("id", id)
                .setResultTransformer(Transformers.aliasToBean(DtoColor.class))
                .list().iterator();
        DtoColor m = null;
        while (itr.hasNext()) {
            m = (DtoColor) itr.next();
        }
        return m;
    }

    public static String getProductColors(Session mdk, int idProduct) {
        String arrayColor = "";
        Iterator itr = mdk.createSQLQuery("SELECT idColor"
                + " FROM productColors"
                + " WHERE idProduct = :idProduct")
                .setParameter("idProduct", idProduct)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list().iterator();

        while (itr.hasNext()) {
            arrayColor = arrayColor + String.valueOf(((Map) itr.next()).get("idColor"));
        }

        return arrayColor;
    }

    public static int[] getGalleryManufacturer(Session o2c, int idGallery) {
        String result = "";
        Iterator itr = o2c.createSQLQuery("SELECT idManufacturer"
                + " FROM galleryManufacturer"
                + " WHERE idGallery = :idGallery")
                .setParameter("idGallery", idGallery)
                .list().iterator();

        while (itr.hasNext()) {
            result = result + (int) itr.next() + ",";
        }

        String[] arrString = result.split(",");
        int[] arrInt = new int[arrString.length];
        for (int i = 0; i < arrInt.length; i++) {
            System.out.println("Array: " + arrString[i]);
            arrInt[i] = Integer.parseInt(arrString[i]);
        }
        return arrInt;
    }

    public static int[] getGalleryCollection(Session o2c, int idGallery) {
        String result = "";
        Iterator itr = o2c.createSQLQuery("SELECT idCollection"
                + " FROM galleryCollection"
                + " WHERE idGallery = :idGallery")
                .setParameter("idGallery", idGallery)
                .list().iterator();

        while (itr.hasNext()) {
            result = result + (int) itr.next() + ",";
        }
        String[] arrString = result.split(",");
        int[] arrInt = new int[arrString.length];
        for (int i = 0; i < arrInt.length; i++) {
            arrInt[i] = Integer.parseInt(arrString[i]);
        }
        return arrInt;
    }

    public static void saveColors(Session mdk, DtoColor m) {
        mdk.createNativeQuery("INSERT INTO productColor"
                + " (description, created, createdBy, modified, modifiedBy, active)"
                + " VALUES"
                + " (:description, :created, :createdBy, :modified, :modifiedBy, :active)")
                .setParameter("description", m.getDescription())
                .setParameter("created", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static void updateColors(Session mdk, DtoColor m) {
        mdk.createNativeQuery("UPDATE productColor SET"
                + " description = :description,"
                + " modified = :modified,"
                + " modifiedBy = :modifiedBy,"
                + " active = :active"
                + " WHERE id = :id")
                .setParameter("id", m.getId())
                .setParameter("description", m.getDescription())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }
    ////Maintenance for materials

    public static ArrayList<DtoMaterial> getMaterials(Session mdk) {
        ArrayList<DtoMaterial> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM productMaterial")
                .setResultTransformer(Transformers.aliasToBean(DtoMaterial.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoMaterial) itr.next());
        }
        return a;
    }

    public static DtoMaterial getMaterial(Session mdk, int id) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM productMaterial"
                + " WHERE id = :id")
                .setParameter("id", id)
                .setResultTransformer(Transformers.aliasToBean(DtoMaterial.class))
                .list().iterator();
        DtoMaterial m = null;
        while (itr.hasNext()) {
            m = (DtoMaterial) itr.next();
        }
        return m;
    }

    public static void saveMaterial(Session mdk, DtoMaterial m) {
        mdk.createNativeQuery("INSERT INTO productMaterial"
                + " (description, created, createdBy, modified, modifiedBy, active)"
                + " VALUES"
                + " (:description, :created, :createdBy, :modified, :modifiedBy, :active)")
                .setParameter("description", m.getDescription())
                .setParameter("created", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static void updateMaterial(Session mdk, DtoMaterial m) {
        mdk.createNativeQuery("UPDATE productMaterial SET"
                + " description = :description,"
                + " modified = :modified,"
                + " modifiedBy = :modifiedBy,"
                + " active = :active"
                + " WHERE id = :id")
                .setParameter("id", m.getId())
                .setParameter("description", m.getDescription())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }
    ///////////////////Sub-materials maintenance/////////////////////  

    public static ArrayList<DtoSubMaterial> getSubMaterials(Session mdk) {
        ArrayList<DtoSubMaterial> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " idProductMaterial,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM productSubMaterial")
                .setResultTransformer(Transformers.aliasToBean(DtoSubMaterial.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoSubMaterial) itr.next());
        }
        return a;
    }

    public static DtoSubMaterial getSubMaterial(Session mdk, int id) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " idProductMaterial,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM productSubMaterial"
                + " WHERE id = :id")
                .setParameter("id", id)
                .setResultTransformer(Transformers.aliasToBean(DtoSubMaterial.class))
                .list().iterator();
        DtoSubMaterial m = null;
        while (itr.hasNext()) {
            m = (DtoSubMaterial) itr.next();
        }
        return m;
    }

    public static void saveSubMaterial(Session mdk, DtoSubMaterial m) {
        mdk.createNativeQuery("INSERT INTO productSubMaterial"
                + " (idProductMaterial, description, created, createdBy, modified, modifiedBy, active)"
                + " VALUES"
                + " (:idProductMaterial, :description, :created, :createdBy, :modified, :modifiedBy, :active)")
                .setParameter("idProductMaterial", m.getIdProductMaterial())
                .setParameter("description", m.getDescription())
                .setParameter("created", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static void updateSubMaterial(Session mdk, DtoSubMaterial m) {
        mdk.createNativeQuery("UPDATE productSubMaterial SET"
                + " idProductMaterial = :idProductMaterial,"
                + " description = :description,"
                + " modified = :modified,"
                + " modifiedBy = :modifiedBy,"
                + " active = :active"
                + " WHERE id = :id")
                .setParameter("id", m.getId())
                .setParameter("idProductMaterial", m.getIdProductMaterial())
                .setParameter("description", m.getDescription())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }
///////////////////////////metrics Maintenanace------------------

    public static ArrayList<DtoMetricsSystem> getMetricsSystems(Session mdk) {
        ArrayList<DtoMetricsSystem> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM metricsSystem")
                .setResultTransformer(Transformers.aliasToBean(DtoMetricsSystem.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoMetricsSystem) itr.next());
        }
        return a;
    }

    public static DtoMetricsSystem getMetricsSystem(Session mdk, int id) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM metricsSystem"
                + " WHERE id = :id")
                .setParameter("id", id)
                .setResultTransformer(Transformers.aliasToBean(DtoMetricsSystem.class))
                .list().iterator();
        DtoMetricsSystem m = null;
        while (itr.hasNext()) {
            m = (DtoMetricsSystem) itr.next();
        }
        return m;
    }

    public static void saveMetricsSystem(Session mdk, DtoMetricsSystem m) {
        mdk.createNativeQuery("INSERT INTO metricsSystem"
                + " (description, created, createdBy, modified, modifiedBy, active)"
                + " VALUES"
                + " (:description, :created, :createdBy, :modified, :modifiedBy, :active)")
                .setParameter("description", m.getDescription())
                .setParameter("created", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static void updateMetricsSystem(Session mdk, DtoMetricsSystem m) {
        mdk.createNativeQuery("UPDATE metricsSystem SET"
                + " description = :description,"
                + " modified = :modified,"
                + " modifiedBy = :modifiedBy,"
                + " active = :active"
                + " WHERE id = :id")
                .setParameter("id", m.getId())
                .setParameter("description", m.getDescription())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    ///////////////////Sizes Maintenance/////////////////////  
    public static ArrayList<DtoSize> getSizes(Session mdk) {
        ArrayList<DtoSize> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " idMetricsSystem,"
                + " description,"
                + " length,"
                + " depth,"
                + " width,"
                + " unitsPerSq2,"
                + " unitsPerSf2,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM productSize")
                .setResultTransformer(Transformers.aliasToBean(DtoSize.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoSize) itr.next());
        }
        return a;
    }

    public static DtoSize getSize(Session mdk, int id) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " idMetricsSystem,"
                + " description,"
                + " length,"
                + " depth,"
                + " width,"
                + " unitsPerSq2,"
                + " unitsPerSf2,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM productSize"
                + " WHERE id = :id")
                .setParameter("id", id)
                .setResultTransformer(Transformers.aliasToBean(DtoSize.class))
                .list().iterator();
        DtoSize m = null;
        while (itr.hasNext()) {
            m = (DtoSize) itr.next();
        }
        return m;
    }

    public static void saveSize(Session mdk, DtoSize m) {
        mdk.createNativeQuery("INSERT INTO productSize"
                + " (idMetricsSystem, description, length, depth, width, unitsPerSq2, unitsPerSf2, created, createdBy, modified, modifiedBy, active)"
                + " VALUES"
                + " (:idMetricsSystem, :description, :length, :depth, :width, :unitsPerSq2, :unitsPerSf2, :created, :createdBy, :modified, :modifiedBy, :active)")
                .setParameter("idMetricsSystem", m.getIdMetricsSystem())
                .setParameter("description", m.getDescription())
                .setParameter("length", m.getLength())
                .setParameter("depth", m.getDepth())
                .setParameter("width", m.getWidth())
                .setParameter("unitsPerSq2", m.getUnitsPerSq2())
                .setParameter("unitsPerSf2", m.getUnitsPerSf2())
                .setParameter("created", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static void updateSize(Session mdk, DtoSize m) {
        mdk.createNativeQuery("UPDATE productSize SET"
                + " idMetricsSystem = :idMetricsSystem,"
                + " description = :description,"
                + " length = :length,"
                + " depth = :depth,"
                + " width = :width,"
                + " unitsPerSq2 = :unitsPerSq2,"
                + " unitsPerSf2 = :unitsPerSf2,"
                + " modified = :modified,"
                + " modifiedBy = :modifiedBy,"
                + " active = :active"
                + " WHERE id = :id")
                .setParameter("id", m.getId())
                .setParameter("idMetricsSystem", m.getIdMetricsSystem())
                .setParameter("description", m.getDescription())
                .setParameter("length", m.getLength())
                .setParameter("depth", m.getDepth())
                .setParameter("width", m.getWidth())
                .setParameter("unitsPerSq2", m.getUnitsPerSq2())
                .setParameter("unitsPerSf2", m.getUnitsPerSf2())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    /////////////////////////////////// Vendor maintenance //////
    public static ArrayList<DtoVendor> getVendors(Session mdk) {
        ArrayList<DtoVendor> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " vname,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM vendor")
                .setResultTransformer(Transformers.aliasToBean(DtoVendor.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoVendor) itr.next());
        }
        return a;
    }

    public static DtoVendor getVendor(Session mdk, String id) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " vname,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM vendor"
                + " WHERE id = :id")
                .setParameter("id", id)
                .setResultTransformer(Transformers.aliasToBean(DtoVendor.class))
                .list().iterator();
        DtoVendor m = null;
        while (itr.hasNext()) {
            m = (DtoVendor) itr.next();
        }
        return m;
    }

    public static void saveVendor(Session mdk, DtoVendor m) {
        mdk.createNativeQuery("INSERT INTO vendor"
                + " (id, vname, created, createdBy, modified, modifiedBy, active)"
                + " VALUES"
                + " (:id, :vname, :created, :createdBy, :modified, :modifiedBy, :active)")
                .setParameter("id", m.getId())
                .setParameter("vname", m.getVname())
                .setParameter("created", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static void updateVendor(Session mdk, DtoVendor m) {
        mdk.createNativeQuery("UPDATE vendor SET"
                + " vname = :vname,"
                + " modified = :modified,"
                + " modifiedBy = :modifiedBy,"
                + " active = :active"
                + " WHERE id = :id")
                .setParameter("id", m.getId())
                .setParameter("vname", m.getVname())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }
    ///vendor contact maintenance//////

    public static ArrayList<DtoVendorContact> getVendorsContacts(Session mdk, String idVendor) {
        ArrayList<DtoVendorContact> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " idVendor,"
                + " description,"
                + " type,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM vendorContact"
                + " WHERE idVendor=:idVendor")
                .setParameter("idVendor", idVendor)
                .setResultTransformer(Transformers.aliasToBean(DtoVendorContact.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoVendorContact) itr.next());
        }
        return a;
    }

    public static DtoVendorContact getVendorsContact(Session mdk, int id) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " idVendor,"
                + " description,"
                + " type,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM vendorContact"
                + " WHERE id = :id")
                .setParameter("id", id)
                .setResultTransformer(Transformers.aliasToBean(DtoVendorContact.class))
                .list().iterator();
        DtoVendorContact m = null;
        while (itr.hasNext()) {
            m = (DtoVendorContact) itr.next();
        }
        return m;
    }

    public static void saveVendorContact(Session mdk, DtoVendorContact m) {
        mdk.createNativeQuery("INSERT INTO vendorContact"
                + " (idVendor, description, type, created, createdBy, modified, modifiedBy, active)"
                + " VALUES"
                + " (:idVendor, :description, :type, :created, :createdBy, :modified, :modifiedBy, :active)")
                .setParameter("idVendor", m.getIdVendor())
                .setParameter("description", m.getDescription())
                .setParameter("type", m.getType())
                .setParameter("created", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static void updateVendorContact(Session mdk, DtoVendorContact m) {
        mdk.createNativeQuery("UPDATE vendorContact SET"
                + " modified = :modified,"
                + " modifiedBy = :modifiedBy,"
                + " active = :active"
                + " WHERE id = :id")
                .setParameter("id", m.getId())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static void deleteVendorContact(Session mdk, int idContact) {
        mdk.createNativeQuery("DELETE FROM vendorContact"
                + " WHERE id = :id")
                .setParameter("id", idContact)
                .executeUpdate();
    }

    public static void activeVendorContact(Session mdk, DtoVendorContact m) {
        mdk.createNativeQuery("UPDATE vendorContact SET"
                + " type = :vname,"
                + " modified = :modified,"
                + " modifiedBy = :modifiedBy,"
                + " active = :active"
                + " WHERE id = :id")
                .setParameter("id", m.getId())
                .setParameter("type", m.getType())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static ArrayList<DtoSubMaterial> getSubMaterials(Session mdk, int idProductMaterial) {
        ArrayList<DtoSubMaterial> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " idProductMaterial,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM productSubMaterial"
                + " WHERE idProductMaterial  = :idProductMaterial")
                .setParameter("idProductMaterial", idProductMaterial)
                .setResultTransformer(Transformers.aliasToBean(DtoSubMaterial.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoSubMaterial) itr.next());
        }
        return a;
    }

    public static ArrayList<DtoString> getCitiesPostalCodes(Session mdk, String province) {
        ArrayList<DtoString> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT distinct city as description"
                + " FROM postalCode"
                + " WHERE province = :province")
                .setParameter("province", province)
                .setResultTransformer(Transformers.aliasToBean(DtoString.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoString) itr.next());
        }
        return a;
    }

    public static ArrayList<DtoString> getPostalCodes(Session mdk, String city) {
        ArrayList<DtoString> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT distinct postalCode as description"
                + " FROM postalCode"
                + " WHERE city = :city")
                .setParameter("city", city)
                .setResultTransformer(Transformers.aliasToBean(DtoString.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoString) itr.next());
        }
        return a;
    }
    ///////////////////////////Maintenance Vendor Address/////////////////////
    ///vendor contact maintenance//////

    public static ArrayList<DtoVendorAddressQuery> getVendorsAddress(Session mdk, String id) {

        ArrayList<DtoVendorAddressQuery> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " V.id,"
                + " V.description,"
                + " P.postalCode,"
                + " P.city,"
                + " P.province,"
                + " V.active"
                + " FROM vendorAddress as V INNER JOIN postalCode as P"
                + " ON V.idPostalCode = P.id"
                + " WHERE V.idVendor=:idVendor")
                .setParameter("idVendor", id)
                .setResultTransformer(Transformers.aliasToBean(DtoVendorAddressQuery.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoVendorAddressQuery) itr.next());
        }
        return a;
// 
//        ArrayList<DtoVendorAddress> a = new ArrayList<>();
//        Iterator itr = mdk.createNativeQuery("SELECT"
//                + " id,"
//                + " idVendor,"
//                + " idPostalCode,"
//                + " description,"   
//                + " created,"
//                + " createdBy,"
//                + " modified,"
//                + " modifiedBy,"
//                + " active"
//                + " FROM vendorAddress"
//                + " WHERE idVendor=:idVendor")
//                .setParameter("idVendor", id)
//                .setResultTransformer(Transformers.aliasToBean(DtoVendorAddress.class))
//                .list().iterator();
//
//        while (itr.hasNext()) {
//            a.add((DtoVendorAddress) itr.next());
//        }
//        return a;
    }

    public static DtoVendorAddress getVendorAddress(Session mdk, String id) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " idVendor,"
                + " idPostalCode,"
                + " description,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active"
                + " FROM vendorAddress"
                + " WHERE id=:id")
                .setParameter("id", id)
                .setResultTransformer(Transformers.aliasToBean(DtoVendorAddress.class))
                .list().iterator();
        DtoVendorAddress m = null;
        while (itr.hasNext()) {
            m = (DtoVendorAddress) itr.next();
        }
        return m;
    }

    public static void saveVendorAddress(Session mdk, DtoVendorAddress m) {
        mdk.createNativeQuery("INSERT INTO vendorAddress"
                + " (idVendor, idPostalCode, description, created, createdBy, modified, modifiedBy, active)"
                + " VALUES"
                + " (:idVendor, :idPostalCode, :description, :created, :createdBy, :modified, :modifiedBy, :active)")
                .setParameter("idVendor", m.getIdVendor())
                .setParameter("idPostalCode", m.getIdPostalCode())
                .setParameter("description", m.getDescription())
                .setParameter("created", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static void updateVendorAddress(Session mdk, DtoVendorAddress m) {
        mdk.createNativeQuery("UPDATE vendorAddress SET"
                + " idPostalCode= :idPostalCode,"
                + " description= :description,"
                + " modified = :modified,"
                + " modifiedBy = :modifiedBy,"
                + " active = :active"
                + " WHERE id = :id")
                .setParameter("id", m.getId())
                .setParameter("idPostalCode", m.getIdPostalCode())
                .setParameter("description", m.getDescription())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static void deleteVendorAddress(Session mdk, String idVendorAddress) {
        mdk.createNativeQuery("DELETE FROM vendorAddress"
                + " WHERE idVendor = :id")
                .setParameter("id", idVendorAddress)
                .executeUpdate();
    }

    public static void activeVendorAddress(Session mdk, DtoVendorAddress m) {
        mdk.createNativeQuery("UPDATE vendorAddress SET"
                + " idPostalCode= :idPostalCode,"
                + " description= :description,"
                + " modified = :modified,"
                + " modifiedBy = :modifiedBy,"
                + " active = :active"
                + " WHERE id = :id")
                .setParameter("id", m.getId())
                .setParameter("idPostalCode", m.getIdPostalCode())
                .setParameter("description", m.getDescription())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.getActive())
                .executeUpdate();
    }

    public static int getIdPostalCodes(Session mdk, String postalCode) {
        int id = 0;
        Iterator itr = mdk.createSQLQuery("SELECT id"
                + " FROM postalCode"
                + " WHERE postalCode = :postalCode")
                .setParameter("postalCode", postalCode)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list().iterator();
        while (itr.hasNext()) {
            id = Numeros.entero(String.valueOf(((Map) itr.next()).get("id")));
        }

        return id;
    }
}
