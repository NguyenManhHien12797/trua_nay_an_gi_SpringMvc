function addToCart(price,user_id,product_id) {
	if(user_id == null){
		window.location="https://localhost:8443/shopbaeFood/login?mess=not-logged-in";
	}

    let CartDTO = {
    	price: price,
    	user_id: user_id,
    	product_id: product_id
    		
    };
   
	
    $.ajax({
    	headers: {
             'Accept': 'application/json',
             'Content-Type': 'application/json'
         },
        type: "POST",
        data: JSON.stringify(CartDTO),
        //tên API
        url:`/shopbaeFood/user/addToCart`,
        //xử lý khi thành công
        success: function (data) {
      	console.log("suss");  
    	swal("Đã thêm vào giỏ hàng!"," ","success");
        },
        error: function (data){
        	console.log("err"); 
		
		    
		    swal({
		    	  title: "Vui lòng đặt hàng của cửa hàng trước rồi tiếp tục!",
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

      })
}

