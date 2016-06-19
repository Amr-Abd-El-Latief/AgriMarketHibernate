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
        <title>Sign Up</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:700,400&amp;subset=cyrillic,latin,greek,vietnamese">
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/mobirise/css/style.css">
        <link rel="stylesheet" href="assets/mobirise/css/mbr-additional.css" type="text/css">



    </head>
    <body>
        <section class="engine"><a rel="external" href="https://mobirise.com">Mobirise free website builder software
            </a></section>
        <section class="mbr-navbar mbr-navbar--freeze mbr-navbar--absolute mbr-navbar--transparent mbr-navbar--sticky mbr-navbar--auto-collapse" id="menu-0">
            <div class="mbr-navbar__section mbr-section">
                <div class="mbr-section__container container">
                    <div class="mbr-navbar__container">
                        <div class="mbr-navbar__column mbr-navbar__column--s mbr-navbar__brand">
                            <span class="mbr-navbar__brand-link mbr-brand mbr-brand--inline">
                                <span class="mbr-brand__logo"><a href="index.jsp"><img class="mbr-navbar__brand-img mbr-brand__img" src="assets/images/untitled-382x276-41.png" alt="AgriMarket" title="AgriMarket"></a></span>
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
        </section>

        <section class="mbr-section mbr-section--relative mbr-section--fixed-size mbr-parallax-background mbr-after-navbar" id="form1-21" style="background-image: url(assets/images/istock-000016896298xlarge-4200x2833-56.jpg);">
            <div class="mbr-section__container mbr-section__container--std-padding container">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="row">
                            <div class="col-sm-8 col-sm-offset-2">
                                <form action="index.jsp" method="post"  name="signUpForm">
                                    <div class="form-container">
                                        <div class="row">
                                            <div class="mbr-box mbr-box--fixed mbr-box--adapted">
                                                <div class="mbr-box__magnet mbr-box__magnet--top-left mbr-section__left col-sm-6">

                                                    <div class="form-group">
                                                        <input type="text" class="form-control" name="first-name" required="" placeholder="First Name*">
                                                    </div>

                                                    <div class="form-group">
                                                        <input type="text" class="form-control" name="last-name" required="" placeholder="Last Name*">
                                                    </div>

                                                    <div class="form-group">
                                                        <input class="form-control" required="" id="input-field" type="text" name="number" placeholder="Card Number"/>
                                                    </div>

                                                    <script>
                                                        
                                                        function validCard() {
                                                            var cardStr = document.signUpForm.number.value;
                                                            cardStr = cardStr.replace(/\s+/g, '');
                                                            if (isNaN(cardStr))
                                                                return false;
                                                            if (cardStr.length < 16)
                                                                return false;

                                                            // otherwise return true 
                                                            return true;
                                                        }
                                                        
                                                        function validCvc() {
                                                            var cvcStr = document.signUpForm.cvc.value;
                                                            cvcStr = cvcStr.replace(/\s+/g, '');
                                                            if (isNaN(cvcStr))
                                                                return false;
                                                            if (cvcStr.length < 4)
                                                                return false;

                                                            // otherwise return true 
                                                            return true;
                                                        }
                                                        
                                                        function validDate() {
                                                            var month, // month 
                                                                    year;     // year 
                                                            var dateStr = document.signUpForm.expiry.value;
                                                            arr = dateStr.replace(/\s+/g, '').split("/");
                                                            if (arr.length != 2)
                                                                return false;
                                                            month = parseInt(arr[0]);
                                                            year = parseInt(arr[1]);
                                                            if (isNaN(month) || isNaN(year))
                                                                return false;
                                                            
                                                            var date=new Date(year,month-1);
                                                            if(date<=new Date())
                                                                return false;
//                                                            if (month < 1 || month > 12 || year < parseInt(new Date().getFullYear()))
//                                                                return false;

                                                            // otherwise return true 
                                                            return true;
                                                        }
                                                        function trySubmit() {
                                                            
                                                            if (!validCard())
                                                                document.signUpForm.number.setCustomValidity("Please enter a valid card number");
                                                           else
                                                                document.signUpForm.number.setCustomValidity("");
                                                            
                                                            if (!validDate())
                                                                document.signUpForm.expiry.setCustomValidity("Please enter a valid date");
                                                           else
                                                                document.signUpForm.expiry.setCustomValidity("");
                                                           
                                                            if (!validCvc())
                                                                document.signUpForm.cvc.setCustomValidity("Please enter a 4 digit number");
                                                           else
                                                                document.signUpForm.cvc.setCustomValidity("");
                                                           
                                                           
                                                                
                                                        }

                                                    </script>

                                                    <div class="form-group">
                                                        <input class="form-control" required="" id="column-left" type="text" name="expiry" placeholder="Expiry Date MM / YY"/></div>
                                                    <div class="form-group">
                                                        <input class="form-control" required="" id="column-right" type="text" name="cvc" placeholder="CCV"/></div>

                                                </div>
                                                <div class="mbr-box__magnet mbr-class-mbr-box__magnet--center-left col-sm-6 mbr-section__right">

                                                    <div class="card-wrapper"></div>

                                                    <div class="form-group">
                                                        <input type="email" class="form-control" name="email" required="" placeholder="Email*">
                                                    </div>

                                                    <div class="form-group">
                                                        <input type="tel" pattern="^([0|\+[0-9]{1,5})?([1-9][0-9]{9})$"  class="form-control" required="" name="phone" placeholder="Phone*">
                                                    </div>

                                                </div>

                                            </div>
                                        </div>
                                    </div>

                                    <div class="mbr-buttons mbr-buttons--right"><button  id="input-button"  type="submit" onclick="trySubmit();" class="mbr-buttons__btn btn btn-lg btn-danger">SIGN UP</button></div>

                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <script src='assets/js/jquery.min.js'></script>
        <script src='assets/js/card.js'></script>
        <script src='assets/js/jquery.card.js'></script>
        <script src="assets/js/index.js"></script>


        <script src="assets/web/assets/jquery/jquery.min.js"></script>
        <script src="assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/smooth-scroll/SmoothScroll.js"></script>
        <!--[if lte IE 9]>
          <script src="assets/jquery-placeholder/jquery.placeholder.min.js"></script>
        <![endif]-->
        <script src="assets/jarallax/jarallax.js"></script>
        <script src="assets/mobirise/js/script.js"></script>


    </body>
</html>