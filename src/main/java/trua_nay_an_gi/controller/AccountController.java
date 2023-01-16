package trua_nay_an_gi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.service.IAccountService;

@Controller
public class AccountController {

	@Autowired
	private IAccountService accountService;

	@GetMapping(value = { "account-list" })
	public String listAccount(Model model) {
		model.addAttribute("listAccount", accountService.findAll());
		return "account-list";
	}

//	  @PostMapping("add")
//	  public String addAccount(Model model) {
//	    model.addAttribute("account", new Account());
//	    return "account-save";
//	  }

	@GetMapping("/user/create-account")
	public ModelAndView showCreateForm() {
		ModelAndView modelAndView = new ModelAndView("/account-save");
		modelAndView.addObject("account", new Account());
		return modelAndView;
	}

	@PostMapping("/create-account")
	public ModelAndView saveAccount(@ModelAttribute("account") Account account) {
		accountService.save(account);
		ModelAndView modelAndView = new ModelAndView("/account-save");
		modelAndView.addObject("account", new Account());
		return modelAndView;
	}

	@GetMapping("/{id}")
	public String findAccountById(@PathVariable Long id, Model model) {
		Account account = accountService.findById(id);
		model.addAttribute("account", account);
		return "account-view";
	}

	@GetMapping("/user/search")
	public String findAccountByName(@RequestParam(name = "name") String name, Model model) {
		Account account = accountService.findByName(name);
		model.addAttribute("listAccount", account);
		return "account-list";
	}

	@GetMapping("/user/findId")
	public String findIdByName(@RequestParam(name = "name") String name, Model model) {
		Long id = accountService.findIdUserByUserName(name);
		model.addAttribute("id", id);
		return "account-list";
	}

	@GetMapping("update/{id}")
	public String updateAccount(@PathVariable Long id, Model model) {
		Account account = accountService.findById(id);
		model.addAttribute("account", account);
		return "account-update";
	}

//	  @RequestMapping("/saveCustomer")
//	  public String doSaveCustomer(@ModelAttribute("Customer") Customer customer, Model model) {
//	    customerService.save(customer);
//	    model.addAttribute("listCustomer", customerService.findAll());
//	    return "customer-list";
//	  }
	@PostMapping("/updateAccount")
	public String doUpdateCustomer(@ModelAttribute("account") Account account, Model model) {
		accountService.update(account);
		model.addAttribute("listAccount", accountService.findAll());
		return "account-list";
	}

	@DeleteMapping("delete/{id}")
	public String doDeleteAccount(@PathVariable Long id, Model model) {
		accountService.delete(id);
		model.addAttribute("listAccount", accountService.findAll());
		return "account-list";
	}

}
