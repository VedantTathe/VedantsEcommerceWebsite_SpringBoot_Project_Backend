package com.example.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")
public class FileDownloadController {

    // API to access images
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            // Load the resource from the classpath
            Resource resource = new ClassPathResource("static/uploads/images/" + filename);

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                System.out.println("File not found: " + filename); // Debug log
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage()); // Debug log
            return ResponseEntity.internalServerError().build();
        }
    }
}
