package me.dio.academia.digital.service.impl;

import jakarta.transaction.Transactional;
import me.dio.academia.digital.entity.Aluno;
import me.dio.academia.digital.entity.AvaliacaoFisica;
import me.dio.academia.digital.entity.form.AvaliacaoFisicaForm;
import me.dio.academia.digital.entity.form.AvaliacaoFisicaUpdateForm;
import me.dio.academia.digital.repository.AlunoRepository;
import me.dio.academia.digital.repository.AvaliacaoFisicaRepository;
import me.dio.academia.digital.service.IAvaliacaoFisicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoFisicaServiceImpl implements IAvaliacaoFisicaService {

  @Autowired
  private AvaliacaoFisicaRepository avaliacaoFisicaRepository;

  @Autowired
  private AlunoRepository alunoRepository;

  @Override
  public AvaliacaoFisica create(AvaliacaoFisicaForm form) {
    AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica();
    Aluno aluno = alunoRepository.findById(form.getAlunoId()).get();

    avaliacaoFisica.setAluno(aluno);
    avaliacaoFisica.setPeso(form.getPeso());
    avaliacaoFisica.setAltura(form.getAltura());

    return avaliacaoFisicaRepository.save(avaliacaoFisica);
  }

  @Override
  public AvaliacaoFisica create(AvaliacaoFisica avaliacaoFisica) {
    return avaliacaoFisicaRepository.save(avaliacaoFisica);
  }

  @Override
  public Optional<AvaliacaoFisica> getAvaliacaoFisicaById(Long id) {
    return avaliacaoFisicaRepository.findById(id);
  }

  @Override
  public List<AvaliacaoFisica> getAll() {

    return avaliacaoFisicaRepository.findAll();
  }

  @Override
  public Optional<AvaliacaoFisica> updateAvaliacaoFisica(Long id, AvaliacaoFisicaUpdateForm formUpdate) {
    return avaliacaoFisicaRepository.findById(id);
  }

  @Override
  @Transactional
  public void deleteavaliacaoFisica(Long id) {
    avaliacaoFisicaRepository.deleteById(id);
  }
}
