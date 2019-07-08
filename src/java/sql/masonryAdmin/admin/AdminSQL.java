package sql.masonryAdmin.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import sql.masonryAdmin.maintenance.DtoManufacturer;
import sql.masonryAdmin.maintenance.DtoVendor;
import sql.masonryAdmin.maintenance.DtoVendorUser;
import util.Fechas;
import util.Numeros;

/**
 *
 * @author CR104978
 */
public class AdminSQL {

    public static DtoVendorUser getVendorUser(Session mdk, String code) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " codeVendorUser,"
                + " nickName,"
                + " fullName,"
                + " email,"
                + " passwordVendorUser,"
                + " idVendor,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active,"
                + " statusVendorUser"
                + " FROM vendorUser"
                + " WHERE codeVendorUser = :codeVendorUser")
                .setParameter("codeVendorUser", code)
                .setResultTransformer(Transformers.aliasToBean(DtoVendorUser.class))
                .list().iterator();
        DtoVendorUser m = null;
        while (itr.hasNext()) {
            m = (DtoVendorUser) itr.next();
        }
        return m;
    }

    public static int getLastIdVendorRol(Session mdk, String user) {
        int result = 0;
        Iterator itr = mdk.createSQLQuery("SELECT MAX(id) as result"
                + " FROM vendorRol"
                + " WHERE createdBy = :user")
                .setParameter("user", user)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list().iterator();
        while (itr.hasNext()) {
            result = Numeros.entero(String.valueOf(((Map) itr.next()).get("result")));
        }
        return result;
    }

    public static void saveVendorUserRol(Session mdk, int rol, String id, String idVendor) {
        mdk.createNativeQuery("INSERT INTO vendorUserRol"
                + " (idVendorRol, codeVendorUser, idVendor)"
                + " VALUES"
                + " (:idVendorRol, :codeVendorUser, :idVendor)")
                .setParameter("idVendorRol", rol)
                .setParameter("codeVendorUser", id)
                .setParameter("idVendor", idVendor)
                .executeUpdate();
    }

    public static void saveVendorRolDetail(Session vdk, int idVendorRol, String module, String description, boolean permission, String user, Date ya, String idVendor) {
        if (permission) {
            vdk.createNativeQuery("INSERT INTO vendorRolDetail"
                    + " (idVendorRol, module, description, permission, created, createdBy, modified, modifiedBy, idVendor)"
                    + " VALUES"
                    + " (:idVendorRol, :module, :description, :permission, :created, :createdBy, :modified, :modifiedBy, :idVendor)")
                    .setParameter("idVendorRol", idVendorRol)
                    .setParameter("module", module)
                    .setParameter("description", description)
                    .setParameter("permission", permission)
                    .setParameter("created", ya)
                    .setParameter("createdBy", user)
                    .setParameter("modified", ya)
                    .setParameter("modifiedBy", user)
                    .setParameter("idVendor", idVendor)
                    .executeUpdate();
        }
    }

    public static void saveVendorUser(Session vdk, DtoVendorUser m) {
        vdk.createNativeQuery("INSERT INTO vendorUser"
                + " (codeVendorUser, nickName, fullName, email, passwordVendorUser, idVendor, created, createdBy, modified, modifiedBy, active, statusVendorUser)"
                + " VALUES"
                + " (:codeVendorUser, :nickName, :fullName, :email, :passwordVendorUser, :idVendor, :created, :createdBy, :modified, :modifiedBy, :active, :statusVendorUser)")
                .setParameter("codeVendorUser", m.getCodeVendorUser())
                .setParameter("nickName", m.getNickName())
                .setParameter("fullName", m.getFullName())
                .setParameter("email", m.getEmail())
                .setParameter("passwordVendorUser", m.getPasswordVendorUser())
                .setParameter("idVendor", m.getIdVendor())
                .setParameter("created", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.isActive())
                .setParameter("statusVendorUser", m.getStatusVendorUser())
                .executeUpdate();
    }

    public static int getConsecutiveVendor(Session mdk, String type, String idVendor) {
        int consecutive = 0;
        Iterator itr = mdk.createSQLQuery("SELECT consecutive"
                + " FROM vendorConsecutive"
                + " WHERE vtype = :vtype"
                + " AND idVendor = :idVendor")
                .setParameter("vtype", type)
                .setParameter("idVendor", idVendor)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list().iterator();
        while (itr.hasNext()) {
            consecutive = Numeros.entero(String.valueOf(((Map) itr.next()).get("consecutive")));
        }

        return consecutive;
    }

    public static void saveConsecutiveVendor(Session mdk, String type, String idVendor) {

        mdk.createNativeQuery("INSERT INTO vendorConsecutive"
                + " (consecutive, vtype, increment, idVendor)"
                + " VALUES"
                + " (:consecutive, :vtype, :increment, :idVendor)")
                .setParameter("consecutive", 1)
                .setParameter("vtype", type)
                .setParameter("increment", 1)
                .setParameter("idVendor", idVendor)
                .executeUpdate();
    }

    public static void incrementConsecutiveVendor(Session mdk, String type, String idVendor) {
        mdk.createNativeQuery("UPDATE vendorConsecutive SET"
                + " consecutive = consecutive + increment"
                + " WHERE vtype = :vtype"
                + " AND idVendor = :idVendor")
                .setParameter("vtype", type)
                .setParameter("idVendor", idVendor)
                .executeUpdate();
    }

    public static void updateEmail(Session o2c, int idMail, String observations, String status) {
        o2c.createNativeQuery("UPDATE mailsToSend SET"
                + " sended = :sended,"
                + " attempts = attempts + 1,"
                + " observations = :observations,"
                + " status = :status"
                + " WHERE id = :id")
                .setParameter("id", idMail)
                .setParameter("sended", Fechas.ya())
                .setParameter("observations", observations)
                .setParameter("status", status)
                .executeUpdate();
    }

    public static void saveUserRol(Session mdk, String codeUser, String idMasonryRol) {
        mdk.createNativeQuery("INSERT INTO masonryUserRol"
                + " (codeUser, idMasonryRol)"
                + " VALUES"
                + " (:codeUser, :idMasonryRol)")
                .setParameter("codeUser", codeUser)
                .setParameter("idMasonryRol", idMasonryRol)
                .executeUpdate();
    }

    public static void deleteUserRol(Session mdk, String codeUser) {
        mdk.createNativeQuery("DELETE FROM masonryUserRol"
                + " WHERE codeUser = :codeUser")
                .setParameter("codeUser", codeUser)
                .executeUpdate();
    }

    public static void updateUser(Session vdk, DtoUser m) {
        vdk.createNativeQuery("UPDATE masonryUser"
                + " SET fullName = :fullName,"
                + " email = :email,"
                + " menuAdmin = :menuAdmin,"
                + " menuProdAdmin = :menuProdAdmin,"
                + " menuProdComp = :menuProdComp,"
                + " modified  = :modified,"
                + " modifiedBy = :modifiedBy,"
                + " active = :active "
                + " WHERE id = :idEdit")
                .setParameter("idEdit", m.getId())
                .setParameter("fullName", m.getFullName())
                .setParameter("email", m.getEmail())
                .setParameter("menuAdmin", m.isMenuAdmin())
                .setParameter("menuProdAdmin", m.isMenuProdAdmin())
                .setParameter("menuProdComp", m.isMenuProdComp())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.isActive())
                .executeUpdate();
    }

    public static void saveUser(Session vdk, DtoUser m) {
        vdk.createNativeQuery("INSERT INTO masonryUser"
                + " (guid, codeUser, fullName, email, passwordUser, menuAdmin, menuProdAdmin, menuProdComp, created, createdBy, modified, modifiedBy, active)"
                + " VALUES"
                + " (:guid, :codeUser, :fullName, :email, :passwordUser, :menuAdmin, :menuProdAdmin, :menuProdComp, :created, :createdBy, :modified, :modifiedBy, :active)")
                .setParameter("guid", m.getGuid())
                .setParameter("codeUser", m.getCodeUser())
                .setParameter("fullName", m.getFullName())
                .setParameter("email", m.getEmail())
                .setParameter("passwordUser", m.getPasswordUser())
                .setParameter("menuAdmin", m.isMenuAdmin())
                .setParameter("menuProdAdmin", m.isMenuProdAdmin())
                .setParameter("menuProdComp", m.isMenuProdComp())
                .setParameter("created", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
                .setParameter("active", m.isActive())
                .executeUpdate();
    }

    public static void saveEmail(Session vdk, DtoEmail m) {
        vdk.createNativeQuery("INSERT INTO mailsToSend"
                + " (recipients,subject,body,status,created,attempts,observations,type)"
                + " VALUES"
                + " (:recipients,:subject,:body,:status,:created,:attempts,:observations,:type)")
                .setParameter("recipients", m.getRecipients())
                .setParameter("subject", m.getSubject())
                .setParameter("body", m.getBody())
                .setParameter("status", m.getStatus())
                .setParameter("created", m.getCreated())
                .setParameter("attempts", m.getAttempts())
                .setParameter("observations", m.getObservations())
                .setParameter("type", m.getType())
                .executeUpdate();
    }

    public static void incrementConsecutive(Session mdk, String type) {
        mdk.createNativeQuery("UPDATE consecutive SET"
                + " consecutive = consecutive + increment"
                + " WHERE type = :type")
                .setParameter("type", type)
                .executeUpdate();
    }

    public static int getConsecutive(Session mdk, String type) {
        int consecutive = 0;
        Iterator itr = mdk.createSQLQuery("SELECT consecutive"
                + " FROM consecutive"
                + " WHERE type = :type")
                .setParameter("type", type)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list().iterator();
        while (itr.hasNext()) {
            consecutive = Numeros.entero(String.valueOf(((Map) itr.next()).get("consecutive")));
        }

        return consecutive;
    }

    public static DtoUser getUser(Session mdk, String codeUser) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " guid,"
                + " codeUser,"
                + " fullName,"
                + " email,"
                + " passwordUser,"
                + " menuAdmin,"
                + " menuProdAdmin,"
                + " menuProdComp,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active,"
                + " lastLogin"
                + " FROM masonryUser"
                + " WHERE codeUser = :codeUser")
                .setParameter("codeUser", codeUser)
                .setResultTransformer(Transformers.aliasToBean(DtoUser.class))
                .list().iterator();
        DtoUser m = null;
        while (itr.hasNext()) {
            m = (DtoUser) itr.next();
        }
        return m;
    }

    public static DtoEmailConfiguration getEmailConfiguration(Session mdk) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " email,"
                + " name,"
                + " user,"
                + " password,"
                + " host,"
                + " hostPort,"
                + " configSet"
                + " FROM mailConfiguration")
                .setResultTransformer(Transformers.aliasToBean(DtoEmailConfiguration.class))
                .list().iterator();
        DtoEmailConfiguration m = null;
        while (itr.hasNext()) {
            m = (DtoEmailConfiguration) itr.next();
        }
        return m;
    }

    public static String getUserRols(Session o2c, String codeUser) {
        String result = "";
        Iterator itr = o2c.createSQLQuery("SELECT idMasonryRol"
                + " FROM masonryUserRol"
                + " WHERE codeUser = :codeUser")
                .setParameter("codeUser", codeUser)
                .list().iterator();

        while (itr.hasNext()) {
            result = result.concat("," + (int) itr.next());
        }
        return result;
    }

    public static DtoUser getUser(Session mdk, int idEdit) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " guid,"
                + " codeUser,"
                + " fullName,"
                + " email,"
                + " passwordUser,"
                + " menuAdmin,"
                + " menuProdAdmin,"
                + " menuProdComp,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active,"
                + " lastLogin"
                + " FROM masonryUser"
                + " WHERE id = :idEdit")
                .setParameter("idEdit", idEdit)
                .setResultTransformer(Transformers.aliasToBean(DtoUser.class))
                .list().iterator();
        DtoUser m = null;
        while (itr.hasNext()) {
            m = (DtoUser) itr.next();
        }
        return m;
    }

    public static DtoUser getUserByGuid(Session mdk, String guid) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " guid,"
                + " codeUser,"
                + " fullName,"
                + " email,"
                + " passwordUser,"
                + " menuAdmin,"
                + " menuProdAdmin,"
                + " menuProdComp,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active,"
                + " lastLogin"
                + " FROM masonryUser"
                + " WHERE guid = :guid")
                .setParameter("guid", guid)
                .setResultTransformer(Transformers.aliasToBean(DtoUser.class))
                .list().iterator();
        DtoUser m = null;
        while (itr.hasNext()) {
            m = (DtoUser) itr.next();
        }
        return m;
    }

    public static ArrayList<DtoUser> getUsers(Session mdk) {
        ArrayList<DtoUser> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " guid,"
                + " codeUser,"
                + " fullName,"
                + " email,"
                + " passwordUser,"
                + " menuAdmin,"
                + " menuProdAdmin,"
                + " menuProdComp,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active,"
                + " lastLogin"
                + " FROM masonryUser")
                .setResultTransformer(Transformers.aliasToBean(DtoUser.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoUser) itr.next());
        }
        return a;
    }

    public static ArrayList<DtoEmail> getPendingEmails(Session mdk) {
        ArrayList<DtoEmail> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " recipients,"
                + " subject,"
                + " body,"
                + " status,"
                + " created,"
                + " sended,"
                + " attempts,"
                + " observations,"
                + " type"
                + " FROM mailsToSend"
                + " WHERE status = 'PENDING'")
                .setResultTransformer(Transformers.aliasToBean(DtoEmail.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoEmail) itr.next());
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
}
