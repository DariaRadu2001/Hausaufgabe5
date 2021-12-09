package Controller;

import Modele.Lehrer;
import Repository.KursRepository;
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

    @Override
    public Lehrer create(Lehrer obj) throws IOException, DasElementExistiertException, SQLException {
        return this.lehrerRepository.create(obj);
    }

    @Override
    public List<Lehrer> getAll() throws SQLException, IOException {
        return this.lehrerRepository.getAll();
    }

    @Override
    public Lehrer update(Lehrer obj) throws IOException, SQLException {
        return this.lehrerRepository.update(obj);
    }

    @Override
    public boolean delete(Long objID) throws IllegalAccessException, IOException, SQLException {
        return this.lehrerRepository.delete(objID);
    }

    @Override
    public boolean findOne(Long id) throws IOException, SQLException {
        return this.lehrerRepository.findOne(id);
    }
}
