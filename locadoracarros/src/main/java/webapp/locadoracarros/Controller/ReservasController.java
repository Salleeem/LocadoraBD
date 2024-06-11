package webapp.locadoracarros.Controller;

import java.util.List;
import java.sql.Date;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import webapp.locadoracarros.Model.Clientes;
import webapp.locadoracarros.Model.Carros;
import webapp.locadoracarros.Model.Reservas;
import webapp.locadoracarros.Repository.ClientesRepository;
import webapp.locadoracarros.Repository.CarrosRepository;
import webapp.locadoracarros.Repository.ReservasRepository;

@Controller
public class ReservasController {

    private final ReservasRepository reservasRepository;
    private final ClientesRepository clientesRepository;
    private final CarrosRepository carrosRepository;

    public ReservasController(ReservasRepository reservasRepository, ClientesRepository clientesRepository, CarrosRepository carrosRepository) {
        this.reservasRepository = reservasRepository;
        this.clientesRepository = clientesRepository;
        this.carrosRepository = carrosRepository;
    }

    @PostMapping("/reservarCarro")
    public ModelAndView cadastrarReserva(@RequestParam("cliente") Long clienteId,
                                         @RequestParam("carro") Long carroId,
                                         @RequestParam("localRetirada") String localRetirada,
                                         @RequestParam("dataRetirada") Date dataRetirada,
                                         @RequestParam("dataDevolu") Date dataDevolu) {
        // Encontrar o cliente pelo ID
        Clientes cliente = clientesRepository.findById(clienteId)
                                             .orElseThrow(() -> new IllegalArgumentException("Cliente inválido: " + clienteId));
                                             
        // Encontrar o carro pelo ID
        Carros carro = carrosRepository.findById(carroId)
                                        .orElseThrow(() -> new IllegalArgumentException("Carro inválido: " + carroId));
        
        // Criar nova reserva
        Reservas reserva = new Reservas();
        reserva.setCliente(cliente);
        reserva.setCarro(carro);
        reserva.setLocalRetirada(localRetirada);
        reserva.setDataRetirada(dataRetirada);
        reserva.setDataDevolu(dataDevolu);
        
        // Salvar a reserva
        reservasRepository.save(reserva);
        
        return new ModelAndView("redirect:/sucesso");
    }

    @GetMapping("/reservarCarro")
    public String showReservaForm(Model model) {
        List<Clientes> clientes = (List<Clientes>) clientesRepository.findAll();
        List<Carros> carros = (List<Carros>) carrosRepository.findAll();

        model.addAttribute("clientes", clientes);
        model.addAttribute("carros", carros);
        model.addAttribute("reserva", new Reservas()); // Adicionando um objeto reserva para o formulário

        return "reservas";
    }

    
}
