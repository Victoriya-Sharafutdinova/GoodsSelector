package com.example.GoodsSelector.controllers;

import com.example.GoodsSelector.services.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ParserController {
    private final ParserService parserService;

    @Autowired
    public ParserController(ParserService parserService) {
        this.parserService = parserService;
    }

    @GetMapping(value = "/parse")
    public ResponseEntity<?> get() {
        // 91879
        parserService.parse("Крупная бытовая техника.xlsx", 100, 0, 2, 1, 6, 8, 14, 21);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
