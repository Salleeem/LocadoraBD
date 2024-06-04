package webapp.locadoracarros.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import webapp.locadoracarros.Model.Clientes;

public interface ClientesRepository extends CrudRepository<Clientes, Long> {
    Iterable<Clientes> findAll(); // Mantendo Iterable e convertendo para List<Clientes> onde necessário
}
