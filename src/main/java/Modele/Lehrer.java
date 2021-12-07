package Modele;

public class Lehrer extends Person{

    private long lehrerID;

    public Lehrer(String vorname, String nachname, long lehrerID) {
        super(vorname, nachname);
        this.lehrerID = lehrerID;
    }

    public long getLehrerID() {
        return lehrerID;
    }

    public void setLehrerID(long lehrerID) {
        this.lehrerID = lehrerID;
    }

    @Override
    public String toString() {
        return "Lehrer{" +
                ", lehrerID=" + lehrerID +
                '}';
    }

}
