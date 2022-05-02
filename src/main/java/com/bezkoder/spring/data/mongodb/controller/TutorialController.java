package com.bezkoder.spring.data.mongodb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.bezkoder.spring.data.mongodb.model.Tutorial;
import com.bezkoder.spring.data.mongodb.repository.TutorialRepository;
import com.bezkoder.spring.data.mongodb.service.CocheService;
import com.bezkoder.spring.data.mongodb.service.HolaMundoImpl;
import com.bezkoder.spring.data.mongodb.service.ReportePdf;
import com.bezkoder.spring.data.mongodb.model.*;


import javax.servlet.http.HttpServletResponse;
//import com.bezkoder.spring.data.mongodb.model.Contact;

//import com.bezkoder.spring.data.mongodb.service.ExportarPdfService;
import org.thymeleaf.context.IContext;
import org.springframework.stereotype.Controller;
//import com.bezkoder.spring.data.mongodb.service.CocheService;
import java.io.IOException;
import java.util.ArrayList; 
import org.apache.poi.util.IOUtils;
import java.io.ByteArrayInputStream;
//import org.apache.http.HttpHeaders;
import org.springframework.web.bind.annotation.ModelAttribute;
//@CrossOrigin(origins = "http://localhost:8080")
import org.springframework.http.MediaType;
//import java.awt.PageAttributes.MediaType;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.springframework.ui.Model;
import org.thymeleaf.TemplateEngine;
import com.bezkoder.spring.data.mongodb.dto.Raffle;
import com.lowagie.text.DocumentException;
import org.thymeleaf.context.IWebContext;
import org.springframework.context.ApplicationContext;

import com.bezkoder.spring.data.mongodb.service.UserService;
 
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@Controller
@RestController
@RequestMapping("/api")

public class TutorialController {
  @Autowired
  private HolaMundoImpl excelFileService;
  @GetMapping("/downloadExcelFile")
	public void downloadExcelFile(HttpServletResponse response) throws IOException {
        //List<Contact> contacts = (List<Contact>)contactRepository.findAll();
        List<Contact> miLista = new ArrayList<Contact>();
        Contact itemContact=new Contact();
        itemContact.setAddress("address");
        itemContact.setEmail("email");
        itemContact.setFirstName("firstName");
        long numero=122;
        itemContact.setId(numero);
        itemContact.setLastName("lastName");
        itemContact.setPhoneNumber("phoneNumber");
        miLista.add(itemContact);
        miLista.add(itemContact);
        miLista.add(itemContact);
        miLista.add(itemContact);
        miLista.add(itemContact);
        miLista.add(itemContact);
        miLista.add(itemContact);
        miLista.add(itemContact);
        miLista.add(itemContact);
        miLista.add(itemContact);
        miLista.add(itemContact);
        ByteArrayInputStream byteArrayInputStream = excelFileService.export(miLista);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=contacts.xlsx");
        IOUtils.copy(byteArrayInputStream, response.getOutputStream());
  }
  
  
  @Autowired
  CocheService cocheGenarator;

  @Autowired
	private ApplicationContext context;
  @RequestMapping("/2222222222")
    String hellow() 
    {
        return "Hello World!";
    }


  @Autowired
  TutorialRepository tutorialRepository;
  
  @GetMapping("/tutorials")
  public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
    try {
      List<Tutorial> tutorials = new ArrayList<Tutorial>();
     
      System.out.println(title);
      if (title == null)
        tutorialRepository.findAll().forEach(tutorials::add);
      else
        tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
      return new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println("errorrrrrrr");
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

@Autowired
private ReportePdf pdfGenarator;
// ResponseEntity<ByteArrayResource>
@GetMapping("/raffle/pdf")
	public ResponseEntity<ByteArrayResource> rafflePDF(@ModelAttribute final Raffle raffle, final HttpServletRequest request,
			final HttpServletResponse response) throws DocumentException {

		List<String> winners = raffle.getWinners();

		Map<String, Object> mapParameter = new HashMap<String, Object>();
		mapParameter.put("name", "juan ramirezzzzzzzzzzzzzz");
		mapParameter.put("winners", winners);

		ByteArrayOutputStream byteArrayOutputStreamPDF = pdfGenarator.generatePdfFile("index.html", mapParameter,"holamundo.pdf");
		ByteArrayResource inputStreamResourcePDF = new ByteArrayResource(byteArrayOutputStreamPDF.toByteArray());
    String fileName2 = "reaffle.pdf";
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName2).contentType(MediaType.APPLICATION_PDF)
				.contentLength(inputStreamResourcePDF.contentLength()).body(inputStreamResourcePDF);

	}
  @DeleteMapping("/tutorials")
  public ResponseEntity<HttpStatus> deleteAllTutorials() {
    try {
      tutorialRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @PutMapping("/tutorials/{id}")
  public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") String id, @RequestBody Tutorial tutorial) {
  
    Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
    
    if (tutorialData.isPresent()) {
      Tutorial _tutorial = tutorialData.get();
      _tutorial.setTitle(tutorial.getTitle());
      _tutorial.setDescription(tutorial.getDescription());
      _tutorial.setPublished(tutorial.isPublished());
      return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  @GetMapping("/tutorials/published")
  public ResponseEntity<List<Tutorial>> findByPublished() {
    try {
      List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @GetMapping("/tutorials/{id}")
  public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") String id) {
    Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

    if (tutorialData.isPresent()) {
      return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/tutorials")
  public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
    try {
      Tutorial _tutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
      return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
    } catch (Exception e) {
      System.out.println("errror");
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  
  @Autowired
  private UserService userService;

  @GetMapping("/datosSql")
    public Object getUsers() {
        return userService.getUsers();
    }
}
