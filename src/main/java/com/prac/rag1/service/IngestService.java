package com.prac.rag1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngestService {
    private final PagePdfDocumentReaderService pdfDocumentReaderService;
    private final TokenSpliterService tokenSpliterService;
    private final VectorStore vectorStore;

    public void ingest(final MultipartFile file) {
        List<Document> docs = pdfDocumentReaderService.getDocsFromPdf(file.getResource());
        docs = tokenSpliterService.splitDocuments(docs);
        vectorStore.add(docs);
    }
}
