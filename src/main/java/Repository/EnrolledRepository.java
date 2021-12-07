package Repository;

import Modele.Enrolledment;
import Exception.DasElementExistiertException;
import Exception.ListIsEmptyException;
import java.io.IOException;
import java.util.List;


public class EnrolledRepository extends JDBCRepository implements ICrudRepository<Enrolledment>{

    @Override
    public Enrolledment create(Enrolledment obj) throws IOException, DasElementExistiertException {
        return null;
    }

    @Override
    public List<Enrolledment> getAll() {
        return null;
    }

    @Override
    public Enrolledment update(Enrolledment obj) throws IOException, ListIsEmptyException {
        return null;
    }

    @Override
    public boolean delete(Long objID) throws IllegalAccessException, IOException {
        return false;
    }
}
