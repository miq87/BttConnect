package pl.miq3l.bttconnect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.miq3l.bttconnect.domain.Inverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelHandler {

    private static ExcelHandler INSTANCE;
    ObjectMapper mapper = new ObjectMapper();
    private final Map<String, String> map = new TreeMap<>();
    private final List<String> cols = Inverter.getFields();
    private final List<Inverter> inverters = new ArrayList<>();
    private final File excelFile = new File("src/main/resources/products.xlsx");

    public static ExcelHandler getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ExcelHandler();
        }
        return INSTANCE;
    }

    public List<Inverter> getInverters() {
        return inverters;
    }

    private ExcelHandler() { }

    public void read() {
        try {
            FileInputStream fis = new FileInputStream(excelFile);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();

            while(sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                System.out.println(sheet.getSheetName());

                switch (sheet.getSheetName()) {
                    case "AC10_IP20":
                        this.getInvertersFromSheet(sheet);
                        break;
                    case "AC10_IP66":
                        this.getInvertersFromSheet(sheet);
                        break;
                    default:
                        break;
                }
            }
            workbook.close();
            fis.close();
        }
        catch(IOException e) {
            System.out.println("File not found");
        }

    }

    private void getInvertersFromSheet(Sheet sheet) {
        int FIRST_ROW_TO_GET = 1;
        for (int i = FIRST_ROW_TO_GET; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Iterator<Cell> cellIterator = row.cellIterator();

            int y = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                map.put(cols.get(y++), cell.toString());
            }
            this.inverters.add(mapper.convertValue(map, Inverter.class));
        }
    }

    public static void main(String[] args) {
        ExcelHandler eh = ExcelHandler.getInstance();
        eh.read();
        eh.getInverters().forEach(System.out::println);
    }

}
