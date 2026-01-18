package com.prac.rag1.web.controller;

import com.prac.rag1.application.service.VectorStoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vector-store")
@RequiredArgsConstructor
public class VectorStoreController {

    private final VectorStoreService vectorStoreService;

    @PostMapping("/seed")
    public ResponseEntity<String> seed() {
        vectorStoreService.seed();
        return ResponseEntity.ok("Seeded test documents.");
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> search(@RequestParam String q,
                                               @RequestParam(defaultValue = "3") int k) {
        var contents = vectorStoreService.search(q, k);
        return ResponseEntity.ok(contents);
    }
}