/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var accion, permiso, mensaje, mensajes;
$(document).ready(function () {
    mensaje = $("#mensaje").val();
    mensajes = $("#mensajes").val();
    scroll();
    if (mensaje === "true") {
        mostrarNotificaciones();
    }
});
