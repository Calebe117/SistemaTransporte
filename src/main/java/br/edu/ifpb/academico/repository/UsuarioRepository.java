package br.edu.ifpb.academico.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.edu.ifpb.academico.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByLogin(String login);

    Optional<Usuario> findByLogin(String login);
}
