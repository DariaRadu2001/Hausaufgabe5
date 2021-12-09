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

    /**
     * legt in DB ein neuer Kurs
     * @param obj, das Objekt, das man hinlegt
     * @return der Kurs, wenn man ihn hinlegt
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws DasElementExistiertException, der Kurs ist in DB
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public Kurs create(Kurs obj) throws IOException, DasElementExistiertException, SQLException {
       return kursRepo.create(obj);
    }

    /**
     * gibt alle Tupels aus der Tabelle kurs
     * @return die Liste von Kurse
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public List<Kurs> getAll() throws SQLException, IOException {
        return kursRepo.getAll();
    }

    /**
     * ändert die Attribute eines Tupels aus der Tabelle kurs
     * @param obj, das Objekt mit dem switch erledigt
     * @return der Kurs
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public Kurs update(Kurs obj) throws IOException, SQLException {
        return kursRepo.update(obj);
    }

    /**
     * löscht ein Tupel aus der Tabelle kurs
     * @param objID, das Element das man löschen will
     * @return true, wenn man die Aktion erledigen kann, anderen falls false
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public boolean delete(Long objID) throws IOException, SQLException {
        return kursRepo.delete(objID);
    }

    /**
     * sucht einen Kurs nach seinem Id
     * @param id des Objektes
     * @return true, wenn man die Aktion erledigen kann, anderen falls false
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
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

    /**
     * löscht einen Kurs, der ein Professor unterrichtet
     * @param idKurs des Kurses
     * @param idLehrer des Professors
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     */
    public void entfernenKurs(long idKurs, long idLehrer) throws SQLException, IOException {
        if(this.kursRepo.findOne(idKurs) && this.lehrerRepo.findOne(idLehrer))
        {
            Kurs kurs = this.kursRepo.getKursNachId(idKurs);
            if(kurs.getLehrer() == idLehrer)
            {
                this.studentenRepo.andernKredits(idKurs,0,kurs.getEcts());
                this.enrolledRepo.deleteEnrolledNachKurs(idKurs);
                this.kursRepo.delete(idKurs);
            }
        }
    }

    /**
     * schaut welche Kurse noch freie Platzen haben
     * @return die Liste von Kurse
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     */
    public List<Kurs> getKurseFreiePlatze() throws SQLException, IOException {
        return this.kursRepo.kurseFreiePlatze();
    }

    /**
     * ändert die Anzahl der ECTS eines Kurses
     * @param idKurs, des Kurses
     * @param ECTS, die neue ECTS
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     */
    public void andernECTS(long idKurs, int ECTS) throws SQLException, IOException {
        if(this.kursRepo.findOne(idKurs))
        {
            int alteECTS = this.kursRepo.andernECTS(idKurs,ECTS);
            this.studentenRepo.andernKredits(idKurs, ECTS, alteECTS);
        }
    }
}
