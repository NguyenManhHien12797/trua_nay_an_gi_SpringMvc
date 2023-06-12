package shopbaeFood.service.seviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import shopbaeFood.model.Account;
import shopbaeFood.model.Cart;
import shopbaeFood.model.Merchant;
import shopbaeFood.model.Order;
import shopbaeFood.model.OrderDetail;
import shopbaeFood.model.Product;
import shopbaeFood.model.dto.OrderDTO;
import shopbaeFood.repository.ICartRepository;
import shopbaeFood.repository.IOrderDetailRepository;
import shopbaeFood.repository.IOrderRepository;
import shopbaeFood.repository.IProductRepository;
import shopbaeFood.service.IAccountService;
import shopbaeFood.service.IMerchantService;
import shopbaeFood.service.IOrderService;
import shopbaeFood.utils.Constants;
import shopbaeFood.utils.Page;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IOrderDetailRepository orderDetailRepository;

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private IMerchantService merchantService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final int PAGE_SIZE = 5;

    @Override
    public boolean checkout(Order order, RedirectAttributes redirectAttributes) {
        Account account = accountService.getAccount();
        List<Cart> carts = cartRepository.findAllCartByUserIdAndDeleteFlag(account.getUser().getId());
        if (carts.isEmpty()) {
            redirectAttributes.addFlashAttribute("mess", "Mời bạn thêm sản phẩm vào giỏ hàng!");
            return true;
        }

        order.setAppUser(account.getUser());
        order.setStatus(Constants.ORDER_STATE.PENDING);
        orderRepository.save(order);
        OrderDetail orderDetail = new OrderDetail();
        int quantity;
        for (Cart cart : carts) {
            orderDetail.setProduct(cart.getProduct());
            orderDetail.setQuantity(cart.getQuantity());
            orderDetail.setPrice(cart.getProduct().getNewPrice());
            orderDetail.setOrder(order);
            orderDetail.setDeleteFlag(false);
            orderDetailRepository.save(orderDetail);
            Product product = productRepository.findById(cart.getProduct().getId());
            quantity = product.getQuantity() - cart.getQuantity();
            product.setQuantity(quantity);
            productRepository.update(product);
            cart.setDeleteFlag(true);
            cartRepository.update(cart);
        }
        messagingTemplate.convertAndSend("/topic/" + order.getMerchant_id(), new OrderDTO(order.getId(),
                account.getUser().getName(), order.getStatus(), orderDetail.getProduct().getMerchant().getName()));
        return true;

    }

    @Override
    public Map<String, List<Product>> productMap(Order order, RedirectAttributes redirectAttributes) {
        Account account = accountService.getAccount();
        List<Cart> carts = cartRepository.findAllCartByUserIdAndDeleteFlag(account.getUser().getId());
        List<Product> listProductDelete = new ArrayList<>();
        List<Product> listProductChangePrice = new ArrayList<>();
        List<Product> listProductOutOfStock = new ArrayList<>();
        Map<String, List<Product>> productMap = new HashMap<>();
        for (Cart cart : carts) {
            if (cart.getProduct().isDeleteFlag()) {
                listProductDelete.add(cart.getProduct());
            }

            if (!cart.getProduct().getNewPrice().equals(cart.getPrice())) {
                listProductChangePrice.add(cart.getProduct());
            }

            if (cart.getProduct().getQuantity() < 0 || cart.getProduct().getQuantity() < cart.getQuantity()) {
                listProductOutOfStock.add(cart.getProduct());
            }
        }
        if (listProductDelete.isEmpty() && listProductChangePrice.isEmpty() && listProductOutOfStock.isEmpty()) {
            checkout(order, redirectAttributes);
            return productMap;
        }
        if (!listProductDelete.isEmpty()) {
            redirectAttributes.addFlashAttribute("listProductDelete", listProductDelete);
            productMap.put("listProductDelete", listProductDelete);
        }
        if (!listProductChangePrice.isEmpty()) {
            redirectAttributes.addFlashAttribute("listProductChangePrice", listProductChangePrice);
            productMap.put("listProductChangePrice", listProductChangePrice);
        }
        if (!listProductOutOfStock.isEmpty()) {
            redirectAttributes.addFlashAttribute("listProductOutOfStock", listProductOutOfStock);
            productMap.put("listProductOutOfStock", listProductOutOfStock);
        }

        return productMap;
    }

    @Override
    public Map<String, List<Cart>> productMap(Order order) {
        Account account = accountService.getAccount();
        List<Cart> carts = cartRepository.findAllCartByUserIdAndDeleteFlag(account.getUser().getId());
        List<Cart> listProductDelete = new ArrayList<>();
        List<Cart> listProductChangePrice = new ArrayList<>();
        List<Cart> listProductOutOfStock = new ArrayList<>();
        Map<String, List<Cart>> productMap = new HashMap<>();
        for (Cart cart : carts) {
            if (cart.getProduct().isDeleteFlag()) {
                listProductDelete.add(cart);
            }

            if (!cart.getProduct().getNewPrice().equals(cart.getPrice())) {
                listProductChangePrice.add(cart);
            }

            if (cart.getProduct().getQuantity() < 0 || cart.getProduct().getQuantity() < cart.getQuantity()) {
                listProductOutOfStock.add(cart);
            }
        }
        if (listProductDelete.isEmpty() && listProductChangePrice.isEmpty() && listProductOutOfStock.isEmpty()) {
            return null;
        }
        if (!listProductDelete.isEmpty()) {
            productMap.put("listProductDelete", listProductDelete);
        }
        if (!listProductChangePrice.isEmpty()) {
            productMap.put("listProductChangePrice", listProductChangePrice);
        }
        if (!listProductOutOfStock.isEmpty()) {
            productMap.put("listProductOutOfStock", listProductOutOfStock);
        }

        return productMap;
    }

    @Override
    public List<Order> findOrdersByUserId(Long user_id) {
        return orderRepository.findOrdersByUserId(user_id);
    }

    @Override
    public String updateOrderStatus(Long order_id, String status) {
        Order order = orderRepository.findById(order_id);
        Merchant merchant = merchantService.findById(order.getMerchant_id());
        order.setStatus(status);
        orderRepository.update(order);
        if (Constants.ORDER_STATE.SELLER_RECEIVE.equals(status)) {
            messagingTemplate.convertAndSend("/topic/" + order.getAppUser().getId(),
                    new OrderDTO(order.getId(), order.getAppUser().getName(), status, merchant.getName()));
        }
        String route = "redirect: /shopbaeFood/merchant/order-manager/seller-receive/1";
        if (Constants.ORDER_STATE.BUYER_RECEIVE.equals(status) || Constants.ORDER_STATE.BUYER_REFUSE.equals(status)) {
            route = "redirect:/user/cart";
        }
        return route;

    }

    @Override
    public void deleteOrder(Long order_id) {
        Order order = orderRepository.findById(order_id);
        order.setUserDeleteFlag(true);
        orderRepository.update(order);

    }

    @Override
    public Page<Order> page(String status, int pageNumber, HttpSession session) {
        Long merchant_id = (Long) session.getAttribute("userId");
        Page<Order> page = new Page<Order>();
        List<Order> orders = null;
        int lastPageNumber = 0;
        if (Constants.ORDER_STATE.PENDING.equals(status) || Constants.ORDER_STATE.SELLER_RECEIVE.equals(status)) {
            orders = page.paging(pageNumber, PAGE_SIZE, orderRepository.findOrdersByMerchantId(merchant_id, status));
            lastPageNumber = page.lastPageNumber(PAGE_SIZE,
                    orderRepository.findOrdersByMerchantId(merchant_id, status));
        }

        if (Constants.ORDER_STATE.HISTORY.equals(status)) {
            orders = page.paging(pageNumber, PAGE_SIZE, orderRepository.findOrdersByMerchantIdAndStatus(merchant_id,
                    Constants.ORDER_STATE.BUYER_RECEIVE, Constants.ORDER_STATE.BUYER_REFUSE));

            lastPageNumber = page.lastPageNumber(PAGE_SIZE, orderRepository.findOrdersByMerchantIdAndStatus(merchant_id,
                    Constants.ORDER_STATE.BUYER_RECEIVE, Constants.ORDER_STATE.BUYER_REFUSE));

        }

        page.setPaging(orders);
        page.setLastPageNumber(lastPageNumber);

        return page;
    }

}
