package com.example.pry_portafolio_backend.config;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;


@Configuration
public class FirebaseAdminConfig {

    @PostConstruct
    public void init() {
        try {
            // Evita reinicializar si ya existe una instancia
            if (!FirebaseApp.getApps().isEmpty()) {
                return;
            }

            // CARGAMOS EL ARCHIVO DESDE RESOURCES
            // Asegúrate de que el nombre coincida con el archivo en src/main/resources
            ClassPathResource resource = new ClassPathResource("serviceAccountKey.json");

            if (!resource.exists()) {
                throw new IllegalStateException("No se encontró el archivo serviceAccountKey.json en src/main/resources");
            }

            try (InputStream serviceAccount = resource.getInputStream()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
                FirebaseApp.initializeApp(options);
                System.out.println("✅ Firebase Admin inicializado correctamente.");
            }
        } catch (Exception e) {
            // Imprime el error real para saber qué pasó
            e.printStackTrace();
            throw new IllegalStateException("Error fatal al inicializar Firebase: " + e.getMessage(), e);
        }
    }
}