package com.example.controller;

import com.example.entity.Message;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "http://localhost:5173")
public class MessageController {

    @Autowired
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // Endpoint to get all messages
    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    // Endpoint to get a message by ID
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Optional<Message> message = messageService.getMessageById(id);
        return message.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to create a new message

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        try {
            Message savedMessage = messageService.saveMessage(message);
            return ResponseEntity.ok(savedMessage);
        } catch (Exception e) {
            // Log the exception and return an error message
            return ResponseEntity.status(500).body(null);
        }
    }
    
    // Endpoint to delete a message
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
    
    
//    @ExceptionHandler(OptimisticLockingFailureException.class)
//    public ResponseEntity<String> handleOptimisticLockingFailure(OptimisticLockingFailureException ex) {
//        return new ResponseEntity<>("Optimistic Locking Failure: " + ex.getMessage(), HttpStatus.CONFLICT);
//    }

}
