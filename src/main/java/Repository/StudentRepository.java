package Repository;

import Exception.DasElementExistiertException;
import Exception.ListIsEmptyException;
import Modele.Student;

import java.io.IOException;
import java.util.List;

public class StudentRepository extends JDBCRepository implements ICrudRepository<Student>{

    @Override
    public Student create(Student obj) throws IOException, DasElementExistiertException {
        return null;
    }

    @Override
    public List<Student> getAll() {
        return null;
    }

    @Override
    public Student update(Student obj) throws IOException, ListIsEmptyException {
        return null;
    }

    @Override
    public boolean delete(Long objID) throws IllegalAccessException, IOException {
        return false;
    }
}
