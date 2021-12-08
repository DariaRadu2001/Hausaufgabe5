package Repository;

import Modele.Enrolledment;
import Exception.DasElementExistiertException;
import Exception.ListIsEmptyException;
import java.io.IOException;
import java.util.List;


public class EnrolledRepository extends JDBCRepository implements ICrudRepository<Enrolledment>{

    @Override
    public Enrolledment create(Enrolledment obj) throws IOException, DasElementExistiertException {
        this.startConnection();



        this.startConnection();
        return obj;
    }

    @Override
    public List<Enrolledment> getAll() throws IOException {
        this.startConnection();



        this.startConnection();


        return null;
    }

    @Override
    public Enrolledment update(Enrolledment obj) throws IOException, ListIsEmptyException {

        this.startConnection();



        this.startConnection();

        return null;
    }

    @Override
    public boolean delete(Long objID) throws IllegalAccessException, IOException {

        this.startConnection();



        this.startConnection();

        return false;
    }
}
