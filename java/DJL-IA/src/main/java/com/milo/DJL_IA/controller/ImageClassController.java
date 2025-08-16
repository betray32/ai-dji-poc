package com.milo.DJL_IA.controller;


import com.milo.DJL_IA.model.ImageClassifyRequest;
import com.milo.DJL_IA.model.TopPrediction;
import com.milo.DJL_IA.service.ImageClassService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ai.djl.modality.Classifications;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/vision")
public class ImageClassController {
    private final ImageClassService service;

    public ImageClassController(ImageClassService service) {
        this.service = service;
    }

    @PostMapping("/classify")
    public ResponseEntity<?> classify(@RequestBody ImageClassifyRequest req) {
        try {
            if ((req.getUrl() == null || req.getUrl().isBlank()) &&
                    (req.getBase64() == null || req.getBase64().isBlank())) {
                return ResponseEntity.badRequest().body("Provide either 'url' or 'base64'.");
            }

            Classifications result = (req.getUrl() != null && !req.getUrl().isBlank())
                    ? service.classifyFromUrl(req.getUrl())
                    : service.classifyFromBase64(req.getBase64());

            // Top-5 para mostrar algo útil
            List<TopPrediction> top5 = result.topK(5).stream()
                    .sorted(Comparator.comparingDouble(Classifications.Classification::getProbability).reversed())
                    .map(c -> new TopPrediction(c.getClassName(), c.getProbability()))
                    .toList();

            return ResponseEntity.ok(top5);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing image: " + e.getMessage());
        }
    }
}
