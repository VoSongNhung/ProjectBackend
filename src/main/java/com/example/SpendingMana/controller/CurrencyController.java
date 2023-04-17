package com.example.SpendingMana.controller;

import com.example.SpendingMana.Service.CurrencyService;
import com.example.SpendingMana.entity.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/currency")
public class CurrencyController {

    @Autowired
    CurrencyService currencyServlet;
    @PostMapping("/exportExcel")
    public ResponseEntity<?> exportCurrency(){
        if(currencyServlet.exportCurrency()){
            return new ResponseEntity<>("Export file excel successfully !", HttpStatus.OK);
        }
        return new ResponseEntity<>("Export file excel failed", HttpStatus.OK);
    }

    @PostMapping("/importExcel")
    public ResponseEntity<?> importCurrency(@RequestParam("file") MultipartFile file){
        try {
            List<Currency> provinceResponses = currencyServlet.importCurrency(file);
            return new ResponseEntity<>(provinceResponses, HttpStatus.OK);
        }catch (IOException e){
            e.printStackTrace();
            return new ResponseEntity<>("Import file excel failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
