var myMessages = ['info','warning','error','success'];

$(document).ready(function(){

    // Initially, hide them all
    hideAllMessages();

    // When message is clicked, hide it
    $('.message').click(function(){
        $(this).animate({top: -$(this).outerHeight()}, 500);
    });

	//Listener for submit event
	$("#sign-up-form").submit(function() {

        hideAllMessages();
		register();
		return false;
	});
});

function register() {

	//Display loader
	$("#overlay").fadeIn(150);

    //Get email and format JSON
    var email = $("#email-field").val();
    var data = '{"email": "' + email + '"}';

    //Post JSON
    $.ajax({
        type: "POST",
        url: "/register",
        dataType: "text",
        data: data,
        success: registrationSuccess,
        error: registrationError});
}

function registrationSuccess(data, textStatus, jqHXR) {

    //Fade out overlay and then call clearForm function
    $("#overlay").fadeOut(150, function() {
        clearForm();
        showMessage("success");
        console.log("success");
    });
}

function registrationError(jqXHR, textStatus, errorThrown) {

    //Fade out overlay and then display the error message
    $("#overlay").fadeOut(150, function() {
        showMessage("error", errorThrown);
        console.log("error");
    });
}

function clearForm() {

    //Clear all input fields
    $("#sign-up-form :input[type=text]").val("");
}

function hideAllMessages()
{
    var messagesHeights = new Array(); // this array will store height for each

    for (i=0; i<myMessages.length; i++) {
        messagesHeights[i] = $('.' + myMessages[i]).outerHeight(); // fill array
        $('.' + myMessages[i]).css('top', -messagesHeights[i]); //move element outside viewport
    }
}

function showMessage(type, text)
{
    $('.' + type).find(".message-text").text(text);
    $('.' + type).animate({top:"0"}, 500);
}



