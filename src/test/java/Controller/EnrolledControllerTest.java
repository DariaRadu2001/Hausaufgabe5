package Controller;
import Exception.ListIsEmptyException;
import Exception.DasElementExistiertException;
import Modele.Enrolled;
import Modele.Kurs;
import Modele.Student;
import Repository.EnrolledRepository;
import Repository.KursRepository;
import Repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnrolledControllerTest {

    KursRepository kursRepository = Mockito.mock(KursRepository.class);
    EnrolledRepository enrolledRepository = Mockito.mock(EnrolledRepository.class);
    StudentRepository studentRepository = Mockito.mock(StudentRepository.class);
    EnrolledController enrolledController = null;

    @BeforeEach
    void setUp() throws SQLException, IOException, ListIsEmptyException {

        Enrolled enrolled1 = new Enrolled(1,1);
        Enrolled enrolled2 = new Enrolled(1,2);
        Enrolled enrolled3 = new Enrolled(2,1);
        Enrolled enrolled4 = new Enrolled(3,1);
        Enrolled enrolled5 = new Enrolled(4,2);

        List<Enrolled> enrolledListe = Arrays.asList(enrolled1,enrolled2,enrolled3,enrolled4);

        Mockito.when(enrolledRepository.getAll()).thenReturn(enrolledListe);
        Mockito.when(enrolledRepository.findOne(1,1)).thenReturn(true);
        Mockito.when(enrolledRepository.findOne(10,1)).thenReturn(false);
        Mockito.when(enrolledRepository.findOne(11,11)).thenReturn(false);
        Mockito.when(enrolledRepository.findOne(1,11)).thenReturn(false);
        Mockito.when(enrolledRepository.create(enrolled5)).thenReturn(new Enrolled(4,2));
        Mockito.when(enrolledRepository.create(new Enrolled(1,1))).thenReturn(null);

        enrolledController = new EnrolledController(kursRepository, studentRepository, enrolledRepository);
    }

    @Test
    void createExisting() throws ListIsEmptyException, SQLException, IOException, DasElementExistiertException {
        assertNull(enrolledController.create(new Enrolled(1,1)));
    }

    @Test
    void getAll() throws ListIsEmptyException, SQLException, IOException {
        assertEquals(4,enrolledController.getAll().size());
    }

    @Test
    void findOne() throws SQLException, IOException {
        assertTrue(enrolledController.findOne(1,1));
    }

    @Test
    void findOneNot() throws SQLException, IOException {
        assertFalse(enrolledController.findOne(11,11));
    }

    @Test
    void findOneKursNotExisting() throws SQLException, IOException {
        assertFalse(enrolledController.findOne(10,1));
    }

    @Test
    void findOneStudentNotExisting() throws SQLException, IOException {
        assertFalse(enrolledController.findOne(1,11));
    }

    @Test
    void create() throws ListIsEmptyException, SQLException, IOException, DasElementExistiertException {
        Enrolled enrolled5 = new Enrolled(4,2);
        Mockito.when(enrolledRepository.create(enrolled5)).thenReturn(enrolled5);
        Mockito.when(enrolledRepository.findOne(4,2)).thenReturn(false);
        Mockito.when(studentRepository.findOne(4)).thenReturn(true);
        Mockito.when(kursRepository.findOne(2)).thenReturn(true);
        Mockito.when(kursRepository.getKursNachId(2)).thenReturn(new Kurs(2,"",1,100,1));
        Mockito.when(kursRepository.getAnzahlStudenten(2)).thenReturn(10);
        Mockito.when(studentRepository.getNotwendigeKredits(4)).thenReturn(30);
        Mockito.when(studentRepository.getStudentNachId(4)).thenReturn(new Student("Mara","Luca",4,0));
        Enrolled enrolled1 = enrolledController.create(enrolled5);
        assertNotNull(enrolled1);
    }
}