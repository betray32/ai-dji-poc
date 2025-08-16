package com.milo.DJL_IA.model;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a request for image classification.
 * Contains either a URL or a base64 encoded image.
 */
@Builder
@Data
public class ImageClassifyRequest {
    private String url;
    private String base64;
}
