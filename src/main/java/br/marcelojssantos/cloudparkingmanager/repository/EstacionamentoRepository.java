package br.marcelojssantos.cloudparkingmanager.repository;

import br.marcelojssantos.cloudparkingmanager.model.Estacionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacionamentoRepository extends JpaRepository<Estacionamento, String> {
}
