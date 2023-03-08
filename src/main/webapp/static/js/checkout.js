$(".btn-checkout-a").click(function(){
  $(".modal-checkout").show();
});

$(".modal-overlay").click(function(){
  $(".modal-checkout").hide();
});


function deleteCart(cart_id){
	
		console.log(document.getElementById(`${cart_id}`))
	     $.ajax({
        //tên API
        url:`/shopbaeFood/user/delete/${cart_id}`,
        //xử lý khi thành công
        success: function (data) {
			document.getElementById(`${cart_id}`).remove();
		}
		
		})
}

function drawAlert(carts, title){
	  var list = document.createElement("div");
      list.classList.add('alert-product');
      list.setAttribute("style", "max-height: 250px;overflow: auto;");
      list.innerHTML = `
				<ul style=" display: flex;list-style: none;justify-content: space-between;padding-left: 0;">
							<li scope="col">Ảnh</li>
							<li scope="col" style=" width: 200px;">Tên</li>
							<li scope="col">Giá</li>
							<li scope="col">Hành động</li>
				</ul>
			
      			`
			for(let i =0;i< carts.length; i++){
				console.log("order");
				console.log(order);
				let ul = document.createElement('ul');
				let tdImg = document.createElement('li');
				let img = document.createElement('img');
				let name = document.createElement('li');
				let priceLi = document.createElement('li');
				let oldPrice = document.createElement('span');
				let newPrice = document.createElement('span');
				let action = document.createElement('li');
				/*let a = document.createElement('a');*/
				let icon = document.createElement('i');
				let newPriceContent = document.createTextNode(carts[i].product.newPrice + " đ") ;
				let oldPriceContent = document.createTextNode(carts[i].product.oldPrice + " đ") ;
				let imgContent = '/shopbaeFood/image/' +carts[i].product.image;
				let id = data.listProductChangePrice[i].id;
				let nameContent = document.createTextNode(carts[i].product.name);
				oldPrice.appendChild(oldPriceContent);
				newPrice.appendChild(newPriceContent);
				priceLi.appendChild(oldPrice);
				priceLi.appendChild(newPrice);
				
				img.setAttribute("src", imgContent);
  				img.setAttribute("width", "40");
  				img.setAttribute("height", "40");
  				img.setAttribute("style", "border-radius:50%");
  				
  				icon.classList.add('fa-solid');
  				icon.classList.add('fa-trash');
  				icon.setAttribute("style", "font-size: 22px; cursor:pointer; color:grey");
  				icon.setAttribute("onclick", `deleteCart(${carts[i].id})`);
 /* 			a.classList.add('alert-delete');
  				a.setAttribute("href", href);
  				a.appendChild(icon);*/
  				
				tdImg.appendChild(img);
				name.appendChild(nameContent);
				name.setAttribute("style", "width:200px;text-align: left;");
				action.setAttribute("style", "transform: translateX(-30px);");
				oldPrice.setAttribute("style", "display:block; transform: translateX(-40px);text-decoration: line-through;color: red;");
				newPrice.setAttribute("style", "display:block;transform: translateX(-40px);");
				action.appendChild(icon);
				ul.setAttribute("id", id);
				ul.appendChild(tdImg);
				ul.appendChild(name);
				ul.appendChild(priceLi);
				ul.appendChild(action);
				ul.setAttribute("style", "display: flex;list-style: none;justify-content: space-between; padding-left: 0;");
				list.appendChild(ul);
			}
	
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
			console.log(!data.length)
			console.log(data)
		if(!data.length){
			swal("Đặt hàng thành công!"," ","success");
	/*		setTimeout(function(){
  			 window.location.reload();
			}, 2000);*/
			;
		}
      console.log("suss");  
      console.log(data.listProductChangePrice);
      var list = document.createElement("div");
      list.classList.add('alert-product');
      list.setAttribute("style", "max-height: 250px;overflow: auto;");
      list.innerHTML = `
				<ul style=" display: flex;list-style: none;justify-content: space-between;padding-left: 0;">
							<li scope="col">Ảnh</li>
							<li scope="col" style=" width: 200px;">Tên</li>
							<li scope="col">Giá</li>
							<li scope="col">Hành động</li>
				</ul>
			
      			`
      	  	if(data.listProductChangePrice){
			for(let i =0;i< data.listProductChangePrice.length; i++){
				console.log("order");
				console.log(order);
				let ul = document.createElement('ul');
				let tdImg = document.createElement('li');
				let img = document.createElement('img');
				let name = document.createElement('li');
				let priceLi = document.createElement('li');
				let oldPrice = document.createElement('span');
				let newPrice = document.createElement('span');
				let action = document.createElement('li');
				/*let a = document.createElement('a');*/
				let icon = document.createElement('i');
				let newPriceContent = document.createTextNode(data.listProductChangePrice[i].product.newPrice + " đ") ;
				let oldPriceContent = document.createTextNode(data.listProductChangePrice[i].product.oldPrice + " đ") ;
				let imgContent = '/shopbaeFood/image/' +data.listProductChangePrice[i].product.image;
				let id = data.listProductChangePrice[i].id;
				let nameContent = document.createTextNode(data.listProductChangePrice[i].product.name);
				oldPrice.appendChild(oldPriceContent);
				newPrice.appendChild(newPriceContent);
				priceLi.appendChild(oldPrice);
				priceLi.appendChild(newPrice);
				
				img.setAttribute("src", imgContent);
  				img.setAttribute("width", "40");
  				img.setAttribute("height", "40");
  				img.setAttribute("style", "border-radius:50%");
  				
  				icon.classList.add('fa-solid');
  				icon.classList.add('fa-trash');
  				icon.setAttribute("style", "font-size: 22px; cursor:pointer; color:grey");
  				icon.setAttribute("onclick", `deleteCart(${data.listProductChangePrice[i].id})`);
 /* 			a.classList.add('alert-delete');
  				a.setAttribute("href", href);
  				a.appendChild(icon);*/
  				
				tdImg.appendChild(img);
				name.appendChild(nameContent);
				name.setAttribute("style", "width:200px;text-align: left;");
				action.setAttribute("style", "transform: translateX(-30px);");
				oldPrice.setAttribute("style", "display:block; transform: translateX(-40px);text-decoration: line-through;color: red;");
				newPrice.setAttribute("style", "display:block;transform: translateX(-40px);");
				action.appendChild(icon);
				ul.setAttribute("id", id);
				ul.appendChild(tdImg);
				ul.appendChild(name);
				ul.appendChild(priceLi);
				ul.appendChild(action);
				ul.setAttribute("style", "display: flex;list-style: none;justify-content: space-between; padding-left: 0;");
				list.appendChild(ul);
				
			}
			swal({
        		title: "Có một số sản phẩm sau đã bị đổi giá!: ", 
        		content:  drawAlert(data.listProductChangePrice) , 
        		html: true,
        		 icon: "error",
				});
		  }
      	console.log(data.listProductDelete); 
      	if(!data.listProductDelete){
			  console.log("listProductDelete khong ton tai");
		  }
      	console.log(data); 
		
    
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

