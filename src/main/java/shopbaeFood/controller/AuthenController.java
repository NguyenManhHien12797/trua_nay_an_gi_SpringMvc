package shopbaeFood.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import shopbaeFood.exception.CheckOtpException;
import shopbaeFood.model.Account;
import shopbaeFood.model.AppUser;
import shopbaeFood.model.Merchant;
import shopbaeFood.model.Product;
import shopbaeFood.model.Status;
import shopbaeFood.model.UserForm;
import shopbaeFood.model.dto.AccountRegisterDTO;
import shopbaeFood.model.dto.PasswordDTO;
import shopbaeFood.service.IAccountService;
import shopbaeFood.service.IAppUserSevice;
import shopbaeFood.service.IAuthenService;
import shopbaeFood.service.IMerchantService;
import shopbaeFood.service.IProductService;

@Controller
public class AuthenController {

    @Autowired
    private IAuthenService authenService;

    @Autowired
    private IAppUserSevice userSevice;

    @Autowired
    private IMerchantService merchantService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IAccountService accountService;

    public static final String USER = "user";
    public static final String MECHANT = "merchant";
    public static final String TITLE_MERCHANT = "Đăng ký người bán";
    public static final String TITLE_USER = "Đăng ký người dùng";

    /**
     * This method returns login page or message when login error
     * 
     * @param mess
     * @param model
     * @return view login
     */
    @GetMapping(value = { "/login" })
    public String showLoginForm(@RequestParam(required = false) String mess, Model model,
            HttpServletResponse response) {
        Account account = accountService.getAccount();
        if (account == null) {
            model.addAttribute("mess", authenService.showMessageLogin(mess));
            response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            return "login";
        }

        return "redirect:/";
    }

    /**
     * This method returns homePage page
     * 
     * @param model
     * @param session
     * @return view homePage
     */
    @GetMapping(value = { "/home", "/" })
    public String home(@RequestParam(required = false) String address, @RequestParam(required = false) String category,
            @RequestParam(required = false) String quickSearch, Model model, HttpSession session) {
        List<Merchant> merchants = authenService.getMerchants(address, category, quickSearch, session);

        model.addAttribute("merchants", merchants);
        model.addAttribute("listAddress", authenService.getAddress());
        session.setAttribute("categories", authenService.getCategories());
        model.addAttribute("categories", authenService.getCategories());
        model.addAttribute("quickSearchs", authenService.getListQuickSearch(category));
        model.addAttribute("category", category);

        return "homepage";
    }

    /**
     * This method returns merchant details page
     * 
     * @param id    : merchant_id
     * @param model
     * @return view merchant-detail
     */
    @GetMapping(value = { "/home/merchant-detail/{id}" })
    public String merchantDetails(@PathVariable Long id, Model model) {
        Merchant merchant = merchantService.findById(id);
        List<Product> products = productService.findAllProductByMerchantAndDeleteFlag(merchant);

        model.addAttribute("merchant", merchant);
        model.addAttribute("products", products);

        return "merchant-details";
    }

    /**
     * This method returns fragments: user_info/ user_update_info by route
     * 
     * @param route : user_info/ user_update_info
     * @param model
     * @return view user_info page
     */
    @GetMapping(value = { "/home/{route}" })
    public String userInfo(@PathVariable String route, Model model) {
        Account account = accountService.getAccount();
        if (account == null) {
            return "redirect:/login?mess=not-logged-in";
        }
        AppUser user = userSevice.findById(account.getUser().getId());
        UserForm userForm = new UserForm(user.getId(), user.getName(), user.getPhone(), user.getAddress());
        model.addAttribute("user", user);
        model.addAttribute("account", account);
        model.addAttribute("userForm", userForm);
        model.addAttribute("route", route);

        return "user-info";
    }

