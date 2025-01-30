package com.rabobank.config;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SSLConfig {
    public static void main(String[] args) {
        try {
        	   // Load the keystore
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(new FileInputStream("myKeystore.jks"), "$2a$12$V/vMHom7B71tk7WFU2k1l.p.iNMmIVVP/nS.5xktfI4kJLNrmsJvy".toCharArray());

            // Load the trust store
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(new FileInputStream("myTruststore.jks"), "$2a$12$V/vMHom7B71tk7WFU2k1l.p.iNMmIVVP/nS.5xktfI4kJLNrmsJvy".toCharArray());

            // Initialize KeyManagerFactory
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, "$2a$12$V/vMHom7B71tk7WFU2k1l.p.iNMmIVVP/nS.5xktfI4kJLNrmsJvy".toCharArray());

            // Initialize TrustManagerFactory
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);

            // Get TrustManagers
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

            // Configure SSL context
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagers, null);

            // Create a socket factory
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket("example.com", 443);

            // Use the SSL socket for secure communication
            OutputStream out = sslSocket.getOutputStream();
            out.write("Hello, SSL!".getBytes());
            sslSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
