package com.christian.service.impl;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public List<ContactModel> listAllContacts() {
		List<Contact> contacts = contactRepository.findAll();
		List<ContactModel> contactModel = new ArrayList<ContactModel>();
		for (Contact contact : contacts) {
			contactModel.add(contactConverter.convertContactContact2Model(contact));
		}
		return contactModel;
	}

	@Override
	public Contact findContactById(int id) {
		return contactRepository.findById(id);
	}
	
	public ContactModel findContactByIdModel(int id) {
		return contactConverter.convertContactContact2Model(findContactById(id));
	}

	@Override
	public void removeContact(int id) {
		// contactRepository.delete(contactRepository.findById(id));
		
		Contact contact = findContactById(id);
		if (null!=contact) {
			contactRepository.delete(contact);
		}
	}
}
