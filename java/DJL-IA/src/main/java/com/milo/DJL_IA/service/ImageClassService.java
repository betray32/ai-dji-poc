package com.milo.DJL_IA.service;

import ai.djl.Application;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.TranslateException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Base64;

/**
 * Servicio para clasificación de imágenes utilizando DJL.
 * Carga un modelo preentrenado y permite clasificar imágenes desde URL o base64.
 */
@Service
public class ImageClassService {
    private ZooModel<Image, Classifications> model;

    @PostConstruct
    public void init() throws Exception {
        // Carga un modelo de clasificación general (ResNet50/Imagenet)
        Criteria<Image, Classifications> criteria = Criteria.builder()
                .setTypes(Image.class, Classifications.class)
                .optApplication(Application.CV.IMAGE_CLASSIFICATION)
                // .optFilter("backbone", "resnet") // opcional: forzar backbone
                // .optFilter("layers", "50")       // opcional: forzar resnet50
                .build();

        model = ModelZoo.loadModel(criteria);

        // Warm-up para evitar primer request lento
        try (var predictor = model.newPredictor()) {
            Image dummy = ImageFactory.getInstance().fromImage(new java.awt.image.BufferedImage(224, 224, java.awt.image.BufferedImage.TYPE_INT_RGB));
            predictor.predict(dummy);
        } catch (TranslateException ignore) {
            // warm-up best effort
        }
    }

    public Classifications classifyFromUrl(String url) throws Exception {
        Image img = ImageFactory.getInstance().fromUrl(new URL(url));
        try (var predictor = model.newPredictor()) {
            return predictor.predict(img);
        }
    }

    public Classifications classifyFromBase64(String base64) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(base64);
        try (var bais = new ByteArrayInputStream(bytes)) {
            Image img = ImageFactory.getInstance().fromInputStream(bais);
            try (var predictor = model.newPredictor()) {
                return predictor.predict(img);
            }
        }
    }
}
