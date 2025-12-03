package com.prac.rag1.controller;

import com.prac.rag1.service.EmbeddingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmbeddingController.class)
class EmbeddingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmbeddingService embeddingService;

    @Test
    @DisplayName("메시지를 임베딩 요청하면 결과 벡터를 반환해야 한다")
    void embed() throws Exception {
        // given
        float[] mockEmbeddings = {0.1f, 0.2f, 0.3f};
        given(embeddingService.embedText(anyString())).willReturn(mockEmbeddings);

        // when & then
        mockMvc.perform(get("/embedding")
                        .param("message", "Hello Spring AI"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.embedding").exists())
                .andExpect(jsonPath("$.embedding.results[0].output[0]").value(0.1))
                .andExpect(jsonPath("$.embedding.results[0].output[1]").value(0.2));
    }
}
