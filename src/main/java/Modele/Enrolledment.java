package Modele;

public class Enrolledment {

    public static long id = 0;
    private long idStudent;
    private long idKurs;

    public Enrolledment(long idStudent, long idKurs) {
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
}
