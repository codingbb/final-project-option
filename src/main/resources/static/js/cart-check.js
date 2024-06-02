

let cartList = [];

document.querySelector("#purchaseButton").addEventListener("click", function (e) {
    e.preventDefault(); // 기본 폼 제출을 방지

    let offerTable = document.querySelectorAll(".my-cart-list");

    offerTable.forEach(value => {

        let checkBox = value.querySelector(".item-check");

        if(checkBox.checked){
            let cartId = value.querySelector(".cartId").value; // 수정된 부분
            let optionId = value.querySelector(".optionId").value; // 수정된 부분
            let orderQty = value.querySelector(".orderQty-class").getAttribute("data-order-qty"); // 수정된 부분

            //console.log(cartId);
            //console.log(buyQty);

            let checkedCart = {
                cartId: cartId,
                optionId: optionId,
                orderQty: orderQty,
                status: checkBox.checked ? true : false
            };

            cartList.push(checkedCart);
        }
    });
    console.log(cartList);

    $.ajax({
        url: '/cart/update',
        data: JSON.stringify(cartList),
        contentType: 'application/json; charset=utf-8',
        type: 'POST'

    }).done((res)=>{
        // alert("성공");
        console.log(res.message);
        location.href = "/order-save-form"

    }).fail((res)=>{
        console.log(res);

        if (res.responseJSON.status === 400) {
            alert(res.responseJSON.msg);
            location.reload();
        } else {
            location.href = "/order-save-form"
        }

    });
});

