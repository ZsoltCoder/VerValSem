package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentFileRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServiceTest {

    Validator<Grade> gradeValidator = new GradeValidator();
    Validator<Homework> homeworkValidator = new HomeworkValidator();

    StudentXMLRepository fileRepository1;
    HomeworkXMLRepository fileRepository2;
    GradeXMLRepository fileRepository3;

    Service service;
    Validator<Student> studentValidator = new StudentValidator();

    @BeforeAll
    void setUp() {

        fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");
        service = new Service(fileRepository1, fileRepository2, fileRepository3);
        service.saveStudent("18", "Zsolt", 531);
        service.saveStudent("20", "Nem Zsolt", 531);
        service.saveHomework("21", "dsahdghadas", 5, 1);
    }

    @Test
    void givenValidDataSaveStudentSuccesful() {
        String id = "19";
        String name = "Zsolt";
        int group = 531;
        int result = service.saveStudent(id, name, group);
        assertEquals(1, result, "Saving result should be one");
    }

    @Test
    void saveHomeworkWithValidData() {
        String id = "22";
        String description = "sjkgdaskdasd";
        int deadline = 5;
        int start = 1;

        int result = service.saveHomework(id, description, deadline,start);

        assertEquals(1, result , "Save homework result should be 1");
    }

    @Test
    void GivenValidDataUpdateStudentSuccesful() {
        String id = "18";
        String name = "Zsolti";
        int group = 450;
        int result = service.updateStudent(id, name, group);
        assertTrue(result == 1, "Updating result should be one");
    }

    @Test
    void GivenExistingIDdeleteStudentSuccesful() {
        String id = "20";
        int result = service.deleteStudent(id);
        assertNotEquals(0, result, "Deleting result should be one");

    }

    @ParameterizedTest
    @ValueSource(strings = {"21", "69"})
    public void deleteHomeworkWhitIds(String id) {
        int result = service.deleteHomework(id);

        if ("21".equals(id)) {
            assertEquals(1, result, "Deleting result should be one");
        } else {
            assertEquals(0, result, "Deleting result should be zero");
        }

    }

    @AfterAll
    void deleteResources(){
        service.deleteStudent("19");
        service.deleteStudent("18");
        service.deleteHomework("22");
    }


}