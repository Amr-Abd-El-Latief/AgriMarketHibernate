<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
    <head>
        <!-- Site made with Mobirise Website Builder v2.9, https://mobirise.com -->
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="generator" content="Mobirise v2.9, mobirise.com">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" href="assets/images/untitled-382x276-65.png" type="image/x-icon">
        <meta name="description" content="">
        <title>Profile</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:700,400&amp;subset=cyrillic,latin,greek,vietnamese">
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/mobirise/css/style.css">
        <link rel="stylesheet" href="assets/mobirise/css/mbr-additional.css" type="text/css">
        <link rel="stylesheet" href="assets/cart/css/screen.css" type="text/css" />

        <script src="assets/web/assets/jquery/jquery.min.js"></script>
        <c:if test="${empty sessionScope.user}">

            <script>
                location.href = "sign_in.jsp";
            </script>
        </c:if>
        <script>

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
            $("document").ready(function () {
                refreshCart();

            });
        </script>

    </head>
    <body  style="background-color: rgb(26, 188, 156);">
        <jsp:include page="header.jsp"/>

        <section class="mbr-section mbr-section--relative mbr-after-navbar" id="msg-box4-32">

            <div class="mbr-section__container mbr-section__container--isolated container">
                <div class="row">
                    <div class="mbr-box__magnet--sm mbr-box__magnet--top-center mbr-section__left col-sm-4">

                        <figure class="mbr-figure mbr-figure--full-width mbr-figure--caption-inside-bottom ">
                            <img class="mbr-figure__img" id="image" src="${sessionScope.user.imageUrl}" style="width: 59%;">
                            <c:if test="${empty sessionScope.user.imageUrl}">

                                <script>
                                    $("#image").attr("src", "assets/images/user.png");
                                </script>
                            </c:if>
                            <h3 class="mbr-figure__caption" style="width: 59%;">${sessionScope.user.userName}</h3>
                        </figure>
                        <div class="mbr-section__container mbr-section__container--middle" style="width: 59%;">
                            <div class="mbr-buttons mbr-buttons--center">
                                <script>
                                    function edit_profile() {
                                        location.href = 'edit_profile.jsp';
                                    }
                                </script>
                                <button  onclick='edit_profile()' class="mbr-buttons__btn btn btn-lg btn-link">Edit profile</button>

                            </div>
                        </div>
                    </div>
                    <div class="mbr-box__magnet mbr-class-mbr-box__magnet--center-left col-sm-6 mbr-section__right">
                        <div class="mbr-section__container mbr-section__container--middle">
                            <div class="mbr-header mbr-header--auto-align mbr-header--wysiwyg">

                            </div>
                        </div>
                        <div class="mbr-section__container mbr-section__container--middle">
                            <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                <h2><strong>${sessionScope.user.job}</strong></h2>    
                            </div>
                        </div>
                        <div class="mbr-section__container mbr-section__container--middle">
                            <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                <h2><strong>${sessionScope.user.DOB}</strong></h2>    
                            </div>
                        </div>
                        <div class="mbr-section__container mbr-section__container--middle">
                            <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                <h2><strong>${sessionScope.user.email}</strong></h2>    
                            </div>
                        </div>
                        <div class="mbr-section__container mbr-section__container--middle">
                            <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                <h2><strong>${sessionScope.user.creditNumber}</strong></h2>    
                            </div>
                        </div>
                        <div class="mbr-section__container mbr-section__container--middle">
                            <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                <h2><strong>${sessionScope.user.address}</strong></h2>    
                            </div>
                        </div>
                        <div class="mbr-section__container mbr-section__container--middle">
                            <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                <h2><strong>$${sessionScope.user.balance}</strong></h2>    
                            </div>
                        </div>
                        <div class="mbr-section__container mbr-section__container--middle">
                            <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                <script>
                                    var interests = "Interested in : ";
                                    <c:forEach var = "interest" items = "${sessionScope.user.interests}">
                                    interests += "${interest.name}" + ",";
                                    </c:forEach>
                                    interests = interests.substring(0, interests.length - 1);
                                </script>
                                <h2><strong id="finalInt"></strong></h2>  
                                <script>
                                    $("#finalInt").html(interests);
                                </script>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </section>


    <script src="assets/web/assets/jquery/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/smooth-scroll/SmoothScroll.js"></script>
    <script src="assets/mobirise/js/script.js"></script>


</body>
</html>