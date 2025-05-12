package com.url.api.services;

import com.url.api.models.Url;
import com.url.api.repositories.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public ResponseEntity<?> createShortedUrl(String originalUrl, HttpServletRequest httpServletRequest){
        try{

            Optional<Url> verificationUrl = urlRepository.findByUrl(originalUrl);
            if(verificationUrl.isPresent()){
                return ResponseEntity.status(401).body("Url already exist");
            }

            Random generator = new Random();
            StringBuilder stringBuilder = new StringBuilder();
            String character = "abcdefghijklmnopqrstuvwxyz";

            for(int i = 0; i < 5; i++){
                int indice = generator.nextInt(character.length());
                stringBuilder.append(character.charAt(indice));
            }

            String shortedUrl = httpServletRequest
                    .getRequestURL().toString().concat(stringBuilder.toString());

            Url url = new Url();
            url.setShortedId(stringBuilder.toString());
            url.setOriginalUrl(originalUrl);
            urlRepository.save(url);

            return ResponseEntity.status(201).body(shortedUrl);

        }catch(RuntimeException exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    public ResponseEntity<?> redirectUrl(String id){
        try{
            Optional<Url> findedUrl = urlRepository.findByShortedId(id);
            if(findedUrl.isEmpty()){
                return ResponseEntity.status(400).body("Url doesn't exist");
            }

            String originalUrl = findedUrl.get().getOriginalUrl();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(URI.create(originalUrl));

            return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).build();

        }catch(RuntimeException exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

}
