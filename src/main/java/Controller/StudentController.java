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

    @Override
    public Student create(Student obj) throws IOException,DasElementExistiertException, SQLException {
        return this.studentenRepo.create(obj);
    }

    @Override
    public List<Student> getAll() throws SQLException, IOException {
        return this.studentenRepo.getAll();
    }

    @Override
    public Student update(Student obj) throws IOException, SQLException {
        return this.studentenRepo.update(obj);
    }

    @Override
    public boolean delete(Long objID) throws IllegalAccessException, IOException, SQLException {
        return this.studentenRepo.delete(objID);
    }

    @Override
    public boolean findOne(Long id) throws IOException, SQLException {
        return this.studentenRepo.findOne(id);
    }

    public List<Student> filter() throws SQLException, IOException {
        return this.studentenRepo.filterList();
    }

    public List<Student> sort() throws SQLException, IOException {
        return this.studentenRepo.sortList();
    }

    public List<Long> getListeAngemeldeteStudenten(long idKurs) throws SQLException{
        return this.studentenRepo.getStudentenAngemeldetBeiEineKurs(idKurs);
    }
}
