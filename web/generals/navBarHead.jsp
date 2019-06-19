<script type="text/javascript">
    function LogOut() {
        $("#ModalLogOut").modal("hide");
        window.location = "/MasonryCMS/logOut.mdk";
    }
    function AskLogOut() {
        $("#ModalLogOut").modal();
    }
    $(document).keydown(function (event) {
        if (event.keyCode === 27) {
            $('#ModalLogOut').modal("hide");
        }
    });
</script>
<style>
    /* Toggle Styles */

    #wrapper {
        padding-left: 0;
        -webkit-transition: all 0.5s ease;
        -moz-transition: all 0.5s ease;
        -o-transition: all 0.5s ease;
        transition: all 0.5s ease;
    }

    #wrapper.toggled {
        padding-left: 250px;
    }

    #sidebar-wrapper {
        position: fixed;
        left: 250px;
        z-index: 1000;
        overflow-y: auto;
        margin-left: -250px;
        width: 0;
        height: 100%;
        background: #000;
        -webkit-transition: all 0.5s ease;
        -moz-transition: all 0.5s ease;
        -o-transition: all 0.5s ease;
        transition: all 0.5s ease;
    }

    #wrapper.toggled #sidebar-wrapper {
        width: 250px;
    }

    #page-content-wrapper {
        padding: 15px;
        width: 100%;
    }

    #wrapper.toggled #page-content-wrapper {
        position: absolute;
        margin-right: -250px;
    }

    /* Sidebar Styles */

    .sidebar-nav {
        position: absolute;
        top: 0;
        margin: 0;
        padding: 0;
        width: 250px;
        list-style: none;
    }

    .sidebar-nav li {
        text-indent: 20px;
        line-height: 40px;
    }

    .sidebar-nav li a {
        display: block;
        color: #999999;
        text-decoration: none;
    }

    .sidebar-nav li a:hover {
        background: rgba(255, 255, 255, 0.2);
        color: #fff;
        text-decoration: none;
    }

    .sidebar-nav li a:active,
    .sidebar-nav li a:focus {
        text-decoration: none;
    }

    .sidebar-nav > .sidebar-brand {
        height: 65px;
        font-size: 18px;
        line-height: 60px;
    }

    .sidebar-nav > .sidebar-brand a {
        color: #999999;
    }

    .sidebar-nav > .sidebar-brand a:hover {
        background: none;
        color: #fff;
    }

    @media (min-width: 768px) {
        #wrapper {
            padding-left: 250px;
        }

        #wrapper.toggled {
            padding-left: 0;
        }

        #sidebar-wrapper {
            width: 250px;
        }

        #wrapper.toggled #sidebar-wrapper {
            width: 0;
        }

        #page-content-wrapper {
            padding: 20px;
        }

        #wrapper.toggled #page-content-wrapper {
            position: relative;
            margin-right: 0;
        }
    }
</style>
<title>Masonry Admin CMS</title>
<link rel="shortcut icon" type="image/png" href="/Masonry/home/img/logo.png"/>
<%@ taglib uri="/struts-tags" prefix="st"%>  
<st:set var="title">${param.title}</st:set>


<div class="modal" id="ModalLogOut">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title"><i class="fa fa-question-circle-o"></i>&nbsp;&nbsp;Masonry CMS</h4>
            </div>
            <div class="modal-body">
                <h4>You really want to close the session?</h4>
            </div>
            <div class="modal-footer">
                <button type="button" style="background-color: #FF6600 !important; " class="btn btn-warning" onclick="LogOut();"><i class="glyphicon glyphicon-log-out"></i>&nbsp;Yes</button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><i class="glyphicon glyphicon-remove"></i>&nbsp;No</button>
            </div>
        </div>
    </div>
</div>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#menu-toggle" id="menu-toggle"><span class="glyphicon glyphicon-list" aria-hidden="true"></span></a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">

                <ul class="nav navbar-nav navbar-left">
                    <li><a style="color:#FF6600 !important;"><st:property value="%{title}"/></a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/MasonryCMS/home/home.mdk"><span style="color:#FF6600 !important;" class="glyphicon glyphicon-home" aria-hidden="true"></span> Home</a></li>
                <!--
                <li><a href="#"><span style="color:#FF6600 !important;" class="glyphicon glyphicon-question-sign" aria-hidden="true"></span> Messages </a></li>
                <li><a href="#"><span style="color:#FF6600 !important;" class="glyphicon glyphicon-question-sign" aria-hidden="true"></span> Help</a></li>
                -->
                <li><a href="#"><span style="color:#FF6600 !important;" class="fa fa-cog" aria-hidden="true"></span> Profile</a></li>
                <li><a onclick="AskLogOut();"><span style="color:#FF6600 !important;" class="glyphicon glyphicon-off" aria-hidden="true"></span> Log Out</a></li>
            </ul>
            <form class="navbar-form navbar-right" action="#" method="GET">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Search..." id="query" name="search" value="">
                    <div class="input-group-btn">
                        <button type="submit" class="btn btn-warning"><span class="glyphicon glyphicon-search"></span></button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</nav>

