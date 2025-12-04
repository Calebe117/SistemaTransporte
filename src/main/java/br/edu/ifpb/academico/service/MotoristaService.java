package br.edu.ifpb.academico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.academico.repository.MotoristaRepository;
import br.edu.ifpb.academico.entity.Motorista;
import br.edu.ifpb.academico.repository.CnhRepository;

@Service
public class MotoristaService {
    
    @Autowired
    protected MotoristaRepository motoristaRepository;

    @Autowired
    protected CnhRepository cnhRepository; 
    
    public Motorista save(Motorista motorista) {
        return motoristaRepository.save(motorista);
    }
    
    public List<Motorista> list() {
        return motoristaRepository.findAll();
    }
    
    public Motorista findById(Long id) {
        return motoristaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Motorista não encontrado"));
    }
    
    public void deleteById(Long id) {
        if (!motoristaRepository.existsById(id)) {
            throw new RuntimeException("Motorista não encontrado");
        }
        motoristaRepository.deleteById(id);
    }
    
    public boolean existsByEmail(String email) {
        return motoristaRepository.existsByEmail(email);
    }

    public boolean existsByCnhNumero(String numeroCnh) {
        return cnhRepository.existsByNumeroCnh(numeroCnh);
    }
}
