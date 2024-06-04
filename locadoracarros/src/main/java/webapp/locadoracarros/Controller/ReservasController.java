package webapp.locadoracarros.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import webapp.locadoracarros.Model.Clientes;
import webapp.locadoracarros.Model.Reservas;
import webapp.locadoracarros.Repository.ClientesRepository;
import webapp.locadoracarros.Repository.ReservasRepository;

@Controller
public class ReservasController {

    private final ReservasRepository reservasRepository;
    private final ClientesRepository clientesRepository;

    public ReservasController(ReservasRepository reservasRepository, ClientesRepository clientesRepository) {
        this.reservasRepository = reservasRepository;
        this.clientesRepository = clientesRepository;
    }

    @PostMapping("/reservarCarro")
    public ModelAndView cadastrarReserva(@ModelAttribute Reservas reserva) {
        reservasRepository.save(reserva);
        return new ModelAndView("redirect:/sucesso");
    }

    @GetMapping("/listarReservas")
    public String listarReservas(Model model) {
        List<Reservas> reservas = reservasRepository.findAll();
        model.addAttribute("reservas", reservas);
        return "listarReservas";
    }

    @GetMapping("/reservarCarro")
    public String showReservaForm(Model model) {
        Iterable<Clientes> clientesIterable = clientesRepository.findAll();
        List<Clientes> clientes = new ArrayList<>();
        clientesIterable.forEach(clientes::add);

        // Log dos clientes recuperados
        clientes.forEach(System.out::println);

        model.addAttribute("clientes", clientes);
        return "reserva_form";
    }

}
