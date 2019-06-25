/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.util;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import sql.masonryAdmin.maintenance.DtoCollection;
import sql.masonryAdmin.maintenance.DtoColor;
import sql.masonryAdmin.maintenance.DtoManufacturer;
import sql.masonryAdmin.maintenance.DtoMaterial;
import sql.masonryAdmin.maintenance.MaintenanceSQL;
import sql.masonryAdmin.maintenance.DtoMetricsSystem;
import sql.masonryAdmin.maintenance.DtoPackageType;
import sql.masonryAdmin.maintenance.DtoPostalCode;
import sql.masonryAdmin.maintenance.DtoSize;
import sql.masonryAdmin.maintenance.DtoStyle;
import sql.masonryAdmin.maintenance.DtoSubMaterial;
import sql.masonryAdmin.maintenance.DtoTexture;
import sql.masonryAdmin.maintenance.DtoString;

/**
 *
 * @author CR104978
 */
public class CombosMaintenance {

    public static ArrayList<KeyCombos> getMaterials(Session mdk) {
        ArrayList<KeyCombos> combo = new ArrayList<>();
        combo.add(new KeyCombos(0, "SELECT ONE MATERIAL"));
        List<DtoMaterial> materials = MaintenanceSQL.getMaterials(mdk);
        for (DtoMaterial c : materials) {
            combo.add(new KeyCombos((c.getId()), c.getDescription()));
        }
        return combo;
    }

    public static ArrayList<KeyCombos> getSubMaterials(Session mdk) {
        ArrayList<KeyCombos> combo = new ArrayList<>();
        combo.add(new KeyCombos(0, "SELECT ONE SUBMATERIAL"));
        List<DtoSubMaterial> subMaterials = MaintenanceSQL.getSubMaterials(mdk);
        for (DtoSubMaterial c : subMaterials) {
            combo.add(new KeyCombos((c.getId()), c.getDescription()));
        }
        return combo;
    }

    public static ArrayList<KeyCombos> getSubMaterials(Session mdk, int material) {
        ArrayList<KeyCombos> combo = new ArrayList<>();
        combo.add(new KeyCombos(0, "SELECT ONE SUBMATERIAL"));
        List<DtoSubMaterial> subMaterials = MaintenanceSQL.getSubMaterials(mdk, material);
        for (DtoSubMaterial c : subMaterials) {
            combo.add(new KeyCombos((c.getId()), c.getDescription()));
        }
        return combo;
    }

    public static ArrayList<KeyCombos> getStyles(Session mdk) {
        ArrayList<KeyCombos> combo = new ArrayList<>();
        List<DtoStyle> styles = MaintenanceSQL.getStyles(mdk);
        for (DtoStyle c : styles) {
            combo.add(new KeyCombos((c.getId()), c.getDescription()));
        }
        return combo;
    }

    public static ArrayList<KeyCombos> getSizes(Session mdk) {
        ArrayList<KeyCombos> combo = new ArrayList<>();
        List<DtoSize> styles = MaintenanceSQL.getSizes(mdk);
        for (DtoSize c : styles) {
            combo.add(new KeyCombos((c.getId()), c.getDescription()));
        }
        return combo;
    }

    public static ArrayList<KeyCombos> getManufacturers(Session mdk) {
        ArrayList<KeyCombos> combo = new ArrayList<>();
        List<DtoManufacturer> manufacturers = MaintenanceSQL.getManufacturers(mdk);
        for (DtoManufacturer c : manufacturers) {
            combo.add(new KeyCombos((c.getId()), c.getDescription()));
        }
        return combo;
    }

    public static ArrayList<KeyCombos> getTextures(Session mdk) {
        ArrayList<KeyCombos> combo = new ArrayList<>();
        List<DtoTexture> textures = MaintenanceSQL.getTextures(mdk);
        for (DtoTexture c : textures) {
            combo.add(new KeyCombos((c.getId()), c.getDescription()));
        }
        return combo;
    }

    public static ArrayList<KeyCombos> getPackageTypes(Session mdk) {
        ArrayList<KeyCombos> combo = new ArrayList<>();
        List<DtoPackageType> packageTypes = MaintenanceSQL.getPackageTypes(mdk);
        for (DtoPackageType c : packageTypes) {
            combo.add(new KeyCombos((c.getId()), c.getDescription()));
        }
        return combo;
    }

    public static ArrayList<KeyCombos> getUnits(Session mdk) {
        ArrayList<KeyCombos> combo = new ArrayList<>();
        List<DtoMetricsSystem> units = MaintenanceSQL.getMetricsSystems(mdk);
        for (DtoMetricsSystem c : units) {
            combo.add(new KeyCombos((c.getId()), c.getDescription()));
        }
        return combo;
    }

    public static ArrayList<KeyCombos> getCollections(Session mdk) {
        ArrayList<KeyCombos> combo = new ArrayList<>();
        List<DtoCollection> collections = MaintenanceSQL.getCollections(mdk);
        for (DtoCollection c : collections) {
            combo.add(new KeyCombos((c.getId()), c.getDescription()));
        }
        return combo;
    }

    public static ArrayList<KeyCombos> getColors(Session mdk) {
        ArrayList<KeyCombos> combo = new ArrayList<>();
        List<DtoColor> colors = MaintenanceSQL.getColors(mdk);
        for (DtoColor c : colors) {
            combo.add(new KeyCombos((c.getId()), c.getDescription()));
        }
        return combo;
    }

    public static ArrayList<KeyCombos> getTypeContact() {
        ArrayList<KeyCombos> combo = new ArrayList<>();
        combo.add(new KeyCombos(1, "Email"));
        combo.add(new KeyCombos(2, "Mobile Number"));
        combo.add(new KeyCombos(3, "Office phone Number"));
        combo.add(new KeyCombos(4, "Other"));
        return combo;
    }

    public static ArrayList<KeyCombosString> getProvincePostalCodes(Session mdk) {
        ArrayList<KeyCombosString> combo = new ArrayList<>();
        combo.add(new KeyCombosString("NONE"));
        List<DtoString> provincePostalCodes = MaintenanceSQL.getProvincePostalCodes(mdk);
        for (DtoString c : provincePostalCodes) {
            combo.add(new KeyCombosString(c.getDescription()));
        }
        return combo;
    }

    public static ArrayList<KeyCombosString> getCitiesPostalCodes(Session mdk, String province) {
        ArrayList<KeyCombosString> combo = new ArrayList<>();
        combo.add(new KeyCombosString("NONE"));
        List<DtoString> cities = MaintenanceSQL.getCitiesPostalCodes(mdk, province);
        for (DtoString c : cities) {
            combo.add(new KeyCombosString(c.getDescription()));
        }
        return combo;
    }

    public static ArrayList<KeyCombosString> getPostalCodes(Session mdk, String city) {
        ArrayList<KeyCombosString> combo = new ArrayList<>();
        combo.add(new KeyCombosString("NONE"));
        List<DtoString> postalCodes = MaintenanceSQL.getPostalCodes(mdk, city);
        for (DtoString c : postalCodes) {
            combo.add(new KeyCombosString(c.getDescription()));
        }
        return combo;
    }
}
