package pl.miq3l.bttconnect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.miq3l.bttconnect.domain.Inverter;
import pl.miq3l.bttconnect.domain.Part;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Inverter_AC30 {
    private String part;
    private String output;
    private String filter;

    public static List<String> getFields() {
        return Arrays.stream(Inverter_AC30.class.getDeclaredFields())
                .map(Field::getName).collect(Collectors.toList());
    }
}

public class ExcelHandler {

    private static ExcelHandler INSTANCE;
    private final List<Part> parts = new ArrayList<>();
    private final File excelFile = new File("src/main/resources/products.xlsx");
    ObjectMapper mapper = new ObjectMapper();

    public static ExcelHandler getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ExcelHandler();
        }
        return INSTANCE;
    }

    public List<Part> getParts() {
        return parts;
    }

    private ExcelHandler() { }

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

    public static void main(String[] args) {
        ExcelHandler eh = ExcelHandler.getInstance();
        eh.read();
        eh.getParts().forEach(System.out::println);
    }

}
