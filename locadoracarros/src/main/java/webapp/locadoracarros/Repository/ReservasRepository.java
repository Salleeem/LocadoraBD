package webapp.locadoracarros.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import webapp.locadoracarros.Model.Reservas;

public interface ReservasRepository extends CrudRepository<Reservas, Long> {
    List<Reservas> findAll();
    
    List<Reservas> findByClienteCpf(String cpf);
    
    @Query("SELECT r FROM Reservas r WHERE r.carro.modelo = :modelo")
    List<Reservas> findByCarroModelo(@Param("modelo") String modelo);

    
}
