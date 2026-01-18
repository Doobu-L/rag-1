package com.prac.rag1.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.content.Media;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public String getResponse(String userInput, MultipartFile image, boolean useRag) throws IOException {
        String systemPrompt;
        UserMessage userMessage;

        if (image != null && !image.isEmpty()) {
            // 1. 멀티모달 요청 처리
            systemPrompt = "당신은 유능한 한국어 어시스턴트입니다. 사용자의 질문과 이미지를 보고 친절하고 상세하게 답변해주세요.";
            userMessage = new UserMessage.Builder()
                    .text(userInput)
                    .media(new Media(MimeTypeUtils.parseMimeType(image.getContentType()), image.getResource()))
                    .build();
        } else {
            // 2. 텍스트 기반 요청 처리
            userMessage = new UserMessage(userInput);
            if (useRag) {
                // RAG 모드
                systemPrompt = buildRagSystemPrompt(userInput);
            } else {
                // 일반 LLM 모드
                systemPrompt = "당신은 유능한 한국어 어시스턴트입니다. 사용자에게 친절하고 상세하게 답변해주세요.";
            }
        }

        return this.chatClient.prompt()
                .system(systemPrompt)
                .messages(userMessage)
                .call()
                .content();
    }

    private String buildRagSystemPrompt(String userInput) {
        List<Document> hits = vectorStore.similaritySearch(SearchRequest.builder().query(userInput).topK(5).build());

        StringBuilder ctx = new StringBuilder();
        for (int i = 0; i < hits.size(); i++) {
            Document d = hits.get(i);
            ctx.append("【").append(i + 1).append("】 ").append(d.getFormattedContent().trim()).append("\n");
            if (d.getMetadata() != null && !d.getMetadata().isEmpty()) {
                ctx.append("meta: ").append(d.getMetadata()).append("\n");
            }
            ctx.append("\n");
        }
        System.out.printf("ctx: %s\n", ctx.toString());

        return """
        당신은 한국어 어시스턴트입니다.
        아래 <컨텍스트>만을 근거로 간결하고 정확히 답하세요. 모르면 모른다고 하세요.
        <컨텍스트>
        %s
        </컨텍스트>
        """.formatted(ctx.toString());
    }
}
