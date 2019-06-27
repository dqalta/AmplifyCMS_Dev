package sql.masonryAdmin.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import sql.masonryAdmin.maintenance.DtoManufacturer;
import sql.masonryAdmin.maintenance.DtoVendor;
import util.Numeros;

/**
 *
 * @author CR104978
 */
public class AdminSQL {

    public static void saveUserRol(Session mdk, String codeUser, int idMasonryRol) {
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

    public static void saveUser(Session vdk, DtoUser m) {
        vdk.createNativeQuery("INSERT INTO masonryUser"
                + " (codeUser, nickName, fullName, email, passwordUser, menuAdmin, menuProdAdmin, menuProdComp, created, createdBy, modified, modifiedBy, active, statusUser)"
                + " VALUES"
                + " (:codeUser, :nickName, :fullName, :email, :passwordUser, :menuAdmin, :menuProdAdmin, :menuProdComp, :created, :createdBy, :modified, :modifiedBy, :active, :statusUser)")
                .setParameter("codeUser", m.getCodeUser())
                .setParameter("nickName", m.getNickName())
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
                .setParameter("statusUser", m.getStatusUser())
                .executeUpdate();
    }

    public static void incrementConsecutive(Session mdk, String type) {
        mdk.createNativeQuery("UPDATE consecutive SET"
                + " consecutive = consecutive + increment"
                + " WHERE type = :type")
                .setParameter("type", type)
                .executeUpdate();
    }

    public static void saveRol(Session mdk, DtoRol m) {
        mdk.createNativeQuery("INSERT INTO masonryUser"
                + " (codeUser, nickName, fullName, email, passwordUser, menuAdmin, menuProdAdmin, menuProdComp, created, createdBy, modified, modifiedBy, active, statusUser)"
                + " VALUES"
                + " (:codeUser, :nickName, :fullName, :email, :passwordUser, :menuAdmin, :menuProdAdmin, :menuProdComp, :created, :createdBy, :modified, :modifiedBy, :active, :statusUser)")
                .setParameter("codeUser", m.getDescription())
                .setParameter("nickName", m.getCreated())
                .setParameter("createdBy", m.getCreatedBy())
                .setParameter("modified", m.getModified())
                .setParameter("modifiedBy", m.getModifiedBy())
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
                + " codeUser,"
                + " nickName,"
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
                + " statusUser"
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

    public static ArrayList<DtoUser> getUsers(Session mdk) {
        ArrayList<DtoUser> a = new ArrayList<>();
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " codeUser,"
                + " nickName,"
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
                + " statusUser"
                + " FROM masonryUser")
                .setResultTransformer(Transformers.aliasToBean(DtoUser.class))
                .list().iterator();

        while (itr.hasNext()) {
            a.add((DtoUser) itr.next());
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
