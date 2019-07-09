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
                <br><br><br>

                <div class="container-fluid">
                    <div class="row">
                        <div class="btn-group pull-left">
                            <a class="btn btn-warning pull-left" onclick="showDivAdd();"><i class="glyphicon glyphicon-plus"></i>&nbsp;New Gallery</a>
                        </div>
                    </div>
                </div>

                <div id="divAdd" class="panel panel-default">
                    <div class="container-fluid">
                        <st:form id="formulario" name="formulario" cssClass="form-vertical" action="gallery" method="post" theme="bootstrap"  enctype="multipart/form-data">                                        
                            <st:hidden id="accion" name="accion" value="%{accion}"/>
                            <st:hidden id="id" name="id" value="%{id}"/>
                            <st:hidden id="idEdit" name="idEdit" value="%{idEdit}"/>
                            <br>
                            <div class="row">
                                <div class="col-sm-8">
                                    <st:textfield label="Description:" name="description" id="description" class="form-control" value="%{description}" placeholder="Description..."/>
                                </div>
                                <!--starts the loader-->
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <label class="control-label" for="fileDiv">Search Photos:</label>
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
                                                <button type="button" class="btn btn-danger" id="btn-upload" onclick="save()" style="display:none;"  data-toggle="tooltip" data-placement="top" data-original-title="Upload">
                                                    <span class="glyphicon glyphicon-upload"></span>&nbsp;
                                                </button>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <!--finish loader-->
                            </div>
                            <div class="row">
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <label for="manufacturer">Manufacturer:</label>
                                        <st:select class="form-control"  id="manufacturer" multiple="true" name="manufacturer" value="%{manufacturer}" list="manufacturers" listKey="id" listValue="description" onChange="chargeCollections();"/>                                          
                                    </div>
                                </div>
                                <div class="col-sm-4"> 
                                    <div class="form-group">
                                        <label for="collection">Collections:</label>
                                        <st:select class="form-control"  id="collection" multiple="true" name="collection" value="%{collection}" list="collections" listKey="id" listValue="description"/>                                          
                                    </div>
                                </div>
                                <br>
                            </st:form>   
                            <div class="pull-right">
                                <a class="btn btn-warning pull-right" onclick="save();"><i class="glyphicon glyphicon-ok"></i>&nbsp;Save</a>
                                <a class="btn btn-default pull-right" onclick="cancel();"><i class="glyphicon glyphicon-remove"></i>&nbsp;Cancel</a>
                            </div>  
                            <br>
                            <br>
                        </div>
                    </div>
                </div>

                <div class="container-fluid">
                    <h4 id="titleAdd" style="display:none;">Photos for add</h4>
                    <div id="galleryDiv" class="row"> </div>
                </div>

                <div class="container-fluid"> 
                    <st:if test="%{!getGalleriesPhotos().isEmpty()}">
                        <hr>
                        <h4>Saved Photos</h4>
                    </st:if>  
                    <div id="galleryDivSee" class="row">
                        <st:if test="%{!getGalleriesPhotos().isEmpty()}">
                            <st:iterator value="GalleriesPhotos" var="GalleriesPhotos" status="index">
                                <div class="col-sm-2 imgUp" data-toggle="tooltip" data-placement="top" data-original-title="<st:property value="%{#GalleriesPhotos.photoFileName}" />" id="file_<st:property value="%{#GalleriesPhotos.id}" />">
                                    <div class="imagePreview" style="background-image:url(<st:property value="%{#GalleriesPhotos.photo}" />)"></div>
                                    <label class="btn btn-warning  btn-upload"><st:property value="%{#GalleriesPhotos.photoFileName}" /></label>
                                    <i class="fa fa-times del" onclick="deletePhoto('<st:property value="%{#GalleriesPhotos.id}" />');"></i>
                                </div>
                            </st:iterator>
                        </st:if>
                    </div>
                </div>

                <div class="container-fluid">
                    <div id="divGalleryGroups">
                        <br>
                        <h4>Gallery List</h4>
                        <p>Click to gallery group to manage the photos.</p>
                        <hr>
                        <div class="container_card">  
                            <div class="row">

                                <st:if test="%{!getGalleries().isEmpty()}">
                                    <st:iterator value="Galleries" var="Galleries" status="index"> 
                                        <div class="col-sm-3">
                                            <div class="card">
                                                <img src="<st:property value="%{#Galleries.photo}"/>" alt="Photo" style="width:100%">
                                                <div class="container-card">
                                                    <h4><b><st:property value="%{#Galleries.description}" /></b></h4> 
                                                    <p><st:property value="%{#Galleries.quantity}" /> photos...</p> 
                                                    <p style="color:#CCC;">Last updated  <st:date name="%{#Galleries.modified}" format="dd/MM/yyyy" nice="true"/></p>
                                                    <hr>
                                                    <div class="pull-right">
                                                        <a class="btn btn-warning pull-right" onclick="showGallery('<st:property value="%{#Galleries.id}" />');">&nbsp;Show more</a>
                                                    </div>  
                                                    <br>
                                                    <br>
                                                </div>
                                            </div>
                                        </div>

                                    </st:iterator>
                                </st:if><st:else>
                                    <p>No available galleries to show.</p>
                                </st:else>

                                <!--
                                <div class="cardList" onclick="showGallery(1);">
                                    <div class="card">
                                        <div class="card__bg" style="background-image: url('http://localhost:8080/MasonryCMS/home/img/ladrillo2.jpeg')"></div>
                                    </div>
                                    <div class="card">
                                        <div class="card__bg" style="background-image: url('http://localhost:8080/MasonryCMS/home/img/ladrillo1.jpeg')"></div>
                                    </div>
                                    <div class="card">
                                        <div class="card__bg" style="background-image: url('http://localhost:8080/MasonryCMS/home/img/ladrillo3.jpeg')"></div>
                                    </div>
                                    <span class="cardList__title">Brampton Brick<b>/</b>Historic Series</span>
                                </div>
                                <div class="cardList" onclick="showGallery(2);">
                                    <div class="card">
                                        <div class="card__bg" style="background-image: url('http://localhost:8080/MasonryCMS/home/img/ladrillo3.jpeg')"></div>
                                    </div>
                                    <div class="card">
                                        <div class="card__bg" style="background-image: url('http://localhost:8080/MasonryCMS/home/img/ladrillo1.jpeg')"></div>
                                    </div>
                                    <div class="card">
                                        <div class="card__bg" style="background-image: url('http://localhost:8080/MasonryCMS/home/img/ladrillo2.jpeg')"></div>
                                    </div>
                                    <span class="cardList__title">Rinox<b>/</b>Mediterranean Series</span>
                                </div>
                                <div class="cardList" onclick="showGallery(3);">
                                    <div class="card">
                                        <div class="card__bg" style="background-image: url('http://localhost:8080/MasonryCMS/home/img/ladrillo3.jpeg')"></div>
                                    </div>
                                    <div class="card">
                                        <div class="card__bg" style="background-image: url('http://localhost:8080/MasonryCMS/home/img/ladrillo2.jpeg')"></div>
                                    </div>
                                    <div class="card">
                                        <div class="card__bg" style="background-image: url('http://localhost:8080/MasonryCMS/home/img/ladrillo1.jpeg')"></div>
                                    </div>
                                    <span class="cardList__title">Quickcrete<b>/</b>Shadow Stone</span>
                                </div>
                                <div class="cardList" onclick="showGallery(4);">
                                    <div class="card">
                                        <div class="card__bg" style="background-image: url('http://localhost:8080/MasonryCMS/home/img/ladrillo3.jpeg')"></div>
                                    </div>
                                    <div class="card">
                                        <div class="card__bg" style="background-image: url('http://localhost:8080/MasonryCMS/home/img/ladrillo2.jpeg')"></div>
                                    </div>
                                    <div class="card">
                                        <div class="card__bg" style="background-image: url('http://localhost:8080/MasonryCMS/home/img/ladrillo1.jpeg')"></div>
                                    </div>
                                    <span class="cardList__title">Quickcrete<b>/</b>Shadow Stone</span>
                                </div>
                                -->
                            </div>
                        </div>
                    </div>
                </div>


                <br>
                <br>
                <br>
                <br> 
                <div class="modal" id="ModalProcesando">
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
