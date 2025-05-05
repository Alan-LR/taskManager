package com.example.taskManager.controller;

import com.example.taskManager.dtos.comments.CommentRequestDTO;
import com.example.taskManager.dtos.comments.CommentResponseDTO;
import com.example.taskManager.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private CommentService service;

    public CommentController(CommentService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CommentResponseDTO> saveComment(@RequestBody CommentRequestDTO data, JwtAuthenticationToken token){
        CommentResponseDTO savedComment = service.saveComment(data, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

}
