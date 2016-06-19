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
        <title>AgriMarket</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:700,400&amp;subset=cyrillic,latin,greek,vietnamese">
        <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="../assets/animate.css/animate.min.css">
        <link rel="stylesheet" href="../assets/mobirise/css/style.css">
        <link rel="stylesheet" href="../assets/mobirise/css/mbr-additional.css" type="text/css">

        <link rel="stylesheet" href="../assets/mobirise/css/style.css">
        <link rel="stylesheet" href="../assets/mobirise-gallery/style.css">
        <link rel="stylesheet" href="../assets/mobirise-slider/style.css">
    </head>
    <body>
        <!--        <section class="engine"><a rel="external" href="https://mobirise.com">Mobirise website making software
                    </a></section>
                <section class="mbr-navbar mbr-navbar--freeze mbr-navbar--absolute mbr-navbar--transparent mbr-navbar--sticky mbr-navbar--auto-collapse" id="menu-42">
                    <div class="mbr-navbar__section mbr-section">
                        <div class="mbr-section__container container">
                            <div class="mbr-navbar__container">
                                <div class="mbr-navbar__column mbr-navbar__column--s mbr-navbar__brand">
                                    <span class="mbr-navbar__brand-link mbr-brand mbr-brand--inline">
                                        <span class="mbr-brand__logo"><a href="index.jsp"><img class="mbr-navbar__brand-img mbr-brand__img" src="../assets/images/untitled-382x276-11.png" alt="AgriMarket" title="Flower Shop"></a></span>
                                        <span class="mbr-brand__name"><a class="mbr-brand__name text-white" href="index.jsp">AGRIMARKET</a></span>
                                    </span>
                                </div>
                                <div class="mbr-navbar__hamburger mbr-hamburger text-white"><span class="mbr-hamburger__line"></span></div>
                                <div class="mbr-navbar__column mbr-navbar__menu">
                                    <nav class="mbr-navbar__menu-box mbr-navbar__menu-box--inline-right">
                                        <div class="mbr-navbar__column"><ul class="mbr-navbar__items mbr-navbar__items--right mbr-buttons mbr-buttons--freeze mbr-buttons--right btn-decorator mbr-buttons--active"><li class="mbr-navbar__item"><a class="mbr-buttons__link btn text-white" href="index.jsp">HOME</a></li> <li class="mbr-navbar__item"><a class="mbr-buttons__link btn text-white" href="about.jsp">ABOUT</a></li> <li class="mbr-navbar__item"><a class="mbr-buttons__link btn text-white" href="contact_us.jsp">CONTACT</a></li></ul></div>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>-->


        <jsp:include page="header.jsp"/>

        <section class="mbr-box mbr-section mbr-section--relative mbr-section--fixed-size mbr-section--full-height mbr-section--bg-adapted mbr-parallax-background mbr-after-navbar" id="header1-44" style="background-image: url(../assets/images/istock-000016896298xlarge-4200x2833-32.jpg);">
            <div class="mbr-box__magnet mbr-box__magnet--sm-padding mbr-box__magnet--center-left">
                <div class="mbr-overlay" style="opacity: 0.2; background-color: rgb(34, 34, 34);"></div>
                <div class="mbr-box__container mbr-section__container container">
                    <div class="mbr-box mbr-box--stretched"><div class="mbr-box__magnet mbr-box__magnet--center-left">
                            <div class="row"><div class=" col-sm-6 col-sm-offset-6">
                                    <div class="mbr-hero animated fadeInUp">
                                        <h1 class="mbr-hero__text">AGRIMARKET</h1>
                                        <p class="mbr-hero__subtext">Agricultural Market in Egypt</p>
                                    </div>
                                    <div class="mbr-buttons btn-inverse mbr-buttons--left">
                                        <a class="mbr-buttons__btn btn btn-lg animated fadeInUp delay btn-primary" href="admin.jsp#header3-41">
                                            PRODUCT MANAGMENT</a> 
                                        <a class="mbr-buttons__btn btn btn-lg animated fadeInUp delay btn-info" href="#gallery2-5">USER MANAGMENT</a></div>
                                </div></div>
                        </div></div>
                </div>
                <div class="mbr-arrow mbr-arrow--floating text-center">
                    <div class="mbr-section__container container">
                        <a class="mbr-arrow__link" href="#gallery2-5"><i class="glyphicon glyphicon-menu-down"></i></a>
                    </div>
                </div>
            </div>
        </section>

        <section class="mbr-section" id="header3-45">
            <div class="mbr-section__container container mbr-section__container--first">
                <div class="mbr-header mbr-header--wysiwyg row">
                    <div class="col-sm-8 col-sm-offset-2">
                        <h3 class="mbr-header__text">User Management</h3>

                    </div>
                </div>
            </div>
        </section>

        <section class="mbr-section" id="content1-47">
            <div class="mbr-section__container container mbr-section__container--last">
                <div class="row">
                    <div class="mbr-article mbr-article--wysiwyg col-sm-8 col-sm-offset-2"><p>AGRIMARKET USERS</p></div>
                </div>
            </div>
        </section>

        <!--  ________________________________________________________________________________________________________ --->
        <section class="mbr-gallery mbr-section mbr-section--no-padding" id="gallery2-5">

            <div class="container mbr-section__container mbr-gallery-layout-article mbr-section__container--isolated">
                <div class=" col-sm-8 col-sm-offset-2">
                    <div class="row mbr-gallery-row">

                        <c:if test="${sessionScope.users ==null}">
                            <c:redirect url="../LoadUsersServlet?key=1#gallery2-5" />

                        </c:if>
                        <c:forEach items="${sessionScope.users}" var="user">
                            <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 mbr-gallery-item" style="width:200px;">
                                <a href="${pageContext.request.contextPath}/LoadUsersServlet?user=${user.email}" > 
                                    <img id="${user.userName}" src="${pageContext.servletContext.contextPath }/LoadImageServlet?param1=${user.email}" style="width:200px;height:150px;" >
                                   
                                    <c:if test="${empty user.imageUrl}">
                                        <script type="text/javascript">
                                            $("#${user.userName}").attr("src", "../assets/images/user.png");
                                        </script>
                                    </c:if>   

                                </a>
                                <center> <h3 class="mbr_figure__caption"> ${user.userName}</h3></center>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>

        </section>




        <!--  ________________________________________________________________________________________________________ --->   

        <footer class="mbr-section mbr-section--relative mbr-section--fixed-size" id="footer1-43" style="background-color: rgb(68, 68, 68);">

            <div class="mbr-section__container container">
                <div class="mbr-footer mbr-footer--wysiwyg row">
                    <div class="col-sm-12">
                        <p class="mbr-footer__copyright">Copyright (c) 2016 AGRIMARKET. <a class="mbr-footer__link text-gray" href="https://mobirise.com/">Terms of Use</a>  | <a class="mbr-footer__link text-gray" href="https://mobirise.com/">Privacy Policy</a></p>
                    </div>
                </div>
            </div>
        </footer>


        <script src="../assets/web/assets/jquery/jquery.min.js"></script>
        <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="../assets/smooth-scroll/SmoothScroll.js"></script>
        <script src="../assets/jarallax/jarallax.js"></script>
        <script src="../assets/mobirise/js/script.js"></script>

        <script src="../assets/web/assets/jquery/jquery.min.js"></script>
        <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="../assets/smooth-scroll/SmoothScroll.js"></script>
        <script src="../assets/masonry/masonry.pkgd.min.js"></script>
        <script src="../assets/imagesloaded/imagesloaded.pkgd.min.js"></script>
        <script src="../assets/bootstrap-carousel-swipe/bootstrap-carousel-swipe.js"></script>
        <script src="../assets/mobirise/js/script.js"></script>
        <script src="../assets/mobirise-gallery/script.js"></script>


    </body>
</html>
