
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%--<c:if test="principal.isUserInRole('manager')">
    <c:redirect url="/admin.jsp" />
</c:if>--%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- Site made with Mobirise Website Builder v2.9, https://mobirise.com -->
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="generator" content="Mobirise v2.9, mobirise.com">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" href="../assets/images/untitled-382x276-65.png" type="image/x-icon">
        <meta name="description" content="">
        <title>AgriMarket</title>

        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:700,400&amp;subset=cyrillic,latin,greek,vietnamese">
        <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css" />
        <link rel="stylesheet" href="../assets/animate.css/animate.min.css" type="text/css"/>
        <link rel="stylesheet" href="../assets/mobirise/css/style.css" type="text/css"/>
        <link rel="stylesheet" href="../assets/mobirise-gallery/style.css" />
        <link rel="stylesheet" href="../assets/mobirise-slider/style.css" />
        <link rel="stylesheet" href="../assets/mobirise/css/mbr-additional.css" type="text/css">
        <link rel="stylesheet" href="../assets/mobirise/css/popup.css" type="text/css">
        <link rel="stylesheet" href="../assets/mobirise/css/main.css" type="text/css">


        <script src="../assets/web/assets/jquery/jquery.min.js"></script>
        <style>
            a{
                text-decoration: none;
            }
        </style>
        <script type="text/javascript">
            
            var cl = 0;
            $(document).ready(function () {
                
                $.ajax({
                    url: "/AgriMarket/gategories",
                    type: 'GET',
                    contentType: 'application/json',
                    dataType: 'json',
                    success: function (data) {
                        $.each(data, function (i, category) {
                             $('.pcategory').append('<option   value=' + category.id + '>' + category.name + '</option>');
//                        location.reload();   
                        });
                    }
                });

                $("#edit-form .modify-product").click(function (event) {
                    
                    var pName = event.target.id;
                    
                      
                    $.ajax({
                        url: "modifyproduct",
                        type: 'GET',
                        contentType: 'application/json',
                        dataType: 'json',
                        data: {productName: pName},
                        success: function (data) {
                             $("#product-name-modify").val(data.name);
                            $("#hidden-input").val(data.name);
                            $("#price-modify").val(data.price);
                            $("#quantity-modify").val(data.quantity);
                           
                            document.getElementById("rdoSelect").selectedIndex = data.categoryId;
                            
                            $(".category").val(data.categoryId);
                            $("#dec-modify").val(data.desc);
                        }


                    });
                });
//            $("a").click(function (event) {
//
            });


        </script>

    </head>
    <body>

        <%--<jsp:include page="header.jsp"/>--%>

        <section class="mbr-box mbr-section mbr-section--relative mbr-section--fixed-size mbr-section--full-height mbr-section--bg-adapted mbr-parallax-background mbr-after-navbar" id="header1-40" style="background-image: url(../assets/images/istock-000016896298xlarge-4200x2833-32.jpg);">
            <div class="mbr-box__magnet mbr-box__magnet--sm-padding mbr-box__magnet--center-left">
                <div class="mbr-overlay" style="opacity: 0.2; background-color: rgb(34, 34, 34);"></div>
                <div class="mbr-box__container mbr-section__container container">
                    <div class="mbr-box mbr-box--stretched"><div class="mbr-box__magnet mbr-box__magnet--center-left">
                            <div class="row"><div class=" col-sm-6 col-sm-offset-6">
                                    <div class="mbr-hero animated fadeInUp">
                                        <h1 class="mbr-hero__text">AGRIMARKET</h1>
                                        <p class="mbr-hero__subtext">Agricultural Market in Egypt</p>
                                    </div>
                                    <div class="mbr-buttons btn-inverse mbr-buttons--left"><a class="mbr-buttons__btn btn btn-lg animated fadeInUp delay btn-primary" href="#accordion">PRODUCT MANAGMENT</a> 
                                        <a class="mbr-buttons__btn btn btn-lg animated fadeInUp delay btn-info" href="admin_user_managment.jsp">USER MANAGMENT</a></div>
                                </div></div>
                        </div></div>
                </div>
                <div class="mbr-arrow mbr-arrow--floating text-center">
                    <div class="mbr-section__container container">
                        <a class="mbr-arrow__link" href="#header3-41"><i class="glyphicon glyphicon-menu-down"></i></a>
                    </div>
                </div>
            </div>
        </section>

        <section class="mbr-section" id="header3-41">
            <div class="mbr-section__container container mbr-section__container--first">
                <div class="mbr-header mbr-header--wysiwyg row">
                    <div class="col-sm-8 col-sm-offset-2">
                        <h3 class="mbr-header__text" style="    margin-left: 180px;">Product Management</h3>

                    </div>
                </div>
            </div>
        </section>

        <section class="mbr-gallery mbr-section mbr-section--no-padding" id="gallery2-38">
            <!-- Gallery -->
            <div class="container mbr-section__container mbr-gallery-layout-article mbr-section__container--last">


                <div class="panel-group" id="accordion">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapse1">
                                    ADD NEW Category</a>
                            </h4>
                            <span class="add-form" style="    font-size: 19px;"></span>
                        </div>
                        <div id="collapse1" class="panel-collapse collapse ">
                            <div class="panel-body"> 
                                <div class="container" style="margin-left: -89px;">

                                    <form class="form-horizontal"    method="post" action="/AgriMarket/gategories" >

                                        <span style="color: red;font-size: 19px;margin-left: 202px;">${param["statuscat"]}</span>
                                        <span style="color: black;margin-left: -8px;font-size: 19px;">${param["successcat"]}</span>
                                        <div class="form-group">
                                            <label class="control-label col-sm-2" for="name">Category Name</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control"  name="categoryName" id="name" placeholder="product name" required>
                                            </div>
                                        </div>
                                        <div class="form-group">        
                                            <div class="col-sm-offset-2 col-sm-10">
                                                <button   class="btn btn-default" id="add">ADD</button>
                                            </div>

                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapse2">
                                    ADD NEW PRODUCT</a>
                            </h4>
                        <span class="add-form" style="    font-size: 19px;"></span>
                        </div>
                        <div id="collapse2" class="panel-collapse collapse">
                            <div class="panel-body"> 
                                <div class="container" style="margin-left: -89px;">

                                    <form class="form-horizontal" enctype="MULTIPART/FORM-DATA"  role="form" method="post" action="addproduct" onsubmit="return validateProductForm()">

                                        <span style="color: red;font-size: 19px;margin-left: 202px;">${param["status"]}</span>
                                        <span style="color: black;margin-left: -8px;font-size: 19px;">${param["success"]}</span>
                                        <div class="form-group">
                                            <label class="control-label col-sm-2" for="name">Product Name</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" value="${param.name}" name="name" id="name" placeholder="product name" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-2" for="price">Price</label>
                                            <div class="col-sm-10">          
                                                <input type="text"   class="form-control" name="price" id="price" placeholder="price $" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-2" for="categoryId">Category:</label>
                                            <div class="col-sm-10">          
                                                <label class="category-label">
                                                    <select   class="pcategory" name="pcategory" required>
                                                        <option selected>Select you category</option>

                                                    </select>
                                                </label>
                                                <!--</div>-->
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-2" for="quantity">Quantity</label>
                                            <div class="col-sm-10">          
                                                <input type="text" class="form-control" id="quantity" name="quantity" placeholder="quantity" required>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-sm-2" for="image" >Image</label>
                                            <div class="col-sm-10">          
                                                <span class="btn btn-default btn-file">
                                                    Browse <input type="file"   name="image"  />
                                                </span>                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-2" for="desc">Description</label>
                                            <div class="col-sm-10">          
                                                <textarea class="form-control" rows="5" cols="109" id="desc" name="desc" required></textarea>
                                            </div>
                                        </div>

                                        <div class="form-group">        
                                            <div class="col-sm-offset-2 col-sm-10">
                                                <button   class="btn btn-default" id="add">ADD</button>
                                            </div>

                                        </div>
                                    </form>
                                </div></div>
                        </div>
                    </div>
                    <div class="panel panel-default " id="product-div">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapse3">
                                    View Products</a>
                            </h4>
                        </div>
                        <div id="collapse3" class="panel-collapse collapse in">
                            <div class="panel-body">
                                <div class=" col-sm-12 col-sm-offset-2" style="    margin-left: 3px;">
                                    <!--<div class="row mbr-gallery-row">-->
                                          <c:if test="${applicationScope.products == null}">
                                        <c:redirect url="getProducts" />
                                    </c:if>
                                    <c:forEach items="${products}" var="product" >

                                        <div class="col-lg-2 col-md-4 col-sm-6 col-xs-12 mbr-gallery-item">
                                            <span class="productName">${product.name}</span>
                                            <a id='+${product.name}+' href="#lb-gallery2-38" data-slide-to="0" data-toggle="modal">
                                                <img width="550" height="240"
                                                     style="border: 1.1px solid #2969b0;
                                                     border-bottom: none;"  src="${pageContext.request.contextPath}/images/${product.name}">
                                                <span class="icon glyphicon glyphicon-zoom-in">${product.name}</span>
                                            </a>
                                            <div class="container" style="margin-left: -13px;margin-top: 3px;">
                                                <form action="addproduct" method="get" id="edit-form">
                                                    <a   class="modify-product"   style="cursor: pointer"><span id='${product.name}' class="glyphicon glyphicon-edit" >Modify</span></a>
                                                    <a  href="javascript:;" onclick="parentNode.submit()
                                                                ;" class="delete-product" href="#" ><span class="glyphicon glyphicon-remove"  style="margin-left: 24px;">Delete</span></a>
                                                    <input type="hidden"   value="${product.name}" name="product_Name">
                                                </form>
                                            </div>
                                        </div>

                                    </c:forEach>   
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="clearfix"></div>
                </div>
        </section>

        <footer class="mbr-section mbr-section--relative mbr-section--fixed-size" id="footer1-35" style="background-color: rgb(68, 68, 68);">

            <div class="mbr-section__container container">
                <div class="mbr-footer mbr-footer--wysiwyg row">
                    <div class="col-sm-12">
                        <p class="mbr-footer__copyright">Copyright (c) 2016 AGRIMARKET. <a class="mbr-footer__link text-gray" href="https://mobirise.com/">Terms of Use</a>  | <a class="mbr-footer__link text-gray" href="https://mobirise.com/">Privacy Policy</a></p>
                    </div>
                </div>
            </div>
        </footer>

        <!-- modify product-->                          
        <div id="signup">

            <div  class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" id="close-btn-signup" class="close" data-dismiss="modal">
                            <span aria-hidden="true">×</span>
                            <span class="sr--only close-modal common-sprite">Close</span>
                        </button>
                        <p>Edit Product </p>
                        <span class="add-form" style="    font-size: 19px;"></span>
                    </div>
                    <div class="modal-body">
                        <div >
                            <form class="form-horizontal" role="form" action="modifyproduct" method="post" onsubmit="return validateModifyProductForm()">
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="product-name-modify">Product Name</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" disabled id="product-name-modify"   required placeholder="product name">
                                        <input type="hidden"   name="name" id="hidden-input">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="price-modify">Price</label>
                                    <div class="col-sm-10">          
                                        <input type="text" class="form-control" name="price" id="price-modify" value="${param.price}" required placeholder="price $">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="categoryId">Category:</label>
                                    <div class="col-sm-10">          
                                        <label class="category-label">
                                            <select   class="pcategory" disabled id="rdoSelect" name="pcategory" required>
                                                <option selected>Select you category</option>

                                            </select>
                                        </label>
                                        <!--</div>-->
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="pwd">Quantity</label>
                                    <div class="col-sm-10">          
                                        <input type="text" class="form-control" name="quantity" value="${param.quantity}" id="quantity-modify" required placeholder="quantity 1,2,3...">
                                    </div>
                                </div>
<!--                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="pwd">Image</label>
                                    <div class="col-sm-10">          
                                        <span class="btn btn-default btn-file">
                                            Browse <input type="file">
                                        </span>                                            </div>
                                </div>-->
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="dec-modify">Description</label>
                                    <div class="col-sm-10">          
                                        <textarea class="form-control" required rows="5" name="desc" value="${param.desc}" cols="50" id="dec-modify" ></textarea>
                                    </div>
                                </div>

                                <div class="form-group">        
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-default">Modify</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div><!---modal-body--->
                </div>
            </div>
        </div>
                                   
        <!-- end modify product-->
       
        <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="../assets/smooth-scroll/SmoothScroll.js"></script>
        <script src="../assets/jarallax/jarallax.js"></script>
        <script src="../assets/masonry/masonry.pkgd.min.js"></script>
        <script src="../assets/imagesloaded/imagesloaded.pkgd.min.js"></script>
        <script src="../assets/bootstrap-carousel-swipe/bootstrap-carousel-swipe.js"></script>
        <script src="../assets/mobirise/js/script.js"></script>
        <script src="../assets/mobirise-gallery/script.js"></script>
        <script src="../assets/js/index.js"></script>

    </body>
</html>
