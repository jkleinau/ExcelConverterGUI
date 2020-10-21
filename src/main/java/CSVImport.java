import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVImport {
    public ArrayList<String[]> data;
    public int headerIndex = -1;
    public int endIndex = -1;
    public String[] aufmassHeader = {
            "Raumname ",
            "Bodenoberfläche, ",
            "Volumen, ",
            "Boden Umfang, ",
            "Decke Umfang, ",
            "Wandfläche, ",
            "Wandfläche ohne Öffnung, ",
            "Umfang Türen, ",
            "Fensterflächen, ",
            "Raumhöhe "
    };

    public void loadData(String pathname) {
        CSVReader reader = null;
        data = new ArrayList<>();
        try {
            reader = new CSVReader(new FileReader(pathname));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                data.add(nextLine);
                //System.out.println(Arrays.toString(nextLine));
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

    }

    public void findHeader() {
        for (String[] entry :
                data) {
            if (entry[0].equals("ATTRIBUTE DER RÄUME")) {
                //Header defined automatically turned off
                // aufmassHeader = entry;
                headerIndex = data.indexOf(entry);
            } else if (headerIndex != -1 && entry[0].equals("")) {
                endIndex = data.indexOf(entry);
                return;
            }
        }
    }

    public void createAufmasszeilen(List<Aufmasszeile> aufmasszeileList) {
        //+2 because skip the header line and the first "Erdgeschoss" manually
        //TODO create Tree structure for different stories etc
        assert headerIndex != -1 && endIndex != -1;
        for (int i = headerIndex + 2; i < endIndex; i++) {
            for (int j = 1; j < aufmassHeader.length; j++) {
                aufmasszeileList.add(new Aufmasszeile("Erdgeschoss, "
                        + data.get(i)[0],
                        aufmassHeader[j] + data.get(i)[0],
                        data.get(i)[j]));
                //System.out.println(new Aufmasszeile("Erdgeschoss, " + data.get(i)[0],aufmassHeader[j],data.get(i)[j]).toString());
            }
        }
    }
}
