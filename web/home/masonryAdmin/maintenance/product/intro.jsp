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
        <st:include value="/generals/css-js-app.jsp"/>
        <st:include value="/generals/css-js-LcSwitch.jsp"/>
        <st:include value="/generals/css-js-JqueryNumber.jsp"/>
        <st:include value="/generals/css-js-Chosen.jsp"/>
        <link rel="stylesheet" type="text/css" href="/MasonryCMS/home/masonryAdmin/maintenance/product/style.css" media="screen" />
        <script src="/MasonryCMS/home/masonryAdmin/maintenance/product/script.js" type="text/javascript"></script>        
    </head>
    <body style="margin: 0px;">
        <st:if test="%{sesionActiva == true}">     
            <st:hidden id="permiso" name="permiso" value="%{permiso}" />
            <st:hidden id="mensaje" name="mensaje" value="%{mensaje}" />
            <st:hidden id="mensajes" name="mensajes" value="%{mensajes}" />
            <st:include value="/generals/navBarHead.jsp" >
                <st:param name="title">PRODUCTS</st:param>
            </st:include>
            <st:if test="%{permiso == true}">  
                <st:form id="formulario" name="formulario" cssClass="form-vertical" action="product" method="post" theme="bootstrap">                                        
                    <st:hidden id="accion" name="accion" value="%{accion}"/>
                    <st:hidden id="idEdit" name="idEdit" value="%{idEdit}"/>

                    <div class="container">

                        <section>
                            <div class="wizard">
                                <div class="wizard-inner">
                                    <div class="connecting-line"></div>
                                    <ul class="nav nav-tabs" role="tablist">

                                        <li role="presentation" class="active">
                                            <a href="#step1" data-toggle="tab" data-value="GeneralInformation" aria-controls="step1" role="tab" title="General Information.">
                                                <span class="round-tab">
                                                    <i class="fa fa-pencil"></i>
                                                </span>
                                            </a>
                                        </li>

                                        <li role="presentation" class="disabled">
                                            <a href="#step2" data-toggle="tab" data-value="Mes" aria-controls="step2" role="tab" title="Features.">
                                                <span class="round-tab">
                                                    <i class="fa fa-cogs"></i>
                                                </span>
                                            </a>
                                        </li>
                                        <li role="presentation" class="disabled">
                                            <a href="#step3" data-toggle="tab" data-value="Fecha" aria-controls="step3" role="tab" title="Categories.">
                                                <span class="round-tab">
                                                    <i class="fa fa-sitemap"></i>
                                                </span>
                                            </a>
                                        </li>
                                        <li role="presentation" class="disabled">
                                            <a href="#step4" data-toggle="tab" data-value="Dimensions" aria-controls="step4" role="tab" title="Dimensions.">
                                                <span class="round-tab">
                                                    <i class="fa fa-arrows"></i>
                                                </span>
                                            </a>
                                        </li>
                                        <li role="presentation" class="disabled">
                                            <a href="#complete" data-toggle="tab" data-value="Final" aria-controls="complete" role="tab" title="Complete Process.">
                                                <span class="round-tab">
                                                    <i class="glyphicon glyphicon-ok"></i>
                                                </span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>


                                <div class="tab-content">
                                    <div class="tab-pane active" role="tabpanel" id="step1">
                                        <h4 style="color:#FF6600 !important;">General Information</h4>
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <st:textfield label="SKU:" name="sku" id="sku" class="form-control" value="%{sku}" placeholder="SKU..."/>
                                            </div>
                                            <div class="col-sm-4">                                                      
                                                <div class="form-group"  style="overflow: visible !important;">
                                                    <label for="idManufacturer">Manufacturer:</label>
                                                    <st:select class="form-control"  id="idManufacturer" name="idManufacturer" value="%{idManufacturer}" list="manufacturers" listKey="id" listValue="description"/>                                          
                                                </div>
                                            </div>
                                            <div class="col-sm-4">                                                      
                                                <div class="form-group">
                                                    <label for="collection">Collections:</label>
                                                    <st:select class="form-control"  id="collection" multiple="true" name="collection" value="%{collection}" list="collections" listKey="id" listValue="description"/>                                          
                                                </div>
                                            </div>
                                            <div class="col-sm-1">
                                                <div class="form-group">
                                                    <label for="check_hasCorner">Active:</label>
                                                    <st:hidden id="active" name="active" value="%{active}" />
                                                    <st:checkbox class="lcc" theme="simple" name="check_active" id="check_active" fieldValue="%{active}" />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-6">
                                                <st:textfield label="Name:" name="pname" id="pname" class="form-control" value="%{pname}" placeholder="Name..."/>
                                            </div>
                                            <div class="col-sm-6">
                                                <st:textfield label="Description:" name="description" id="description" class="form-control" value="%{description}" placeholder="Description..."/>
                                            </div>
                                        </div>
                                        <ul class="list-inline pull-right">
                                            <li><button onclick="cancelar();" type="button" class="btn btn-default"><i class="glyphicon glyphicon-remove"></i> Cancel</button></li>
                                            <li><button onclick="validateStep1();" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-arrow-right"></i> Next</button></li>
                                        </ul>
                                    </div>
                                    <div class="tab-pane" role="tabpanel" id="step2">                                            
                                        <h4 style="color:#FF6600 !important;">General Information</h4>
                                        <div class="row">
                                            <div class="col-sm-3">                                                      
                                                <div class="form-group">
                                                    <label for="idStyle">Style:</label>
                                                    <st:select class="form-control"  id="idStyle" name="idStyle" value="%{idStyle}" list="styles" listKey="id" listValue="description"/>                                          
                                                </div>
                                            </div>
                                            <div class="col-sm-3">                                                      
                                                <div class="form-group">
                                                    <label for="idTexture">Texture:</label>
                                                    <st:select class="form-control"  id="idTexture" name="idTexture" value="%{idTexture}" list="textures" listKey="id" listValue="description"/>                                          
                                                </div>
                                            </div>
                                            <div class="col-sm-4">                                                      
                                                <div class="form-group">
                                                    <label for="idPackageType">Package Type:</label>
                                                    <st:select class="form-control"  id="idPackageType" name="idPackageType" value="%{idPackageType}" list="packageTypes" listKey="id" listValue="description"/>                                          
                                                </div>
                                            </div> 
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-3">                                                      
                                                <div class="form-group">
                                                    <label for="idSize">Size:</label>
                                                    <st:select class="form-control"  id="idSize" name="idSize" value="%{idSize}" list="sizes" listKey="id" listValue="description"/>                                          
                                                </div>
                                            </div>
                                            <div class="col-sm-4">                                                      
                                                <div class="form-group">
                                                    <label for="color">Colors:</label>
                                                    <st:select class="form-control"  id="color" multiple="true" name="color" value="%{color}" list="colors" listKey="id" listValue="description"/>                                          
                                                </div>
                                            </div>
                                            <div class="col-sm-2">
                                                <div class="form-group">
                                                    <label for="check_hasCorner">Has Corner:</label>
                                                    <st:hidden id="hasCorner" name="hasCorner" value="%{hasCorner}" />
                                                    <st:checkbox class="lcc" theme="simple" name="check_hasCorner" id="check_hasCorner" fieldValue="%{hasCorner}" />
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class="form-group">
                                                    <label for="check_canSellLayer">Can Sell Layer:</label>
                                                    <st:hidden id="canSellLayer" name="canSellLayer" value="%{canSellLayer}" />
                                                    <st:checkbox class="lcc" theme="simple" name="check_canSellLayer" id="check_canSellLayer" fieldValue="%{canSellLayer}" />
                                                </div>
                                            </div>
                                        </div>
                                        <ul class="list-inline pull-right">
                                            <li><button type="button" class="btn btn-default prev-step"><i class="glyphicon glyphicon-arrow-left"></i> Previous</button></li>
                                            <li><button type="button" onclick="validateStep2();"  class="btn btn-warning"><i class="glyphicon glyphicon-arrow-right"></i> Next</button></li>
                                        </ul>
                                    </div>
                                    <div class="tab-pane" role="tabpanel" id="step3">                      
                                        <h4 style="color:#FF6600 !important;">Categories</h4>
                                        <div class="row">
                                            <div class="col-sm-3">                                                      
                                                <div class="form-group">
                                                    <label for="idMaterial">Material:</label>
                                                    <st:select class="form-control"  id="idMaterial" name="idMaterial" value="%{idMaterial}" list="materials" listKey="id" listValue="description" onchange="chargeSubMaterials();"/>                                          
                                                </div>
                                            </div>
                                            <div class="col-sm-3">                                                      
                                                <div class="form-group">
                                                    <label for="idSubMaterial">Sub Material:</label>
                                                    <st:select class="form-control"  id="idSubMaterial" name="idSubMaterial" value="%{idSubMaterial}" list="subMaterials" listKey="id" listValue="description"/>                                          
                                                </div>
                                            </div>
                                        </div>

                                        <ul class="list-inline pull-right">
                                            <li><button type="button" class="btn btn-default prev-step"><i class="glyphicon glyphicon-arrow-left"></i> Previous</button></li>
                                            <li><button onclick="validateStep3();" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-arrow-right"></i> Next</button></li>
                                        </ul>
                                    </div>
                                    <div class="tab-pane" role="tabpanel" id="step4">
                                        <h3>Dimensions</h3>
                                        <div class="row">                                                
                                            <div class="col-sm-3">
                                                <st:textfield label="Pallet Weigth:" name="palletWeight" id="palletWeight" class="form-control" value="%{palletWeight}" placeholder="Pallet Weight..."/>
                                            </div>                                               
                                            <div class="col-sm-3">
                                                <st:textfield label="Units Pallet: " name="unitsPallet" id="unitsPallet" class="form-control" value="%{unitsPallet}" placeholder="Units Pallet..."/>
                                            </div>                                               
                                            <div class="col-sm-3">
                                                <st:textfield label="Layers Pallet:" name="layersPallet" id="layersPallet" class="form-control" value="%{layersPallet}" placeholder="Layers Pallet..."/>
                                            </div>                                               
                                            <div class="col-sm-3">
                                                <st:textfield label="Units Layer:" name="unitsLayer" id="unitsLayer" class="form-control" value="%{unitsLayer}" placeholder="Units Layer..."/>
                                            </div> 
                                        </div>
                                        <div class="row">                                               
                                            <div class="col-sm-3">
                                                <st:textfield label="Linear Feet Corner:" name="linearFeetCorner" id="linearFeetCorner" class="form-control" value="%{linearFeetCorner}" placeholder="Linear Feet Corner..."/>
                                            </div>                                               
                                            <div class="col-sm-3">
                                                <st:textfield label="Square Feet Per Package Type:" name="sqftPerPackageType" id="sqftPerPackageType" class="form-control" value="%{sqftPerPackageType}" placeholder="Square Feet Per Package Type..."/>
                                            </div>                                            
                                            <div class="col-sm-3">
                                                <st:textfield label="Quantity Of Units Per Package Type:" name="qtyOfUnitsPerPackageType" id="qtyOfUnitsPerPackageType" class="form-control" value="%{qtyOfUnitsPerPackageType}" placeholder="Quantity Of Units Per Package Type..."/>
                                            </div>
                                        </div>
                                        <ul class="list-inline pull-right">
                                            <li><button type="button" class="btn btn-default prev-step"><i class="glyphicon glyphicon-arrow-left"></i> Previous</button></li>
                                            <li><button onclick="validateStep4();" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-ok"></i> Save</button></li>
                                        </ul>
                                    </div>
                                    <div class="tab-pane" role="tabpanel" id="complete">
                                        <h3>Process complete</h3>
                                        <p>Product created successfully</p>
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <st:textfield label="Slug:" name="slug" id="slug" class="form-control" value="%{slug}" placeholder="SLUG..."/>
                                            </div>
                                        </div>
                                        <button onclick="cancelar();" type="button" class="btn btn-primary"><i class="glyphicon glyphicon-ok"></i> Ok</button>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>

                <br>
            </st:form>  
                <hr>
                <br>
                <br>
            <div style="padding: 20px;" class="table-responsive">
                <table id="table_product" class="table table-striped" style="width:100%; margin: 0px auto;">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Description</th>
                            <th>Created</th>
                            <th>Created By</th>
                            <th>Modified</th>
                            <th>Modified By</th>
                            <th>Active</th>
                            <th>Edit</th>
                        </tr>
                    </thead>
                    <tbody>
                           <st:if test="%{!getProducts().isEmpty()}">
                               <st:iterator value="products" var="products" status="index">
                                   <tr>
                                       <td><st:property value="%{#products.id}" /></td>           
                                       <td><st:property value="%{#products.pname}" /></td>        
                                       <td><st:date name="%{#products.created}" format="dd/MM/yyyy"/></td>       
                                       <td><st:property value="%{#products.createdBy}" /></td>    
                                       <td><st:date name="%{#products.modified}" format="dd/MM/yyyy"/></td>    
                                       <td><st:property value="%{#products.modifiedBy}" /></td>       
                                       <td>
                                           <st:if test="%{#products.active == true}">
                                               <i class="glyphicon glyphicon-off text-success" data-toggle="tooltip" data-placement="top" title="" data-original-title="Active"</i>
                                           </st:if>
                                           <st:else>
                                               <i class="glyphicon glyphicon-off text-danger"  data-toggle="tooltip" data-placement="top" title="" data-original-title="Inactive"></i>
                                           </st:else>
                                       </td>       
                                       <td onclick="edit('<st:property value="%{#products.id}" />');"><i class="glyphicon glyphicon-pencil" data-toggle="tooltip" data-placement="left" title="" data-original-title="Edit Row"></i></td>
                                   </tr>
                               </st:iterator>
                           </st:if>
                    </tbody>
                </table>
            </div>
            

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