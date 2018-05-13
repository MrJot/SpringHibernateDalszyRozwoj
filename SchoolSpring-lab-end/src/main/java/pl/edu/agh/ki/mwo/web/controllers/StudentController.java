package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.SchoolClass;
import pl.edu.agh.ki.mwo.model.Student;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;
@Controller
public class StudentController {
	
	@RequestMapping(value="/Students")
    public String listStudents(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

    	model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
    	
        return "studentList";    
    }
	
	
    
    @RequestMapping(value="/AddStudent")
    public String addStudent(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
        return "studentForm";    
    }
    
    
    
    @RequestMapping(value="/ModifyStudent")
   public String modifyStudent(@RequestParam(value="studentId", required=false) String studentId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	Student student = DatabaseConnector.getInstance().getStudent(studentId);
    	model.addAttribute("studentName", student.getName());
    	model.addAttribute("studentSurname", student.getSurname());
    	model.addAttribute("studentPesel", student.getPesel());
    	model.addAttribute("studentId",student.getId());
    	model.addAttribute("schoolClassStudent", DatabaseConnector.getInstance().getSpecificStudentClass(studentId));
    	model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
        return "studentModForm";
    }
    
    @RequestMapping(value="/SaveStudent", method=RequestMethod.POST)
    public String saveStudent(@RequestParam(value="studentName", required=false) String name,
    		@RequestParam(value="studentSurname", required=false) String surname,
    		@RequestParam(value="studentPesel", required=false) String pesel,
    		@RequestParam(value="schoolClassStudent",required=false)  String schoolClassId,
    		@RequestParam(value="studentId", required=false) String studentId,
    		
    		
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	
    	Student student = DatabaseConnector.getInstance().getStudent(studentId);
    
    	student.setName(name);
    	student.setSurname(surname);
    	student.setPesel(pesel);
    	
    	   
    	DatabaseConnector.getInstance().addStudent(student, schoolClassId);
       	model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
    	model.addAttribute("message", "Dane studenta zostały zapisane.");
         	
    	return "studentList";
    }
    
    

    @RequestMapping(value="/CreateStudent", method=RequestMethod.POST)
    public String createStudent(@RequestParam(value="studentName", required=false) String name,
    		@RequestParam(value="studentSurname", required=false) String surname,
    		@RequestParam(value="studentPesel", required=false) String pesel,
    		@RequestParam(value="schoolClassStudent",required=false)  String schoolClassId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	Student student = new Student();
    	student.setName(name);
    	student.setSurname(surname);
    	student.setPesel(pesel);
    	
    	   
    	DatabaseConnector.getInstance().addStudent(student, schoolClassId);
       	model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
    	model.addAttribute("message", "Nowa uczen zostal dodany");
         	
    	return "studentList";
    }
    

    
    
    
    @RequestMapping(value="/DeleteStudent", method=RequestMethod.POST)
    public String deleteStudent(@RequestParam(value="studentId", required=false) String studentId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteStudent(studentId);  	
       	model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
    	model.addAttribute("message", "Student został usunięty");
         	
    	return "studentList";
    }

}
