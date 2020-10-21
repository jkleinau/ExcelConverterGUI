

public class Aufmasszeile {
    private String datum;
    private String blattnummer;
    private final String stichwort;
    private final String text;
    private final String anzahl;
    private String laenge;
    private String breite;
    private String hoehe;
    private String zeilenergebnis;
    private String abrechnungsdatum;
    private String abrechnungsbeleg;


    public Aufmasszeile(String stichwort, String text, String anzahl) {
        this.stichwort = stichwort;
        this.text = text;
        this.anzahl = anzahl;
    }

    public String getStichwort() {
        return stichwort;
    }

    public String getText() {
        return text;
    }

    public String getAnzahl() {
        return anzahl;
    }

    @Override
    public String toString() {
        return "Aufmasszeile{" +
                "stichwort='" + stichwort + '\'' +
                ", text='" + text + '\'' +
                ", anzahl='" + anzahl + '\'' +
                '}';
    }
}
