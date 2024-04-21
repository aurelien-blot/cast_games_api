package com.castruche.cast_games_api.formatter;

import com.castruche.cast_games_api.dto.contact.ContactDto;
import com.castruche.cast_games_api.entity.Contact;
import org.springframework.stereotype.Service;

@Service
public class ContactFormatter implements IFormatter<Contact, ContactDto>{

    @Override
    public ContactDto entityToDto(Contact entity) {
        if(entity == null){
            return null;
        }
        ContactDto contactDto = new ContactDto();
        contactDto.setId(entity.getId());
        contactDto.setActive(entity.isActive());
        if(entity.getPlayer()!=null){
            contactDto.setPlayerId(entity.getPlayer().getId());
            contactDto.setPlayerUsername(entity.getPlayer().getUsername());
        }
        if(entity.getContact()!=null){
            contactDto.setContactId(entity.getContact().getId());
            contactDto.setContactUsername(entity.getContact().getUsername());
        }
        else if(entity.getContactMail()!=null){
            contactDto.setContactUsername(entity.getContactMail());
        }
        contactDto.setBlocked(entity.isBlocked());
        return contactDto;
    }

    @Override
    public Contact dtoToEntity(ContactDto dto) {
        Contact contact = new Contact();
        contact.setId(dto.getId());
        return contact;
    }

}
