package br.edu.ifpb.academico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.academico.entity.Motorista;
import br.edu.ifpb.academico.entity.Viagem;
import br.edu.ifpb.academico.entity.Viagem.StatusViagem;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Long> {

    List<Viagem> findByMotorista(Motorista motorista);

    List<Viagem> findByStatus(StatusViagem status);

    boolean existsByMotoristaAndStatus(Motorista motorista, StatusViagem status);

}
