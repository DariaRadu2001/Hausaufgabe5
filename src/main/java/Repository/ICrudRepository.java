package Repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import Exception.DasElementExistiertException;
import Exception.ListIsEmptyException;

public interface ICrudRepository<T> {

    /**
     * legt ein Objekt der Typ T in der RepoListe
     * @param obj, das Objekt die ich hinlege
     * @return das Objekt
     */
    T create(T obj) throws IOException, DasElementExistiertException, SQLException;

    /**
     * gibt alle Elementen aus der RepoListe
     * @return eine Liste mit Elementen der Typ T
     */
    List<T> getAll() throws IOException, SQLException, ListIsEmptyException;

    /**
     * Verändert einige Attribute eines Objektes
     * @param obj, das Objekt mit dem switch erledigt
     * @return das alte Objet mit den neuen Attributen
     */
    T update(T obj) throws IOException, ListIsEmptyException, SQLException;

    /**
     * aus der RepoListe ein Objekt löschen
     * @param objID, das Objekt, das ich löschen will
     */
    boolean delete(Long objID) throws IllegalAccessException, IOException, SQLException;

}
