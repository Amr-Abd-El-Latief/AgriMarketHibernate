<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="generator" content="Mobirise v2.9, mobirise.com">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" href="assets/images/untitled-382x276-65.png" type="image/x-icon">
        <meta name="description" content="">
        <title>Sign Up</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:700,400&amp;subset=cyrillic,latin,greek,vietnamese">
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/mobirise/css/style.css">
        <link rel="stylesheet" href="assets/mobirise/css/mbr-additional.css" type="text/css">

        <%--<jsp:useBean id="user" scope="session" class="model.pojo.User"/>--%>
        <script src="assets/web/assets/jquery/jquery.min.js"></script>
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
            <c:forEach var="interest" items="${sessionScope.user.interests}">
                document.forms[0].${interest.name}.checked = true;
            </c:forEach>

                $('#creditInput').blur(function () {

                    $.get('signup?creditnumber=' + $('#creditInput').val(), renderCreditState);
                });

                $('#user-email').blur(function () {

                    $.post('signupemail?useremail=' + $('#user-email').val(), renderEmailState);
                });

            }

            );

            function saveChanges() {
                $("#editForm").submit();
            }


            function renderCreditState(responseTxt, statusTxt, xhr) {

                if (statusTxt == "success")
                {
//        alert("credit state  :" + responseTxt);

                    if (responseTxt == "") {
//            alert("empty responce (write)state  : " + responseTxt);
//            $("#creditInput").val("");
                        $("#credit-error").html(responseTxt);


                    } else {
//            $("#creditInput").val(responseTxt);
                        $("#credit-error").html(responseTxt);
                        $("#credit-error").focus();

                    }

                }
            }




            function renderEmailState(responseTxt, statusTxt, xhr) {

                if (statusTxt == "success")
                {
//        alert("credit state  :" + responseTxt);

                    if (responseTxt == "") {
//            alert("empty responce (write)state  : " + responseTxt);
//            $("#creditInput").val("");
                        $("#email-error").html(responseTxt);
                    } else {
//            $("#creditInput").val(responseTxt);
                        $("#email-error").html(responseTxt);
                        $("#email-error").focus();
                    }

                }
            }

//
            function validCard() {
                var cardStr = document.editForm.credit.value;
                if (cardStr != null && cardStr.length != 0) {
                    cardStr = cardStr.replaceAll('-/g', '');
                    if (isNaN(cardStr))
                        return false;
                    if (cardStr.length < 3)
                        return false;

                    // otherwise return true 
                    return true;
                } else
                    return false;
            }

            function trySubmit() {

                if (!validCard())
                    document.editForm.credit.setCustomValidity("Please enter a valid card number");
                else {
                    document.editForm.credit.setCustomValidity("");
                    return true;
                }
            }

        </script>
    </head>
    <body>
        <section class="engine"><a rel="external" href="https://mobirise.com">
            </a></section>
        <form action="signup" enctype="MULTIPART/FORM-DATA" method="post" name="editForm" id="editForm">

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
                                    <div class="mbr-navbar__column"><ul class="mbr-navbar__items mbr-navbar__items--right mbr-buttons mbr-buttons--freeze mbr-buttons--right btn-decorator mbr-buttons--active"><li class="mbr-navbar__item"><a class="mbr-buttons__link btn text-white" href="index.jsp">HOME</a></li></ul></div>
                                    <div class="mbr-navbar__column"><ul class="mbr-navbar__items mbr-navbar__items--right mbr-buttons mbr-buttons--freeze mbr-buttons--right btn-inverse mbr-buttons--active"><li class="mbr-navbar__item"><button onclick="trySubmit()" type="submit" class="mbr-buttons__btn btn btn-lg btn-default" >Sign Up</button></div>
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

                            <figure class="mbr-figure mbr-figure--adapted mbr-figure--caption-inside-bottom ">

                                <img class="mbr-figure__img" id="image" src="${sessionScope.user.imageUrl}" style="width: 59%;">

                                <c:if test="${empty sessionScope.user.imageUrl}">

                                    <script>

                                        $("#image").attr("src", "assets/images/user.png");
                                    </script>
                                </c:if>
                                <input type="file" id="realImage"  name="filename"  accept="image/*">
                            </figure>
                        </div>

                        <div class="mbr-box__magnet mbr-class-mbr-box__magnet--center-left col-sm-5 mbr-section__right">
                            <!--
                                                    <form action="index.html" method="post"  name="signUpForm">-->
                            <div class="mbr-section__container mbr-section__container--middle">
                                <div class="mbr-header mbr-header--auto-align mbr-header--wysiwyg">
                                    <input type="text" class="form-control" name="name" required="" placeholder="User Name*">

                                </div>
                            </div>

                            <div class="mbr-section__container mbr-section__container--middle">
                                <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                    <input id="user-email" type="email" class="form-control" name="mail" required=""  placeholder="Email*">

                                </div>
                            </div>

                            <div class="mbr-section__container mbr-section__container--middle">
                                <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                    <h5 id='email-error'></h5>
                                </div>
                            </div>







                            <div class="mbr-section__container mbr-section__container--middle">
                                <div class="mbr-header mbr-header--auto-align mbr-header--wysiwyg">
                                    <input type="password" class="form-control" name="password" required="" value = '' placeholder="Password*">

                                </div>
                            </div>
                            <div class="mbr-section__container mbr-section__container--middle">
                                <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                    <input  type="text" class="form-control" name="job" required="" value = '' placeholder="Job*">

                                </div>
                            </div>
                            <div class="mbr-section__container mbr-section__container--middle">
                                <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                    <input type="date" class="form-control" name="date" min="1920-01-01" max="2010-12-29" required="" value = '' placeholder="Birth Date*">

                                </div>
                            </div>
                            <div class="mbr-section__container mbr-section__container--middle">
                                <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                    <input type="text" class="form-control" name="address" required="" value = '' placeholder="Address*">

                                </div>
                            </div>

                            <div class="mbr-section__container mbr-section__container--middle">
                                <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                    <input id="creditInput" type="text" class="form-control" name="credit"    value = '' placeholder="Credit Number*">

                                </div>
                            </div>

                            <div class="mbr-section__container mbr-section__container--middle">
                                <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                    <h5 id='credit-error'></h5>
                                </div>
                            </div>
                            <div class="mbr-section__container mbr-section__container--middle">
                                <div class="mbr-article mbr-article--auto-align mbr-article--wysiwyg">
                                    <jsp:include page="interests.html"/>

                                </div>
                            </div>
                        </div>

                    </div>
                </div>

                </div>
            </section>

        </form>
        <script src="assets/web/assets/jquery/jquery.min.js"></script>
        <script src="assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/smooth-scroll/SmoothScroll.js"></script>
        <script src="assets/mobirise/js/script.js"></script>
    </body>
</html>