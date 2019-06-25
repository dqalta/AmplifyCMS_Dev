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
        dataTable("table_vendor");
        $("#province").chosen({width: "100%"});
        $("#city").chosen({width: "100%"});
        $("#postalCode").chosen({width: "100%"});


        if (accion === 10) {
            $('a[data-value="tabContact"]').click();
        }


        if (accion === 11) {
            $('a[data-value="tabAddress"]').click();
        }
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

function chargeCities() {
    var province = $("#province").val();
    if (province === null) {
        province = "";
    }

    $.ajax({
        tradional: true,
        type: "post",
        datatype: "html",
        data:
                {
                    "province": province
                },
        url: "/MasonryCMS/masonryAdmin/queries/ajax/select-cities.mdk",
        success: function (html)
        {
            $("#city").html(html).trigger("chosen:updated");
        },
        error: function (estado)
        {
            //   alert(estado);
        }
    });
}

function chargePostalCodes() {
    var city = $("#city").val();
    if (city === null) {
        city = "";
    }
    $.ajax({
        tradional: true,
        type: "post",
        datatype: "html",
        data:
                {
                    "city": city
                },
        url: "/MasonryCMS/masonryAdmin/queries/ajax/select-postal-codes.mdk",
        success: function (html)
        {
            $("#postalCode").html(html).trigger("chosen:updated");
        },
        error: function (estado)
        {
            //   alert(estado);
        }
    });
}
function cancel() {
    $("#ModalProcesando").modal({backdrop: 'static', keyboard: false});
    window.location = "/MasonryCMS/masonryAdmin/maintenance/vendor.mdk";
}

function save() {
    $("#ModalProcesando").modal({backdrop: 'static', keyboard: false});
    $("#accion").val(1);
    $("#formulario").submit();
}
function saveContact() {
    $("#ModalProcesando").modal({backdrop: 'static', keyboard: false});
    $("#accion").val(3);
    $("#formulario").submit();
}
function deleteContact(id) {
    $("#ModalProcesando").modal({backdrop: 'static', keyboard: false});
    $("#accion").val(4);
    $("#idContact").val(id);
    $("#formulario").submit();
}
function deleteAddress(id) {
    $("#ModalProcesando").modal({backdrop: 'static', keyboard: false});
    $("#accion").val(8);
    $("#idAddress").val(id);
    $("#formulario").submit();
}
function activeContact(id) {
    $("#ModalProcesando").modal({backdrop: 'static', keyboard: false});
    $("#accion").val(5);
    $("#idContact").val(id);
    $("#formulario").submit();
}

function saveAddress() {
    $("#ModalProcesando").modal({backdrop: 'static', keyboard: false});
    $("#accion").val(6);
    $("#formulario").submit();
}
function activeAddress(id) {
    $("#ModalProcesando").modal({backdrop: 'static', keyboard: false});
    $("#accion").val(7);
    $("#idAddress").val(id);
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