package Modele;

public class Enrolled {

    public static long id = 0;
    private long idStudent;
    private long idKurs;

    public Enrolled(long idStudent, long idKurs) {
        this.idStudent = idStudent;
        this.idKurs = idKurs;
        id++;
    }

    public long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(long idStudent) {
        this.idStudent = idStudent;
    }

    public long getIdKurs() {
        return idKurs;
    }

    public void setIdKurs(long idKurs) {
        this.idKurs = idKurs;
    }

    @Override
    public String toString() {
        return "Enrolled{" +
                "idStudent=" + idStudent +
                ", idKurs=" + idKurs +
                '}';
    }
}
