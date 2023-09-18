$(document).ready(function() {
    $(function () {
        var token = $("meta[name='_csrf']").attr('content');
        var header = $("meta[name='_csrf_header']").attr('content');
        if(token && header) {
            $(document).ajaxSend(function(event, xhr, options) {
                xhr.setRequestHeader(header, token);
            });
        }
    });

    var socket = new WebSocket('ws://' + window.location.host + '/webSocketHandler');

    $(".close-btn").click(function() {
        var link = '/cartList';
        location.href=link;
    })

    $(".order-btn").click(function() {
        orderPayment();
    })

    // 랜덤번호 생성기
    function combinationfunc() {
            let memberId = $(".member-id").val();
            let currDate = new Date().toISOString().slice(0, 10).replace(/-/g, '');
            let randomNum = Math.floor(Math.random() * 10).toString();
            let combinationNum;

            for (let i = 0; i < 4; i++) {
                randomNum += Math.floor(Math.random() * 10).toString();
            }
            console.log("memberId : "+memberId);
            if(memberId == 0){
                console.log("======="+typeof(memberId));
                console.log(memberId);
                combinationNum = currDate + randomNum;
            } else{
                combinationNum = memberId + currDate + randomNum;
            }
            return combinationNum;
    }

    function orderPayment(){
        let today = new Date();

        var year = today.getFullYear();
        var month = ('0' + (today.getMonth() + 1)).slice(-2);
        var day = ('0' + today.getDate()).slice(-2);
        var hours = ('0' + today.getHours()).slice(-2);
        var minutes = ('0' + today.getMinutes()).slice(-2);
        var seconds = ('0' + today.getSeconds()).slice(-2);

        var orderDate = year + '-' + month  + '-' + day + ' ' + hours + ':' + minutes  + ':' + seconds;

        const data = {
            type: "order",
            storeid : $(".store-id").val(),
            storeName : $(".store-name").val(),
            memberid : $(".member-id").val(),
            paymentMethod : $("input[type='radio']:checked").val(),
            orderTotalPrice : $(".payment-total").val(),
            orderDate : orderDate,
            orderRequest : $("textarea[name='request']").val(),
            orderid : combinationfunc()
        }
        console.log("data:"+data.orderid);
        $.ajax({
            url: "/orderInsert",
            type: "post",
            data : data,
            traditional : true
        })
        .done(function(){
            console.log(data);
        })
        .fail(function(){
            console.log("error");
        })

        /* 웹소켓 부분*/
        if (socket.readyState !== WebSocket.OPEN) {
            // WebSocket이 연결되지 않은 상태일 때 실행될 코드
            console.log("연결 안됨");
        }
        else{
        // 서버에게 주문 정보 전송
        socket.send(JSON.stringify(data));
        console.log("값을 보냄");
        }

        var link = '/orderComplete';
        location.href=link;
    }
});