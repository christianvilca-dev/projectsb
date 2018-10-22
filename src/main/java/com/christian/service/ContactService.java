package com.christian.service;

import java.util.List;

import com.christian.entity.Contact;
import com.christian.model.ContactModel;

public interface ContactService {

	public abstract ContactModel addContact(ContactModel contactModel);
	
	public abstract List<ContactModel> listAllContacts();
	
	public abstract Contact findContactById(int id);
	
	public abstract void removeContact(int id);
	
	public ContactModel findContactByIdModel(int id);
}
