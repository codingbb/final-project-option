

let cartList = [];

document.querySelector("#purchaseButton").addEventListener("click", function (e) {
    e.preventDefault(); // 기본 폼 제출을 방지

    let offerTable = document.querySelectorAll(".my-cart-list");

    offerTable.forEach(value => {

        let checkBox = value.querySelector(".item-check");

        if(checkBox.checked){
            let cartId = value.querySelector("input[name='cartId']").value; // cartId 가져오는 방식 수정
            let orderQty = value.querySelector(".orderQty").value; // orderQty 가져오는 방식 수정
            let productId = value.querySelector("input[name='productId']").value;

            //console.log(cartId);
            //console.log(buyQty);

            let checkedCart = {
                cartId: cartId,
                productId: productId,
                orderQty: orderQty,
                isChecked: checkBox.checked ? true : false
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
        alert("실패입니다.");
    });
});

