package pl.miq3l.bttconnect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.miq3l.bttconnect.domain.Inverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelHandler {

    private static ExcelHandler INSTANCE;
    private final Map<String, Inverter> products;
    ObjectMapper mapper = new ObjectMapper();

    public static ExcelHandler getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ExcelHandler();
        }
        return INSTANCE;
    }

    private ExcelHandler() {
        this.products = new HashMap<>();
    }

    private void read() {
        Map<String, String> map = new TreeMap<>();
        List<String> cols = Inverter.getFields();
        Inverter inverter;

        try {
            File excelFile = new File("src/main/resources/products.xlsx");
            FileInputStream fis = new FileInputStream(excelFile);

            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int FIRST_ROW_TO_GET = 1;

            for (int i = FIRST_ROW_TO_GET; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Iterator<Cell> cellIterator = row.cellIterator();

                int y = 0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    map.put(cols.get(y++), cell.toString());
                }
                inverter = mapper.convertValue(map, Inverter.class);
                this.products.put(inverter.getPart(), inverter);
            }

            workbook.close();
            fis.close();
        }
        catch(IOException e) {
            System.out.println("File not found");
        }

    }

    public static void main(String[] args) {
        ExcelHandler.getInstance().read();
        ExcelHandler.getInstance().products.entrySet()
                .forEach(System.out::println);
    }

}
