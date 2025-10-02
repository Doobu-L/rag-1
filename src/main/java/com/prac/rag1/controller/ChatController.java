package com.prac.rag1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController
{

        private final ChatClient chatClient;
        private final VectorStore vectorStore;


        @GetMapping("/ask")
        String generation(@RequestParam String userInput) {
            SimpleLoggerAdvisor customLogger = new SimpleLoggerAdvisor(
                    request -> "Custom request: " + request.prompt().getUserMessage(),
                    response -> "Custom response: " + response.getResult(),
                    0
            );

            List<Document> hits = vectorStore.similaritySearch(SearchRequest.builder().query(userInput).topK(5).build());

            StringBuilder ctx = new StringBuilder();
            for (int i = 0; i < hits.size(); i++) {
                Document d = hits.get(i);
                ctx.append("【").append(i + 1).append("】 ").append(d.getFormattedContent().trim()).append("\n");
                if (d.getMetadata() != null && !d.getMetadata().isEmpty()) {
                    ctx.append("meta: ").append(d.getMetadata()).append("\n");
                }
                ctx.append("\n");
            };

            System.out.printf("ctx: %s\n", ctx.toString());
            String system = """
            당신은 한국어 어시스턴트입니다.
            아래 <컨텍스트>만을 근거로 간결하고 정확히 답하세요. 모르면 모른다고 하세요.
            <컨텍스트>
            %s
            </컨텍스트>
            """.formatted(ctx.toString());

            return this.chatClient.prompt()
                    .system(system)
                    .advisors(customLogger)
                    //.advisors(new QuestionAnswerAdvisor())
                    .user(userInput)
                    .call()
                    .content();
        }

}
