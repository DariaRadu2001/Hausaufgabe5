package Controller;

import Modele.Lehrer;
import Repository.LehrerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import Exception.DasElementExistiertException;

class LehrerControllerTest {

    LehrerRepository lehrerRepo = Mockito.mock(LehrerRepository.class);
    LehrerController lehrerController = null;

    @BeforeEach
    void setUp() throws SQLException, IOException, DasElementExistiertException {

        Lehrer lehrer1 = new Lehrer ("Martin","Sommer",1);
        Lehrer lehrer2 = new Lehrer ("Luther","King",2);
        Lehrer lehrer3 = new Lehrer ("Inge","Bottesch",3);
        Lehrer lehrer4 = new Lehrer ("Mirela","Lazar",4);
        Lehrer lehrer5 = new Lehrer("Magda","Pop",4);
        Lehrer lehrer6 = new Lehrer("Daria","Miklos",5);
        List<Lehrer> lehrerListe = Arrays.asList(lehrer1,lehrer2,lehrer3,lehrer4);


        Mockito.when(lehrerRepo.getAll()).thenReturn(lehrerListe);
        Mockito.when(lehrerRepo.findOne(1)).thenReturn(true);
        Mockito.when(lehrerRepo.findOne(10)).thenReturn(false);
        Mockito.when(lehrerRepo.create(lehrer6)).thenReturn(lehrer6);
        Mockito.when(lehrerRepo.create(new Lehrer("","",1))).thenReturn(null);
        Mockito.when(lehrerRepo.delete(5L)).thenReturn(true);
        Mockito.when(lehrerRepo.delete(10L)).thenReturn(false);
        Mockito.when(lehrerRepo.update(new Lehrer("Magda","Pop",4))).thenReturn(lehrer5);
        Mockito.when(lehrerRepo.update(new Lehrer("","",10))).thenReturn(null);
        lehrerController = new LehrerController(lehrerRepo);
        Mockito.when(lehrerController.create(lehrer6)).thenReturn(lehrer6);
    }

    @Test
    void createExistingLehrer() throws SQLException, IOException, DasElementExistiertException {
        assertNull(lehrerController.create(new Lehrer("","",1)));
    }

    @Test
    void getAll() throws SQLException, IOException {
        assertEquals(4,lehrerController.getAll().size());
    }

    @Test
    void updateNotExisting() throws SQLException, IOException {
       assertNull(lehrerController.update(new Lehrer("","",10)));
    }

    @Test
    void delete() throws SQLException, IOException {
        assertTrue(lehrerController.delete(5L));
    }

    @Test
    void deleteNotExisting() throws SQLException, IOException {
        assertFalse(lehrerController.delete(10L));
    }

    @Test
    void findOne() throws SQLException, IOException {
        assertTrue(lehrerController.findOne(1L));
    }

    @Test
    void findOneNot() throws SQLException, IOException {
        assertFalse(lehrerController.findOne(10L));
    }

    @Test
    void create() throws SQLException, IOException, DasElementExistiertException {
        Lehrer lehrer6 =  new Lehrer("Daria","Miklos",5);
        Mockito.when(lehrerRepo.create(lehrer6)).thenReturn(lehrer6);
        Lehrer lehrer = lehrerController.create(lehrer6);
        assertNotNull(lehrer);
    }

    @Test
    void update() throws SQLException, IOException {
        Lehrer lehrer5 = new Lehrer("Magda","Pop",4);
        Mockito.when(lehrerRepo.update(lehrer5)).thenReturn(lehrer5);
        Lehrer lehrer = lehrerController.update(lehrer5);
        assertNotNull(lehrer);
    }
}