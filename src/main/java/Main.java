import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String xmlFilePathMaterial = "src\\main\\resources\\xmlFile.xml";
    public static final String xmlFilePathAufmass = "src/main/resources/AufmassImport.xml";
    public static List<Aufmasszeile> aufmasszeileList = new ArrayList<>();

    public static void main(String[] args) {
//        ExcelReader excelReader = new ExcelReader();
//        Map<Integer, List<String>> data = excelReader.loadData("src/main/resources/PS_BSU_Gesamt-LV_Final.xlsx");
//        writeToXml(data);

        CSVImport csvImport = new CSVImport();
        csvImport.loadData("src/main/resources/Grundriss 3.csv");
        csvImport.findHeader();
        csvImport.createAufmasszeilen(aufmasszeileList);

        writeCSVToXMLforAufmass();

        //System.out.println(data.toString());
    }



    public static void writeExcelToXmlForMaterial(Map<Integer, List<String>> data) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("SCHRIFTSTUECKE");
            document.appendChild(root);

            Attr attr = document.createAttribute("xmlns");
            attr.setValue("urn:in-software-com:IN-SCHRIFTSTUECKE");
            root.setAttributeNode(attr);

            Element schriftstueck = document.createElement("SCHRIFTSTUECK");
            root.appendChild(schriftstueck);

            Element kopf = document.createElement("KOPF");
            schriftstueck.appendChild(kopf);

            //createElement("BELEGNUMMER", "A002203", kopf,document);

            //createElement("UEBERSCHRIFT", "Angebot aus LV", kopf,document);

            //createElement("PROJEKT", " ", kopf,document);

            Element positionen = document.createElement("POSITIONEN");
            schriftstueck.appendChild(positionen);

            for (Integer key :
                    data.keySet()) {

                List<String> entry = data.get(key);

                Element materialposition = document.createElement("MATERIALPOSITION");
                positionen.appendChild(materialposition);

                if (!entry.get(0).equals(" ")) {
                    createElement("POSITIONSNUMMER", entry.get(0), materialposition, document);
                }

                createElement("MENGE", "1", materialposition, document);

                if (!entry.get(3).equals(" ")) {
                    createElement("MENGENEINHEIT", entry.get(3), materialposition, document);
                }
                if (!entry.get(2).equals(" ")) {
                    createElement("LANGTEXT", entry.get(2), materialposition, document);
                }
                if (!entry.get(1).equals(" ")) {
                    createElement("KURZTEXT", entry.get(1), materialposition, document);
                }
                if (!entry.get(4).equals(" ")) {
                    createElement("EINZEL_VK", entry.get(4), materialposition, document);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePathMaterial));


            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }

    }

    public static void writeCSVToXMLforAufmass() {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentFactory.newDocumentBuilder();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        assert documentBuilder != null;
        Document document = documentBuilder.newDocument();

        Element root = document.createElement("PROJEKT");
        document.appendChild(root);

        Attr attr = document.createAttribute("xmlns");
        attr.setValue("urn:in-software-com:IN-AUFMASS");
        root.setAttributeNode(attr);


        Element position = document.createElement("POSITION");
        root.appendChild(position);

        Attr attr1 = document.createAttribute("ID");
        attr1.setValue("01.01");
        position.setAttributeNode(attr1);

        for (Aufmasszeile aufmasszeile :
                aufmasszeileList) {
            createElementfromAufmass(aufmasszeile, position, document);
        }


        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePathAufmass));
            transformer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        System.out.println("Done creating XML File");
    }

    public static void createElement(String tagName, String value, Element root, Document document) {
        Element temp = document.createElement(tagName);
        temp.appendChild(document.createTextNode(value));
        root.appendChild(temp);
    }

    public static void createElementfromAufmass(Aufmasszeile aufmasszeile, Element root, Document document) {
        Element aufmasszeilen = document.createElement("AUFMASSZEILE");
        root.appendChild(aufmasszeilen);

        createElement("STICHWORT", aufmasszeile.getStichwort(), aufmasszeilen, document);
        createElement("TEXT", aufmasszeile.getText(), aufmasszeilen, document);
        createElement("AUFMASS", aufmasszeile.getAnzahl(), aufmasszeilen, document);

    }

}
