package com.smartfoodsaver.service;

import com.smartfoodsaver.model.ContactMessage;
import com.smartfoodsaver.repository.ContactMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService {

  private final ContactMessageRepository repo;

  public ContactService(ContactMessageRepository repo) {
    this.repo = repo;
  }

  @Transactional
  public void save(ContactMessage msg) {
    repo.save(msg);
  }
}
