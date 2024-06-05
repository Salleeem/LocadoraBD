package webapp.locadoracarros.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import webapp.locadoracarros.Model.Reservas;
import webapp.locadoracarros.Repository.ReservasRepository;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "reservas";
    }

    @GetMapping("/sucesso")
    public String sucesso() {
        return "sucesso";
    }

    @GetMapping("/listarreservas")
    public String listarreservas() {
        return "/listarreservas";
    }

    @GetMapping("/clientes")
    public String clientes(){
        return "/clientes";
    }

    @GetMapping("/carros")
    public String carros() {
        return "/carros";
    }
    


    @GetMapping("/editarReserva/{idReserva}")
    public String editarReserva(@PathVariable Long idReserva, Model model){
        Reservas reserva = reservasRepository.findById(idReserva).orElseThrow();
        model.addAttribute("reserva", reserva);
        return "editarReserva";
    }

    @PostMapping("/editarReserva/{idReserva}")
    public String editarReserva(@PathVariable Long idReserva, @ModelAttribute Reservas reserva){
        Reservas reservaToUpdate = reservasRepository.findById(idReserva).orElseThrow();
        reservaToUpdate.setModeloCarro(reserva.getModeloCarro());
        reservaToUpdate.setLocalRetirada(reserva.getLocalRetirada());
        reservaToUpdate.setDataRetirada(reserva.getDataRetirada());
        reservaToUpdate.setDataDevolu(reserva.getDataDevolu());
        reservasRepository.save(reservaToUpdate);
        return "redirect:/listarReservas";
    }

    @GetMapping("/deletarReserva/{idReserva}")
    public String deletarReserva(@PathVariable Long idReserva){
        reservasRepository.deleteById(idReserva);
        return "redirect:/listarReservas";
    }

    
}