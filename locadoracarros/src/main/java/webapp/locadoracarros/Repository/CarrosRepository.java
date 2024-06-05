package webapp.locadoracarros.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.locadoracarros.Model.Carros;

public interface CarrosRepository extends JpaRepository<Carros, Long>{

}
