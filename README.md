# ai-djl-poc

This repository contains a proof of concept (POC) built with **Java** and **DJL (Deep Java Library)** to perform **AI-powered image classification**.  
The application exposes its functionality through a **REST API** developed with **Spring Boot**.

---

## **Repository Structure**

- **python/**  
  Placeholder for POCs in Python (not currently used in this project).  

- **java/**  
  Contains the full implementation of the AI service using Java + DJL.  

### Inside `java/com.milo.DJL_IA`:
- **controller/**
  - `ImageClassController`: REST controller exposing the `/vision/classify` endpoint to classify images via URL or Base64 input.  

- **model/**
  - `ImageClassifyRequest`: Represents the request payload with either an image URL or a Base64 string.  
  - `TopPrediction`: Simple DTO that holds a class name and its probability for returning top predictions.  

- **service/**
  - `ImageClassService`: Core service that loads a pretrained ResNet model with DJL and performs image classification (URL/Base64 supported).  
  - `AutoExec`: Utility/runner class for executing predefined service logic (if needed).  
  - `DjllApplication`: Main Spring Boot application entry point to start the REST API.  

- **resources/**
  - `application.properties`: Application configuration file.  

- **test/**  
  Contains (optional) unit tests.  

---

## **Technologies Used**
- **Java 17**: Main programming language.  
- **Spring Boot**: For building the REST API.  
- **DJL (Deep Java Library)**: To load and run pretrained AI models (ResNet / ImageNet).  

---

## **Usage**

1. **Start the Application**:  
   Build and run the Spring Boot app. It will start a REST API (default on port 8080).  

2. **Classify an Image**:  
   Send a POST request to the API endpoint:

   ```bash
   POST http://localhost:8080/vision/classify
   Content-Type: application/json

   {
     "url": "https://raw.githubusercontent.com/awslabs/djl/master/examples/src/test/resources/kitten.jpg"
   }

---
# Samples
```json
[
  { "className": "tabby, tabby cat", "probability": 0.82 },
  { "className": "tiger cat", "probability": 0.12 },
  { "className": "Egyptian cat", "probability": 0.04 },
  { "className": "lynx", "probability": 0.01 },
  { "className": "cougar, puma", "probability": 0.01 }
]

