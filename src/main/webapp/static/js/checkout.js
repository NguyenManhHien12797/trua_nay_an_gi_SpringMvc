$(".btn-checkout-a").click(function(){
  $(".modal-checkout").show();
});

$(".modal-overlay").click(function(){
  $(".modal-checkout").hide();
});


function deleteCart(cart_id, user_id){
	
	     $.ajax({
        //tên API
        url:`/shopbaeFood/user/delete/${cart_id}`,
        //xử lý khi thành công
        success: function (data) {
			document.getElementById(`cart${cart_id}`).remove();
			document.querySelectorAll(`[data-name="${cart_id}"]`).forEach(e => e.remove());
			let listProductChangePriceClass = document.querySelectorAll(".listProductChangePriceClass > ul");
			let listProductDeleteClass = document.querySelectorAll(".listProductDeleteClass > ul");
			let listProductOutOfStockClass = document.querySelectorAll(".listProductOutOfStockClass > ul");
			if(listProductChangePriceClass.length == 0 && document.querySelector(".changePriceSpan")){
				document.querySelector(".changePriceSpan").remove();
			}
			
			if(listProductDeleteClass.length == 0 && document.querySelector(".deleteSpan")){
				document.querySelector(".deleteSpan").remove();
			}
			
			if(listProductOutOfStockClass.length == 0 && document.querySelector(".outOfStockSpan")){
				document.querySelector(".outOfStockSpan").remove();
			}
			getCarts(user_id);
		}
		
		})
}



function getCarts(userId){
		console.log("getCart")
		console.log(userId)
		$.ajax({
        //tên API
        url:`/shopbaeFood/user/getCart/${userId}`,
        //xử lý khi thành công
        success: function (data) {
			
				if(data.length == 0){
				document.querySelector(".card-body-bill > form").remove();
				let img = document.createElement('img');
				img.setAttribute("src", "https://taphoa.cz/static/media/cart-empty-img.8b677cb3.png");
				img.classList.add('card-body-noimage');
				document.querySelector(".card-body-cart").appendChild(img);
				setTimeout(function(){
  			 		window.location.reload();
					}, 1500);
			}
			
		}
		
		})
}

function drawTitle(){
	var list = document.createElement("div");
      list.classList.add('alert-product');
      list.innerHTML = `
				<ul >
							<li scope="col">Ảnh</li>
							<li scope="col" style=" width: 200px;">Tên</li>
							<li scope="col">Giá</li>
							<li scope="col">Hành động</li>
				</ul>
			
      			`
     return list;
}
function drawAlert(carts, className){

		var listUl = document.createElement("div");
		 listUl.classList.add('alert-product');
		 listUl.classList.add('alert-product-item');
		 listUl.classList.add(className);
			for(let i =0;i< carts.length; i++){
				let ul = document.createElement('ul');
				let tdImg = document.createElement('li');
				let img = document.createElement('img');
				let name = document.createElement('li');
				let priceLi = document.createElement('li');
				let oldPrice = document.createElement('span');
				let newPrice = document.createElement('span');
				let action = document.createElement('li');
				let icon = document.createElement('i');
				let newPriceContent = document.createTextNode(carts[i].product.newPrice + " đ") ;
				let oldPriceContent = document.createTextNode(carts[i].product.oldPrice + " đ") ;
				let imgContent = '/shopbaeFood/image/' +carts[i].product.image;
				let id = carts[i].id;
				let nameContent = document.createTextNode(carts[i].product.name);
				oldPrice.appendChild(oldPriceContent);
				newPrice.appendChild(newPriceContent);
				priceLi.appendChild(oldPrice);
				priceLi.appendChild(newPrice);
				
				img.setAttribute("src", imgContent);
  				
  				icon.classList.add('fa-solid');
  				icon.classList.add('fa-trash');
  				icon.classList.add('icon-alert');
  				icon.setAttribute("onclick", `deleteCart(${carts[i].id}, ${carts[i].user_id.id})`);
  				
				tdImg.appendChild(img);
				name.appendChild(nameContent);
				action.appendChild(icon);
				ul.setAttribute("id", id);
				ul.classList.add(id);
				ul.setAttribute("data-name", id);
				ul.appendChild(tdImg);
				ul.appendChild(name);
				ul.appendChild(priceLi);
				ul.appendChild(action);
				listUl.appendChild(ul);
			}
			
			return listUl;
	
}


