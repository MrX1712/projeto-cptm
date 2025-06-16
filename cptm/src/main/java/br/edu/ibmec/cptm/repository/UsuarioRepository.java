package br.edu.ibmec.cptm.repository;

import br.edu.ibmec.cptm.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByEmailAndSenha(String email, String senha);

    Optional<Usuario> findByEmailAndCpf(String email, String cpf);
}