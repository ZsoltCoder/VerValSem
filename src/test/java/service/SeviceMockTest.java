package service;

import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SeviceMockTest {

    @Mock
    StudentXMLRepository studentXMLRepository;

    @Mock
    HomeworkXMLRepository homeworkXMLRepository;

    @Mock
    GradeXMLRepository gradeXMLRepository;

    Service service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new Service(studentXMLRepository, homeworkXMLRepository, gradeXMLRepository);
    }


    @Test
    void givenValidDataSaveStudentSuccesful() {
        String id = "69";
        String name = "Zsolt";
        int group = 531;

        Student student = new Student(id, name, group);

        Mockito.doReturn(null).when(studentXMLRepository).save(new Student(id, anyString(), group));
        assertEquals(1, service.saveStudent(id, name, group), "Saving result should be one");
        Mockito.verify(studentXMLRepository).save(student);
    }

    @Test
    void saveHomeworkWithValidData() {
        String id = "22";
        String description = "sjkgdaskdasd";
        int deadline = 5;
        int start = 1;

        Mockito.doReturn(null).when(homeworkXMLRepository).save(new Homework(id, anyString(), deadline, start));
        assertEquals(1, service.saveHomework(id, description, deadline, start), "Save homework result should be 1");
        Mockito.verify(homeworkXMLRepository).save(new Homework(id, description, deadline, start));
    }

    @Test
    void GivenValidDataUpdateStudentSuccesful() {
        String id = "18";
        String name = "Zsolti";
        int group = 450;
        Student student = new Student(id, name, group);
        Mockito.doReturn(student).when(studentXMLRepository).update(student);
        assertTrue(service.updateStudent(id, name, group) == 1, "Updating result should be one");
        Mockito.verify(studentXMLRepository).update(student);
    }

}
