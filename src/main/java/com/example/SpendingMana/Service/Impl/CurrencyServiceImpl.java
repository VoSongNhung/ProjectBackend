package com.example.SpendingMana.Service.Impl;

import com.example.SpendingMana.Constants.FileConstants;
import com.example.SpendingMana.Service.CurrencyService;
import com.example.SpendingMana.entity.Currency;
import com.example.SpendingMana.respository.CurrencyRepository;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Autowired
    CurrencyRepository currencyRepo;
    private static final String FILE_PATH = FileConstants.EXCEL_PARH +"/currency.xlsx";

    @Override
    public List<Currency> listCurrency() {
        return currencyRepo.findAll();
    }

    @Override
    public Currency addCurrency(Currency currency) {
        return currencyRepo.save(currency);
    }

    @Override
    public Currency updateCurrency(Currency currency, Long id) {
        Currency findbyId = currencyRepo.findById(id).get();
        if(!findbyId.equals(null)){
            currency.setCurrency_id(id);
            return currencyRepo.save(currency);
        }
        return null;
    }

    @Override
    public void deleteCurrency(Long id) {
            currencyRepo.deleteById(id);
            System.out.println("Category id: "+ id + " deleted");
    }

    @Override
    public Boolean exportCurrency() {
        try (Workbook workbook = new XSSFWorkbook()) {
            File dataDir = new File(FileConstants.EXCEL_PARH);
            if (!dataDir.exists()) {
                dataDir.mkdir();
            }
            List<Currency> currencyList = currencyRepo.findAll();
            Sheet sheet =workbook.createSheet("Currnency");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID Currency");
            header.createCell(1).setCellValue("Currency Name");
            header.createCell(2).setCellValue("Symbol");
            header.createCell(3).setCellValue("Icon");

            for (int i = 0; i < currencyList.size(); i++) {
                Currency currency = currencyList.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(currency.getCurrency_id());
                row.createCell(1).setCellValue(currency.getNameCurrency());
                row.createCell(2).setCellValue(currency.getSymbol());
                row.createCell(3).setCellValue(currency.getIcon());
            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH)) {
                workbook.write(fileOutputStream);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public List<Currency> importCurrency(MultipartFile file) throws IOException {
        List<Currency> currencies = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())){
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            if(rows.hasNext()){
                rows.next();
            }

            while(rows.hasNext()){
                Row row = rows.next();

                Long idCurrency = null;
                if(row.getCell(0).getCellType() == CellType.NUMERIC) {
                    idCurrency = (long) row.getCell(0).getNumericCellValue();
                } else {
                    idCurrency = Long.parseLong(row.getCell(0).getStringCellValue());
                }
                String nameCurrency = row.getCell(1).getStringCellValue();
                String symbol =  row.getCell(2).getStringCellValue();
                String icon = row.getCell(3).getStringCellValue();

                Currency currency = new Currency(idCurrency,nameCurrency,symbol,icon,null);

                currencyRepo.save(currency);
                currencies.add(currency);
            }

        }catch (IOException e){
            e.printStackTrace();
            throw e;
        }

        return currencies;
    }
}
