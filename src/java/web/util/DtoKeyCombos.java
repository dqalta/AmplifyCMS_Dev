/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web.util;

import java.io.Serializable;

public class DtoKeyCombos implements Serializable{

    private String codigo;
    private String descripcion;

    public DtoKeyCombos() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
