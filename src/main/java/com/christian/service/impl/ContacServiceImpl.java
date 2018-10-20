package com.christian.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.christian.converter.ContactConverter;
import com.christian.entity.Contact;
import com.christian.model.ContactModel;
import com.christian.repository.ContactRepository;
import com.christian.service.ContactService;

@Service("contactServiceImpl")
public class ContacServiceImpl implements ContactService {

	@Autowired
	@Qualifier("contactRepository")
	private ContactRepository contactRepository;
	
	@Autowired
	@Qualifier("contactConverter")
	private ContactConverter contactConverter;
	
	@Override
	public ContactModel addContact(ContactModel contactModel) {
		Contact contact = contactRepository.save(contactConverter.convertContactModel2Contact(contactModel));
		return contactConverter.convertContactContact2Model(contact);
	}
}
