package webapp.locadoracarros.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webapp.locadoracarros.Model.Reservas;
import webapp.locadoracarros.Repository.ReservasRepository;

@Controller
public class HomeController {

    private final ReservasRepository reservasRepository;

    public HomeController(ReservasRepository reservasRepository) {
        this.reservasRepository = reservasRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/reservas")
    public String reservas() {
        return "redirect:/reservarCarro";
    }

    @GetMapping("/sucesso")
    public String sucesso() {
        return "sucesso";
    }

    @GetMapping("/listarreservas")
    public String listarreservas(Model model) {
        model.addAttribute("reservas", reservasRepository.findAll());
        return "listarreservas";
    }

    @GetMapping("/clientes")
    public String clientes() {
        return "clientes";
    }

    @GetMapping("/carros")
    public String carros() {
        return "carros";
    }

    @GetMapping("/editarReserva/{idReserva}")
    public String editarReserva(@PathVariable Long idReserva, Model model) {
        Reservas reserva = reservasRepository.findById(idReserva).orElseThrow();
        model.addAttribute("reserva", reserva);
        return "editarreserva";
    }

    @PostMapping("/editarReserva/{idReserva}")
public String editarReservaPost(@PathVariable Long idReserva, @ModelAttribute Reservas reserva) {
    Reservas reservaToUpdate = reservasRepository.findById(idReserva).orElseThrow();
    // Atualize apenas os campos que você deseja que sejam editáveis
    reservaToUpdate.setLocalRetirada(reserva.getLocalRetirada());
    reservaToUpdate.setDataRetirada(reserva.getDataRetirada());
    reservaToUpdate.setDataDevolu(reserva.getDataDevolu());
    reservasRepository.save(reservaToUpdate);
    return "redirect:/listarreservas";
}


    @GetMapping("/deletarReserva/{idReserva}")
    public String deletarReserva(@PathVariable Long idReserva) {
        reservasRepository.deleteById(idReserva);
        return "redirect:/listarreservas";
    }
}
