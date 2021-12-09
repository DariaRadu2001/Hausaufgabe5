package Controller;
import Exception.DasElementExistiertException;
import Modele.Kurs;
import Repository.EnrolledRepository;
import Repository.KursRepository;
import Repository.LehrerRepository;
import Repository.StudentRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class KursController implements Controller<Kurs> {

    private KursRepository kursRepo;
    private StudentRepository studentenRepo;
    private LehrerRepository lehrerRepo;
    private EnrolledRepository enrolledRepo;

    public KursController(KursRepository kursRepo, StudentRepository studentenRepo, LehrerRepository lehrerRepo, EnrolledRepository enrolledRepo) {
        this.kursRepo = kursRepo;
        this.studentenRepo = studentenRepo;
        this.lehrerRepo = lehrerRepo;
        this.enrolledRepo = enrolledRepo;
    }

    public KursRepository getKursRepo() {
        return kursRepo;
    }

    public void setKursRepo(KursRepository kursRepo) {
        this.kursRepo = kursRepo;
    }

    public StudentRepository getStudentenRepo() {
        return studentenRepo;
    }

    public void setStudentenRepo(StudentRepository studentenRepo) {
        this.studentenRepo = studentenRepo;
    }

    public LehrerRepository getLehrerRepo() {
        return lehrerRepo;
    }

    public void setLehrerRepo(LehrerRepository lehrerRepo) {
        this.lehrerRepo = lehrerRepo;
    }

    public EnrolledRepository getEnrolledRepo() {
        return enrolledRepo;
    }

    public void setEnrolledRepo(EnrolledRepository enrolledRepo) {
        this.enrolledRepo = enrolledRepo;
    }

    @Override
    public Kurs create(Kurs obj) throws IOException, DasElementExistiertException, SQLException {
       return kursRepo.create(obj);
    }

    @Override
    public List<Kurs> getAll() throws SQLException, IOException {
        return kursRepo.getAll();
    }

    @Override
    public Kurs update(Kurs obj) throws IOException, SQLException {
        return kursRepo.update(obj);
    }

    @Override
    public boolean delete(Long objID) throws IOException, SQLException {
        return kursRepo.delete(objID);
    }

    @Override
    public boolean findOne(Long id) throws IOException, SQLException {
        return kursRepo.findOne(id);
    }

    /**
     * Filtert die Liste, indem man die Methode aus dem Repo aufruft
     * @return die gefilterte Liste
     */
    public List<Kurs> filter() throws SQLException, IOException {
        return kursRepo.filter();
    }

    /**
     * sortiert die Liste, indem man die Methode aus dem Repo aufruft
     * @return die sortierte Liste
     */
    public List<Kurs> sort() throws SQLException, IOException {
        return kursRepo.sort();
    }

    public void entfernenKurs(long idKurs, long idLehrer) throws SQLException, IOException {
        if(this.kursRepo.findOne(idKurs) && this.lehrerRepo.findOne(idLehrer))
        {
            Kurs kurs = this.kursRepo.getKursNachId(idKurs);
            if(kurs.getLehrer() == idLehrer)
            {
                this.studentenRepo.andernKredits(idKurs,-1*kurs.getEcts(),kurs.getEcts());
                this.enrolledRepo.deleteEnrolledNachKurs(idKurs);
            }
        }
    }

    public List<Kurs> getKurseFreiePlatze() throws SQLException, IOException {
        return this.kursRepo.kurseFreiePlatze();
    }

    public void andernECTS(long idKurs, int ECTS) throws SQLException, IOException {
        if(this.kursRepo.findOne(idKurs))
        {
            int alteECTS = this.kursRepo.andernECTS(idKurs,ECTS);
            this.studentenRepo.andernKredits(idKurs, ECTS, alteECTS);
        }
    }
}
