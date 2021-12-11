package Controller;

import Exception.DasElementExistiertException;
import Modele.Student;
import Repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

class StudentControllerTest {

    StudentRepository studentRepository = Mockito.mock(StudentRepository.class);
    StudentController studentController = null;


    @BeforeEach
    void setUp() throws SQLException, IOException, DasElementExistiertException, IllegalAccessException {

        Student student1 = new Student (1,"Martin","Sommer"); //Kredits 30
        Student student2 = new Student (2,"Luther","King"); //Kredits 28
        Student student3 = new Student (3,"Inge","Bottesch"); //Kredits 14
        Student student4 = new Student (4,"Mirela","Lazar"); //Kredits 30
        Student student5 = new Student(4,"Magda","Pop");
        Student student6 = new Student(5,"Daria","Miklos");
        List<Student> studentenListe = Arrays.asList(student1,student2,student3,student4);
        List<Long> listeAngemeldet = Arrays.asList(1L,4L);
        List<Student> sortList = Arrays.asList(student3,student2,student1,student4);
        List<Student> filterList = Arrays.asList(student1,student4);
        Mockito.when(studentRepository.filterList()).thenReturn(filterList);
        Mockito.when(studentRepository.sortList()).thenReturn(sortList);
        Mockito.when(studentRepository.getStudentenAngemeldetBeiEineKurs(1L)).thenReturn(listeAngemeldet);
        Mockito.when(studentRepository.getAll()).thenReturn(studentenListe);
        Mockito.when(studentRepository.findOne(1)).thenReturn(true);
        Mockito.when(studentRepository.findOne(10)).thenReturn(false);
        Mockito.when(studentRepository.create(student6)).thenReturn(student6);
        Mockito.when(studentRepository.create(new Student(1,"",""))).thenReturn(null);
        Mockito.when(studentRepository.delete(5L)).thenReturn(true);
        Mockito.when(studentRepository.delete(10L)).thenReturn(false);
        Mockito.when(studentRepository.update(student5)).thenReturn(student5);
        Mockito.when(studentRepository.update(new Student(20,"",""))).thenReturn(null);
        studentController = new StudentController(studentRepository);
    }

    @Test
    void createExisting() throws SQLException, IOException, DasElementExistiertException {
        Assertions.assertNull(studentController.create(new Student(1,"","")));
    }

    @Test
    void getAll() throws SQLException, IOException {
        Assertions.assertEquals(4, studentController.getAll().size());
    }

    @Test
    void updateNotExisting() throws SQLException, IOException {
        Assertions.assertNull(studentController.update(new Student(20,"","")));
    }

    @Test
    void delete() throws SQLException, IOException, IllegalAccessException {
        Assertions.assertTrue(studentController.delete(5L));
    }

    @Test
    void deleteNotExisting() throws SQLException, IOException, IllegalAccessException {
        Assertions.assertFalse(studentController.delete(10L));
    }

    @Test
    void findOne() throws SQLException, IOException {
        Assertions.assertTrue(studentController.findOne(1L));
    }

    @Test
    void findOneNot() throws SQLException, IOException {
        Assertions.assertFalse(studentController.findOne(10L));
    }

    @Test
    void filter() throws SQLException, IOException {
        Student student1 = new Student (1,"Martin","Sommer"); //Kredits 30
        Student student4 = new Student (4,"Mirela","Lazar"); //Kredits 30
        List<Student> filterList2 = studentController.filter();
        List<Student> filterList = Arrays.asList(student1,student4);
        int ct = 0;
        for(Student student : filterList)
        {
            if(student.getStudentID() == filterList2.get(ct).getStudentID())
                ct++;
        }
        Assertions.assertEquals(2,ct);
    }

    @Test
    void sort() throws SQLException, IOException {
        Student student1 = new Student (1,"Martin","Sommer"); //Kredits 30
        Student student2 = new Student (2,"Luther","King"); //Kredits 28
        Student student3 = new Student (3,"Inge","Bottesch"); //Kredits 14
        Student student4 = new Student (4,"Mirela","Lazar"); //Kredits 30
        List<Student> sortList = studentController.sort();
        List<Student> sortList2 = Arrays.asList(student3,student2,student1,student4);
        int ct = 0;
        for(Student student : sortList)
        {
            if(student.getStudentID() == sortList2.get(ct).getStudentID())
                ct++;
        }
        Assertions.assertEquals(4,ct);
    }

    @Test
    void getListeAngemeldeteStudenten() throws SQLException {
        Assertions.assertEquals(2,studentController.getListeAngemeldeteStudenten(1L).size());
    }

    @Test
    void update() throws SQLException, IOException {
        Assertions.assertEquals(new Student(4,"Magda","Pop"), studentController.update(new Student(4,"Magda","Pop")));
    }

    @Test
    void create() throws SQLException, IOException, DasElementExistiertException {
        Student student = new Student(5,"Daria","Miklos");
        System.out.println(student);
        Assertions.assertEquals(new Student(5,"Daria","Miklos"), studentController.create(new Student(5,"Daria","Miklos")));
    }
}