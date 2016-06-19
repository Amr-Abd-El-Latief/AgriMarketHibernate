<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Site made with Mobirise Website Builder v2.9, https://mobirise.com -->
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="generator" content="Mobirise v2.9, mobirise.com">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" href="../assets/images/untitled-382x276-65.png" type="image/x-icon">
        <meta name="description" content="">
        <title>Profile</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:700,400&amp;subset=cyrillic,latin,greek,vietnamese">
        <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="../assets/mobirise/css/style.css">
        <link rel="stylesheet" href="../assets/mobirise/css/mbr-additional.css" type="text/css">
        <script src="assets/web/assets/jquery/jquery.min.js"></script>
        <c:if test="${empty sessionScope.currUser}">
            <c:redirect url="admin_user_managment.jsp" />
        </c:if>
        <style>
            .div-table{
                display:table;         
                width:auto;         
                background-color:#eee;         
                border:1px solid  #666666;         
                border-spacing:5px;/*cellspacing:poor IE support for  this*/
            }
            .div-table-row{
                display:table-row;
                width:50px;
                clear:both;
            }
            .div-table-col{
                float:left;/*fix for  buggy browsers*/
                display:table-column;         
                width:80px;         
                background-color:#ccc;  
            }
            .mbr-section__container--middle {
                padding-bottom: 15px;
            }
        </style>


    </head>
    <body  style="background-color: rgb(26, 188, 156);">
        <jsp:include page="header.jsp"/>

        <section class="mbr-section mbr-section--relative mbr-after-navbar" id="msg-box4-32" style="background-color: rgb(26, 188, 156);">

            <div class="mbr-section__container mbr-section__container--isolated container">
                <div class="row">
                    <div class="mbr-box__magnet mbr-box__magnet--top-left mbr-section__left col-sm-6">

                        <figure class="mbr-figure mbr-figure--adapted mbr-figure--caption-inside-bottom ">
                            <img class="mbr-figure__img" id="usrImg"  src="${pageContext.servletContext.contextPath }/LoadImageServlet?param1=${sessionScope.currUser.email}" style="width: 59%;">
                            <c:if test="${empty sessionScope.currUser.image}">
                                <script>
                                    $("#usrImg").attr("src", "../assets/images/user.png");
                                </script>
                            </c:if>

                            <h3 class="mbr-figure__caption" style="width: 59%;">${sessionScope.currUser.userName}</h3>
                        </figure>

                    </div>
                    <div class="mbr-box__magnet mbr-class-mbr-box__magnet--center-left col-sm-6 mbr-section__right">
                        <div class="mbr-section__container mbr-section__container--middle">
                            <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                <h2><strong>${sessionScope.currUser.job}</strong></h2>    
                            </div>
                        </div>
                        <div class="mbr-section__container mbr-section__container--middle">
                            <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                <h2><strong>${sessionScope.currUser.DOB}</strong></h2>    
                            </div>
                        </div>
                        <div class="mbr-section__container mbr-section__container--middle">
                            <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                <h2><strong>${sessionScope.currUser.email}</strong></h2>    
                            </div>
                        </div>
                        <div class="mbr-section__container mbr-section__container--middle">
                            <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                <h2><strong>${sessionScope.currUser.creditNumber}</strong></h2>    
                            </div>
                        </div>
                        <div class="mbr-section__container mbr-section__container--middle">
                            <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                <h2><strong>${sessionScope.currUser.address}</strong></h2>    
                            </div>
                        </div>
                        <div class="mbr-section__container mbr-section__container--middle">
                            <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                <h2><strong>Interested in:</strong></h2>
                                <c:forEach items="${sessionScope.currUser.interests}" var="interest">
                                    ${interest.name}
                                </c:forEach>
                            </div>
                        </div>


                        <!--  <div class="mbr-section__container mbr-section__container--middle">
                                 <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                     <h2><strong>Interested in:</strong></h2> <div class="mbr-plan__details">
                                         <ul>
                        <c:forEach items="${sessionScope.currUser.interests}" var="interest">
                            <li><strong>${interest.name}</strong></li>
                        </c:forEach>
            </ul></div>
    </div>
