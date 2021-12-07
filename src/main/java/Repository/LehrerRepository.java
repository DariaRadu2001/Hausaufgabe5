package Repository;
import Exception.DasElementExistiertException;
import Exception.ListIsEmptyException;
import Modele.Lehrer;

import Exception.DasElementExistiertException;
import Exception.ListIsEmptyException;
import java.io.IOException;
import java.util.List;


public class LehrerRepository extends JDBCRepository implements ICrudRepository<Lehrer>{

    @Override
    public Lehrer create(Lehrer obj) throws IOException, DasElementExistiertException {
        return null;
    }

    @Override
    public List<Lehrer> getAll() {
        return null;
    }

    @Override
    public Lehrer update(Lehrer obj) throws IOException, ListIsEmptyException {
        return null;
    }

    @Override
    public boolean delete(Long objID) throws IllegalAccessException, IOException {
        return false;
    }
}
