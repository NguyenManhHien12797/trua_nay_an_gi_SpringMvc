
var merchant_id = document.querySelector('#userId');

var stompClient = null;
function connect() {
	console.log(merchant_id)
	var socket = new SockJS('/shopbaeFood/chat');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
	    stompClient.subscribe(`/topic/${merchant_id.value.trim()}`, function(data) {
			 var message = JSON.parse(data.body);
				console.log(message);
				if(message.status == 'order-pending'){
				swal({
		    	  title: "Có đơn hàng mới từ "+ message.user,
		    	  text: "Đi đến quản lý đơn hàng -->",
		    	  icon: "warning",
		    	  buttons: true,
		    	  dangerMode: true,
		    	})
		    	.then((ok) => {
		    	  if (ok) {
		    		  window.location="https://localhost:8443/shopbaeFood/merchant/order-manager/order-pending"; 
		    	  } 
		    	});
				}else{
					swal({
		    	  		title: "Shop "+message.merchant+" đã gửi hàng đơn hàng số: "+message.id,
		    	  		text: "Đi đến giỏ hàng -->",
		    	  		icon: "warning",
		    	  		buttons: true,
		    	  		dangerMode: true,
		    		})
		    		.then((ok) => {
		    	  		if (ok) {
		    		 		 window.location="https://localhost:8443/shopbaeFood/user/cart"; 
		    	 		 } 
		    		});
				}

        	
	    });
	});
	}
connect();