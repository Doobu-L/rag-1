package com.prac.rag1.web.controller;

import com.prac.rag1.application.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController
{

        private final ChatService chatService;


        @PostMapping("/ask")
        String generation(@RequestParam("userInput") String userInput,
                                @RequestParam(value = "image", required = false) MultipartFile image,
                                @RequestParam(defaultValue = "false") boolean useRag) throws IOException {

            return chatService.getResponse(userInput, image, useRag);
        }

}
