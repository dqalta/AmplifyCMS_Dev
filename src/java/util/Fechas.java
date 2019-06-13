/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author CR104978
 */
public class Fechas {

    public static Date ya() {
        return new Date();
    }

    public static String inicioMes() {
        String anio = String.valueOf(anioEnCurso());
        String mes;
        if (mesEnCurso() < 10) {
            mes = "0" + String.valueOf(mesEnCurso());
        } else {
            mes = String.valueOf(mesEnCurso());
        }
        String fecha = anio + "-" + mes + "-" + "01";
        return fecha;
    }

    public static String finalMes() {
        String anio = String.valueOf(anioEnCurso());
        String mes;
        if (mesEnCurso() < 10) {
            mes = "0" + String.valueOf(mesEnCurso());
        } else {
            mes = String.valueOf(mesEnCurso());
        }
        String fecha = anio + "-" + mes + "-" + ultimoDiaMes();
        return fecha;
    }

    public static String hoy(String formato) {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);//FORMATO DE FECHA
        String hoy = sdf.format(ya());
        return hoy;
    }

    public static String ayer(String formato) {
        String fecha;
        SimpleDateFormat sdf = new SimpleDateFormat(formato);//FORMATO DE FECHA
        Calendar c = new GregorianCalendar();
        c.setTime(ya());
        c.add(Calendar.DAY_OF_MONTH, -1);
        fecha = sdf.format(c.getTime());
        return fecha;
    }

    public static int mesEnCurso() {
        Calendar calendario = new GregorianCalendar();
        calendario.setTime(ya());
        int mes = calendario.get(Calendar.MONTH);
        mes++;
        return mes;
    }

    public static int ultimoDiaMes() {
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.getActualMaximum(Calendar.DATE);
        return dia;
    }

    public static int anioEnCurso() {
        Calendar calendario = new GregorianCalendar();
        calendario.setTime(ya());
        int anio = calendario.get(Calendar.YEAR);
        return anio;
    }

    public static boolean esFecha(String valor) {
        boolean flag = true;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            sdf.parse(valor);
        } catch (ParseException x) {
            flag = false;
        }
        return flag;
    }

    public static String fecha(String valor, String formato_entrada, String formato_salida) {
        String fecha;
        SimpleDateFormat sdf_entrada = new SimpleDateFormat(formato_entrada);
        SimpleDateFormat sdf_salida = new SimpleDateFormat(formato_salida);
        try {
            fecha = sdf_salida.format(sdf_entrada.parse(valor));
        } catch (ParseException x) {
            fecha = null;
        }
        return fecha;
    }

    public static String fecha(Date valor, String formato_salida) {
        String fecha;
        SimpleDateFormat sdf_salida = new SimpleDateFormat(formato_salida);
        fecha = sdf_salida.format(valor);
        return fecha;
    }

    public static String fecha(DateTime valor, String formato) {
        return DateTimeFormat.forPattern(formato).print(valor);
    }

    public static DateTime fecha(Date fecha) {
        return new DateTime(fecha);
    }

    public static Date fecha(String valor) {
        Date fecha;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            fecha = sdf.parse(valor);
        } catch (ParseException x) {
            fecha = null;
        }
        return fecha;
    }

    public static Date fecha(String valor, String formato) {
        Date fecha;
        try {
            fecha = new SimpleDateFormat(formato).parse(valor);
        } catch (ParseException x) {
            fecha = null;
        }
        return fecha;
    }

    public static DateTime fechaJoda(String valor, String formato) {
        return DateTimeFormat.forPattern(formato).parseDateTime(valor);
    }

    public static int minutosEntre(DateTime inicio, DateTime fin) {
        return Minutes.minutesBetween(inicio, fin).getMinutes();
    }

    public static int minutosEntre(Date inicio, Date fin) {
        DateTime i = new DateTime(inicio);
        DateTime f = new DateTime(fin);
        return Minutes.minutesBetween(i, f).getMinutes();
    }

    public static String nombreMes(int valor) {
        String mes;
        switch (valor) {
            case 1: {
                mes = "ene";
                break;
            }
            case 2: {
                mes = "feb";
                break;
            }
            case 3: {
                mes = "mar";
                break;
            }
            case 4: {
                mes = "abr";
                break;
            }
            case 5: {
                mes = "may";
                break;
            }
            case 6: {
                mes = "jun";
                break;
            }
            case 7: {
                mes = "jul";
                break;
            }
            case 8: {
                mes = "ago";
                break;
            }
            case 9: {
                mes = "sep";
                break;
            }
            case 10: {
                mes = "oct";
                break;
            }
            case 11: {
                mes = "nov";
                break;
            }
            case 12: {
                mes = "dic";
                break;
            }
            default: {
                mes = "error";
                break;
            }
        }
        return mes;
    }

    public static String tiempo(int minutos) {
        String t;
        int h = minutos / 60;
        int m = minutos % 60;
        if (h < 10) {
            t = "0" + String.format("%d:%02d", h, m);
        } else {
            t = String.format("%d:%02d", h, m);
        }
        return t;
    }

    public static Date cambiarDiasFecha(Date fecha, int dias) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);

        return calendar.getTime();
    }

    public static int diasDiferencia(Date fechaInicial, Date fechaFinal) {
        int dias = (int) ((fechaInicial.getTime() - fechaFinal.getTime()) / 86400000);

        return dias;
    }
}
