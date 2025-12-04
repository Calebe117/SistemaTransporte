package br.edu.ifpb.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.academico.entity.Cnh;

@Repository
public interface CnhRepository extends JpaRepository<Cnh, Long> {

    boolean existsByNumeroCnh(String numeroCnh);

}
