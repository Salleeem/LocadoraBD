package webapp.locadoracarros.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import webapp.locadoracarros.Model.Reservas;
import webapp.locadoracarros.Repository.ReservasRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReservasController {

    private final ReservasRepository reservasRepository;

    public ReservasController(ReservasRepository reservasRepository) {
        this.reservasRepository = reservasRepository;
    }

    @PostMapping("/reservarCarro")
    public ModelAndView cadastrarReserva(@ModelAttribute Reservas reserva) {
        reservasRepository.save(reserva);
        return new ModelAndView("redirect:/sucesso");
    }

    @GetMapping("/listarReservas")
    public String listarReservas(Model model){
        List<Reservas> reservas = reservasRepository.findAll();
        model.addAttribute("reservas", reservas);
        return "listarReservas";
    }
}