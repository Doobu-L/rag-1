package com.prac.rag1.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(VectorStoreController.class)
class VectorStoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VectorStore vectorStore;

    @Test
    @DisplayName("검색 쿼리를 요청하면 유사한 문서의 내용을 반환해야 한다")
    void search() throws Exception {
        // given
        Document doc1 = new Document("Spring AI is awesome");
        Document doc2 = new Document("RAG with Java");
        List<Document> mockResults = List.of(doc1, doc2);

        // VectorStore 동작 Mocking
        given(vectorStore.similaritySearch(any(SearchRequest.class))).willReturn(mockResults);

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