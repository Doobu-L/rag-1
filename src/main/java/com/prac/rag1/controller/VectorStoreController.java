package com.prac.rag1.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vector-store")
@RequiredArgsConstructor
public class VectorStoreController {

    private final VectorStore vectorStore;

    @PostMapping("/seed")
    public ResponseEntity<String> seed() {

        List <Document> documents = List.of(
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
                new Document("The World is Big and Salvation Lurks Around the Corner"),
                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));

        vectorStore.add(documents);
        return ResponseEntity.ok("Seeded test documents.");
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> search(@RequestParam String q,
                                               @RequestParam(defaultValue = "3") int k) {
        var results = this.vectorStore.similaritySearch(SearchRequest.builder().query(q).topK(5).build());

        var contents = results.stream()
                .map(Document::getText)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contents);
    }
}