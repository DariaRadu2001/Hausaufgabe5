package Controller;

import Modele.Lehrer;
import Repository.LehrerRepository;

import Exception.DasElementExistiertException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LehrerController implements Controller<Lehrer>{

    private LehrerRepository lehrerRepository;

    public LehrerController(LehrerRepository lehrerRepository) {
        this.lehrerRepository = lehrerRepository;
    }

    public LehrerRepository getLehrerRepository() {
        return lehrerRepository;
    }

    public void setLehrerRepository(LehrerRepository lehrerRepository) {
        this.lehrerRepository = lehrerRepository;
    }

    /**
     * legt in DB ein neuer Lehrer
     * @param obj, das Objekt, das man hinlegt
     * @return der Lehrer, wenn man ihn hinlegt
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws DasElementExistiertException, der Lehrer existiert im DB
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public Lehrer create(Lehrer obj) throws IOException, DasElementExistiertException, SQLException {
        return this.lehrerRepository.create(obj);
    }

    /**
     * gibt alle Tupels aus der Tabelle lehrer
     * @return die Liste von Professoren
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public List<Lehrer> getAll() throws SQLException, IOException {
        return this.lehrerRepository.getAll();
    }

    /**
     * ändert die Attribute eines Tupels aus der Tabelle lehrer
     * @param obj, das Objekt mit dem switch erledigt
     * @return der Lehrer
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public Lehrer update(Lehrer obj) throws IOException, SQLException {
        return this.lehrerRepository.update(obj);
    }

    /**
     * löscht ein Tupel aus der Tabelle lehrer
     * @param objID, das Element das man löschen will
     * @return true, wenn man die Aktion erledigen kann, anderen falls false
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public boolean delete(Long objID) throws IOException, SQLException {
        return this.lehrerRepository.delete(objID);
    }

    /**
     * sucht einen Lehrer nach seinem Id
     * @param id des Objektes
     * @return true, wenn man die Aktion erledigen kann, anderen falls false
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public boolean findOne(Long id) throws IOException, SQLException {
        return this.lehrerRepository.findOne(id);
    }
}
