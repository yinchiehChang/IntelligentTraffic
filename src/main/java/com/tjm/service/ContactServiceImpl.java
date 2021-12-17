package com.tjm.service;

import com.tjm.mapper.ContactMapper;
import com.tjm.pojo.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    ContactMapper contactMapper;

    @Override
    public int insertContact(Contact contact) {
        return contactMapper.insertContact(contact);
    }

    @Override
    public List<Contact> queryContactList() {
        return contactMapper.queryContactList();
    }
}
