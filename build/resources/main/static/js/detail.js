$(function() {
    const increaseButton = document.getElementById('increaseButton');
    const decreaseButton = document.getElementById('decreaseButton');
    const error = document.getElementById('error');
    const countInput = document.getElementById('countInput');
    const buyButton = document.getElementById('buybutton');
    const cartButton = document.getElementById('cartbutton');
    const amount = document.getElementById('amount');
    const total = document.getElementById('total');
    let count = 1;
    amount.value = count;
    total.value = singlePrice;

    if (logged_id == null) {
        $("#buybutton").click(function() {
            window.location.href = "/user/login";
        });
        $("#cartbutton").click(function() {
            window.location.href = "/user/login";
        });
    }

    if(count > stock) {
        error.classList.remove("d-none");
    }

    increaseButton.addEventListener('click', () => {
      count++;
      countInput.value = count;
      amount.value = count;
      total.value = count*singlePrice;
      if(count > stock) {
          error.classList.remove("d-none");
          cartButton.setAttribute('disabled', 'disabled');
          buyButton.setAttribute('disabled', 'disabled');
      }
    });

    decreaseButton.addEventListener('click', () => {
      if(count == 1) {
        return;
      }
      count--;
      countInput.value = count;
      amount.value = count;
      total.value = count*singlePrice;
      if((count == stock || count < stock)) {
          error.classList.add("d-none");
          cartButton.removeAttribute('disabled');
          buyButton.removeAttribute('disabled');
      }
    });

    $('#delete').click(function(){
        let answer = confirm("Delete product?");
        if(answer) {
            $("form[name='frmDelete']").submit();
        }
    });

    if (logged_id != null) {
        $("#buybutton").click(function() {
            $("form[name='buyform']").submit();
        });
        $("#cartbutton").click(function() {
            $("form[name='cartform']").submit();
        });
    }
});
