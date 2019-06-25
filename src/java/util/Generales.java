/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author CR104978
 */
public class Generales {

    public static String generateCode(String name) {
        String[] fName = name.split(" ");
        String code_ = "";
        int tam = fName.length > 3 ? 3 : fName.length;

        for (int i = 0; i < tam; i++) {
            switch (tam) {
                case 1:
                    code_ = code_ + fName[i].substring(0, 3);
                    break;
                case 2:
                    if (i == 0) {
                        code_ = code_ + fName[i].substring(0, 1);
                    } else {
                        code_ = code_ + fName[i].substring(0, 2);
                    }
                    break;
                case 3:
                    code_ = code_ + fName[i].substring(0, 1);
                    break;
            }
        }
        return code_.toUpperCase();
    }

    public static String cortarString(String cadena, int inicia, int termina) {
        String resultado = "";
        resultado = cadena.substring(inicia, termina);
        return resultado;
    }

    public static String encabezadoXML() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    }

    public static String finSesionXML() {
        StringBuilder data = new StringBuilder();
        data.append("<data>").append("\n");
        data.append("<sesion>NO</sesion>").append("\n");
        data.append("</data>").append("\n");
        return data.toString();
    }

    public static byte[] archivoABytes(File archivo) {
        byte bytes[] = new byte[(int) archivo.length()];
        try {
            FileInputStream fis = new FileInputStream(archivo);
            fis.read(bytes);
            fis.close();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return bytes;
    }

    public static double kilobytes(double bytes) {
        double kilobytes = 0;
        try {
            kilobytes = (bytes / 1024);
        } catch (Exception ex) {
            kilobytes = 0;
        }
        return kilobytes;
    }
    /*
    public static String encabezadoCorreoPNC(HtmlEmail correo, String nombre, String tipo) throws EmailException {
        StringBuilder s = new StringBuilder();
        s.append("<html>").append("\n");
        s.append("<head>").append("\n");
        s.append("<title>PNC</title>").append("\n");
        s.append("<meta charset=\"UTF-8\">").append("\n");
        s.append("<style type=\"text/css\">").append("\n");
        s.append("body{font-family:\"Helvetica Neue\",Arial,Helvetica,sans-serif;}").append("\n");
        s.append("hr{background:#ddd; color:#ddd; clear:both; float:none; width:100%; height:1px; margin:0 0 17px; border:none;}").append("\n");
        s.append("hr.space{background:#fff; color:#fff; visibility:hidden;}").append("\n");
        s.append("table{width:100%;}").append("\n");
        s.append("thead th{background:lightgrey;}").append("\n");
        s.append(".princial{width: 60%; margin: 0px auto;}").append("\n");
        s.append(".titulo{background-color:lightgrey; font-weight:bold; text-align:center; margin-bottom: 10px;}").append("\n");
        s.append(".contenido{text-align:justify; margin-bottom: 10px;}").append("\n");
        s.append(".centro{text-align:center;}").append("\n");
        s.append("</style>").append("\n");
        s.append("</head>").append("\n");
        s.append("<body>").append("\n");
        s.append("<div class=\"princial\">").append("\n");
        URL url = Generales.class.getClassLoader().getResource("/img/logo.jpg");
        String cid = correo.embed(url, "Logo");
        s.append("<div><img title=\"Logo\" alt=\"Logo\" src=\"cid:").append(cid).append("\" /></div>").append("\n");
        s.append("<div class=\"titulo\"><h3>CONTROL DE PRODUCTO NO CONFORME</h3></div>").append("\n");
        s.append("<div class=\"contenido\">Se&ntilde;or(a) <strong>").append(nombre).append("</strong> se le notifica que se ").append(tipo).append(", el detalle de la misma se presenta acontinuaci&oacute;n:</div>").append("\n");
        s.append("<hr>").append("\n");
        return s.toString();
    }

    public static String detalleCorreoPNC(PncDtoQuejas q) {
        StringBuilder s = new StringBuilder();
        s.append("<div class=\"contenido\">").append("\n");
        s.append("<table>").append("\n");
        s.append("<tr>").append("\n");
        s.append("<td>ID Queja:</td>").append("\n");
        s.append("<td>").append(q.getId()).append("</td>").append("\n");
        s.append("</tr>").append("\n");
        s.append("<tr>").append("\n");
        s.append("<td>Fecha ingreso:</td>").append("\n");
        s.append("<td>").append(Fechas.fecha(q.getCreado(), "dd/MM/yyyy")).append("</td>").append("\n");
        s.append("</tr>").append("\n");
        s.append("<tr>").append("\n");
        s.append("<td>Pa&iacute;s:</td>").append("\n");
        s.append("<td>").append(q.getPais()).append("</td>").append("\n");
        s.append("</tr>").append("\n");
        s.append("<tr>").append("\n");
        s.append("<td>Centro de producci&oacute;n:</td>").append("\n");
        s.append("<td>").append(q.getCentroProduccion()).append("</td>").append("\n");
        s.append("</tr>").append("\n");
        s.append("<tr>").append("\n");
        s.append("<td>Encargado del proyecto:</td>").append("\n");
        s.append("<td>").append(q.getEncargadoProyecto()).append("</td>").append("\n");
        s.append("</tr>").append("\n");
        s.append("<tr>").append("\n");
        s.append("<td>Jefe operativo:</td>").append("\n");
        s.append("<td>").append(q.getJefeOperativo()).append("</td>").append("\n");
        s.append("</tr>").append("\n");
        s.append("<tr>").append("\n");
        s.append("<td>Fecha estimada de cierre:</td>").append("\n");
        s.append("<td>").append(Fechas.fecha(q.getFechaCierreEstimada(), "dd/MM/yyyy")).append("</td>").append("\n");
        s.append("</tr>").append("\n");
        s.append("<tr>").append("\n");
        s.append("<td>Categoria:</td>").append("\n");
        s.append("<td>").append(q.getCategoria()).append("</td>").append("\n");
        s.append("</tr>").append("\n");
        s.append("<tr>").append("\n");
        s.append("<td>No conformidad:</td>").append("\n");
        s.append("<td>").append(q.getNoConformidad()).append("</td>").append("\n");
        s.append("</tr>").append("\n");
        s.append("<tr>").append("\n");
        s.append("<td>Motivo:</td>").append("\n");
        s.append("<td>").append(q.getMotivo()).append("</td>").append("\n");
        s.append("</tr>").append("\n");
        s.append("<tr>").append("\n");
        s.append("<td>Es no conformidad:</td>").append("\n");
        s.append("<td>").append(q.getEsNoConformidad()).append("</td>").append("\n");
        s.append("</tr>").append("\n");
        s.append("<tr>").append("\n");
        s.append("<td>Estado:</td>").append("\n");
        s.append("<td>").append(q.getEstado()).append("</td>").append("\n");
        s.append("</tr>").append("\n");
        s.append("<tr>").append("\n");
        s.append("<td>Creada por:</td>").append("\n");
        s.append("<td>").append(q.getCreadoPor()).append("</td>").append("\n");
        s.append("</tr>").append("\n");
        s.append("</table>").append("\n");
        s.append("</div>").append("\n");
        return s.toString();
    }

    public static String planAccionPnc(ArrayList<MapQuejasPlanAccion> p) {
        StringBuilder s = new StringBuilder();
        s.append("<div class=\"contenido\">").append("\n");
        s.append("<div class=\"centro\"><h3>PLAN DE ACCION</h3></div>").append("\n");
        s.append("<table>").append("\n");
        s.append("<thead>").append("\n");
        s.append("<th>Actividad</th>").append("\n");
        s.append("<th>Fecha</th>").append("\n");
        s.append("<th>Acci&oacute;n</th>").append("\n");
        s.append("<th>Responsable</th>").append("\n");
        s.append("<th>Costo</th>").append("\n");
        s.append("<th>C&oacute;mo</th>").append("\n");
        s.append("<th>Por qu&eacute;</th>").append("\n");
        s.append("<th>D&oacute;nde</th>").append("\n");
        s.append("<th>Decisi&oacute;n</th>").append("\n");
        s.append("<th>Estado</th>").append("\n");
        s.append("<th>Comentarios</th>").append("\n");
        s.append("</thead>").append("\n");
        for (int i = 0; i < p.size(); ++i) {
            s.append("<tr>").append("\n");
            s.append("<td>").append(p.get(i).getActividad()).append("</td>").append("\n");
            s.append("<td>").append(Fechas.fecha(p.get(i).getFecha(), "dd/MM/yyyy")).append("</td>").append("\n");
            s.append("<td>").append(p.get(i).getAccion()).append("</td>").append("\n");
            s.append("<td>").append(p.get(i).getResponsable()).append("</td>").append("\n");
            s.append("<td>").append(p.get(i).getCosto()).append("</td>").append("\n");
            s.append("<td>").append(p.get(i).getComo()).append("</td>").append("\n");
            s.append("<td>").append(p.get(i).getPorque()).append("</td>").append("\n");
            s.append("<td>").append(p.get(i).getDonde()).append("</td>").append("\n");
            s.append("<td>").append(p.get(i).getDecision()).append("</td>").append("\n");
            s.append("<td>").append(p.get(i).getEstado()).append("</td>").append("\n");
            s.append("<td>").append(p.get(i).getComentarios()).append("</td>").append("\n");
            s.append("</tr>").append("\n");
        }
        s.append("</table>").append("\n");
        s.append("</div>").append("\n");
        return s.toString();
    }

    public static String pieCorreoPNC(HtmlEmail correo) throws EmailException {
        StringBuilder s = new StringBuilder();
        s.append("<div class=\"contenido\"><strong>No responder a este correo ya que se genera de forma autom&aacute;tica.</strong></div>").append("\n");
        s.append("<hr/>").append("\n");
        URL url = Generales.class.getClassLoader().getResource("/img/firma_pnc.jpg");
        String cid = correo.embed(url, "Firma");
        s.append("<div><img title=\"Firma\" alt=\"Firma\" src=\"cid:").append(cid).append("\" /></div>").append("\n");
        s.append("</div>").append("\n");
        s.append("</body>").append("\n");
        s.append("</html>").append("\n");
        return s.toString();
    }
    
     */
}
