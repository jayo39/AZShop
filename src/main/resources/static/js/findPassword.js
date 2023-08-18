const codeInput = `
    <input type="text" class="form-control mt-1" id="code" placeholder="Enter the 5 digit code" name="code" required>
`

$(function() {
    const emailRegexPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    $("#getCode").click(function() {
        const inputEmail = $("#email").val();
        if(emailRegexPattern.test(inputEmail)) {
            $("#emailVal").val(inputEmail);
            checkEmail(inputEmail);
        } else {
            $("#emailHelp").text("Enter an email address.");
            return;
        }

    });
    $("#submit").click(function() {
        $("form[name='checkCode']").submit();
    });

});

function checkEmail(address) {
    $.ajax({
        url: "/user/checkEmail?address=" + address,
        type: "GET",
        cache: false,
        success: function(data, status, xhr){
            if(status == "success") {
                $('#email').attr('readonly', 'readonly');
                $("#getCode").attr('disabled', 'disabled');
                showStatus(data, address);
                return;
            }
        },
    });
}

function showStatus(data, address) {
    if(data) {
        $("#codeInput").append(codeInput);
        $("#emailHelp").text("Sent code!");
        $.ajax({
            url: "/user/sendMail?address=" + address,
            type: "POST",
            cache: false,
            success: function(data, status, xhr){
                if(status == "success") {
                    return;
                }
            },
        });
        $("#submit").removeAttr("disabled");
    } else {
        $("#emailHelp").text("This email is not registered.");
    }
}