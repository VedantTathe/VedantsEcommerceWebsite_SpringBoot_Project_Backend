package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

	@Autowired
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    // Method to get all messages
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Method to get a message by ID
    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

    // Method to delete a message by ID
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
}
