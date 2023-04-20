package com.example.SpendingMana.controller;

import com.example.SpendingMana.Service.CurrencyService;
import com.example.SpendingMana.entity.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/currency")
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;

    @GetMapping("/getall")
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public List<Currency> getAllCurrency(){
        return currencyService.listCurrency();
    }
    @PostMapping("/add")
    @RolesAllowed("ROLE_ADMIN")
    public Currency addCCurrency(@RequestBody Currency currency){
        return currencyService.addCurrency(currency);
    }
    @PutMapping("/update")
    @RolesAllowed("ROLE_ADMIN")
    public Currency updateCurrency(@RequestBody Currency currency,@PathVariable Long id){
        return currencyService.updateCurrency(currency,id);
    }
    @DeleteMapping("/delete/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public void deleteCurrency(@PathVariable("id") Long id){
        currencyService.deleteCurrency(id);
    }

    @PostMapping("/exportExcel")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> exportCurrency(){
        if(currencyService.exportCurrency()){
            return new ResponseEntity<>("Export file excel successfully !", HttpStatus.OK);
        }
        return new ResponseEntity<>("Export file excel failed", HttpStatus.OK);
    }

    @PostMapping("/importExcel")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> importCurrency(@RequestParam("file") MultipartFile file){
        try {
            List<Currency> provinceResponses = currencyService.importCurrency(file);
            return new ResponseEntity<>(provinceResponses, HttpStatus.OK);
        }catch (IOException e){
            e.printStackTrace();
            return new ResponseEntity<>("Import file excel failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
