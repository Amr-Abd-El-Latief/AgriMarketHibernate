/* Card.js plugin by Jesse Pollak. https://github.com/jessepollak/card */


//modify product
 
 


$("#close-btn-signup").click(function () {
    $("#signup").css("display", "none");
    $("#nav-right").css("display", "block");
    $(".footer").css("display", "block");

});


$(".modify-product").click(function () {
    $("#signup").css("display", "block");


});






// validation product form 
function validateProductForm() {
    var regFlo = /[-+]?(\d*[.])?\d+/;
    var regNum = /^\d+$/;
    var product_price = $("#price").val();
    var product_quantity = $("#quantity").val();
    if (!regFlo.test(product_price) || !regNum.test(product_quantity)) {
        $(".add-form").text("Quantity(whole number)  and price  must be number only").fadeToggle(5000, function () {
            $(".add-form").css("display", "none");
        });
        $(".add-form").css("color", "red");
        return false;
    } else if ($('.pcategory').val() == 'Select you category') {
        $(".add-form").text("please choose category").fadeToggle(5000, function () {
            $(".add-form").css("display", "none");
        });
        $(".add-form").css("color", "red");
        return false;
    }
    return true;
}


// validation modify product form 
function validateModifyProductForm() {
    var regFlo = /[-+]?(\d*[.])?\d+/;
    var regNum = /^\d+$/;
    var product_price = $("#price-modify").val();
    var product_quantity = $("#quantity-modify").val();
    if (!regFlo.test(product_price) || !regNum.test(product_quantity)) {
        $(".add-form").text("Quantity(whole number)  and price  must be number only").fadeToggle(5000, function () {
            $(".add-form").css("display", "none");
        });
        $(".add-form").css("color", "red");
        return false;
    }
    return true;
}


$('form').card({
    container: '.card-wrapper',
    width: 280,

    formSelectors: {
        nameInput: 'input[name="first-name"], input[name="last-name"]'
    }
});


 