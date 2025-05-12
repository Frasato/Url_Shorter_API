package com.url.api.controllers;

import com.url.api.dtos.RequestIdDto;
import com.url.api.dtos.RequestUrlDto;
import com.url.api.services.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping()
    public ResponseEntity<?> createShortedUrl(@RequestBody RequestUrlDto requestUrlDto, HttpServletRequest httpServletRequest){
        return urlService.createShortedUrl(requestUrlDto.url(), httpServletRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> redirectUrl(@PathVariable("id") RequestIdDto requestIdDto){
        return urlService.redirectUrl(requestIdDto.id());
    }

}
