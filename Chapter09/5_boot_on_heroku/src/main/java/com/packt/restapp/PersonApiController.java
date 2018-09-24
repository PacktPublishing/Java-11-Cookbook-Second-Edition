package com.packt.restapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonApiController {
    @Autowired PersonMapper personMapper;

    @GetMapping
    public ResponseEntity<List<Person>> getPersons(){
        return new ResponseEntity<>(personMapper.getPersons(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Integer id){
        return new ResponseEntity<>(personMapper.getPerson(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Person> newPerson(@RequestBody Person person){
        personMapper.insert(person);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person, @PathVariable Integer id){
        person.setId(id);
        personMapper.save(person);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Integer id){
        personMapper.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
