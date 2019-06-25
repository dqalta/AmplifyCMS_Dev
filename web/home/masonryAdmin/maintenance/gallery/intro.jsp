<%-- 
    Document   : inicio
    Created on : 10-oct-2017, 15:13:01
    Author     : CR108002
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="st"%>
<%@ taglib uri="/struts-jquery-tags" prefix="stj"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="stb"%>
<!DOCTYPE html>
<html>
    <head>    
        <st:include value="/generals/css-js-Head.jsp" />
        <st:include value="/generals/css-js-Bootstrap.jsp"/>
        <st:include value="/generals/css-js-Datatables.jsp"/>
        <st:include value="/generals/css-js-Chosen.jsp"/>
        <st:include value="/generals/css-js-app.jsp"/>
        <script src="/MasonryCMS/home/masonryAdmin/maintenance/gallery/script.js" type="text/javascript"></script>       
        <link rel="stylesheet" type="text/css" href="/MasonryCMS/home/masonryAdmin/maintenance/gallery/style.css" media="screen" />
    </head>
    <body style="margin: 0px;">
        <st:if test="%{sesionActiva == true}"> 

            <st:hidden id="permiso" name="permiso" value="%{permiso}" />
            <st:hidden id="mensaje" name="mensaje" value="%{mensaje}" />
            <st:hidden id="mensajes" name="mensajes" value="%{mensajes}" />


            <st:include value="/generals/navBarHead.jsp" >
                <st:param name="title">Gallery</st:param>
            </st:include> 
            <st:if test="%{permiso == true}">              
                <br><br>

                <div class="panel-group">
                    <div class="panel panel-default">
                        <div class="panel-heading clickable">
                            <h4 class="panel-title">
                                <a><i class="glyphicon glyphicon-chevron-down"></i></a>
                            </h4>
                        </div>
                        <div id="form-panel" class="panel-collapse collapse">
                            <div class="panel-body">    
                                <ul class="nav nav-tabs">
                                    <li class="active"><a href="#maintenanceTab" data-toggle="tab" aria-expanded="true">Gallery</a></li>                  
                                </ul>
                                <div id="tabsContents" class="tab-content">
                                    <div class="tab-pane fade active in" id="maintenanceTab">    
                                        <div class="container-fluid">
                                            <st:form id="formulario" name="formulario" cssClass="form-vertical" action="gallery" method="post" theme="bootstrap"  enctype="multipart/form-data">                                        
                                                <st:hidden id="accion" name="accion" value="%{accion}"/>
                                                <st:hidden id="id" name="id" value="%{id}"/>
                                                <br>
                                                <div class="row">
                                                    <div class="col-sm-8">
                                                        <st:textfield label="Description:" name="description" id="description" class="form-control" value="%{description}" placeholder="Description..."/>
                                                    </div>
                                                    <!--starts the loader-->
                                                    <st:if test="%{existGallery == true}">
                                                        <div class="col-sm-4">
                                                            <div class="form-group">
                                                                <label class="control-label" for="fileDiv">Search Photo:</label>
                                                                <div class="input-group" id="fileDiv">
                                                                    <input type="text" class="form-control" id="input-name" disabled="disabled">
                                                                    <st:file id="photo" name="photo" class="file" accept="image/png,image/jpeg" multiple="true" />
                                                                    <span class="input-group-btn">
                                                                        <button type="button" class="btn btn-info" id="btn-clean" style="display:none;" data-toggle="tooltip" data-placement="top" data-original-title="Clear">
                                                                            <span class="glyphicon glyphicon-remove"></span>&nbsp;
                                                                        </button>
                                                                        <div id="btn-search" class="btn btn-default image-preview-input"  data-toggle="tooltip" data-placement="top" data-original-title="Search/Change">
                                                                            <span class="glyphicon glyphicon-folder-open"></span>&nbsp;
                                                                        </div>
                                                                        <button type="button" class="btn btn-danger" id="btn-upload" onclick="uploadPhotos()" style="display:none;"  data-toggle="tooltip" data-placement="top" data-original-title="Upload">
                                                                            <span class="glyphicon glyphicon-upload"></span>&nbsp;
                                                                        </button>
                                                                    </span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </st:if> 
                                                    <!--finish loader-->
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-4">
                                                        <div class="form-group">
                                                            <label for="manufacturer">Manufacturer:</label>
                                                            <st:select class="form-control"  id="manufacturer" multiple="true" name="manufacturer" value="%{manufacturer}" list="manufacturers" listKey="id" listValue="description"/>                                          
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-4"> 
                                                        <div class="form-group">
                                                            <label for="collection">Collections:</label>
                                                            <st:select class="form-control"  id="collection" multiple="true" name="collection" value="%{collection}" list="collections" listKey="id" listValue="description"/>                                          
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-4">                                                        
                                                        <div class="form-group">
                                                            <label for="size_">Sizes:</label>
                                                            <st:select class="form-control"  id="size_" multiple="true" name="size_" value="%{size_}" list="sizes" listKey="id" listValue="description"/>                                          
                                                        </div>
                                                    </div>
                                                    <br>
                                                </st:form>   
                                                <div class="btn-group pull-right">
                                                    <a class="btn btn-danger pull-right" onclick="save();"><i class="glyphicon glyphicon-ok"></i>&nbsp;Save</a>
                                                    <a class="btn btn-default pull-right" onclick="cancel();"><i class="glyphicon glyphicon-remove"></i>&nbsp;Cancel</a>
                                                </div>  
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="container-fluid">
                    <div class="row" id="galleryDiv">

                    </div>
                </div>
                <br>
                <br>
                <br>
                <br>
                <br>
                <br>
                <br> <div class="modal" id="ModalProcesando">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title"><i class="glyphicon glyphicon-folder-open"></i>&nbsp;&nbsp;Masonry CMS</h4>
                            </div>
                            <div class="modal-body">
                                <h4>Processing, Please wait...<i class="glyphicon glyphicon-repeat fast-right-spinner"></i></h4>
                            </div>
                        </div>
                    </div>
                </div>
            </st:if> 
            <st:else>
                <st:include value="/generals/permiso.jsp" />
            </st:else>

            <st:include value="/generals/navBarFooter.jsp" /> 
        </st:if>
        <st:else>
            <%response.sendRedirect("/MasonryCMS/");%>
        </st:else> 





    </body>
</html>
