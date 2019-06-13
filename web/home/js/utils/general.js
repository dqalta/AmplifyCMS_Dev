/*
 * Funciones que se usan en todas las pantallas 
 */



$(document).on('click', '.clickable', function (e) {
    var $this = $(this);
    if (!$this.hasClass('panel-collapsed')) {
        $this.parents('.panel').find('.panel-body').slideUp(1000);
        $this.addClass('panel-collapsed');
        $this.find('i').removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
    } else {
        $this.parents('.panel').find('.panel-body').slideDown(1000);
        $this.removeClass('panel-collapsed');
        $this.find('i').removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
    }
});

function archivo() {
// Limpiar Archivo
    $('#btn-limpiar').click(function () {
        $('#input-nombre').val("");
        $('#btn-limpiar').hide();
        $("#btn-subir").hide();
    });
// Buscar Archivo
    $('#btn-buscar').click(function () {
        $('#archivo').click();
    });
// Función cuando carga el archivo
    $("#archivo").change(function () {
        var file = this.files[0];
        $("#btn-limpiar").show();
        $("#btn-subir").show();
        $("#input-nombre").val(file.name);
    });
}

function copyToClipboard(text) {
    var dummy = document.createElement("input");
    dummy.style.display = 'none';
    document.body.appendChild(dummy);
    dummy.value = text;
    dummy.select();
    document.execCommand("copy");
    document.body.removeChild(dummy);
}

function scroll() {
    $('[data-toggle="tooltip"]').tooltip({
        container: 'body'
    });

    $('.ir-arriba').click(function () {
        $('body, html').animate({
            scrollTop: '0px'
        }, 300);
    });

    $(window).scroll(function () {
        if ($(this).scrollTop() > 0) {
            $('.ir-arriba').slideDown(300);
        } else {
            $('.ir-arriba').slideUp(300);
        }
    });

    $("ul.chosen-choices").css({"overflow": "auto", "max-height": "100px"});
}

function mostrarNotificaciones() {
    var arrayError = [];
    if (!mensajes.includes("|")) {
        arrayError[0] = mensajes;
    } else {
        mensajes = mensajes.slice(0, -1);
        arrayError = mensajes.split("|");
    }
    var error = [];
    for (var i = 0; i < arrayError.length; i++) {
        error = arrayError[i].split("<>");
        $.notify({
            message: "<strong>" + error[1] + ": </strong>" + error[2]
        }, {
            type: error[0],
            allow_dismiss: true,
            position: null,
            placement: {
                from: "bottom",
                align: "center"
            },
            delay: 2000,
            timer: 1000 + i * 200,
            mouse_over: "pause",
            z_index: 2000
        });
    }
    $("#mensajes").val("");
    $("#mensaje").val(false);
}