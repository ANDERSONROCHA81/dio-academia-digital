package me.dio.academia.digital.controller;

import jakarta.validation.Valid;
import me.dio.academia.digital.entity.Matricula;
import me.dio.academia.digital.entity.form.MatriculaForm;
import me.dio.academia.digital.service.impl.AlunoServiceImpl;
import me.dio.academia.digital.service.impl.MatriculaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

  @Autowired
  private MatriculaServiceImpl service;
  @Autowired
  private AlunoServiceImpl serviceAluno;

  @PostMapping
  public Matricula create(@Valid @RequestBody MatriculaForm form) {
    return service.create(form);
  }

  @GetMapping
  public List<Matricula> getAll(@RequestParam(value = "bairro", required = false) String bairro) {
    return service.getAll(bairro);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getMatriculaById(@PathVariable Long id){
    Optional<Matricula> matriculaOptional = service.getMatriculaById(id);
    if (matriculaOptional.isEmpty()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Matrícula não encontrada.");
    }else {
      return ResponseEntity.status(HttpStatus.OK).body(matriculaOptional.get());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteMatricula(@PathVariable Long id){
    Optional<Matricula> matriculaOptional = service.getMatriculaById(id);
    if (matriculaOptional.isEmpty()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Matrícula não encontrada.");
    }
    service.deleteMatricula(matriculaOptional.get().getId());
    return ResponseEntity.status(HttpStatus.OK).body("Matrícula deletada com sucesso.");
  }
}