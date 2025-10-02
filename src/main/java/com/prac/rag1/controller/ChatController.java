package com.prac.rag1.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController
{

        private final ChatClient chatClient;

        public ChatController(ChatClient.Builder chatClientBuilder) {
            this.chatClient = chatClientBuilder.build();
        }

        @GetMapping("/ask")
        String generation(@RequestParam String userInput) {
            SimpleLoggerAdvisor customLogger = new SimpleLoggerAdvisor(
                    request -> "Custom request: " + request.prompt().getUserMessage(),
                    response -> "Custom response: " + response.getResult(),
                    0
            );

            return this.chatClient.prompt()
                    .advisors(customLogger)
                    //.advisors(new QuestionAnswerAdvisor())
                    .user(userInput)
                    .call()
                    .content();
        }

}
