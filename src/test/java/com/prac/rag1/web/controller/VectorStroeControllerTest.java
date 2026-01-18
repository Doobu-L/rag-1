package com.prac.rag1.web.controller;

import com.prac.rag1.application.service.VectorStoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(VectorStoreController.class)
class VectorStoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VectorStoreService vectorStoreService;

    @Test
    @DisplayName("검색 쿼리를 요청하면 유사한 문서의 내용을 반환해야 한다")
    void search() throws Exception {
        // given
        List<String> mockResults = List.of("Spring AI is awesome", "RAG with Java");

        // VectorStoreService 동작 Mocking
        given(vectorStoreService.search(anyString(), anyInt())).willReturn(mockResults);

        // when & then
        mockMvc.perform(get("/vector-store/search")
                        .param("q", "Spring")
                        .param("k", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0]").value("Spring AI is awesome"))
                .andExpect(jsonPath("$[1]").value("RAG with Java"));
    }
}