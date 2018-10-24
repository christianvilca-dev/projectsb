package com.christian.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.christian.constant.ViewConstant;
import com.christian.model.ContactModel;
import com.christian.service.ContactService;

@Controller
@RequestMapping("/contacts")
public class ContactController {
	
	private static final Log LOG = LogFactory.getLog(ContactController.class);

	@Autowired
	@Qualifier("contactServiceImpl")
	private ContactService contactService;
	
	@GetMapping("/cancel")
	public String cancel() {
		return "redirect:/contacts/showcontacts";
	}
	
	// @PreAuthorize -> Permite poner expresiones de spring
	// El metodo debe ser public
	// hasRole() -> Permite el acceso segun el rol, sino tiene permiso "Error 403"
	// Se puede agregar tanto a nivel de metodo como de clase e inclusive en una service
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	// @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
	// @PreAuthorize("permiteAll()")
	@GetMapping("/contactform")
	public String redirectContactForm(@RequestParam(name="id", required=false) int id, Model model) {
		ContactModel contact = new ContactModel();
		if (id!=0) {
			contact = contactService.findContactByIdModel(id);
		}
		model.addAttribute("contactModel", contact);
		return ViewConstant.CONTACT_FORM;
	}
	
	@PostMapping("/addcontact")
	// El @ModelAttribute(name="contactModel") se corresponde con el th:object="${contactModel}"
	public String addContact(@ModelAttribute(name="contactModel") ContactModel contactModel,
			Model model) {
		LOG.info("METHOD: addContact() -- Params: " + contactModel.toString());
		
		// Para que Hibernate sepa que ya existe y no añadir una nueva
		// En el repository el ".save()" no sabe distinguir si una entidad nueva o no
		// si en el contalModel no le pasas un id y para ello en la vista se
		// pone un "input hidden"
		if (null!=contactService.addContact(contactModel)) {
			model.addAttribute("result", 1);
		} else {
			model.addAttribute("result", 0);
		}
		return "redirect:/contacts/showcontacts";
	}
	
	@GetMapping("/showcontacts")
	public ModelAndView showContact() {
		ModelAndView mav = new ModelAndView(ViewConstant.CONTACTS);
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // Usuario actual de la session
		mav.addObject("username", user.getUsername());
		mav.addObject("contacts", contactService.listAllContacts());
		return mav;
	}
	
	@GetMapping("/removecontact")
	public ModelAndView removeContact(@RequestParam(name="id", required=true) int id) {
		contactService.removeContact(id);
		return showContact();
	}
}
