

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {

    public Map<Integer, List<String>> loadData(String pathname) {
        Workbook workbook = null;
        try {
            FileInputStream file = new FileInputStream(new File(pathname));
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert workbook != null;
        Sheet sheet = workbook.getSheetAt(0);

        Map<Integer, List<String>> data = new HashMap<>();
        int i = 0;
        for (Row row : sheet) {
            data.put(i, new ArrayList<String>());
            for (int j = 0; j < 5; j++) {
                Cell cell = row.getCell(j);
                try {
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            data.get(i).add(cell.getRichStringCellValue().getString());
                            break;
                        case NUMERIC:
                            data.get(i).add(Math.round(cell.getNumericCellValue() * 100.0) / 100.0 + "");
                            break;

                        case _NONE:
                        case BOOLEAN:
                        case BLANK:
                        case FORMULA:
                        case ERROR:
                        default:
                            data.get(i).add(" ");
                    }
                } catch (Exception e) {
                    data.get(i).add("");
                }
            }
            i++;
        }

        //Remove head from Map
        data.remove(0);

        return data;
    }
}
