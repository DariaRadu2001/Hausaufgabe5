package KonsoleView;

import Controller.EnrolledController;
import Controller.KursController;
import Controller.LehrerController;
import Controller.StudentController;
import Modele.Enrolledment;
import Modele.Kurs;
import Modele.Lehrer;
import Modele.Student;
import Exception.DasElementExistiertException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import Exception.ListIsEmptyException;

public class KonsoleView {

    private KursController kursController;
    private LehrerController lehrerController;
    private StudentController studentController;
    private EnrolledController enrolledController;

    public KonsoleView(KursController kursController, LehrerController lehrerController, StudentController studentController, EnrolledController enrolledController) {
        this.kursController = kursController;
        this.lehrerController = lehrerController;
        this.studentController = studentController;
        this.enrolledController = enrolledController;
    }

    public EnrolledController getEnrolledController() {
        return enrolledController;
    }

    public void setEnrolledController(EnrolledController enrolledController) {
        this.enrolledController = enrolledController;
    }

    public KursController getKursController() {
        return kursController;
    }

    public void setKursController(KursController kursController) {
        this.kursController = kursController;
    }

    public LehrerController getLehrerController() {
        return lehrerController;
    }

    public void setLehrerController(LehrerController lehrerController) {
        this.lehrerController = lehrerController;
    }

    public StudentController getStudentController() {
        return studentController;
    }

    public void setStudentController(StudentController studentController) {
        this.studentController = studentController;
    }

    /**
     * Menu fur UI
     */
    public void getMenu()
    {
        System.out.println("""
                1.Filter/Sort\s
                2.Add\s
                3.Show\s
                4.Register\s
                5.Kurse mit freien Platzen\s
                6.Studenten angemeldet bei einem Kurs\s
                7.Entfernen Kurs\s
                8.Andern ECTS\s
                9.Tschuss\s
                """);
    }

    /**
     * der UI
     */
    public void start() throws IOException, DasElementExistiertException, InterruptedException, SQLException {
        while(true)
        {
            getMenu();
            Scanner keyboard = new Scanner( System.in );
            int key;
            do {
                System.out.print("Wahlen Sie bitte eine Option: ");
                key = keyboard.nextInt();
            }
            while(key<1 && key >9);

            long id;
            long idKurs;
            long idStudent;
            long idLehrer;

            switch (key) {
                case 1 -> {
                    getMenuSortFilter();
                    getFunctionSortFilter();
                }
                case 2 -> {
                    getAddMenu();
                    getFunctionAdd();
                }
                case 3 -> {
                    getMenuShow();
                    getFunctionGetAll();
                }
                case 4 -> {
                    do {
                        System.out.print("Wahlen Sie bitte einen Kurs: ");
                        idKurs = keyboard.nextInt();
                    }
                    while (!kursController.findOne(idKurs));
                    do {
                        System.out.print("Wahlen Sie bitte einen Student: ");
                        idStudent = keyboard.nextInt();
                    }
                    while (!studentController.findOne(idStudent));

                    if(this.enrolledController.create(new Enrolledment(idStudent, idKurs)) != null)
                        System.out.println("Der Student wurde registered");
                    else
                        System.out.println("Der Student wurde nicht registered");
                }
                case 5 -> {
                    System.out.println("Freie Kursen:\n" + kursController.getKurseFreiePlatze());
                    Thread.sleep(3000);
                }
                case 6 -> {
                    System.out.println("ID:");
                    id = keyboard.nextLong();
                    if (kursController.findOne(id)) {
                        studentController.getListeAngemeldeteStudenten(id);
                    } else
                        System.out.println("Das gegebene Kurs existiert nicht.\n");
                    Thread.sleep(3000);
                }
                case 7 -> {
                    do {
                        System.out.print("Wahlen Sie bitte einen Lehrer: ");
                        idLehrer = keyboard.nextLong();
                    }
                    while (!lehrerController.findOne(idLehrer));

                    do {
                        System.out.print("Wahlen Sie bitte einen Kurs: ");
                        idKurs = keyboard.nextLong();
                    }
                    while (!kursController.findOne(idKurs));

                    Kurs kurs = this.kursController.getKursRepo().getKursNachId(idKurs);

                    if (kurs.getLehrer() == idLehrer)
                    {
                        this.kursController.entfernenKurs(idKurs, idLehrer);
                        System.out.println("Das Kurs wurde gelöscht");
                    }
                    else
                            System.out.println("Der Lehrer kann das Kurs nicht löschen.\n");

                    Thread.sleep(2000);
                }
                case 8 -> {
                    System.out.println("KursId:");
                    idKurs = keyboard.nextLong();
                    System.out.println("ECTS:");
                    int ects = keyboard.nextInt();
                    if (kursController.findOne(idKurs))
                    {
                        kursController.andernECTS(idKurs, ects);
                        System.out.println("Die ECTS wurden geändert.\n");
                    } else
                        System.out.println("Das Kurs existiert nicht.\n");
                    Thread.sleep(2000);
                }
                case 9 -> {
                    System.out.println("TSCHUSS!!!");
                    System.exit(0);
                }
            }
        }

    }