</div>-->

                    </div>

                </div>
            </div>
        </div>
    </section>
    <!--- class="mbr-section mbr-section--relative mbr-section--fixed-size mbr-parallax-background" -->

    <section  class="mbr-section mbr-section--relative mbr-after-navbar"  id="pricing-table1-1"  >
        <!---   <div class="mbr-overlay" style="opacity: 0.5;"></div>-->
        <div class="mbr-section__container mbr-section__container--std-top-padding container" id="pricing-table1-2">
            <div class="row">
                <c:forEach items="${sessionScope.currUser.orders}" var="order">

                    <c:if test="${order.status.equals('done')}">
                        <div class="mbr-plan col-xs-12 mbr-plan--success col-md-3 col-sm-6">
                            <div class="mbr-plan__box">
                                <div class="mbr-plan__header">
                                    <div class="mbr-header mbr-header--reduce mbr-header--center mbr-header--wysiwyg">
                                        <h3 class="mbr-header__text">ORDER ${order.date}</h3>
                                    </div>
                                </div>
                                <div class="mbr-plan__number">
                                    <div class="mbr-number mbr-number--price" style="">
                                        <div class="mbr-number__num">
                                            <div class="mbr-number__group">
                                                <sup class="mbr-number__left">$</sup>
                                                <span class="mbr-number__value" style="font-size: 43px;">${order.total}</span>
                                            </div>
                                        </div>
                                            <div class="mbr-number__caption" style="font-size: 1.0em;">received</div>
                                    </div>
                                </div>
                                <center>
                                    <div class="mbr-plan__details" >

                                        <div class="div-table">
                                            <div class="div-table-row">
                                                <div class="div-table-col" align="center">Product</div>
                                                <div  class="div-table-col">Price</div>
                                                <div  class="div-table-col">Quantity</div>
                                            </div>
                                            <c:forEach items="${order.products}" var="product">     
                                                <div class="div-table-row">
                                                    <div class="div-table-col">${product.name}</div>
                                                    <div class="div-table-col">${product.price}</div>
                                                    <div class="div-table-col">${product.quantity}</div>
                                                </div>
                                            </c:forEach> 
                                        </div>
                                    </div>
                                </center>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${order.status.equals('cart')}">
                        <div class="mbr-plan col-xs-12 mbr-plan--danger mbr-plan--favorite col-md-3 col-sm-6">
                            <div class="mbr-plan__box">
                                <div class="mbr-plan__header">
                                    <div class="mbr-header mbr-header--reduce mbr-header--center mbr-header--wysiwyg">
                                        <h3 class="mbr-header__text">ORDER ${order.date}</h3>
                                    </div>
                                </div>
                                <div class="mbr-plan__number">
                                    <div class="mbr-number mbr-number--price">
                                        <div class="mbr-number__num">
                                            <div class="mbr-number__group">
                                                <sup class="mbr-number__left">$</sup>
                                                <span class="mbr-number__value">${order.total}</span>
                                            </div>
                                        </div>
                                        <div class="mbr-number__caption">not received yet</div>
                                    </div>
                                </div>
                                <center>
                                    <div class="mbr-plan__details" >

                                        <div class="div-table">
                                            <div class="div-table-row">
                                                <div class="div-table-col" align="center">Product</div>
                                                <div  class="div-table-col">Price</div>
                                                <div  class="div-table-col">Quantity</div>
                                            </div>
                                            <c:forEach items="${order.products}" var="product">     
                                                <div class="div-table-row">
                                                    <div class="div-table-col">${product.name}</div>
                                                    <div class="div-table-col">${product.price}</div>
                                                    <div class="div-table-col">${product.quantity}</div>
                                                </div>
                                            </c:forEach> 
                                        </div>
                                    </div>
                                </center>
                            </div>
                        </div>
                    </c:if>

                </c:forEach>
            </div>
        </div>
    </section>




    <script src="../assets/web/assets/jquery/jquery.min.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/smooth-scroll/SmoothScroll.js"></script>
    <script src="../assets/mobirise/js/script.js"></script>
    <script src="../assets/jarallax/jarallax.js"></script>

</body>
</html>