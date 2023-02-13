$( document ).ready(function() {
    console.log( "ready!" );
    
    
    $(".btn-change-pass").click(function() {
    	swal("Vui lòng check email để lấy OTP!", " ","success");
    	$(".create-otp").show();
        $.ajax({
            //tên API
            url:`/shopbaeFood/home/create-otp`,
            //xử lý khi thành công
            success: function (data) {
         /*  	  location.reload(); */
          	console.log(data);
            }

      		
          });
    });
    
});


function changeHandler(evt) {
    evt.stopPropagation();
    evt.preventDefault();

    // FileList object.
    var files = evt.target.files;

    var file = files[0];

    var fileReader = new FileReader();


    fileReader.onload = function(progressEvent) {
        var url = fileReader.result;

        // Something like: data:image/png;base64,iVBORw...Ym57Ad6m6uHj96js
 /*        console.log(url); */
        //
       	var myImg = document.getElementById("myimage"); 
       	var myImg1 = document.getElementById("myimage1"); 
        myImg.src= url;
        if( myImg.src != null){
        	$("#myimage").show();
        }
        myImg1.src= url;
    }


    // Read file asynchronously.
    fileReader.readAsDataURL(file); // fileReader.result -> URL.
}

function checkotp(account_id){
	
	let otp = $(".otp").val();
	console.log(otp);
	console.log(account_id);
	
    $.ajax({
        //tên API
        type: "POST",
        url:`/shopbaeFood/home/checkotp/${account_id}/${otp}`,
        //xử lý khi thành công
        success: function (data) {
     /*  	  location.reload(); */
      	console.log("suss");
      	$(".mess-otp").hide();
      	 $(".create-otp").hide();
      	 $(".otp").val(" ");
      	$(".change-pass").show();
        },
        error: function (data) {
            /*  	  location.reload(); */
             	console.log("error");
             	 $(".mess-otp").show();
               }

  		
      });

}

function changePassword(account_id) {
	let pass = $(".pass").val();
	console.log(pass);
	console.log(account_id);
	
    $.ajax({
        //tên API
        type: "POST",
        url:`/shopbaeFood/home/change-pass/${account_id}/${pass}`,
        //xử lý khi thành công
        success: function (data) {
     /*  	  location.reload(); */
      	console.log("suss");
      	 swal("Đổi password thành công", " ","success");
      	$(".change-pass").hide();

        },
        error: function (data) {
             	console.log("error");
               }

  		
      });

}


function updateStatus(userId, navRoute,status,route) {
	console.log(route);
    $.ajax({
        type: "POST",
        //tên API
        url:`/shopbaeFood/admin/${navRoute}/${route}/${status}/${userId}`,
        //xử lý khi thành công
        success: function (data) {
      	$(".table-responsive").html(data);
      	  swal("Update thành công!", " ","success");
        }

  		
      });
}