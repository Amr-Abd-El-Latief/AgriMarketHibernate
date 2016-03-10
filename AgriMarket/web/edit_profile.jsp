<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <!-- Site made with Mobirise Website Builder v2.9, https://mobirise.com -->
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="generator" content="Mobirise v2.9, mobirise.com">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" href="assets/images/untitled-382x276-65.png" type="image/x-icon">
        <meta name="description" content="">
        <title>Edit Profile</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:700,400&amp;subset=cyrillic,latin,greek,vietnamese">
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/mobirise/css/style.css">
        <link rel="stylesheet" href="assets/mobirise/css/mbr-additional.css" type="text/css">

        <%--<jsp:useBean id="user" scope="session" class="model.pojo.User"/>--%>

        <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
        <script>

            $("document").ready(function () {

                $("#realImage").css('opacity', '0');
                $("#image").click(function (e) {
                    e.preventDefault();
                    $("#realImage").trigger('click');
                });
                $("#realImage").change(function () {
                    $("#image").attr("src", URL.createObjectURL(event.target.files[0]));
                });
            })

            function saveChanges() {
                $("#editForm").submit();
            }

        </script>
    </head>
    <body>
        <section class="engine"><a rel="external" href="https://mobirise.com">
            </a></section>
        <section class="mbr-navbar mbr-navbar--freeze mbr-navbar--absolute mbr-navbar--transparent mbr-navbar--sticky mbr-navbar--auto-collapse" id="menu-22">
            <div class="mbr-navbar__section mbr-section">
                <div class="mbr-section__container container">
                    <div class="mbr-navbar__container">
                        <div class="mbr-navbar__column mbr-navbar__column--s mbr-navbar__brand">
                            <span class="mbr-navbar__brand-link mbr-brand mbr-brand--inline">
                                <span class="mbr-brand__logo"><a href="index.jsp"><img class="mbr-navbar__brand-img mbr-brand__img" src="assets/images/untitled-382x276-11.png" alt="AgriMarket" title="Flower Shop"></a></span>
                                <span class="mbr-brand__name"><a class="mbr-brand__name text-white" href="index.jsp">AGRIMARKET</a></span>
                            </span>
                        </div>
                        <div class="mbr-navbar__hamburger mbr-hamburger text-white"><span class="mbr-hamburger__line"></span></div>
                        <div class="mbr-navbar__column mbr-navbar__menu">
                            <nav class="mbr-navbar__menu-box mbr-navbar__menu-box--inline-right">
                                <div class="mbr-navbar__column"><ul class="mbr-navbar__items mbr-navbar__items--right mbr-buttons mbr-buttons--freeze mbr-buttons--right btn-decorator mbr-buttons--active"><li class="mbr-navbar__item"><a class="mbr-buttons__link btn text-white" href="index.jsp">HOME</a></li> <li class="mbr-navbar__item"><a class="mbr-buttons__link btn text-white" href="about.jsp">ABOUT</a></li> <li class="mbr-navbar__item"><a class="mbr-buttons__link btn text-white" href="contact_us.jsp">CONTACT</a></li></ul></div>
                                <div class="mbr-navbar__column"><ul class="mbr-navbar__items mbr-navbar__items--right mbr-buttons mbr-buttons--freeze mbr-buttons--right btn-inverse mbr-buttons--active"><li class="mbr-navbar__item"><button type="submit"  class="mbr-buttons__btn btn btn-lg btn-default" onclick="saveChanges();">Save</button></div>
                                        </li></ul></div>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section class="mbr-section mbr-section--relative mbr-after-navbar" id="msg-box4-32" style="background-color: rgb(26, 188, 156);">

            <form action="index.html" method="post"  id="editForm">
                <div class="mbr-section__container mbr-section__container--isolated container">
                    <div class="row">
                        <div class="mbr-box__magnet mbr-box__magnet--top-left mbr-section__left col-sm-3">

                            <figure class="mbr-figure mbr-figure--adapted mbr-figure--caption-inside-bottom ">


                                <img class="mbr-figure__img" id="image" src="assets/images/617f67a372f0dfb8ee62533c431758ee.jpg" style="width: 59%;">

                                <input type="file"id="realImage" hidden="true" accept="image/*">
                            </figure>
                        </div>

                        <div class="mbr-box__magnet mbr-class-mbr-box__magnet--center-left col-sm-5 mbr-section__right">
                            <!--
                                                    <form action="index.html" method="post"  name="signUpForm">-->
                            <div class="mbr-section__container mbr-section__container--middle">
                                <div class="mbr-header mbr-header--auto-align mbr-header--wysiwyg">
                                    <input type="text" class="form-control" name="first-name" required="" value = '${sessionScope.user.userName}' placeholder="User Name*">

                                </div>
                            </div>
                            <div class="mbr-section__container mbr-section__container--middle">
                                <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                    <input type="text" class="form-control" name="job" required="" value = '${sessionScope.user.job}' placeholder="Job*">

                                </div>
                            </div>
                            <div class="mbr-section__container mbr-section__container--middle">
                                <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                    <input type="date" class="form-control" name="date" required="" value = '${sessionScope.user.BOD}' placeholder="Birth Date*">

                                </div>
                            </div>
                            <div class="mbr-section__container mbr-section__container--middle">
                                <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                    <input type="email" class="form-control" name="mail" required="" value = '${sessionScope.user.email}' placeholder="Email*">

                                </div>
                            </div>
                            <div class="mbr-section__container mbr-section__container--middle">
                                <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                    <input type="text" class="form-control" name="credit" required="" value = '${sessionScope.user.creditNumber}' placeholder="Credit Number*">

                                </div>
                            </div>
                            <div class="mbr-section__container mbr-section__container--middle">
                                <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                    <input type="text" class="form-control" name="address" required="" value = '${sessionScope.user.address}' placeholder="Address*">

                                </div>
                            </div>
                            <div class="mbr-section__container mbr-section__container--middle">
                                <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                    <jsp:include page="interests.html"/>

                                </div>
                            </div>
                            <!--
                                                    </form>-->
                        </div>

                    </div>
                </div>

            </form>
        </div>
    </section>


    <script src="assets/web/assets/jquery/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/smooth-scroll/SmoothScroll.js"></script>
    <script src="assets/mobirise/js/script.js"></script>


</body>
</html>