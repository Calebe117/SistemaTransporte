package br.edu.ifpb.academico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.academico.repository.ViagemRepository;
import br.edu.ifpb.entity.Viagem;
import br.edu.ifpb.entity.Viagem.StatusViagem;
import br.edu.ifpb.entity.Motorista;

@Service
public class ViagemService {

    @Autowired
    private ViagemRepository viagemRepository;

    public Viagem save(Viagem viagem) {
        return viagemRepository.save(viagem);
    }

    public List<Viagem> list() {
        return viagemRepository.findAll();
    }

    public Viagem findById(Long id) {
        return viagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viagem não encontrada"));
    }

    public void deleteById(Long id) {
        if (!viagemRepository.existsById(id)) {
            throw new RuntimeException("Viagem não encontrada");
        }
        viagemRepository.deleteById(id);
    }

    public List<Viagem> findByMotorista(Motorista motorista) {
        return viagemRepository.findByMotorista(motorista);
    }

    public List<Viagem> findByStatus(StatusViagem status) {
        return viagemRepository.findByStatus(status);
    }

    public boolean existsViagemEmAndamento(Motorista motorista) {
        return viagemRepository.existsByMotoristaAndStatus(motorista, StatusViagem.EM_ANDAMENTO);
    }
}
