/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.util;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import sql.masonryAdmin.maintenance.DtoMaterial;
import sql.masonryAdmin.maintenance.MaintenanceSQL;


/**
 *
 * @author CR104978
 */
public class CombosMaintenance {

     public static ArrayList<KeyCombos> getMaterials(Session mdk) {
        ArrayList<KeyCombos> combo = new ArrayList<>();
        combo.add(new KeyCombos(0, "SELECT ONE CATEGORY"));
        List<DtoMaterial> materials = MaintenanceSQL.getMaterials(mdk);
        for (DtoMaterial c : materials) {
            combo.add(new KeyCombos((c.getId()), c.getDescription()));
        }
        return combo;
    }
}
