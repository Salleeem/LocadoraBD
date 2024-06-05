package webapp.locadoracarros.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import webapp.locadoracarros.Model.Carros;
import webapp.locadoracarros.Repository.CarrosRepository;

@Controller
public class CarrosController {

    private final CarrosRepository carrosRepository;

    public CarrosController(CarrosRepository carrosRepository) {
        this.carrosRepository = carrosRepository;
    }

    @GetMapping("/cadastrarCarro")
    public String showCarroForm() {
        return "carros";
    }

    @PostMapping("/cadastrarCarro")
    public ModelAndView cadastrarCarro(@ModelAttribute Carros carro) {
        carrosRepository.save(carro);
        return new ModelAndView("redirect:/sucesso");
    }
}
