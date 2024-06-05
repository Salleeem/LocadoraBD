package webapp.locadoracarros.Controller;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView cadastrarReserva(@RequestParam("cliente") Long clienteId,
                                         @RequestParam("modeloCarro") String modeloCarro,
                                         @RequestParam("localRetirada") String localRetirada,
                                         @RequestParam("dataRetirada") Date dataRetirada,
                                         @RequestParam("dataDevolu") Date dataDevolu) {
        // Encontrar o cliente pelo ID
        Clientes cliente = clientesRepository.findById(clienteId)
                                             .orElseThrow(() -> new IllegalArgumentException("Cliente inválido: " + clienteId));
        
        // Criar nova reserva
        Reservas reserva = new Reservas();
        reserva.setCliente(cliente);
        reserva.setModeloCarro(modeloCarro);
        reserva.setLocalRetirada(localRetirada);
        reserva.setDataRetirada(dataRetirada);
        reserva.setDataDevolu(dataDevolu);
        
        // Salvar a reserva
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
        System.out.println("Clientes recuperados:");
        clientes.forEach(cliente -> System.out.println("Cliente: " + cliente.getNome()));

        model.addAttribute("clientes", clientes);
        model.addAttribute("reserva", new Reservas()); // Adicionando um objeto reserva para o formulário

        return "reservas"; // Certifique-se de que este é o nome correto do seu template
    }
}
