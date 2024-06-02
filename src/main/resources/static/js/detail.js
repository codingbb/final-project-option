//클릭한 옵션 값을 html 디자인 넣어서 주려고
$(".panel").click(function () {
    console.log(this);

    let optionId = $(this).attr('id');
    console.log(optionId);
    let optionName = $(this).text().split(" (")[0];
    // console.log(optionName);
    // span 안에 있어서 data("price")로 못찾아왔었음
    let price = $(this).find("span").data("price");
    // console.log(price);

    //중복제거
    let isOptionSelected = false;

    $("#selectedOptions .selected-item").each(function () {
        if (optionId === $(this).attr('id')) {
            isOptionSelected = true;
            return false;
        }
    });

    //디자인 넣기
    if (!isOptionSelected) {
        let newElement = $(`<div class='selected-item' id='${optionId}'>
                                                    ${optionName} - <span id="${optionId}price-class" class="${optionId}price-class price-class" data-price="${price}">${price}</span>

                                                     <div class="quantity-controls d-flex"
                                                      style="width: 100px; text-align: center; margin:0 auto">
                                                     <button type="button" class="decrease-btn" aria-label="수량내리기">-</button>

                                                   <input type="text" class="quantity ${optionId}orderQty" id="orderQty" name="orderQty"
                                                        data-order-qty="" value="1" style="text-align: center;">

                                                     <button type="button" class="increase-btn" aria-label="수량올리기">+</button>
                                                     </div></div>`);

        $("#selectedOptions").append(newElement);

        // newElement가 추가되면 바로 총합계가 상품 가격란에 계산되게
        updateTotal();

        //여기
        $(document).on("input", `.${optionId}orderQty`, function () {
            // 이벤트 위임 방식으로 이벤트 바인딩
            // 수량이랑 값 추출해서 합계 금액 넣을라고

            // alert("이벤트 발생");
            let orderQty = $(`.${optionId}orderQty`).val();
            // console.log("주문수량 " + orderQty);

            let price = $(`.${optionId}price-class`).data("price");
            // console.log("가격 " + price);

            let total = orderQty * price;
            $(`#${optionId}price-class`).text(total);
            // console.log("토탈" + total)

            updateTotal();
        });
    }
    $("#selectedOptions").show();

});

//재사용 위해서 뺌
function updateTotal() {
    // 선택된 아이템을 기준으로 총합계를 계산해야함
    let allTotal = 0;
    $(`.price-class`).each(function () {
        // console.log("변환 전 텍스트: ", $(this).text());

        let priceText = $(this).text().trim();
        // 숫자로 시작하는 부분을 찾아서 변환합니다.
        let matches = priceText.match(/\d+/);
        let priceTotal = 0;
        if (matches) {
            priceTotal = parseInt(matches[0], 10); // 10진수로 변환
        }
        // console.log("낱개토탈 " + priceTotal);
        allTotal += priceTotal;
    });

    $("#allTotal").val(allTotal);

}
