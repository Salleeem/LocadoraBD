package webapp.locadoracarros.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import webapp.locadoracarros.Model.Carros;
import webapp.locadoracarros.Repository.CarrosRepository;

@Controller
public class CarrosController {

    private final CarrosRepository carrosRepository;

    public CarrosController(CarrosRepository carrosRepository) {
        this.carrosRepository = carrosRepository;
    }

    // Cadastrar Carro
    @GetMapping("/cadastrarCarro")
    public String showCarroForm() {
        return "carros";
    }

    @PostMapping("/cadastrarCarro")
    public ModelAndView cadastrarCarro(@ModelAttribute Carros carro) {
        carrosRepository.save(carro);
        return new ModelAndView("redirect:/sucessocar");
    }

    // Listar Carros
    @GetMapping("/listarcarros")
    public String listarCarros(Model model) {
        model.addAttribute("carros", carrosRepository.findAll());
        return "listarcarros";
    }

    // Editar Carro
    @GetMapping("/editarcarro/{idCarro}")
    public String showUpdateForm(@PathVariable("idCarro") Long idCarro, Model model) {
        Carros carro = carrosRepository.findById(idCarro)
                .orElseThrow(() -> new IllegalArgumentException("Carro inválido: " + idCarro));
        model.addAttribute("carro", carro);
        return "editarcarro";
    }

    @PostMapping("/editarcarro/{idCarro}")
    public String updateCarro(@PathVariable("idCarro") Long idCarro, @ModelAttribute("carro") Carros carro, Model model) {
        carrosRepository.save(carro);
        return "redirect:/listarcarros";
    }

    // Deletar Carro
    @GetMapping("/deletarcarro/{idCarro}")
    public String deleteCarro(@PathVariable("idCarro") Long idCarro, Model model) {
        Carros carro = carrosRepository.findById(idCarro)
                .orElseThrow(() -> new IllegalArgumentException("Carro inválido: " + idCarro));
        carrosRepository.delete(carro);
        return "redirect:/listarcarros";
    }
}
