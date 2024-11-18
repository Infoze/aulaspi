package IFRN.PI.ATIVIDADES.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import IFRN.PI.ATIVIDADES.molds.Evento;
import IFRN.PI.ATIVIDADES.molds.convidado;

public interface ConvidadoRepository extends JpaRepository<convidado, Long> {
	
	List<convidado> findByEvento(Evento evento);

}
