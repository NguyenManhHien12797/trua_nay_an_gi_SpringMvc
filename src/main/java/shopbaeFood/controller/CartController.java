package shopbaeFood.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shopbaeFood.model.Account;
import shopbaeFood.model.Cart;
import shopbaeFood.model.Order;
import shopbaeFood.model.OrderDetail;
import shopbaeFood.model.dto.CartDTO;
import shopbaeFood.model.dto.MessageResponse;
import shopbaeFood.service.IAccountService;
import shopbaeFood.service.ICartService;
import shopbaeFood.service.IOrderDetailService;
import shopbaeFood.service.IOrderService;
import shopbaeFood.utils.Constants;

@Controller
public class CartController {

    private final ICartService cartService;

    private final IOrderDetailService orderDetailService;

    private final IAccountService accountService;

    private final IOrderService orderService;

    public CartController(ICartService cartService, IOrderDetailService orderDetailService,
            IAccountService accountService, IOrderService orderService) {
        this.cartService = cartService;
        this.orderDetailService = orderDetailService;
        this.accountService = accountService;
        this.orderService = orderService;
    }

    /**
     * This method is used to add to cart
     * 
     * @param cartDTO
     * @param session
     * @return view merchant-detail page
     */
    @PostMapping("/user/addToCart")
    @ResponseBody
    public MessageResponse addToCart(@RequestBody CartDTO cartDTO, HttpServletRequest request) {
        return cartService.addToCart(request, cartDTO);
    }

    /**
     * This method returns cart_page page
     * 
     * @param model
     * @param session
     * @return view cart_page
     */
    @GetMapping("/user/cart")
    public String showCart(HttpServletRequest request, Model model) {
        Account account = accountService.getAccount();
        if (account == null) {
            return "redirect:/login?mess=not-logged-in";
        }
        if (account.getUser() == null) {
            try {
                request.logout();
            } catch (ServletException e) {
                e.printStackTrace();
            }
            return "redirect:/login?mess=not-logged-in";
        }
        Long userId = account.getUser().getId();

        List<Cart> carts = cartService.findAllCartByUserIdAndDeleteFlag(userId);
        List<Order> orders = orderService.findOrdersByUserId(userId);
        String message = " ";
        if (carts.isEmpty()) {
            message = Constants.CART_MESSAGE.NO_DATA;
        }

        Double totalPrice = 0.0;
        Double cartTotalPrice = 0.0;
        Long merchant_id = null;
        for (Cart cart : carts) {
            cartTotalPrice = cart.getQuantity() * cart.getProduct().getNewPrice();
            cart.setTotalPrice(cartTotalPrice);
            totalPrice += cart.getTotalPrice();
            merchant_id = cart.getProduct().getMerchant().getId();
        }
        Order order = new Order();
        order.setMerchant_id(merchant_id);
        LocalDateTime time = LocalDateTime.now();
        order.setOrderdate(time);
        order.setAppUser(account.getUser());

        model.addAttribute("carts", carts);
        model.addAttribute("message", message);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("order", order);
        model.addAttribute("orders", orders);

        return "/cart_page";
    }

    @GetMapping("/user/getCart/{userId}")
    @ResponseBody
    public List<Cart> getCarts(@PathVariable Long userId) {
        return cartService.getCarts(userId);
    }

    @GetMapping("/user/getOrderDetail/{orderId}")
    @ResponseBody
    public List<OrderDetail> getOrderDetail(@PathVariable Long orderId) {
        List<OrderDetail> orderDetails = orderDetailService.findOrderDetailsByOrderId(orderId);
        return orderDetails;
    }

    /**
     * This method is used to delete product in cart and return cart_page page
     * 
     * @param id : cart_id
     * @return view car_page
     */
    @RequestMapping(value = { "/user/delete/{id}" })
    public String deleteProduct(@PathVariable Long id) {
        cartService.deleteCart(id);
        return "redirect: /shopbaeFood/user/cart";
    }

    @RequestMapping(value = { "/user/increase/{cart_id}" })
    public String increaseQuantity(@PathVariable Long cart_id) {
        cartService.increaseQuantity(cart_id);
        return "redirect: /shopbaeFood/user/cart";
    }

    @RequestMapping(value = { "/user/decrease/{cart_id}" })
    public String decreaseQuantity(@PathVariable Long cart_id) {
        cartService.decreaseQuantity(cart_id);
        return "redirect: /shopbaeFood/user/cart";
    }

    @PostMapping("/user/change-quantity/{cart_id}")
    @ResponseBody
    public MessageResponse changeQuantity(@RequestBody int quantity, @PathVariable Long cart_id) {
        return cartService.changeQuantity(cart_id, quantity);
    }

}