    /**
     * der User gibt die Attribute einem Studenten
     * @return Student
     */
    public Student createStudent() throws SQLException, IOException {
        Scanner scan= new Scanner(System.in);
        System.out.println("Vorname:");
        String vorname= scan.nextLine();
        System.out.println("Nachname:");
        String nachname= scan.nextLine();
        long id;
        do{
            System.out.println("ID:");
            id= scan.nextLong();
        }while(studentController.findOne(id));

        return new Student(id, vorname, nachname);

    }

    /**
     * der User gibt die Attribute einem Lehrer
     * @return Lehrer
     */
    public Lehrer createLehrer() throws SQLException, IOException {
        Scanner scan= new Scanner(System.in);
        System.out.println("Vorname Lehrer:");
        String vorname= scan.nextLine();
        System.out.println("Nachname Lehrer:");
        String nachname= scan.nextLine();
        long id;
        do{
            System.out.println("ID Lehrer:");
            id= scan.nextLong();
        }while(lehrerController.findOne(id));

        return new Lehrer(vorname, nachname, id);

    }

    /**
     * der User gibt die Attribute einem Kurs
     * @return Kurs
     */
    public Kurs createKurs() throws SQLException, IOException {
        Scanner scan= new Scanner(System.in);
        System.out.println("Name:");
        String name= scan.nextLine();

        long id;
        do{
            System.out.println("ID:");
            id= scan.nextLong();
        }while(kursController.findOne(id));

        long idLehrer;
        do{
            System.out.println("Lehrer:");
            idLehrer= scan.nextLong();
        }while(!lehrerController.findOne(idLehrer));

        int maximaleAnzahlStudenten;
        do{
            System.out.println("Maximale Anzahl von Studenten:");
            maximaleAnzahlStudenten= scan.nextInt();
        }while(maximaleAnzahlStudenten <= 0);

        int ects;
        do{
            System.out.println("ECTS:");
            ects= scan.nextInt();
        }while(ects <= 0);

        return new Kurs(id, name, idLehrer, maximaleAnzahlStudenten, ects);

    }

    /**
     * Menu fur UI
     */
    public void getMenuSortFilter()
    {
        System.out.println("""
                1.Filter Kurse\s
                2.Sortiere Kurse\s
                3.Filter Studenten\s
                4.Sortiere Studenten\s
                """);
    }

    /**
     * Ui und Anwendung fur Sort und Filter Methoden
     * @throws InterruptedException, fur Wartezeit
     */
    public void getFunctionSortFilter() throws InterruptedException, SQLException, IOException {
        Scanner scan= new Scanner(System.in);
        int key;
        do {
            System.out.print("Wahlen Sie bitte eine Option: ");
            key = scan.nextInt();
        }
        while(key<1 && key >4);

        switch (key) {
            case 1 -> {
                System.out.println(kursController.filter());
                Thread.sleep(3000);
            }
            case 2 -> {
                kursController.sort();
                System.out.println(kursController.sort());
                Thread.sleep(3000);
            }
            case 3 -> {
                System.out.println(studentController.filter());
                Thread.sleep(3000);
            }
            case 4 -> {
                studentController.sort();
                System.out.println(studentController.sort());
                Thread.sleep(3000);
            }
        }
    }

    /**
     * Menu fur UI
     */
    public void getAddMenu()
    {
        System.out.println("""
                1.Add Kurse\s
                2.Add Lehrer\s
                3.Add Studenten\s
                """);
    }

    /**
     * Ui und Anwendung fur Add Methoden
     * @throws IOException, fur Schreiben im File
     * @throws DasElementExistiertException, das Element existiert in der Liste
     */
    public void getFunctionAdd() throws IOException, DasElementExistiertException, SQLException {
        Scanner scan= new Scanner(System.in);
        int key;
        do {
            System.out.print("Wahlen Sie bitte eine Option: ");
            key = scan.nextInt();
        }
        while(key<1 && key >3);

        switch (key) {
            case 1 -> {
                Kurs kurs = this.createKurs();
                kursController.create(kurs);
            }
            case 2 -> {
                Lehrer lehrer = this.createLehrer();
                lehrerController.create(lehrer);
            }
            case 3 -> {
                Student student = this.createStudent();
                studentController.create(student);
            }
        }
    }

    /**
     * Menu fur UI
     */
    public void getMenuShow()
    {
        System.out.println("""
                1.Show Kurse\s
                2.Show Lehrer\s
                3.Show Studenten\s
                """);
    }

    /**
     * Ui und Anwendung fur Add Methoden
     * @throws InterruptedException, fur Wartezeit
     */
    public void getFunctionGetAll() throws InterruptedException, SQLException, IOException {
        Scanner scan= new Scanner(System.in);
        int key;
        do {
            System.out.print("Wahlen Sie bitte eine Option: ");
            key = scan.nextInt();
        }
        while(key<1 && key >3);

        switch (key) {
            case 1 -> {
                System.out.println("KURSE:\n" + kursController.getAll());
                Thread.sleep(3000);
            }
            case 2 -> {
                System.out.println("LEHRER:\n" + lehrerController.getAll());
                Thread.sleep(3000);
            }
            case 3 -> {
                System.out.println("STUDENTEN:\n" + studentController.getAll());
                Thread.sleep(3000);
            }
        }
    }
}
