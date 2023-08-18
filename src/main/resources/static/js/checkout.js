function fillForm() {
    const selectedOption = $('#address').val();
    const selectedAddress = addresses.filter(address => address.id == selectedOption);

    const postcodeForm = $('#postcode');
    const addressForm = $('#deliveryAddress');
    const detailForm = $('#deliveryDetail');

    if(selectedAddress.length == 0) {
        postcodeForm.val('');
        addressForm.val('');
        detailForm.val('');
    }

    selectedAddress.forEach(address => {
        postcodeForm.val(address.postcode);
        addressForm.val(address.address);
        detailForm.val(address.address_detail);
    });
}

function focusNext(inputElement, nextElementId) {
    var inputLength = inputElement.value.length;
    if (inputLength === 4) {
        var nextElement = $('#' + nextElementId);
        nextElement.focus();
    } else if (inputLength === 2 && nextElementId === 'cardYear') {
        var nextElement = $('#' + nextElementId);
        nextElement.focus();
    }
}