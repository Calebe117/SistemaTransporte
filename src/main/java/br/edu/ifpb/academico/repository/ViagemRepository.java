package br.edu.ifpb.academico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.entity.Viagem;
import br.edu.ifpb.entity.Viagem.StatusViagem;
import br.edu.ifpb.entity.Motorista;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Long> {

    List<Viagem> findByMotorista(Motorista motorista);

    List<Viagem> findByStatus(StatusViagem status);

    boolean existsByMotoristaAndStatus(Motorista motorista, StatusViagem status);

}