<div id="wrapper" class="toggled" style="overflow:hidden;">
    <div class="container-fluid">
        <!-- Sidebar -->
        <div id="sidebar-wrapper">
            <ul class="sidebar-nav">
                <li class="sidebar-brand">
                    <br>
                </li>
                <li class="sidebar-brand">
                    <a href="#" class="navbar-brand">
                        <span class="fa fa-user-circle-o" aria-hidden="true"></span>&nbsp;<span style="color:#FF6600 !important;"><st:property value="#attr.user" /></span>

                    </a>
                </li>
                <li>
                    <a href="/MasonryCMS/home/home.mdk"><span style="color:#FF6600 !important;" class="glyphicon glyphicon-home" aria-hidden="true"></span> Home</a>
                </li> 
                <li>
                    <a href="/MasonryCMS/home/home.mdk"><span style="color:#FF6600 !important;" class="fa fa-cog" aria-hidden="true"></span> Profile</a>
                </li> 
                <hr style="color:#FF6600 !important;">            
                <li>
                    <font color="#337AB7"> Administration</font>
                </li>
                <li>
                    <a href="/MasonryCMS/masonryAdmin/admin/user.mdk"><span style="color:#FF6600 !important;" class="fa fa-users" aria-hidden="true"></span> Users</a>
                </li>  
                <li>
                    <a href="/MasonryCMS/masonryAdmin/admin/rol.mdk"><span style="color:#FF6600 !important;" class="fa fa-id-card-o" aria-hidden="true"></span> Rols</a>
                </li> 
                <li>
                    <a href="/MasonryCMS/masonryAdmin/maintenance/vendor.mdk"><span style="color:#FF6600 !important;" class="glyphicon glyphicon-briefcase" class="fa fa-id-card-o" aria-hidden="true"></span> Vendor</a>
                </li>                                 
                <hr style="color:#FF6600 !important;">            
                <li>
                    <font color="#337AB7"> Product Components</font>
                </li>
                <li>
                    <a href="/MasonryCMS/masonryAdmin/maintenance/product.mdk"><span style="color:#FF6600 !important;" class="fa fa-archive" aria-hidden="true"></span> Product Main</a>
                </li>
                <li>
                <li>
                    <a href="/MasonryCMS/masonryAdmin/maintenance/collection.mdk"><span style="color:#FF6600 !important;" class="fa fa-tags" aria-hidden="true"></span> Collections</a>
                </li>
                <li>
                    <a href="/MasonryCMS/masonryAdmin/maintenance/material.mdk"><span style="color:#FF6600 !important;" class="fa fa-bookmark" aria-hidden="true"></span> Materials</a>
                </li>
                <li>
                    <a href="/MasonryCMS/masonryAdmin/maintenance/subMaterial.mdk"><span style="color:#FF6600 !important;" class="fa fa-bookmark-o" aria-hidden="true"></span> SubMaterials</a>
                </li>
                <hr style="color:#FF6600 !important;">
                <li>
                    <font color="#337AB7"> Product Administration</font>
                </li>
                <li>
                    <a href="/MasonryCMS/masonryAdmin/maintenance/manufacturer.mdk"><span style="color:#FF6600 !important;" class="fa fa-industry" aria-hidden="true"></span> Manufacturers</a>
                </li>
                <li>
                    <a href="/MasonryCMS/masonryAdmin/maintenance/packageType.mdk"><span style="color:#FF6600 !important;" class="fa fa-inbox" aria-hidden="true"></span> Package Types</a>
                </li>
                <li>
                    <a href="/MasonryCMS/masonryAdmin/maintenance/style.mdk"><span style="color:#FF6600 !important;" class="fa fa-paint-brush" aria-hidden="true"></span> Styles</a>
                </li>
                </li>
                <li>
                    <a href="/MasonryCMS/masonryAdmin/maintenance/size.mdk"><span style="color:#FF6600 !important;" class="glyphicon glyphicon-move" aria-hidden="true"></span> Sizes</a>
                </li>
                <li>
                    <a href="/MasonryCMS/masonryAdmin/maintenance/texture.mdk"><span style="color:#FF6600 !important;" class="glyphicon glyphicon-th" aria-hidden="true"></span> Textures</a>
                </li>
                <li>
                    <a href="/MasonryCMS/masonryAdmin/maintenance/color.mdk"><span style="color:#FF6600 !important;" class="glyphicon glyphicon-tint" aria-hidden="true"></span> Colors</a>                                      
                </li>
                <li>
                    <a href="/MasonryCMS/masonryAdmin/maintenance/metricsSystem.mdk"><span style="color:#FF6600 !important;" class="fa fa-balance-scale" aria-hidden="true"></span> Metrics Systems</a>                                      
                </li>
            </ul>
        </div>
        <!-- /#sidebar-wrapper -->

        <!-- Page Content -->
        <div id="page-content-wrapper">

