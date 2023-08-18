$(function() {
    let total = 0;
    $('.amount').each(function() {
        total += parseInt($(this).text());
    });
    $('#total').text(total);
})

