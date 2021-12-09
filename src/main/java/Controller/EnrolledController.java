package Controller;
import Exception.DasElementExistiertException;
import Modele.Enrolled;
import Modele.Kurs;
import Modele.Student;
import Repository.EnrolledRepository;
import Repository.KursRepository;
import Exception.ListIsEmptyException;
import Repository.StudentRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public  class EnrolledController {

    private KursRepository kursRepo;
    private StudentRepository studentenRepo;
    private EnrolledRepository enrolledRepo;

    public EnrolledController(KursRepository kursRepo, StudentRepository studentenRepo, EnrolledRepository enrolledRepo) {
        this.kursRepo = kursRepo;
        this.studentenRepo = studentenRepo;
        this.enrolledRepo = enrolledRepo;
    }


    public Enrolled create(Enrolled obj) throws IOException, DasElementExistiertException, SQLException, ListIsEmptyException {

        long idKurs = obj.getIdKurs();
        long idStudent = obj.getIdStudent();

        if(this.kursRepo.findOne(idKurs) && this.studentenRepo.findOne(idStudent))
        {
            if(!this.enrolledRepo.findOne(idStudent, idKurs))
            {
                Kurs kurs = this.kursRepo.getKursNachId(idKurs);
                if((kurs.getMaximaleAnzahlStudenten() - this.kursRepo.getAnzahlStudenten(idKurs) > 0) && (this.studentenRepo.getNotwendigeKredits(idStudent) >= kurs.getEcts()))
                {
                    this.enrolledRepo.create(obj);
                    Student student = this.studentenRepo.getStudentNachId(idStudent);
                    Student studentNeu = new Student(student.getVorname(),student.getNachname(),idStudent,(student.getTotalKredits()+kurs.getEcts()));
                    this.studentenRepo.update(studentNeu);
                    return obj;
                }
            }

        }
        return null;

    }


    public List<Enrolled> getAll() throws SQLException, IOException, ListIsEmptyException {
        return this.enrolledRepo.getAll();
    }


    public boolean findOne(long idKurs, long idStudent) throws IOException, SQLException {
        return this.enrolledRepo.findOne(idStudent, idKurs);
    }
}
