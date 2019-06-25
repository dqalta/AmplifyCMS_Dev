/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.util;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import sql.masonryAdmin.admin.AdminSQL;
import sql.masonryAdmin.admin.DtoRol;

/**
 *
 * @author CR104978
 */
public class CombosAdmin {

    public static ArrayList<KeyCombos> roles(Session mdk) {
        ArrayList<KeyCombos> combo = new ArrayList<>();
        List<DtoRol> roles = AdminSQL.getRols(mdk);
        for (DtoRol c : roles) {
            combo.add(new KeyCombos(c.getId(), c.getDescription()));
        }
        return combo;
    }
}
