/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var accion, permiso, mensaje, mensajes;
$(document).ready(function () {
    accion = parseInt($("#accion").val());
    permiso = $("#permiso").val();
    mensaje = $("#mensaje").val();
    mensajes = $("#mensajes").val();
    if (permiso === "true") {
        scroll();
        dataTable("table_product");
        $("#idStyle").chosen({width: "100%"});
        $("#idTexture").chosen({width: "100%"});
        $("#idPackageType").chosen({width: "100%"});
        $("#idMaterial").chosen({width: "100%"});
        $("#idSubMaterial").chosen({width: "100%"});
        $("#idManufacturer").chosen({width: "100%"});
        $("#idSize").chosen({width: "100%"});
        $("#color").chosen({width: "100%"});
        $("#collection").chosen({width: "100%"});

        $("#unitsPallet").number(true, 0, "", "");
        $("#layersPallet").number(true, 0, "", "");
        $("#unitsLayer").number(true, 0, "", "");
        $("#qtyOfUnitsPerPackageType").number(true, 0, "", "");

        $("#palletWeight").number(true, 2);
        $("#linearFeetCorner").number(true, 2);
        $("#sqftPerPackageType").number(true, 2);

        if (accion === 5) {
            $('a[data-value="Photos"]').click();
        }
    }
    if (mensaje === "true") {
        mostrarNotificaciones();
    }
    $('#form-panel').collapse();
    chargeChecks();
    chargeCheckBoxes();
    chargeTabs();
    $("#SelectGallery").imagepicker({
        hide_select: true,
        show_label: false
    })
});

function nextTab(elem) {
    $(elem).next().find('a[data-toggle="tab"]').click();
}

function prevTab(elem) {
    $(elem).prev().find('a[data-toggle="tab"]').click();
}

function validateStep1() {
    mensajes = "";
    mensaje = false;
    //VALIDAR QUE CAMPOS NO SEAN BLANCOS NI NULOS
    if (($("#sku").val() === null) || ($("#sku").val().replace(" ", "") === "")) {
        mensajes = mensajes + "danger<>Error<>Please complete field 'SKU'.|";
        mensaje = true;
    }
    if (($("#pname").val() === null) || ($("#pname").val().replace(" ", "") === "")) {
        mensajes = mensajes + "danger<>Error<>Please complete field 'Name'.|";
        mensaje = true;
    }
    if (($("#description").val() === null) || ($("#description").val().replace(" ", "") === "")) {
        mensajes = mensajes + "danger<>Error<>Please complete field 'Description'.|";
        mensaje = true;
    }
    if (($("#idManufacturer").val() === "0")) {
        mensajes = mensajes + "danger<>Error<>Please select one 'Manufacturer'.|";
        mensaje = true;
    }
    if (($("#collection").val() === null)) {
        mensajes = mensajes + "danger<>Error<>Please select at least one 'Collection'.|";
        mensaje = true;
    }
    if (mensaje) {
        mostrarNotificaciones();
    } else {
        var $active = $('.wizard .nav-tabs li.active');
        $active.next().removeClass('disabled');
        nextTab($active);
    }
}

function validateStep2() {
    mensajes = "";
    mensaje = false;
    //VALIDAR QUE CAMPOS NO SEAN BLANCOS NI NULOS
    if (($("#idStyle").val() === "0")) {
        mensajes = mensajes + "danger<>Error<>Please select one 'Style'.|";
        mensaje = true;
    }
    if (($("#idTexture").val() === "0")) {
        mensajes = mensajes + "danger<>Error<>Please select one 'Texture'.|";
        mensaje = true;
    }
    if (($("#idPackageType").val() === "0")) {
        mensajes = mensajes + "danger<>Error<>Please select one 'Package Type'.|";
        mensaje = true;
    }
    if (($("#idSize").val() === "0")) {
        mensajes = mensajes + "danger<>Error<>Please select one 'Size'.|";
        mensaje = true;
    }
    if (($("#color").val() === null)) {
        mensajes = mensajes + "danger<>Error<>Please select at least one 'Collection'.|";
        mensaje = true;
    }
    if (mensaje) {
        mostrarNotificaciones();
    } else {
        var $active = $('.wizard .nav-tabs li.active');
        $active.next().removeClass('disabled');
        nextTab($active);
    }
}

