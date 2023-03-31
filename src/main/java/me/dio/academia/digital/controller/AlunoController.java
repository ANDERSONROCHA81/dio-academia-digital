package me.dio.academia.digital.controller;

import me.dio.academia.digital.entity.Aluno;
import me.dio.academia.digital.entity.AvaliacaoFisica;
import me.dio.academia.digital.entity.form.AlunoForm;
import me.dio.academia.digital.entity.form.AlunoUpdateForm;
import me.dio.academia.digital.service.impl.AlunoServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

  @Autowired
  private AlunoServiceImpl service;

  @PostMapping
  public Aluno create(@Valid @RequestBody AlunoForm form) {
    return service.create(form);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getAlunoById(@PathVariable Long id){
    Optional<Aluno> alunoOptional = service.getAlunoById(id);
    if (!alunoOptional.isPresent()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado.");
    }else {
      return ResponseEntity.status(HttpStatus.OK).body(alunoOptional.get());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateAluno(@PathVariable Long id,
                                            @Valid @RequestBody AlunoUpdateForm formUpdate){
    Optional<Aluno> alunoOptional = service.getAlunoById(id);
    if (!alunoOptional.isPresent()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado.");
    }
    Aluno aluno = new Aluno();
    BeanUtils.copyProperties(formUpdate, aluno);
    aluno.setId(alunoOptional.get().getId());
    aluno.setCpf(alunoOptional.get().getCpf());
    return ResponseEntity.status(HttpStatus.OK).body(service.create(aluno));
  }

  @GetMapping("/avaliacoes/{id}")
  public List<AvaliacaoFisica> getAllAvaliacaoFisicaId(@PathVariable Long id) {
    return service.getAllAvaliacaoFisicaId(id);
  }

  @GetMapping
  public List<Aluno> getAll(@RequestParam(value = "dataDeNascimento", required = false)
                                  String dataDeNacimento){
    return service.getAll(dataDeNacimento);
  }
}
