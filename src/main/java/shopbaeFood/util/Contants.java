package shopbaeFood.util;

public class Contants {
	
	 public static class RESPONSE_MESSAGE{
		 public static final String LOGIN_FAILE = "Tài khoản hoặc mật khẩu không chính xác !!";
		 public static final String LOGIN_FAILE_ACCOUNT_BLOCK = "Tài khoản của bạn đã bị khóa !!";
		 public static final String LOGIN_FAILE_ACCOUNT_PENDING = "Tài khoản đang chờ admin duyệt !!";
		 public static final String LOGIN_FAILE_ACCOUNT_REFUSE = "Admin từ chối đăng ký Merchant !!";
		 public static final String NOT_LOGIN = "Vui lòng đăng nhập để tiếp tục!";
		 public static final String TIME_OUT = "Hết thời gian. Vui lòng đăng nhập để tiếp tục!";
		 public static final String WRONG_OTP = "Sai otp";
	 }

	 public static class LOGIN_STATE{
		 public static final String NOT_LOGIN = "not-logged-in";
		 public static final String TIME_OUT = "timeout";
	 }
	 
	 public static class CART_MESSAGE{
		 public static final String NO_DATA = "khong co du lieu";
	 }
	 
	 public static class ORDER_STATE{
		 public static final String PENDING = "order-pending";
		 public static final String BUYER_REFUSE = "buyer-refuse";
		 public static final String BUYER_RECEIVE = "buyer-receive";
		 public static final String HISTORY = "order-history";
	 }
	 
	 public static final int SESSION_EXPIRATION = 60*60;
	
}
