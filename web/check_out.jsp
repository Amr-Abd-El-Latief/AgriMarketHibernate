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
        <title>Check Out</title>
        <link rel="shortcut icon" href="assets/images/untitled-382x276-65.png" type="image/x-icon">

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

        <script src="assets/web/assets/jquery/jquery.min.js"></script>

        <script>
                function calculate() {

                    var total = 0;
                    var subtotal = 0;
            <c:set var="items" value="${sessionScope.user.cart.items}"/>

                    var i = 0;
            <c:forEach items="${items}" var="item">
                    subtotal =${item.quantity} *${item.product.price};
                    total += subtotal;
                    i++;
            </c:forEach>
                    $("#total").html("$" + total);
                    var totalShip = total + (total * (2 / 100));
                    $("#totalShip").html("$" + totalShip);

                    if (total == 0)
                        $("#checkOut").attr('disabled', 'disabled');
                }
                $("document").ready(function () {
                    calculate();
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
        </div>


        <div class="single-product-area">
            <div class="container">
                <div class="row">
                    <div class="product-content-right">
                        <div class="woocommerce">
                            <form  action="Cart" class="checkout" method="post" name="checkout">
                                <div id="customer_details" class="col2-set">
                                    <h3 id="order_review_heading">Your order</h3>
                                    <div id="order_review" style="position: relative;">
                                        <table class="shop_table">
                                            <thead>
                                                <tr>
                                                    <th class="product-name">Product</th>
                                                    <th class="product-total">Total</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${sessionScope.user.cart.items}" var="item">
                                                    <tr class="cart_item">
                                                        <td class="product-name">
                                                            ${item.product.name} 
                                                            <strong class="product-quantity"${item.quantity}</strong> 
                                                        </td>
                                                        <td class="product-total">
                                                            <span class="amount">$${item.product.price*item.quantity}</span> </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                            <tfoot>
                                                <tr class="cart-subtotal">
                                                    <th>Cart Subtotal</th>
                                                    <td><span class="amount" id="total"></span>
                                                    </td>
                                                </tr>
                                                <tr class="shipping">
                                                    <th>Shipping and Handling</th>
                                                    <td>
                                                        2%
                                                        <input type="hidden" class="shipping_method" value="free_shipping" id="shipping_method_0" data-index="0" name="shipping_method[0]">
                                                    </td>
                                                </tr>
                                                <tr class="order-total">
                                                    <th>Order Total</th>
                                                    <td><strong><span class="amount" id="totalShip"></span></strong> </td>
                                                </tr>
                                            </tfoot>
                                        </table>
                                        <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg"><h4 id="error"></h4></div>

                                        <div id="payment">
                                            <div class="form-row place-order">
                                                <input type="button" onclick="check()" id="checkOut" data-value="Place order" value="Place order" id="place_order" name="woocommerce_checkout_place_order" class="button alt">
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                    </div>
                                </div>


                            </form>

                        </div>                       
                    </div>                    
                </div>
            </div>
        </div>
        <!-- Latest jQuery form server -->

        <!--         Bootstrap JS form CDN 
        -->        
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

        <!--jQuery sticky menu--> 
        <script src="assets/flat_cart/js/owl.carousel.min.js"></script>
        <script src="assets/flat_cart/js/jquery.sticky.js"></script>
        <!--jQuery easing--> 
        <script src="assets/flat_cart/js/jquery.easing.1.3.min.js"></script>
        <!--Main Script--> 
        <script src="assets/flat_cart/js/main.js"></script>
        <script type="text/javascript">

                                                    function  check() {

                                                        $.post("CheckOut", {
                                                        }, checkOutCallBack);
                                                    }
                                                    function checkOutCallBack(responseTxt, statusTxt, xhr) {
                                                        if (statusTxt == "success") {
                                                            if (responseTxt == "success") {
                                                                document.checkout.submit();
                                                            } else
                                                                $("#error").html("You don't have enough balance");
                                                        } else {
                                                            alert("Laaaaa");
                                                        }
                                                    }
        </script>
    </body>
</html>