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

    addCartList();

    function addCartList(){
    	$.ajax({
    		url: "/requestCartList",
    		type: "get",
    	})
    	.done(function(result){
    	    if(result == ""){
    	        return;
    	    }
    	    console.log(result)
    	    cartList(result);
    	})
    	.fail(function(){
    		console.log("error");
    	})
    }

    function cartList(result){
    	const cartList = result.cartDto;
    	const listTotal = result.listTotal;

    	let html = "";

    	for(let i = 0;i < cartList.length;i++) {
    		html += `<li>
    		    <div class="cart-detail">
    			<h2>${cartList[i].cartName}</h3>
    			<div>${cartList[i].cartPrice.toLocaleString()}원</div>
    			<div>수량 : ${cartList[i].cartAmount }</div>
    			<div>가격 : ${cartList[i].cartTotal.toLocaleString() }원</div>
    			</div>
    		    </li>`;
    	}
    	$(".cart ul").html(html);
        $(".list-total-price").html("주문 금액 : " + listTotal.toLocaleString() + "원");

    	if(listTotal > 0){
    	    $(".order-btn").attr("disabled", false);
    	}
    }

    $(".cart-all-delete").click(function() {
        allDelete();
    })

    function allDelete(){
        $.ajax({
            method : "DELETE",
            url : "/cartAllDelete"
        })
        .done(function(){
            console.log("success");
            cartClean()
        })
        .fail(function(){
            console.log("error");
        })
    }

    function cartClean(){
        let html = "";
        $(".cart ul").html(html);
        $(".list-total-price").html("텅 비었어요...");
        $(".order-btn").attr("disabled", true);
    }

    $(".order-btn").click(function() {
        var link = '/order';
        location.href=link;
    })
});