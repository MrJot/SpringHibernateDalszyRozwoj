package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.School;
import pl.edu.agh.ki.mwo.model.SchoolClass;
import pl.edu.agh.ki.mwo.model.Student;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller
public class SchoolClassesController {

    @RequestMapping(value="/SchoolClasses")
    public String listSchoolClass(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

    	model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
    	
        return "schoolClassesList";    
    }
    
    @RequestMapping(value="/AddSchoolClass")
    public String displayAddSchoolClassForm(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

       	model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
       	
        return "schoolClassForm";    
    }

    @RequestMapping(value="/CreateSchoolClass", method=RequestMethod.POST)
    public String createSchoolClass(@RequestParam(value="schoolClassStartYear", required=false) String startYear,
    		@RequestParam(value="schoolClassCurrentYear", required=false) String currentYear,
    		@RequestParam(value="schoolClassProfile", required=false) String profile,
    		@RequestParam(value="schoolClassSchool", required=false) String schoolId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	SchoolClass schoolClass = new SchoolClass();
    	schoolClass.setStartYear(Integer.valueOf(startYear));
    	schoolClass.setCurrentYear(Integer.valueOf(currentYear));
    	schoolClass.setProfile(profile);
    	
    	DatabaseConnector.getInstance().addSchoolClass(schoolClass, schoolId);    	
       	model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
    	model.addAttribute("message", "Nowa klasa została dodana");
         	
    	return "schoolClassesList";
    }
    
    @RequestMapping(value="/DeleteSchoolClass", method=RequestMethod.POST)
    public String deleteSchoolClass(@RequestParam(value="schoolClassId", required=false) String schoolClassId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteSchoolClass(schoolClassId);    	
       	model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
    	model.addAttribute("message", "Klasa została usunięta");
         	
    	return "schoolClassesList";
    }

   @RequestMapping(value="/ModifySchoolClass")
   public String modifySchoolClass(@RequestParam(value="schoolClassId", required=false) String schoolClassId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	SchoolClass sclass = DatabaseConnector.getInstance().getSchoolClass(schoolClassId);
    	model.addAttribute("schoolClassId",sclass.getId());
    	System.out.println(sclass.getId());
    	model.addAttribute("schoolClassStartYear", sclass.getStartYear());
    	model.addAttribute("schoolClassCurrentYear", sclass.getCurrentYear());
    	model.addAttribute("schoolClassProfile",sclass.getProfile());
    	model.addAttribute("schoolClassSchool", DatabaseConnector.getInstance().getSpecificSchool(schoolClassId));
    	model.addAttribute("schools",DatabaseConnector.getInstance().getSchools());
        return "schoolClassModForm";
    }
   
   @RequestMapping(value="/SaveSchoolClass", method=RequestMethod.POST)
   public String saveSchoolClass(@RequestParam(value="schoolClassStartYear", required=false) String startYear,
   		@RequestParam(value="schoolClassCurrentYear", required=false) String currentYear,
   		@RequestParam(value="schoolClassProfile", required=false) String profile,
   		@RequestParam(value="schoolClassSchool", required=false) String schoolId,
   		@RequestParam(value="schoolClassId", required=false) String schoolClassId,
   		Model model, HttpSession session) {    	
   	if (session.getAttribute("userLogin") == null)
   		return "redirect:/Login";
   	
   	SchoolClass schoolClass = DatabaseConnector.getInstance().getSchoolClass(schoolClassId);
   	schoolClass.setStartYear(Integer.valueOf(startYear));
   	schoolClass.setCurrentYear(Integer.valueOf(currentYear));
   	schoolClass.setProfile(profile);
   	System.out.println("School ID: "+schoolId);
   	
   	DatabaseConnector.getInstance().saveSchoolClass(schoolClass, schoolId); 	
    model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
   	model.addAttribute("message", "Dane dotyczace klasy zostaly zapisane");
        	
   	return "schoolClassesList";
   }
    
    
}