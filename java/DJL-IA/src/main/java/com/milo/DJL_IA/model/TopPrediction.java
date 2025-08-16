package com.milo.DJL_IA.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.AccessLevel;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TopPrediction {
    private String className;
    private double probability;
}