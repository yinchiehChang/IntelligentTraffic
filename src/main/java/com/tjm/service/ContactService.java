package com.tjm.service;

import com.tjm.pojo.Contact;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactService {

    int insertContact(Contact contact);

    List<Contact> queryContactList();
}
