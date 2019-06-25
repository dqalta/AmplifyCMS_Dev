package sql.masonryAdmin.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import sql.masonryAdmin.maintenance.DtoVendor;
import util.Numeros;

/**
 *
 * @author CR104978
 */
public class AdminSQL {

    public static void saveRol(Session mdk, DtoRol m) {
        mdk.createNativeQuery("INSERT INTO masonryUser"
                + " (code, nickName, fullName, email, password, menuAdmin, menuProdAdmin, menuProdComp, created, createdBy, modified, modifiedBy, active, status)"
                + " VALUES"
                + " (:code, :nickName, :fullName, :email, :password, :menuAdmin, :menuProdAdmin, :menuProdComp, :created, :createdBy, :modified, :modifiedBy, :active, :status)")
                .setParameter("code", m.getDescription())
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

    public static DtoUser getUser(Session mdk, String code) {
        Iterator itr = mdk.createNativeQuery("SELECT"
                + " id,"
                + " code,"
                + " nickName,"
                + " fullName,"
                + " email,"
                + " password,"
                + " menuAdmin,"
                + " menuProdAdmin,"
                + " menuProdComp,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active,"
                + " status"
                + " FROM masonryUser"
                + " WHERE code = :code")
                .setParameter("code", code)
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
                + " code,"
                + " nickName,"
                + " fullName,"
                + " email,"
                + " password,"
                + " menuAdmin,"
                + " menuProdAdmin,"
                + " menuProdComp,"
                + " created,"
                + " createdBy,"
                + " modified,"
                + " modifiedBy,"
                + " active,"
                + " status"
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
