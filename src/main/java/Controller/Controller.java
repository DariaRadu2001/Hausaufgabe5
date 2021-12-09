package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import Exception.DasElementExistiertException;
import Exception.ListIsEmptyException;
public interface Controller<T> {

    /**
     * legt ein Objekt der Typ T in der RepoListe
     * @param obj, das Objekt, das man hinlegt
     * @return das hingelegte Element
     * @throws IOException, fur Schreiben im File
     * @throws DasElementExistiertException, das Element ist in der Liste
     */
    T create(T obj) throws IOException, DasElementExistiertException, SQLException;

    /**
     * gibt alle Elementen aus der RepoListe
     * @return eine Liste mit Elementen der Typ T
     */
    List<T> getAll() throws SQLException, IOException, ListIsEmptyException;


    /**
     * Verändert einige Attribute eines Objektes
     * @param obj, das Objekt mit dem switch erledigt
     * @return das alte Objet mit den neuen Attributen
     * @throws IOException, fur Schreiben im File
     * @throws ListIsEmptyException, die Liste ist leer
     */
    T update(T obj) throws IOException, ListIsEmptyException, SQLException;

    /**
     * aus der RepoListe ein Objekt löschen
     * @param objID, das Element das man löschen will
     * @return true, wenn man das Element löscht, false wenn man nichts tut
     * @throws IllegalAccessException, die Liste ist leer
     * @throws IOException, fur Schreiben im File
     */
    boolean delete(Long objID) throws IllegalAccessException, IOException, SQLException;

    /**
     * sucht ein Objekt nach seiner ID
     * @param id des Objektes
     * @return true, wenn man das Element findet, false wenn man nicht findet
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    boolean findOne(Long id) throws IOException, SQLException;
}
