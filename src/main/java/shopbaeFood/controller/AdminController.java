package shopbaeFood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shopbaeFood.model.Status;
import shopbaeFood.service.IMerchantService;
import shopbaeFood.util.Page;

@Controller
public class AdminController {

    private final IMerchantService merchantService;

    public AdminController(IMerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping(value = { "/admin" })
    public String redirectMerchantList() {
        return "redirect:/admin/merchant-list/ACTIVE/1";
    }

    /**
     * This method return the admin page
     * 
     * @param navRoute : merchant-list/ user-list
     * @param status   : PENDING/ ACTIVE/ BLOCK
     * @param model
     * @return addmin_page
     */
    @GetMapping(value = { "/admin/{navRoute}/{status}/{pageNumber}" })
    public String adminPage(@PathVariable String navRoute, @PathVariable Status status, @PathVariable int pageNumber,
            Model model) {
        addListAttribute(status, navRoute, pageNumber, model);
        return "admin_page";
    }

    /**
     * This method is used to update status and return fragments by route
     * 
     * @param navRoute : merchant-list/ user-list
     * @param id
     * @param status   : PENDING/ ACTIVE/ BLOCK
     * @param route    : PENDING/ ACTIVE/ BLOCK
     * @param model
     * @return fragments/app-fragments: PENDING/ ACTIVE/ BLOCK
     */
    @PostMapping("/admin/{navRoute}/{route}/{status}/{id}")
    public String updateStatus(@PathVariable String navRoute, @PathVariable Long id, @PathVariable Status status,
            @PathVariable Status route, Model model) {
        merchantService.updateStatus(id, status, navRoute);
        addListAttribute(route, navRoute, 1, model);
        return "fragments/app-fragments ::${route}";
    }

    /**
     * This method is used to add Attribute
     * 
     * @param status
     * @param navRoute
     * @param pageNumber
     * @param model
     */

    private void addListAttribute(Status status, String navRoute, int pageNumber, Model model) {
        Page<?> page = merchantService.page(status, navRoute, pageNumber);
        int lastPageNumber = page.getLastPageNumber();
        model.addAttribute("usersOrMechants", page.getPaging());
        model.addAttribute("lastPageNumber", lastPageNumber);
        model.addAttribute("page", pageNumber);
        model.addAttribute("navRoute", navRoute);
        model.addAttribute("status", status);
        model.addAttribute("role", "admin");
    }

}
