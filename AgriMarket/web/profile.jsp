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
        <title>Profile</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:700,400&amp;subset=cyrillic,latin,greek,vietnamese">
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/mobirise/css/style.css">
        <link rel="stylesheet" href="assets/mobirise/css/mbr-additional.css" type="text/css">

        <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
        <c:if test="${empty sessionScope.user}">

            <script>
                location.href = "sign_in.jsp";
            </script>
        </c:if>

    </head>
    <body>
        <section class="engine"><a rel="external" href="https://mobirise.com">Mobirise mac website builder
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
                                <div class="mbr-navbar__column"><ul class="mbr-navbar__items mbr-navbar__items--right mbr-buttons mbr-buttons--freeze mbr-buttons--right btn-inverse mbr-buttons--active"><li class="mbr-navbar__item">
                                            <a class="mbr-buttons__btn btn btn-default" id="topBtn" href="profile.jsp">${sessionScope.user.userName}</a>
                                            <c:if test="${empty sessionScope.user}">

                                                <script>
                                                    $("#topBtn").html("Sign In");
                                                    
                                                </script>
                                            </c:if>
                                        </li></ul></div>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section class="mbr-section mbr-section--relative mbr-after-navbar" id="msg-box4-32" style="background-color: rgb(26, 188, 156);">

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
                                        location.href = 'Temp';
                                    }
                                </script>
                                <button  onclick='edit_profile()' class="mbr-buttons__btn btn btn-lg btn-link">Edit profile</button>

                            </div>
                        </div>
                    </div>
                    <div class="mbr-box__magnet mbr-class-mbr-box__magnet--center-left col-sm-6 mbr-section__right">
                        <div class="mbr-section__container mbr-section__container--middle">
                            <div class="mbr-header mbr-header--auto-align mbr-header--wysiwyg">
                                <!--<h3 class="mbr-header__text">Awad Hussien</h3>-->

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
                                <script>
                                    var interests = "Interested in : ";
                                    <c:forEach var = "interest" items = "${sessionScope.user.interests}">
                                    interests += "${interest}" + ",";
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