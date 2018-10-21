package com.christian.service;

import java.util.List;

import com.christian.model.ContactModel;

public interface ContactService {

	public abstract ContactModel addContact(ContactModel contactModel);
	
	public abstract List<ContactModel> listAllContacts();
}
