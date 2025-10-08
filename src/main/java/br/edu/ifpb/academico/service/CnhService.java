package br.edu.ifpb.academico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.academico.repository.CnhRepository;
import br.edu.ifpb.entity.Cnh;

@Service
public class CnhService {
	
    @Autowired
    protected CnhRepository cnhRepository;

    public Cnh save(Cnh cnh) {
        return cnhRepository.save(cnh);
    }

    public List<Cnh> list() {
        return cnhRepository.findAll();
    }

    public Cnh findById(Long id) {
        return cnhRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CNH não encontrada"));
    }

    public void deleteById(Long id) {
        if (!cnhRepository.existsById(id)) {
            throw new RuntimeException("CNH não encontrada");
        }
        cnhRepository.deleteById(id);
    }
    
    public boolean existsByNumeroCnh(String numeroCnh) { 
        return cnhRepository.existsByNumeroCnh(numeroCnh); 
    }
}
