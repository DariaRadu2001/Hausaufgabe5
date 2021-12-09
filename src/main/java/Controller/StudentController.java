package Controller;
import Exception.DasElementExistiertException;
import Modele.Student;
import Repository.StudentRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StudentController implements Controller<Student> {

    private StudentRepository studentenRepo;


    public StudentController(StudentRepository studentenRepo) {

        this.studentenRepo = studentenRepo;

    }

    /**
     * legt in DB ein neuer Student
     * @param obj, das Objekt, das man hinlegt
     * @return der Student, wenn man ihn hinlegt
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws DasElementExistiertException, der Student existiert im DB
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public Student create(Student obj) throws IOException,DasElementExistiertException, SQLException {
        return this.studentenRepo.create(obj);
    }

    /**
     * gibt alle Tupels aus der Tabelle studenten
     * @return die Liste von Studenten
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public List<Student> getAll() throws SQLException, IOException {
        return this.studentenRepo.getAll();
    }

    /**
     * ändert die Attribute eines Tupels aus der Tabelle studenten
     * @param obj, das Objekt mit dem switch erledigt
     * @return der Student
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public Student update(Student obj) throws IOException, SQLException {
        return this.studentenRepo.update(obj);
    }

    /**
     * löscht ein Tupel aus der Tabelle student
     * @param objID, das Element das man löschen will
     * @return true, wenn man die Aktion erledigen kann, anderen falls false
     * @throws IllegalAccessException, das Tupel existiert nicht
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public boolean delete(Long objID) throws IllegalAccessException, IOException, SQLException {
        return this.studentenRepo.delete(objID);
    }

    /**
     * sucht einen Student nach seinem Id
     * @param id des Objektes
     * @return true, wenn man die Aktion erledigen kann, anderen falls false
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public boolean findOne(Long id) throws IOException, SQLException {
        return this.studentenRepo.findOne(id);
    }

    /**
     * filtert die Studenten
     * @return gefilterte Liste von Studenten
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     */
    public List<Student> filter() throws SQLException, IOException {
        return this.studentenRepo.filterList();
    }

    /**
     * sortiert die Studenten
     * @return sortierte Liste von Studenten
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     */
    public List<Student> sort() throws SQLException, IOException {
        return this.studentenRepo.sortList();
    }

    /**
     * gibt eine Liste mit allen Studenten die bei einem Kurs teilnehmen
     * @param idKurs des Kurses
     * @return die Liste von Studenten
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    public List<Long> getListeAngemeldeteStudenten(long idKurs) throws SQLException{
        return this.studentenRepo.getStudentenAngemeldetBeiEineKurs(idKurs);
    }
}
