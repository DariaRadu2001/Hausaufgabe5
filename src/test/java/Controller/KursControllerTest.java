package Controller;

import Exception.DasElementExistiertException;
import Modele.Kurs;
import Repository.EnrolledRepository;
import Repository.KursRepository;
import Repository.LehrerRepository;
import Repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KursControllerTest {

    KursRepository kursRepository = Mockito.mock(KursRepository.class);
    EnrolledRepository enrolledRepository = Mockito.mock(EnrolledRepository.class);
    LehrerRepository lehrerRepository = Mockito.mock(LehrerRepository.class);
    StudentRepository studentRepository = Mockito.mock(StudentRepository.class);
    KursController kursController = null;

    @BeforeEach
    void setUp() throws SQLException, IOException {

        Kurs kurs1 = new Kurs(1,"DB",1,10,6);
        Kurs kurs2 = new Kurs(2,"MAP",2,10,6);
        Kurs kurs3 = new Kurs(3,"Logik",3,0,4);
        Kurs kurs4 = new Kurs(4,"SO",3,10,7);
        Kurs kurs5 = new Kurs(1,"Baze de date",1,100,100);
        Kurs kurs6 = new Kurs(5,"Germana",2,19,3);
        List<Kurs> listeKurse = Arrays.asList(kurs1,kurs2,kurs3,kurs4);
        List<Kurs> sortList = Arrays.asList(kurs3,kurs1,kurs2,kurs4);
        List<Kurs> filterList = Arrays.asList(kurs1,kurs2,kurs4);
        List<Kurs> freieKurse = Arrays.asList(kurs1,kurs2,kurs4);

        Mockito.when(kursRepository.kurseFreiePlatze()).thenReturn(freieKurse);
        Mockito.when(kursRepository.filter()).thenReturn(filterList);
        Mockito.when(kursRepository.sort()).thenReturn(sortList);
        Mockito.when(kursRepository.getAll()).thenReturn(listeKurse);
        Mockito.when(kursRepository.findOne(1)).thenReturn(true);
        Mockito.when(kursRepository.findOne(10)).thenReturn(false);
        Mockito.when(kursRepository.create(kurs6)).thenReturn(kurs6);
        Mockito.when(kursRepository.create(new Kurs(1,6))).thenReturn(null);
        Mockito.when(kursRepository.delete(5L)).thenReturn(true);
        Mockito.when(kursRepository.delete(10L)).thenReturn(false);
        Mockito.when(kursRepository.update(kurs5)).thenReturn(kurs5);
        Mockito.when(kursRepository.update(new Kurs(20,1000))).thenReturn(null);
        kursController = new KursController(kursRepository, studentRepository, lehrerRepository, enrolledRepository);
    }

    @Test
    void createExisting() throws SQLException, IOException, DasElementExistiertException {
        assertNull(kursController.create(new Kurs(1,6)));
    }

    @Test
    void getAll() throws SQLException, IOException {
        assertEquals(4, kursController.getAll().size());
    }

    @Test
    void updateNotExisting() throws SQLException, IOException {
        assertNull(kursController.update(new Kurs(20,1000)));
    }

    @Test
    void deleteNotExisting() throws SQLException, IOException {
        assertFalse(kursController.delete(10L));
    }

    @Test
    void delete() throws SQLException, IOException {
        assertTrue(kursController.delete(5L));
    }

    @Test
    void findOne() throws SQLException, IOException {
        assertTrue(kursController.findOne(1L));
    }

    @Test
    void findOneNot() throws SQLException, IOException {
        assertFalse(kursController.findOne(10L));
    }

    @Test
    void filter() throws SQLException, IOException {
        Kurs kurs1 = new Kurs(1,"DB",1,10,6);
        Kurs kurs2 = new Kurs(2,"MAP",2,10,6);
        Kurs kurs4 = new Kurs(4,"SO",3,10,7);
        List<Kurs> filterList = Arrays.asList(kurs1,kurs2,kurs4);
        List<Kurs> filterList2 = kursController.filter();
        int ct = 0;
        for(Kurs kurs: filterList)
        {
            if(kurs.getID() == filterList2.get(ct).getID())
                ct++;
        }
        assertEquals(3, ct);
    }

    @Test
    void sort() throws SQLException, IOException {
        Kurs kurs1 = new Kurs(1,"DB",1,10,6);
        Kurs kurs2 = new Kurs(2,"MAP",2,10,6);
        Kurs kurs3 = new Kurs(3,"Logik",3,0,4);
        Kurs kurs4 = new Kurs(4,"SO",3,10,7);
        List<Kurs> sortList = Arrays.asList(kurs3,kurs1,kurs2,kurs4);
        List<Kurs> sortList2 = kursController.sort();
        int ct = 0;
        for(Kurs kurs: sortList)
        {
            if(kurs.getID() == sortList2.get(ct).getID())
                ct++;
        }
        assertEquals(4, ct);
    }

    @Test
    void getKurseFreiePlatze() throws SQLException, IOException {
        Kurs kurs1 = new Kurs(1,"DB",1,10,6);
        Kurs kurs2 = new Kurs(2,"MAP",2,10,6);
        Kurs kurs4 = new Kurs(4,"SO",3,10,7);
        List<Kurs> freieKurse = Arrays.asList(kurs1,kurs2,kurs4);
        List<Kurs> freieKurse2 = kursController.getKurseFreiePlatze();
        int ct = 0;
        for(Kurs kurs: freieKurse)
        {
            if(kurs.getID() == freieKurse2.get(ct).getID())
                ct++;
        }
        assertEquals(3, ct);

    }

    @Test
    void update() {
    }

    @Test
    void create() {
    }

}