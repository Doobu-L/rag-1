package com.prac.rag1.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TokenSpliterServiceTest {

    private final TokenSpliterService tokenSpliterService = new TokenSpliterService();

    @Test
    @DisplayName("긴 텍스트가 포함된 문서를 기본 설정으로 분할하면 문서의 개수가 늘어나야 한다")
    void splitDocuments() {
        // given
        String longText = "Spring AI ".repeat(1000); // 매우 긴 텍스트 생성
        Document longDoc = new Document(longText);
        List<Document> documents = List.of(longDoc);

        // when
        List<Document> result = tokenSpliterService.splitDocuments(documents);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isGreaterThan(1); // 분할되었으므로 1개보다 많아야 함
        System.out.println("분할된 문서 개수: " + result.size());
    }

    @Test
    @DisplayName("짧은 텍스트는 분할되지 않고 그대로 유지되어야 한다")
    void splitShortDocument() {
        // given
        Document shortDoc = new Document("Short text");
        List<Document> documents = List.of(shortDoc);

        // when
        List<Document> result = tokenSpliterService.splitDocuments(documents);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getText()).isEqualTo("Short text");
    }
}