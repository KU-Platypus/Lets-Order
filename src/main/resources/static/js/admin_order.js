$(function () {
    var token = $("meta[name='_csrf']").attr('content');
    var header = $("meta[name='_csrf_header']").attr('content');
    if(token && header) {
        $(document).ajaxSend(function(event, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    }
});

var index = 0;
var reconnectCount = 0;
var maxReconnectCount = 5;

// 페이지가 로드되자마자 버튼 확인
window.onload = function() {
    var orderReceives = document.querySelectorAll('[id^="receive"]');
    orderReceives.forEach(function(orderReceive) {
        var value = orderReceive.value;
        if(value === "1"){
            // "주문 취소" 버튼을 숨김
            document.getElementById('cancel' + index).style.display = 'none';
            // "주문 접수" 버튼을 비활성화
            document.getElementById('accept' + index).disabled = true;
            document.getElementById('accept' + index).textContent = '접수 완료';
        }
        else if (value === "0"){
            // "주문 접수" 버튼을 숨김
            document.getElementById('accept' + index).style.display = 'none';
            // "주문 취소" 버튼을 비활성화
            document.getElementById('cancel' + index).disabled = true;
            document.getElementById('cancel' + index).textContent = '취소된 주문';
        }
        index++;
    });
}

function connect() {
    var socket = new WebSocket('ws://' + window.location.host + '/webSocketHandler');

    const orderContainer = document.querySelector('.order-list');

    socket.onopen = function(event) {
        // 웹소켓 연결이 수립되면 호출됩니다.
        console.log("연결 수립됨");
    };
    socket.onmessage = function(event) {
        // 서버로부터 메시지를 받으면 호출됩니다.
        console.log("메세지를 받음");

        // JSON 문자열을 객체로 변환
        const order = JSON.parse(event.data);

        // 새로운 주문 추가
        const orderElement = document.createElement('div');
        orderElement.classList.add('card', 'mb-3');
        orderElement.innerHTML = `
            <div class="row g-1 order_box">
                <div class="col-auto">
                    <div class="card-body">
                        <h5 class="card-title">주문번호 : ${order.orderid}</h5>
                        <p class="card-text">${order.orderTotalPrice}원</p>
                        <p>${order.orderDate}</p>
                        <input type ="hidden" id="receive${index}" value="${order.orderReceive}">
                        <button class="btn btn-light" id="accept${index}" onclick="AcceptBtnClick(${index},${order.orderid},${order.memberid})">주문 접수</button>
                        <button class="btn btn-light" id="cancel${index}" onclick="CancelBtnClick(${index},${order.orderid},${order.memberid})">주문 취소</button>
                        <button class="btn btn-light" id="${order.orderid}" onclick="openModal(event)">상세 보기</button>
                    </div>
                </div>
            </div>
            `;
            orderContainer.insertBefore(orderElement, orderContainer.firstChild)
            index++;

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

/** ======= 주문 접수/취소 ======== **/
var data;

function orderReceiveSave(){ // 주문 여부를 저장
    $.ajax({
        url: '/admin/orderReceiveSave',
        type: 'post',
        data: JSON.stringify(data),
        contentType: "application/json",
        traditional: true
    });
}

function AcceptBtnClick(index, orderid, memberid) {
    var socket = new WebSocket('ws://' + window.location.host + '/webSocketHandler');
    var key = "1";
    var jsonData = JSON.stringify({type: "Receipt", orderid: orderid, memberid: memberid, key : key});

    // "주문 취소" 버튼을 숨김
    document.getElementById('cancel' + index).style.display = 'none';
    // "주문 접수" 버튼을 비활성화
    document.getElementById('accept' + index).disabled = true;
    document.getElementById('accept' + index).textContent = '접수 완료';
    data = {
        orderId: orderid,
        key: "1"
    };
    orderReceiveSave();

    socket.onopen = function(event) {
        // 웹소켓 연결이 수립되면 호출됩니다.
        console.log("수락 알림 연결 수립됨");
        socket.send(jsonData);
        };
}

function CancelBtnClick(index, orderid, memberid) {
    var socket = new WebSocket('ws://' + window.location.host + '/webSocketHandler');
    var key = "0";
    var jsonData = JSON.stringify({type: "Receipt", orderid: orderid, memberid: memberid, key : key});

    // "주문 접수" 버튼을 숨김
    document.getElementById('accept' + index).style.display = 'none';
    // "주문 취소" 버튼을 비활성화
    document.getElementById('cancel' + index).disabled = true;
    document.getElementById('cancel' + index).textContent = '취소된 주문';
    data = {
            orderId: orderid,
            key: "0"
    };
    orderReceiveSave();

    socket.onopen = function(event) {
        // 웹소켓 연결이 수립되면 호출됩니다.
        console.log("취소 알림 연결 수립됨");
        socket.send(jsonData);
        };
}

/** ======= 모달창 요소 ======== **/
var modal = document.getElementById("orderModal");
// 닫기 버튼 요소 가져오기
var span = document.getElementsByClassName("close")[0];
var orderid;

// 버튼 클릭 시 모달창 열기
function openModal(event) {
    // orderid 변수의 값을 변경하기
    orderid = event.target.id;

    modal.style.display = "block";

    var url = '/admin/OrderViewDetail?code=' + orderid;
    $('#orderModal .modal-body').load(url);
}

// 닫기 버튼 클릭 시 모달창 닫기
span.onclick = function() {
    modal.style.display = "none";
}

// 모달창 외부 클릭 시 모달창 닫기
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}