<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <!-- Site made with Mobirise Website Builder v2.9, https://mobirise.com -->
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="generator" content="Mobirise v2.9, mobirise.com">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" href="assets/images/untitled-382x276-65.png" type="image/x-icon">
        <meta name="description" content="">

        <title>AgriMarket</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:700,400&amp;subset=cyrillic,latin,greek,vietnamese">
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/animate.css/animate.min.css">
        <link rel="stylesheet" href="assets/mobirise/css/style.css">
        <link rel="stylesheet" href="assets/mobirise-gallery/style.css">
        <link rel="stylesheet" href="assets/mobirise-slider/style.css">
        <link rel="stylesheet" href="assets/mobirise/css/mbr-additional.css" type="text/css">

        <script src="assets/web/assets/jquery/jquery.min.js"></script>

        <script>
            function getMax(proName) {
            <c:set var="products" value="${applicationScope.products}"/>

                var max = 1;
            <c:forEach items="${products}" var="product">

                if ('${product.name}' == proName)
                    max = ${product.quantity};

            </c:forEach>

                return max;
            }


            function addToCart(name) {
                if (getMax(name) > 0) {
                    if (${empty sessionScope.user})
                        location.href = "sign_in.jsp";
                    var jsonData = {
                        operation: "add",
                        timestamp: new Date().getTime(),
                        product: name
                    };
                    $.ajax({
                        url: 'Cart',
                        type: 'GET',
                        contentType: 'application/json',
                        data: jsonData,
                        dataType: 'json',
                        success: function (data) {
                            refreshCartAgain(data);
                        }
                    });
                }
            }
            function refreshCart() {

                var total = 0;
            <c:set var="items" value="${sessionScope.user.cart.items}"/>

                var i = 0;
            <c:forEach items="${items}" var="item">
                total +=${item.quantity} *${item.product.price};
                i++;
            </c:forEach>
                $("#total").html("$" + total);
                $("#itemNo").html("${fn:length(items)}");
            }
            function refreshCartAgain(cart) {

                var total = 0;
                var items = cart.items;
                for (i = 0; i < items.length; i++) {
                    total += (items[i].product.price * items[i].quantity);
                }
                $("#total").html("$" + total);
                $("#itemNo").html(items.length);
            }
            $("document").ready(function () {
                refreshCart();

                $(".pane .delete").click(function () {
                    $(this).parents(".pane").animate({opacity: 'hide'}, "slow");
                    var id = $(this).attr("id");
                    var jsonData = {
                        operation: "delete",
                        timestamp: new Date().getTime(),
                        product: id
                    };
                    $.ajax({
                        url: 'Cart',
                        type: 'GET',
                        contentType: 'application/json',
                        data: jsonData,
                        dataType: 'json',
                        success: function (data) {
                            refreshCartAgain(data);
                        }
                    });
                });


                $("input.quantity").keydown(function (e) {
                    e.preventDefault();
                });

                $("img.productA").keydown(function (e) {
                    var id = $(this).attr("id");
                    alert(id);
                });

                $("input.quantity").change(function () {
                    var id = $(this).attr("id");
                    var jsonData = {
                        operation: "quantity",
                        newVal: $(this).val(),
                        timestamp: new Date().getTime(),
                        product: id
                    };
                    $.ajax({
                        url: 'Cart',
                        type: 'GET',
                        contentType: 'application/json',
                        data: jsonData,
                        dataType: 'json',
                        success: function (data) {
                            refreshCartAgain(data);
                        }
                    });
                });


            });
        </script>

    </head>
    <body >


        <jsp:include page="header.jsp"/>

        <section class="mbr-box mbr-section mbr-section--relative mbr-section--fixed-size mbr-section--full-height mbr-section--bg-adapted mbr-parallax-background mbr-after-navbar" id="header1-1"  style="background-image: url(assets/images/istock-000016896298xlarge-4200x2833-56.jpg);">
            <div class="mbr-box__magnet mbr-box__magnet--sm-padding mbr-box__magnet--center-left">
                <div class="mbr-overlay" style="opacity: 0.2; background-color: rgb(0, 0, 0);"></div>
                <div class="mbr-box__container mbr-section__container container">
                    <div class="mbr-box mbr-box--stretched"><div class="mbr-box__magnet mbr-box__magnet--center-left">
                            <div class="row"><div class=" col-sm-6 col-sm-offset-6">
                                    <div class="mbr-hero animated fadeInUp">
                                        <h1 class="mbr-hero__text">AGRIMARKET</h1>
                                        <p class="mbr-hero__subtext">Agricultural Market in Egypt</p>
                                    </div>

                                </div></div>
                        </div></div>
                </div>
                <div class="mbr-arrow mbr-arrow--floating text-center">
                    <div class="mbr-section__container container">
                        <a class="mbr-arrow__link" href="#footer1-8"><i class="glyphicon glyphicon-menu-down"></i></a>
                    </div>
                </div>
            </div>
        </section>

        <section class="mbr-section" id="header3-8">
            <div class="mbr-section__container container mbr-section__container--first">
                <div class="mbr-header mbr-header--wysiwyg row">
                    <div class="col-sm-8 col-sm-offset-2">
                        <h3 class="mbr-header__text">Search ....</h3>
                        <!--<p class="mbr-header__subtext">SUBTITLE</p>-->
                    </div>
                </div>
            </div>
        </section>


        <section class="mbr-section" id="content1-9">
            <div class="mbr-section__container container mbr-section__container--middle">
                <div class="row">
                    <div class="mbr-article mbr-article--wysiwyg col-sm-8 col-sm-offset-2">
                        <form method="get" id="new" action="getallproducts">
                            <div   style="    margin-left: -4px;    ">
                                <input type="text" onkeyup="searchByName();" id="searchTxt" placeholder="search...." name="search"/>
                                <input type="submit" value="search" />
                                <br>
                                <!--<label class="category-label">-->
                                <div  style="     margin-top: -43px;        
                                      width: -5px; ">
                                    <select  id="search" name="options" style="    margin-top: 16px;margin-left: 313px;">                
                                        <option> choose Options </option>                
                                        <option>Product Name</option>
                                        <option>Category</option>
                                        <option>Product Price</option>
                                    </select>
                                    <select   class="pcategory" required="" id="category" name="category" style="display: none;margin-top: -26px;margin-left: 477px;">
                                        <option selected>Select Category</option>

                                    </select>
                                    <select  id="product-price" required="" name="product-price" style="display: none;      margin-top: -26px;margin-left: 468px;">             
                                        <option>Select Price</option>
                                        <option>5</option>
                                        <option>10</option>
                                        <option>15</option>
                                    </select>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <section class="mbr-gallery mbr-section mbr-section--no-padding" id="gallery2-6">
            <!-- Gallery -->
            <div class="container mbr-section__container mbr-gallery-layout-article mbr-section__container--last">
                <div class=" col-sm-8 col-sm-offset-2">
                    <div class="row mbr-gallery-row" id="productsContainer">
                        <%--<jsp:include page="getallproducts"/>--%>
                        <c:if test="${requestScope.getproducts ==null}" >
                            <c:redirect url="getallproducts" />
                        </c:if>
                        <c:forEach items="${requestScope.getproducts}" var="product">
                            <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 mbr-gallery-item">
                                <a id="${product.name}Click" onclick="addToCart('${product.name}');" data-slide-to="0" data-toggle="modal">
                                    <c:if test="${empty sessionScope.user}">
                                        <script>
                                            $("#${product.name}").href = "sign_in.jsp";
                                        </script>
                                    </c:if>
                                    <img id="image"  class="productA"  alt="" src=${product.imageUrl}>
                                    <c:if test="${empty product.imageUrl}">
                                        <script>
                                            $("#image").attr("src",${product.imageUrl});
                                        </script>
                                    </c:if>
                                    <span id="${product.name}Span" class="icon glyphicon glyphicon-plus">${product.name}</span>
                                    <c:if test="${product.quantity==0}">
                                        <script>
                                            $("#${product.name}Span").attr("class", "icon glyphicon glyphicon-alert");
                                            $("#${product.name}").href = "";
                                        </script>

                                        <center> <h5 class="mbr_figure__caption">Out of stock</h5></center>
                                        </c:if>
                                </a>
                            </div>
                        </c:forEach>   
                    </div>
                </div>

            </div>


        </section>



        <footer class="mbr-section mbr-section--relative mbr-section--fixed-size" id="footer1-8" style="background-color: rgb(68, 68, 68);">

            <div class="mbr-section__container container">
                <div class="mbr-footer mbr-footer--wysiwyg row">
                    <div class="col-sm-12">
                        <p class="mbr-footer__copyright">Copyright (c) 2016 AGRIMARKET.                
                            <a class="mbr-footer__link text-gray" href="https://mobirise.com/">Terms of Use</a>  | <a class="mbr-footer__link text-gray" href="https://mobirise.com/">Privacy Policy</a></p>
                    </div>
                </div>
            </div>
        </footer>


        <script src="assets/web/assets/jquery/jquery.min.js"></script>
        <script src="assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/smooth-scroll/SmoothScroll.js"></script>
        <script src="assets/jarallax/jarallax.js"></script>
        <script src="assets/masonry/masonry.pkgd.min.js"></script>
        <script src="assets/imagesloaded/imagesloaded.pkgd.min.js"></script>
        <script src="assets/bootstrap-carousel-swipe/bootstrap-carousel-swipe.js"></script>
        <script src="assets/mobirise/js/script.js"></script>
        <script src="assets/mobirise-gallery/script.js"></script>
        <script type="text/javascript">
                                            $("#search").change(function () {
                                                var options = $('#search :selected').text();
                                                if (options == 'Category') {
                                                    $.ajax({
                                                        url: "gategories",
                                                        type: 'GET',
                                                        contentType: 'application/json',
                                                        dataType: 'json',
                                                        success: function (data) {
                                                            $.each(data, function (i, category) {
                                                                 //                                                         write("<img id='"+category.id+";' src='no.png' />");
                                                                $('.pcategory').append('<option   value=' + category.id + '>' + category.name + '</option>');
                                                                //                        location.reload();   
                                                            });
                                                        }
                                                    });
                                                    $("#category").css("display", "block");
                                                    $("#product-price").css("display", "none");
                                                } else if (options == 'Product Price') {
                                                    $("#product-price").css("display", "block");
                                                    $("#category").css("display", "none");
                                                } else {
                                                    $("#category").css("display", "none");
                                                    $("#product-price").css("display", "none");
                                                }

                                            });
        </script>
    </body>
</html>