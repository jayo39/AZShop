$(function() {
    refreshTotal();

    $('.productAmount').on('change', function() {
        const productId = $(this).attr('id').replace('amountSelect-', '');
        const amount = $(`#amountSelect-${productId}`).val();
        const singlePrice = parseInt($(`#singlePrice-${productId}`).val());
        changeValues(productId, singlePrice, amount);

        const data = {
            "product_id": productId,
            "amount": amount,
        };

        $.ajax({
            type: 'POST',
            url: '/cart/update',
            data: data,
            success: function() {
                return
            },
            error: function() {
                return
            }
        });

    });
});

function changeValues(productId, singlePrice, amount) {
    var priceT = singlePrice * amount;
    $(`#price-${productId}`).text(priceT);
    refreshTotal();
}

function refreshTotal() {
    let total = 0;
    $('.price').each(function() {
        total += parseInt($(this).text());
    });
    $('#total').text(total);
    $('#totalinput').val(total);
}
