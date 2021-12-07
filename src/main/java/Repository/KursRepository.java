package Repository;
import Exception.DasElementExistiertException;
import Exception.ListIsEmptyException;
import Modele.Kurs;

import java.io.IOException;
import java.util.List;

public class KursRepository extends JDBCRepository implements ICrudRepository<Kurs>{

    @Override
    public Kurs create(Kurs obj) throws IOException, DasElementExistiertException {
        return null;
    }

    @Override
    public List<Kurs> getAll() {
        return null;
    }

    @Override
    public Kurs update(Kurs obj) throws IOException, ListIsEmptyException {
        return null;
    }

    @Override
    public boolean delete(Long objID) throws IllegalAccessException, IOException {
        return false;
    }
}