function validateStep3() {
    mensajes = "";
    mensaje = false;
    //VALIDAR QUE CAMPOS NO SEAN BLANCOS NI NULOS
    if (($("#idMaterial").val() === "0")) {
        mensajes = mensajes + "danger<>Error<>Please select one 'Material'.|";
        mensaje = true;
    }
    if (($("#idSubMaterial").val() === "0")) {
        mensajes = mensajes + "danger<>Error<>Please select one 'Sub Material'.|";
        mensaje = true;
    }
    if (mensaje) {
        mostrarNotificaciones();
    } else {
        var $active = $('.wizard .nav-tabs li.active');
        $active.next().removeClass('disabled');
        nextTab($active);
    }
}

function validateStep5() {
         $('a[data-value="Final"]').click();
}


function validateStep4() {
    mensajes = "";
    mensaje = false;
    //VALIDAR QUE CAMPOS NO SEAN BLANCOS NI NULOS
    if (($("#idMaterial").val() === "0")) {
        mensajes = mensajes + "danger<>Error<>Please select one 'Material'.|";
        mensaje = true;
    }
    if (($("#idSubMaterial").val() === "0")) {
        mensajes = mensajes + "danger<>Error<>Please select one 'Sub Material'.|";
        mensaje = true;
    }
    if (mensaje) {
        mostrarNotificaciones();
    } else {
        save();
    }
}

function chargeSubMaterials() {
    var material = $("#idMaterial").val();
    if (material === null) {
        material = "";
    }
    $.ajax({
        tradional: true,
        type: "post",
        datatype: "html",
        data:
                {
                    "material": material
                },
        url: "/MasonryCMS/masonryAdmin/queries/ajax/select-sub-materials.mdk",
        success: function (html)
        {
            $("#idSubMaterial").html(html).trigger("chosen:updated");
        },
        error: function (estado)
        {
            //   alert(estado);
        }
    });
}

function chargeTabs() {

    $('.nav-tabs > li a[title]').tooltip();

    //Wizard
    $('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
        var $target = $(e.target);
        if ($target.parent().hasClass('disabled') || accion === 2) {
            return false;
        }
    });

    $(".next-step").click(function (e) {
        var $active = $('.wizard .nav-tabs li.active');
        $active.next().removeClass('disabled');
        nextTab($active);
    });

    $(".prev-step").click(function (e) {
        var $active = $('.wizard .nav-tabs li.active');
        prevTab($active);
    });
}

function chargeCheckBoxes() {
    var arr = ["hasCorner", "canSellLayer", "active"];

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
    window.location = "/MasonryCMS/masonryAdmin/maintenance/product.mdk";
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
    $("#" + id).DataTable({
        "sPaginationType": "full_numbers",
        "iDisplayLength": 25,
        "bAutoWidth": true,
        "bProcessing": true,
        "bDeferRender": true,
        "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "Todo"]],
        "oLanguage":
                {
                    "sProcessing": "Cargando...",
                    "sLengthMenu": "Mostrar _MENU_ registros por p&aacute;gina",
                    "sZeroRecords": "No se encontr&oacute; ning&uacute;n registro",
                    "sInfo": "Mostrando de _START_ a _END_ de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando 0 registros",
                    "sInfoFiltered": "(Filtrado de _MAX_ total registros)",
                    "sInfoPostFix": "",
                    "sSearch": "Buscar",
                    "oPaginate":
                            {
                                "sFirst": "Primero",
                                "sPrevious": "Anterior",
                                "sNext": "Siguiente",
                                "sLast": "Final"
                            }
                },
        "sScrollX": "100%",
        "scrollCollapse": true
    });
}