package com.christian.converter;

import org.springframework.stereotype.Component;

import com.christian.entity.Contact;
import com.christian.model.ContactModel;

// TODO: Auto-generated Javadoc
/**
 * JAutoDoc plugin de eclipse
 * Permite crear Java docs
 * 
 * En el MarketPlace > jautodoc
 * 
 * Clase > jautodoc > Add Javadoc
 */
@Component("contactConverter")
public class ContactConverter {

	/**
	 * Convert contact model 2 contact.
	 *
	 * @param contactModel the contact model
	 * @return the contact
	 */
	public Contact convertContactModel2Contact(ContactModel contactModel) {
		Contact contact = new Contact();
		contact.setId(contactModel.getId());
		contact.setFirstname(contactModel.getFirstname());
		contact.setLastname(contactModel.getLastname());
		contact.setTelephone(contactModel.getTelephone());
		contact.setCity(contactModel.getCity());
		return contact;
	}

	/**
	 * Convert contact contact 2 model.
	 *
	 * @param contact the contact
	 * @return the contact model
	 */
	public ContactModel convertContactContact2Model(Contact contact) {
		ContactModel contactModel = new ContactModel();
		contactModel.setId(contact.getId());
		contactModel.setFirstname(contact.getFirstname());
		contactModel.setLastname(contact.getLastname());
		contactModel.setTelephone(contact.getTelephone());
		contactModel.setCity(contact.getCity());
		return contactModel;
	}
}
