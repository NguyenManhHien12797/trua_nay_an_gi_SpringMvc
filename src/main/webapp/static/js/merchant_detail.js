function addToCart(price,user_id,product_id) {
	
	if(user_id == null){
		window.location="https://localhost:8443/shopbaeFood/login?mess=not-logged-in";
	}
	console.log("user_id: "+user_id);
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
      	console.log(data); 
      	if(data.message == "user-not-logged-in"){
			  window.location="https://localhost:8443/shopbaeFood/login?mess=not-logged-in";
			}
      	if(data.message == "not-logged-in"){
			  window.location="https://localhost:8443/shopbaeFood/login?mess=not-logged-in";
			}
      	if(data.message == "add suscess"){
			swal("Đã thêm vào giỏ hàng!"," ","success");
		  }else{
			  if(data.message == "out of stock"){
				swal("Sản phẩm đã hết hàng. Vui lòng chọn sản phẩm khác!"," ","warning");
			  }
			  if(data.message == "other shop"){
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
		  }
    
        },
        error: function(xhr, textStatus, error) {
        console.log(xhr.responseText);
        console.log(xhr.statusText);
        console.log(textStatus);
        console.log(error);
		    
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

