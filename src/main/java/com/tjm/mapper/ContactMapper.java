package com.tjm.mapper;

import com.tjm.pojo.Contact;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ContactMapper {

    int insertContact(Contact contact);

    List<Contact> queryContactList();
}
