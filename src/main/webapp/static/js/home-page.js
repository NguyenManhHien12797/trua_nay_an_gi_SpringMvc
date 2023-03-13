function searchMerchant(search, category){
	
		const isEmpty = str => !str.trim().length;

  		if( isEmpty(search) ) {
    		location.reload();
  		}
		$.ajax({
       	headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        type: "POST",
        data: search ,
        url:`/shopbaeFood/home/search/${category}`,
        //xử lý khi thành công
        success: function (data) {
			console.log(data)
			let content = " ";
      	if(data !=null){
			  for(let i=0; i< data.length; i++){
				  content += drawListMerchant(data[i]);
			  }
		  }
      	  document.getElementById('list-restaurant-item').innerHTML = content;
        },
        error: function(xhr, textStatus, error) {
        console.log(xhr.responseText);
        console.log(xhr.statusText);
        console.log(textStatus);
        console.log(error);
		    
        }
      });

}

function drawListMerchant(merchant){
	return  `
                      <div class="item-restaurant">
                      <a class="item-content" href="/shopbaeFood/home/merchant-detail/${merchant.id}">
                        <div class="img-restaurant">
                          <div class="tag-preferred"><i class="fa fa-thumbs-up"
                                                        aria-hidden="true"></i>Yêu thích
                          </div>
                          <img
                            src="/shopbaeFood/image/${merchant.avatar}"
                            class="">
                        </div>
                        <div class="info-restaurant">
                          <div class="info-basic-res">
                            <h4 class="name-res" title="" >${merchant.name}</h4>
                            <div class="address-res" >${merchant.address}</div>
                          </div>
                          <div >
                          <span style="margin-left: 10px; font-size: 12px;color: #959595;"> ${merchant.openTime} -- ${merchant.closeTime}</span>
                          </div>
                          <div class="opentime-status">
                          <span class="stt online" title="Mở cửa"
                             style="color: rgb(35, 152, 57); background-color: rgb(35, 152, 57);">
                          </span>
                          </div>
                        </div>
                      </a>
                      </div>
      
	
	`;
}

const selectElement = document.querySelector(".dropdown-address");

selectElement.addEventListener("change", (event) => {
  window.location=`https://localhost:8443/shopbaeFood/?address=${event.target.value}`;
});

window.addEventListener("scroll", () =>{
	
	let mainRight = document.querySelector(".main-right-home");
	let height = mainRight.clientHeight - 700;
	let position = mainRight.getBoundingClientRect();
	if((position.bottom - window.innerHeight) <= 0) {
		 $(".now-banner").css({position:"absolute",top: height+"px"});
	}else{
		 $(".now-banner").css({position:"fixed",top: "70px"});
	}
	
})



