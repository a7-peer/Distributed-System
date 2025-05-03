package org.example.coursemanagement.controllers;

import org.example.coursemanagement.JPO.Course;
import org.example.coursemanagement.JPO.Student;
import org.example.coursemanagement.services.CourseService;
import org.example.coursemanagement.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @PostMapping("/{id}/courses/{courseId}")
    public Student enrollStudentInCourse(@PathVariable Long id, @PathVariable Long courseId) {
        // You can now access courseService methods here
        Course course = courseService.getCourseById(courseId);
        if (course != null) {
            return studentService.enrollStudentInCourse(id, courseId);
        }
        return null; // Or handle with an appropriate response
    }

    @DeleteMapping("/{id}/courses/{courseId}")
    public Student removeStudentFromCourse(@PathVariable Long id, @PathVariable Long courseId) {
        return studentService.removeStudentFromCourse(id, courseId);
    }

    @GetMapping("/{id}/courses")
    public Set<Course> getCoursesForStudent(@PathVariable Long id) {
        return studentService.getCoursesForStudent(id);
    }
}
