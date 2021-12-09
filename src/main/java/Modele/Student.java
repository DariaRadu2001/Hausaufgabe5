package Modele;

public class Student extends Person implements Comparable<Student>{

    private long studentID;
    private int totalKredits;

    public Student(String vorname, String nachname, long studentID, int totalKredits) {
        super(vorname, nachname);
        this.studentID = studentID;
        this.totalKredits = totalKredits;
    }

    public Student(long id, String vorname, String nachname) {
        super(vorname, nachname);
        this.studentID = id;
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

    @Override
    public int compareTo(Student student2) {
        return Integer.compare(this.getTotalKredits(), student2.getTotalKredits());

    }
}
