/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var tabla, accion, permiso, mensaje, mensajes;
$(document).ready(function () {
    accion = parseInt($("#accion").val());
    permiso = $("#permiso").val();
    mensaje = $("#mensaje").val();
    mensajes = $("#mensajes").val();
    if (permiso === "true") {
        scroll();
        dataTable("table_subMaterial");
        $("#idProductMaterial").chosen({width: "100%"});
    }
    if (mensaje === "true") {
        mostrarNotificaciones();
    }
    $('#form-panel').collapse();
    chargeChecks();
    chargeCheckBoxes();
});

function chargeCheckBoxes() {
    var arr = ["active"];
    for (var i = 0; i < arr.length; i++) {
        if ($("#" + arr[i]).val() === "true") {
            $("#check_" + arr[i]).lcs_on();
        } else {
            $("#check_" + arr[i]).lcs_off();
        }
    }
}

function chargeChecks() {
    $(".lcc").lc_switch('Yes', 'No');
    $('body').delegate('.lcc', 'lcs-statuschange', function () {
        var status = ($(this).is(':checked')) ? true : false;
        var id = $(this).attr("id");
        $("#" + id.replace("check_", "")).val(status);
    });
}

function cancel() {
    $("#ModalProcesando").modal({backdrop: 'static', keyboard: false});
    window.location = "/MasonryCMS/masonryAdmin/maintenance/submaterial.mdk";
}

function save() {
    $("#ModalProcesando").modal({backdrop: 'static', keyboard: false});
    $("#accion").val(1);
    $("#formulario").submit();
}


function edit(id) {
    $("#ModalProcesando").modal({backdrop: 'static', keyboard: false});
    $("#idEdit").val(id);
    $("#accion").val(2);
    $("#formulario").submit();
}

function dataTable(id) {
    tabla = $("#" + id).DataTable({
        "sPaginationType": "full_numbers",
        "iDisplayLength": 25,
        "bAutoWidth": true,
        "bProcessing": true,
        "bDeferRender": true,
        "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "ALL"]],
        "oLanguage":
                {
                    "sProcessing": "Loading...",
                    "sLengthMenu": "Display _MENU_ entries for page",
                    "sZeroRecords": "No record found",
                    "sInfo": "Showing _START_ to _END_ of _TOTAL_ entries",
                    "sInfoEmpty": "Showing 0 entries",
                    "sInfoFiltered": "(Filtering _MAX_ total entries)",
                    "sInfoPostFix": "",
                    "sSearch": "Search",
                    "oPaginate":
                            {
                                "sFirst": "First",
                                "sPrevious": "Previous",
                                "sNext": "Next",
                                "sLast": "Last"
                            }
                },
        "sScrollX": "100%",
        "scrollCollapse": true
    });
    $('#fieldSearch').on('keyup', function () {
        tabla.search(this.value).draw();
    });
}