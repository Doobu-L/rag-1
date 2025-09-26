package com.prac.rag1.controller;

import com.prac.rag1.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EmbeddingController {

    private final EmbeddingService embeddingService;

    @GetMapping("/ai/embedding")
    public Map embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        float[] embeddings = embeddingService.embedText(message);
        EmbeddingResponse embeddingResponse = new EmbeddingResponse(List.of(new Embedding(embeddings, 0)));
        return Map.of("embedding", embeddingResponse);
    }
}