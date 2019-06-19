package web.util;
//load data  from category and create a array
public class KeyCombosPostalCodes {

    int id;
    String province;

    public KeyCombosPostalCodes(int id, String province) {
        this.id = id;
        this.province = province;
    }

    public int getId() {
        return id;
    }

    public String getProvince() {
        return province;
    }
}