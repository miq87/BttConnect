package pl.miq3l.bttconnect.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import pl.miq3l.bttconnect.components.model.Inverter_AC30;
import pl.miq3l.bttconnect.models.Inverter;
import pl.miq3l.bttconnect.models.Part;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Component
public class ExcelHandler {

    private final List<Part> parts = new ArrayList<>();
    private final File excelFile = new File("src/main/resources/products.xlsx");
    ObjectMapper mapper = new ObjectMapper();

    public List<Part> getParts() {
        return parts;
    }

    public void read() {
        try {
            FileInputStream fis = new FileInputStream(excelFile);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();

            while(sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                getAllFromSheet(sheet);
            }
            workbook.close();
            fis.close();
        }
        catch(IOException e) {
            System.out.println("File not found");
        }
    }

    private void getAllFromSheet(Sheet sheet) {
        Map<String, String> map = new TreeMap<>();
        List<String> cols;
        Part part;
        int y;

        int FIRST_ROW_TO_GET = 1;
        for (int i = FIRST_ROW_TO_GET; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Iterator<Cell> cellIterator = row.cellIterator();

            if(sheet.getSheetName().contains("AC10")) {
                map.clear();
                cols = Inverter.getFields();
                y = 0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    map.put(cols.get(y++), cell.toString());
                }
                Inverter inverter = mapper.convertValue(map, Inverter.class);
                part = Part.builder()
                        .part(inverter.getPart())
                        .description(sheet.getSheetName().replace("_", " ").concat(", ")
                                + inverter.getPower() + "kW / "
                                + inverter.getPowerSupply().split("/")[0] + "V")
                        .build();
                this.parts.add(part);
            }
            else if(sheet.getSheetName().contains("AC30")) {
                map.clear();
                cols = Inverter_AC30.getFields();
                y = 0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    map.put(cols.get(y++), cell.toString());
                }
                Inverter_AC30 inverter_ac30 = mapper.convertValue(map, Inverter_AC30.class);
                part = Part.builder()
                        .part(inverter_ac30.getPart())
                        .description(sheet.getSheetName()
                                + ", Output: " + inverter_ac30.getOutput()
                                + "kW, Filter: " + inverter_ac30.getFilter())
                        .build();
                this.parts.add(part);
            }
        }
    }

}
