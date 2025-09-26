package com.prac.rag1.service;

import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingModel;
import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {

    private final VertexAiTextEmbeddingModel embeddingModel;

    public EmbeddingService(VertexAiTextEmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    // 텍스트를 임베딩하여 벡터를 얻는 메서드
    public float[] embedText(String text) {
        return embeddingModel.embed(text);
    }
}