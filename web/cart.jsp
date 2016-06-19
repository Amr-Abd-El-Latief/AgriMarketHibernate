<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="en">
    <head>

        <c:if test="${empty sessionScope.user}">
            <script>
                location.href = 'sign_in.jsp';
            </script>
        </c:if>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Google Fonts -->
        <link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,200,300,700,600' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Roboto+Condensed:400,700,300' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Raleway:400,100' rel='stylesheet' type='text/css'>

        <!-- Bootstrap -->
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

        <!-- Font Awesome -->
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

        <!-- Custom CSS -->
        <link rel="stylesheet" href="assets/flat_cart/css/owl.carousel.css">
        <link rel="stylesheet" href="assets/flat_cart/style.css">
        <link rel="stylesheet" href="assets/flat_cart/css/responsive.css">

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->


        <link rel="shortcut icon" href="assets/images/untitled-382x276-65.png" type="image/x-icon">
        <meta name="description" content="">

        <title>AgriMarket</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:700,400&amp;subset=cyrillic,latin,greek,vietnamese">
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
                function addTo(product, value) {
                    var id = "#" + product;
                    if (!(parseInt(value) == -1 && parseInt($(id).val()) == 1)) {
                        $(id).val(parseInt($(id).val()) + value);
                        $(id).trigger('change');
                    }
                }
                function refreshCart() {

                    var total = 0;
                    var subtotal = 0;
                    var subId;
            <c:set var="items" value="${sessionScope.user.cart.items}"/>

                    var i = 0;
            <c:forEach items="${items}" var="item">

                    subtotal =${item.quantity} *${item.product.price};
                    total += subtotal;

                    subId = "#" + "${item.product.name}" + "Sub";
                    $(subId).html(subtotal);
                    i++;

                    var max = getMax('${item.product.name}');
                    $("#${item.product.name}").attr("max", max);

            </c:forEach>
                    $("#total").html("$" + total);


                    var totalShip = total + (total * (2 / 100));
                    $("#totalShip").html("$" + totalShip);

                    if (total == 0)
                        $("#checkOut").attr('disabled', 'disabled');
                }
                function refreshCartAgain(cart) {

                    var total = 0;
                    var subtotal = 0;
                    var subId;
                    var items = cart.items;
                    for (i = 0; i < items.length; i++) {
                        subtotal = (items[i].product.price * items[i].quantity);
                        total += subtotal;
                        subId = "#" + items[i].product.name + "Sub";
                        $(subId).html(subtotal);
                    }
                    $("#total").html("$" + total);

                    var totalShip = total + (total * (2 / 100));
                    $("#totalShip").html("$" + totalShip);

                    if (total == 0)
                        $("#checkOut").attr('disabled', 'disabled');
                }
                $("document").ready(function () {
                    refreshCart();

                    $(".pane .delete").click(function () {
                        $(this).parents(".pane").animate({opacity: 'hide'}, "slow");
                        var id = $(this).attr("id").substring(0, $(this).attr("id").length - 3);
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

                    $("input.quantity").change(function () {
                        var id = $(this).attr("id");
                        if ($(this).val() == 0)
                            $("#" + id + "del").trigger('click');

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
    <body>

        <jsp:include page="header_no_cart.jsp"/>
        <div class="product-big-title-area">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="product-bit-title text-center">
                            <h2></h2>
                        </div>
                    </div>
                </div>
            </div>
        </div> <!-- End Page title area -->


        <div class="single-product-area">
            <!--<div class="zigzag-bottom"></div>-->
            <div class="container">
                <div class="row">

                    <!--<div class="col-md-8">-->
                    <div class="product-content-right">
                        <div class="woocommerce">
                            <form name = "cartForm" method="post"  action="check_out.jsp">
                                <table cellspacing="0" class="shop_table cart">
                                    <thead>
                                        <tr>
                                            <th class="product-remove">&nbsp;</th>
                                            <th class="product-thumbnail">&nbsp;</th>
                                            <th class="product-name">Product</th>
                                            <th class="product-price">Price</th>
                                            <th class="product-quantity">Quantity</th>
                                            <th class="product-subtotal">Total</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${sessionScope.user.cart.items}" var="item">
                                            <tr class="cart_item pane">
                                                <td class="product-remove">
                                                    <a title="Remove this item" id="${item.product.name}del" class="remove delete" href="#">X</a> 
                                                </td>

                                                <td class="product-thumbnail">
                                                    <a href="">
                                                        <img width="145" height="145" alt="poster_1_up" class="shop_thumbnail" src="${item.product.imageUrl}" class="userImg">
                                                        <c:if test="${empty item.product.imageUrl}">
                                                            <script>
                                                                $("img.userImg").attr("src", "assets/cart/img/img.png");
                                                            </script>
                                                        </c:if>
                                                    </a>
                                                </td>

                                                <td class="product-name">
                                                    <a href="single-product.html">${item.product.name}</a> 
                                                </td>

                                                <td class="product-price">
                                                    <span class="amount">
                                                        ${item.product.price}</span> 
                                                </td>

                                                <td class="product-quantity">
                                                    <div class="quantity buttons_added">
                                                        <input type="button" onclick="addTo('${item.product.name}', -1)" class="minus" value="-">
                                                        <input type="number" size="4" class="input-text qty text quantity" title="Qty" name="${item.product.name}" id="${item.product.name}"  value="${item.quantity}"  min="1"  >
                                                        <input type="button"  onclick="addTo('${item.product.name}', 1)" class="plus" value="+">
                                                    </div>
                                                </td>

                                                <td class="product-subtotal">
                                                    <span class="amount" id="${item.product.name}Sub">${item.product.price*item.quantity}</span> 
                                                </td>
                                            </tr>
                                        </c:forEach>

                                        <tr>
                                            <td class="actions" colspan="6">
                                                <!--<input type="submit" onclick="goToHome();" value="Update Cart" name="update_cart" class="button">-->
                                                <input type="submit" id="checkOut" value="Proceed to Checkout" name="proceed" class="checkout-button button alt wc-forward">
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </form>

                            <div class="cart-collaterals">

                                <div class="cart_totals ">
                                    <h2>Cart Totals</h2>

                                    <table cellspacing="0">
                                        <tbody>
                                            <tr class="cart-subtotal">
                                                <th>Cart Subtotal</th>
                                                <td><span class="amount" id="total">$</span></td>
                                            </tr>

                                            <tr class="shipping">
                                                <th>Shipping and Handling</th>
                                                <td>2%</td>
                                            </tr>

                                            <tr class="order-total">
                                                <th>Order Total</th>
                                                <td><strong><span class="amount" id="totalShip">$</span></strong> </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>





                            </div>
                        </div>                        
                    </div>                    
                    <!--</div>-->
                </div>
            </div>
        </div>


        <!-- Latest jQuery form server -->
        <script src="https://code.jquery.com/jquery.min.js"></script>

        <!-- Bootstrap JS form CDN -->
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

        <!-- jQuery sticky menu -->
        <script src="assets/flat_cart/js/owl.carousel.min.js"></script>
        <script src="assets/flat_cart/js/jquery.sticky.js"></script>

        <!-- jQuery easing -->
        <script src="assets/flat_cart/js/jquery.easing.1.3.min.js"></script>

        <!-- Main Script -->
        <script src="assets/flat_cart/js/main.js"></script>
    </body>
</html>