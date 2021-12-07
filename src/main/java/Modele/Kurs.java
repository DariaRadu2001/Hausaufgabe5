package Modele;

public class Kurs{

    private long ID;
    private String name;
    private long lehrer;
    private int maximaleAnzahlStudenten;
    private int ects;

    public Kurs(long ID, String name, long lehrer, int maximaleAnzahlStudenten, int ECTS) {
        this.ID = ID;
        this.name = name;
        this.lehrer = lehrer;
        this.maximaleAnzahlStudenten = maximaleAnzahlStudenten;
        this.ects = ECTS;
    }

    public Kurs(long ID,int ECTS) {
        this.ID = ID;
        this.name = "";
        this.lehrer = 0L;
        this.maximaleAnzahlStudenten = 0;
        this.ects = ECTS;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLehrer() {
        return lehrer;
    }

    public void setLehrer(long lehrer) {
        this.lehrer = lehrer;
    }

    public int getMaximaleAnzahlStudenten() {
        return maximaleAnzahlStudenten;
    }

    public void setMaximaleAnzahlStudenten(int maximaleAnzahlStudenten) {
        this.maximaleAnzahlStudenten = maximaleAnzahlStudenten;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }


    @Override
    public String toString() {
        return "Kurs{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", lehrer=" + lehrer +
                ", maximaleAnzahlStudenten=" + maximaleAnzahlStudenten +
                ", ects=" + ects +
                '}';
    }

}
