package com.example.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
@RequestMapping("/api/images/category")
public class CategoryDownloadController {

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            // Load the resource as a ClassPathResource
            Resource resource = new ClassPathResource("static/uploads/images/category/" + filename);

            if (resource.exists()) {
                // Read the resource as InputStream
                InputStream inputStream = resource.getInputStream();
                byte[] fileBytes = inputStream.readAllBytes();

                // Return the file content
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .body(fileBytes);
            } else {
                System.out.println("File not found: " + filename);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}



//	package com.example.controller;
//
//	import org.springframework.core.io.Resource;
//	import org.springframework.core.io.UrlResource;
//	import org.springframework.http.HttpHeaders;
//	import org.springframework.http.ResponseEntity;
//	import org.springframework.web.bind.annotation.GetMapping;
//	import org.springframework.web.bind.annotation.PathVariable;
//	import org.springframework.web.bind.annotation.RequestMapping;
//	import org.springframework.web.bind.annotation.RestController;
//
//	import java.nio.file.Path;
//	import java.nio.file.Paths;
//
//	@RestController
//	@RequestMapping("/api/images/category")
//	public class CategoryDownloadController {
//
//	    // API to access images
//	    @GetMapping("/{filename}")
//	    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
//	        try {
//	            Path filePath = Paths.get("src/main/resources/static/uploads/images/category").resolve(filename).normalize();
//	            Resource resource = new UrlResource(filePath.toUri());
//
//	            if (resource.exists()) {
//	                return ResponseEntity.ok()
//	                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
//	                        .body(resource);
//	            } else {
//	                return ResponseEntity.notFound().build();
//	            }
//	        } catch (Exception e) {
//	            return ResponseEntity.internalServerError().build();
//	        }
//	    }
//	}
//