function checkout(order, carts) {
	if(order.appUser == null){
		window.location="https://localhost:8443/shopbaeFood/login?mess=not-logged-in";
	}
	
	if(!carts.length){
		swal("Chưa có sản phẩm!"," ","error");
		return;
	}
   
	
    $.ajax({
		 headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        type: "POST",
        data: JSON.stringify(order),
        //tên API
        url:`/shopbaeFood/user/checkout`,
        //xử lý khi thành công
        success: function (data) {
		console.log(data)
		if(data.length == 0){
			swal("Đặt hàng thành công!"," ","success");
			document.querySelectorAll(".card-body-cart > div").forEach(e => e.remove());
			getCarts(carts[0].user_id.id);
		
		}else{
			var list = drawTitle();
			
			let changePriceSpan = document.createElement('span');
			let deleteSpan = document.createElement('span');
			let outOfStockSpan = document.createElement('span');
			
			changePriceSpan.classList.add('checkout-mess');
			changePriceSpan.classList.add('changePriceSpan');
			deleteSpan.classList.add('checkout-mess');
			deleteSpan.classList.add('deleteSpan');
			outOfStockSpan.classList.add('checkout-mess');
			outOfStockSpan.classList.add('outOfStockSpan');
			
			let changePriceTitle = document.createTextNode("Một số sản phẩm đã bị đổi giá:") ;
			let deleteTitle = document.createTextNode("Một số sản phẩm đã bị xóa. Vui lòng xóa sản phẩm để tiếp tục:") ;
			let outOfStockTitle = document.createTextNode("Một số sản phẩm đã hết. Vui lòng đặt sản phẩm khác!") ;
			
			changePriceSpan.appendChild(changePriceTitle);
			deleteSpan.appendChild(deleteTitle);
			outOfStockSpan.appendChild(outOfStockTitle);
			
			if(data.listProductDelete){
				list.appendChild(deleteSpan);
				list.appendChild(drawAlert(data.listProductDelete, "listProductDeleteClass"));
			}
			if(data.listProductOutOfStock){
				list.appendChild(outOfStockSpan);
				list.appendChild(drawAlert(data.listProductOutOfStock,"listProductOutOfStockClass"));
			}
			if(data.listProductChangePrice){
				list.appendChild(changePriceSpan);
				list.appendChild(drawAlert(data.listProductChangePrice, "listProductChangePriceClass"));
		  	}
			swal({
        		title: "Có gì đó sai sai!: ", 
        		content:  list,
        		buttons:  ["Quay lại","Đặt hàng"],
		    	dangerMode: true,
				})
				.then((ok) => {
		    	  if (ok) {
					  let listProductChangePriceClass = document.querySelectorAll(".listProductChangePriceClass > ul");
					  let listProductDeleteClass = document.querySelectorAll(".listProductDeleteClass > ul");
					  let listProductOutOfStockClass = document.querySelectorAll(".listProductOutOfStockClass > ul");
					   console.log(listProductChangePriceClass);
					 if(listProductDeleteClass.length == 0 && listProductOutOfStockClass.length == 0){
						 
						    $.ajax({
		 						headers: {
          						'Accept': 'application/json',
          						'Content-Type': 'application/json'
        								},
        						type: "POST",
        						data: JSON.stringify(order),
        						//tên API
        						url:`/shopbaeFood/user/checkout/continute`,
        						//xử lý khi thành công
        						success: function (data) {
									swal("Đặt hàng thành công!"," ","success");
									document.querySelectorAll(".card-body-cart > div").forEach(e => e.remove());
									getCarts(carts[0].user_id.id);
        						},
        						error: function(xhr, textStatus, error) {
        						console.log(xhr.responseText);
        						console.log(xhr.statusText);
        						console.log(textStatus);
        						console.log(error);
		
        						}

      						})
						 
					 }
					 else{
						 swal("Mời xóa các sản phẩm không hợp lệ!"," ","error");
					 }
		    		 
		    	  } 
		    	});	
			}
		
        },
        error: function(xhr, textStatus, error) {
        console.log(xhr.responseText);
        console.log(xhr.statusText);
        console.log(textStatus);
        console.log(error);
		    
        }

      })
}




