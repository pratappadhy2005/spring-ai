package com.spring.ai.firstproject.controllers;

import com.spring.ai.firstproject.entity.Tut;
import com.spring.ai.firstproject.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class ChatController {

    private ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /*private ChatClient openAIChatClient;
    private ChatClient ollamaChatClient;*/

    /*public ChatController(@Qualifier("openAIChatClient") ChatClient openAIChatClient, @Qualifier("ollamaChatClient") ChatClient ollamaChatClient) {
        this.openAIChatClient = openAIChatClient;
        this.ollamaChatClient = ollamaChatClient;
    }*/

    @GetMapping("/chat")
    public ResponseEntity<List<Tut>> chat(@RequestParam(value = "q", required = true) String q) {
        var resultResponse = chatService.chat(q);
        return ResponseEntity.ok(resultResponse);
    }
}
