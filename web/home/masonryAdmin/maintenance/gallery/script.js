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
        files();
        $("#manufacturer").chosen({width: "100%"});
        $("#size_").chosen({width: "100%"});
        $("#collection").chosen({width: "100%"});
    }
    if (mensaje === "true") {
        mostrarNotificaciones();
    }
    $('#form-panel').collapse();
});

function cancel() {
    $("#ModalProcesando").modal({backdrop: 'static', keyboard: false});
    window.location = "/MasonryCMS/masonryAdmin/maintenance/materials.mdk";
}

function save() {
    $("#ModalProcesando").modal({backdrop: 'static', keyboard: false});
    $("#accion").val(1);
    $("#formulario").submit();
}


function uploadPhotos() {
    $("#ModalProcesando").modal({backdrop: 'static', keyboard: false});
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
function files() {
    $(document).on("click", "i.del", function () {
        $(this).parent().remove();
    });
// Limpiar Archivo
    $('#btn-clean').click(function () {
        $('#input-name').val("");
        $('#btn-clean').hide();
        $("#btn-upload").hide();
        $("#galleryDiv").html("");
    });
// Buscar Archivo
    $('#btn-search').click(function () {
        $('#photo').click();
    });
// Función cuando carga el archivo
    if (window.File && window.FileList && window.FileReader)
    {
        $("#photo").change(function () {
            $("#galleryDiv").html("");
            var filesArray = this.files;
            console.log(this.files.length);
            $("#btn-clean").show();
            $("#btn-upload").show();
            if (this.files.length > 1) {
                $("#input-name").val("Photos selected [ " + this.files.length + " ]");
            } else {
                var file = this.files[0];
                $("#input-name").val(file.name);
            }
            var html = "";
            for (var i = 0; i < this.files.length; i++) {
                var file_ = this.files[i];
                var reader = new FileReader();
                reader.name = file_.name;
                reader.idFile = i;
                reader.onload = function (e) {
                    html = "<div class=\"col-sm-2 imgUp\" id=\"file" + e.target.idFile + "\"> <div class=\"imagePreview\" style=\"background-image:url(" + e.target.result + ")\"></div>   <label class=\"btn btn-warning  btn-upload\">" + e.target.name + "</label></div>";
                    $("#galleryDiv").append(html);
                }
                reader.readAsDataURL(file_);
            }
        });
    } else {
        alert("Your browser does not support File API");
    }
}

