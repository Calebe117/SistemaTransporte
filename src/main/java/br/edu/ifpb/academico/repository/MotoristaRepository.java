package br.edu.ifpb.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.entity.Motorista;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, Long> {

    boolean existsByEmail(String email);

}
