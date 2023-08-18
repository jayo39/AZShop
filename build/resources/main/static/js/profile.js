$(function() {
    var successValue = $("#success").text();
    var failValue = $("#fail").text();
    if(successValue !== null && successValue !== "") {
        $("#success").fadeIn(1000, function() {
            $(this).delay(1500).fadeOut(3000);
        });
    }
    else if(failValue !== null && failValue !== "") {
        $("#fail").fadeIn(1000, function() {
            $(this).delay(1500).fadeOut(3000);
        });
    }
    $("#profileSave").click(function() {
        $("form[name='profileSave']").submit();
    });

    $('#delete').click(function(){
        let answer = confirm("Delete account?");
        if(answer) {
            $("form[name='deleteAcc']").submit();
        }
    });

});