package com.bezkoder.spring.data.mongodb.service;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bezkoder.spring.data.mongodb.model.*; 
import com.bezkoder.spring.data.mongodb.model.Person;
//import com.bezkoder.spring.data.mongodb.repository.CocheRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
//import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service 
public class CocheService {
    @Autowired
    public Person[]   listaApi() {
         HttpHeaders headers = new HttpHeaders();
         // headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
         HttpEntity <String> entity = new HttpEntity<String>(headers);
         RestTemplate restTemplate = new RestTemplate();
  
         String resourceUrl
           = "https://jsonplaceholder.typicode.com/posts/1/comments";
  
         // Fetching response as Object  
         ResponseEntity<Person[]> products
           = restTemplate.getForEntity(resourceUrl, Person[].class);
  
         return products.getBody();
    }
    /*
    public Optional<Coche> getById(int id){
        return cocheRepository.idProcedure(id);
    }
    public List<Coche> getByMarca(String marca){
        return cocheRepository.marcaProcedure(marca);
    }
    public void saveProcedure(Coche coche){
        cocheRepository.saveProcedure(coche.getMarca(), coche.getModelo(), coche.getAnyo(), coche.getKm());
    }
    public float mediaKm(){
        return cocheRepository.mediaKm();
    }
    public void borrarProcedure(int id){
        cocheRepository.borrarProcedure(id);
    }*/
}