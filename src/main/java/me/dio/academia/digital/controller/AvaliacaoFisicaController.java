package me.dio.academia.digital.controller;

import jakarta.validation.Valid;
import me.dio.academia.digital.entity.AvaliacaoFisica;
import me.dio.academia.digital.entity.form.AvaliacaoFisicaForm;
import me.dio.academia.digital.entity.form.AvaliacaoFisicaUpdateForm;
import me.dio.academia.digital.service.impl.AvaliacaoFisicaServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoFisicaController {

  @Autowired
  private AvaliacaoFisicaServiceImpl service;

  @PostMapping
  public AvaliacaoFisica create(@Valid @RequestBody AvaliacaoFisicaForm form) {
    return service.create(form);
  }

  @GetMapping
  public List<AvaliacaoFisica> getAll(){
    return service.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getAvaliacaoFisicaById(@PathVariable Long id){
    Optional<AvaliacaoFisica> avaliacaoFisicaOptional = service.getAvaliacaoFisicaById(id);
    if (!avaliacaoFisicaOptional.isPresent()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliação física não encontrada.");
    }else {
      return ResponseEntity.status(HttpStatus.OK).body(avaliacaoFisicaOptional.get());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateAvaliacaoFisica(@PathVariable Long id,
                                            @Valid @RequestBody AvaliacaoFisicaUpdateForm formUpdate){
    Optional<AvaliacaoFisica> avaliacaoFisicaOptional = service.getAvaliacaoFisicaById(id);
    if (avaliacaoFisicaOptional.isEmpty()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliação física não encontrada.");
    }
    AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica();
    BeanUtils.copyProperties(formUpdate, avaliacaoFisica);
    avaliacaoFisica.setId(avaliacaoFisicaOptional.get().getId());
    avaliacaoFisica.setAluno(avaliacaoFisicaOptional.get().getAluno());
    avaliacaoFisica.setDataDaAvaliacao(avaliacaoFisicaOptional.get().getDataDaAvaliacao());
    return ResponseEntity.status(HttpStatus.OK).body(service.create(avaliacaoFisica));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteAvaliacaoFisica(@PathVariable Long id){
    Optional<AvaliacaoFisica> avaliacaoFisicaOptional = service.getAvaliacaoFisicaById(id);
    if (avaliacaoFisicaOptional.isEmpty()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliação física não encontrada.");
    }
    service.deleteavaliacaoFisica(avaliacaoFisicaOptional.get().getId());
    return ResponseEntity.status(HttpStatus.OK).body("Avaliação física deletada com sucesso.");
  }
}
