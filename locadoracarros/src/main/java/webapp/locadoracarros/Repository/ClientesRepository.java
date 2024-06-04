package webapp.locadoracarros.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.locadoracarros.Model.Reservas;

public interface ClientesRepository extends JpaRepository<Reservas, Long>{

    
}
