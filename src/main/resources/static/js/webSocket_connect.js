var reconnectCount = 0;
var maxReconnectCount = 5;

function connect() {
    var socket = new WebSocket('ws://' + window.location.host + '/webSocketHandler');

    socket.onopen = function(event) {
        // 웹소켓 연결이 수립되면 호출됩니다.
        console.log("알림 웹소켓 연결 수립됨");
    };
    socket.onmessage = function(event) {
        var message = JSON.parse(event.data);
        console.log("메세지를 받음");

        if (message.type === "Receipt") {
            // Extract the data from the message
            var orderId = message.orderid;
            var memberId = message.memberid;
            var key = message.key;

            if(key === "1"){
                alert("주문이 접수되었습니다");
            }
            else if(key === "0"){
                alert("주문이 취소되었습니다");
            }
        }
    };
    socket.onclose = function(event) {
        if(reconnectCount < maxReconnectCount){
            console.log("재연결 시도");
            reconnectCount++;
            setTimeout(connect, 1000);
        }
        else{
            console.log("재연결 횟수 초과")
        }
   };
}

// 웹소켓 연결 시도
connect();