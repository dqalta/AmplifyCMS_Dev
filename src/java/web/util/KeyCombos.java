/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web.util;
//load data  from category and create a array
public class KeyCombos {

    int id;
    String description;

    public KeyCombos(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
