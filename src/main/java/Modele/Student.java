package Modele;

public class Student extends Person{

    private long studentID;
    private int totalKredits;

    public Student(String vorname, String nachname, long studentID, int totalKredits) {
        super(vorname, nachname);
        this.studentID = studentID;
        this.totalKredits = totalKredits;
    }

    public Student(String vorname, String nachname, long studentID) {
        super(vorname, nachname);
        this.studentID = studentID;
        this.totalKredits = 0;
    }

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public int getTotalKredits() {
        return totalKredits;
    }

    public void setTotalKredits(int totalKredits) {
        this.totalKredits = totalKredits;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentID=" + studentID +
                ", totalKredits=" + totalKredits +
                '}';
    }

    /**
     * berechnet wv. Kredite ein Student noch braucht bis er insgesamt 30 hat
     * @return Anzahl notwendigen Krediten
     */
    public int getNotwendigeKredits()
    {
        return (30 - this.getTotalKredits());
    }

}