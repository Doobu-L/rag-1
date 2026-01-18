package com.prac.rag1.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.embedding.EmbeddingModel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EmbeddingServiceTest {

    @Mock
    private EmbeddingModel embeddingModel;

    @InjectMocks
    private EmbeddingService embeddingService;

    @Test
    @DisplayName("Text embedding should return float array")
    void embedTextTest() {
        // given
        float[] expectedOutput = {0.1f, 0.2f, 0.3f};
        given(embeddingModel.embed(anyString())).willReturn(expectedOutput);

        // when
        float[] result = embeddingService.embedText("test text");

        // then
        assertThat(result).isEqualTo(expectedOutput);
    }
}