    /**
     * This method is used to update user and return user-info
     * 
     * @param userForm
     * @param account
     * @param session
     * @return view user-info
     */
    @PostMapping(value = { "/user/user-info" })
    private String updateAccountUser(@ModelAttribute("userForm") UserForm userForm,
            @ModelAttribute("account") Account account, HttpSession session) {

        userSevice.updateUserInfo(userForm, account, session);
        return "redirect:/home/user-info";
    }

    /**
     * This method returns register page by role
     * 
     * @param role : user/ merchant
     * @return view register
     */
    @GetMapping("/register/{role}")
    public ModelAndView showFormRegister(@PathVariable String role) {
        ModelAndView modelAndView = new ModelAndView("/register");
        String path = "";
        String title = "";
        if ("user".equals(role)) {
            path = "/register/user";
            title = "Đăng ký người dùng";
        }
        if ("merchant".equals(role)) {
            path = "/register/merchant";
            title = "Đăng ký người bán";
        }

        modelAndView.addObject("register", path);
        modelAndView.addObject("title", title);
        modelAndView.addObject("accountRegisterDTO", new AccountRegisterDTO());

        return modelAndView;
    }

    /**
     * This method is used to register user/ merchant
     * 
     * @param role               : user/ merchant
     * @param accountRegisterDTO
     * @param bindingResult
     * @param model
     * @return view login
     */
    @PostMapping("/register/{role}")
    public String register(@PathVariable String role,
            @Valid @ModelAttribute("accountRegisterDTO") AccountRegisterDTO accountRegisterDTO,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            String title = "";
            if (USER.equals(role)) {
                title = TITLE_USER;
            }
            if (MECHANT.equals(role)) {
                title = TITLE_MERCHANT;
            }

            model.addAttribute("title", title);
            return "register";
        }
        authenService.register(accountRegisterDTO, role);

        return "login";
    }

    /**
     * This method is used to create OTP
     * 
     * @return mess: 'create otp ok'
     */
    @RequestMapping(value = { "/home/create-otp" })
    @ResponseBody
    public String createOTP() {
        authenService.createOtp();
        return "create otp ok";
    }

    /**
     * This method is used to check otp
     * 
     * @param account_id
     * @param otp
     * @return message: 'ok'
     * @throws CheckOtpException if check otp false
     */
    @PostMapping(value = { "/home/checkotp/{account_id}/{otp}" })
    @ResponseBody
    public String checkOTP(@PathVariable Long account_id, @PathVariable String otp) {

        return authenService.checkOtp(account_id, otp);
    }

    @GetMapping("/home/change-pass")
    public String showChangePassPage(Model model) {
        Account account = accountService.getAccount();
        if (account == null) {
            return "redirect:/login?mess=not-logged-in";
        }
        model.addAttribute("passwordDTO", new PasswordDTO());
        return "change_pass";
    }

    @PostMapping(value = { "/home/change-pass" })
    public String changePass(@Valid @ModelAttribute("passwordDTO") PasswordDTO passwordDTO, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "change_pass";
        }
        if (authenService.changePass(passwordDTO)) {
            return "login";
        }
        model.addAttribute("error", "Mật khẩu không đúng");

        return "change_pass";
    }

    /**
     * This method is used to change pass
     * 
     * @param account_id
     * @param pass
     * @return message: 'change pass ok'
     */
    @PostMapping(value = { "/home/change-pass/{account_id}/{pass}" })
    @ResponseBody
    public String changePass(@PathVariable Long account_id, @PathVariable String pass) {
        authenService.changePass(pass, account_id);

        return "change pass ok";
    }

    @PostMapping(value = { "/home/search/{category}" })
    @ResponseBody
    public List<Merchant> searchMerchant(@RequestBody(required = false) String search,
            @PathVariable(required = false) String category) {
        System.out.println(search);
        List<Merchant> merchants = merchantService.findMerchantsByStatusAndCategoryAndSearch(Status.ACTIVE, category,
                search);
        return merchants;
    }

}
