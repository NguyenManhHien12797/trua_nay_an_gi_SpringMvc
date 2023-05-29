$( document ).ready(function() {
    console.log( "ready!" );
    
    $(".btn-change-pass").click(function() {
        $.ajax({
            //tên API
            url:`/shopbaeFood/home/create-otp`,
            success: function (data) {
            }
          });
    });
});

function changeHandler(evt) {
    evt.stopPropagation();
    evt.preventDefault();

    // FileList object.
    const files = evt.target.files;
    const file = files[0];
    const fileReader = new FileReader();
    fileReader.onload = function(progressEvent) {
        const url = fileReader.result;
       	const myImg1 = document.getElementById("myimage1");
        myImg1.src= url;
    }

    fileReader.readAsDataURL(file);
}

function checkotp(account_id){
	
	let otp = $(".otp").val();
	console.log(otp);
	console.log(account_id);
	
    $.ajax({
        //tên API
        type: "POST",
        url:`/shopbaeFood/home/checkotp/${account_id}/${otp}`,
        success: function (data) {
      	console.log("suss");
      	$(".mess-otp").hide();
      	 $(".create-otp").hide();
      	 $(".otp").val('');
      	$(".change-pass").show();
        },
        error: function (data) {
            $(".mess-otp").show();
        }
      });

}

const exampleModal = document.getElementById('exampleModal')
exampleModal.addEventListener('hide.bs.modal', event => {
  $(".mess-otp").hide();
  $(".create-otp").show();
  $(".otp").val('');
  $(".change-pass").hide();
})
var myModal = new bootstrap.Modal(document.getElementById('exampleModal'), {
  keyboard: false
})

function changePassword(account_id) {
	let pass = $(".pass").val();
	
    $.ajax({
        //tên API
        type: "POST",
        url:`/shopbaeFood/home/change-pass/${account_id}/${pass}`,
        success: function (data) {
      	console.log("suss");
        swal("Đổi password thành công", " ","success");
      	$(".change-pass").hide();
        myModal.hide();

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