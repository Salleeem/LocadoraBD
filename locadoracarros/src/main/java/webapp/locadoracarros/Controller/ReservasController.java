package webapp.locadoracarros.Controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import webapp.locadoracarros.Model.Carros;
import webapp.locadoracarros.Model.Clientes;
import webapp.locadoracarros.Model.Reservas;
import webapp.locadoracarros.Repository.CarrosRepository;
import webapp.locadoracarros.Repository.ClientesRepository;
import webapp.locadoracarros.Repository.ReservasRepository;

@Controller
public class ReservasController {

    private final ReservasRepository reservasRepository;
    private final ClientesRepository clientesRepository;
    private final CarrosRepository carrosRepository;

    public ReservasController(ReservasRepository reservasRepository, ClientesRepository clientesRepository,
            CarrosRepository carrosRepository) {
        this.reservasRepository = reservasRepository;
        this.clientesRepository = clientesRepository;
        this.carrosRepository = carrosRepository;
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

    @PostMapping("/reservarCarro")
    public String cadastrarReserva(@RequestParam("cliente") Long clienteId, @RequestParam("carro") Long carroId,
            @RequestParam("localRetirada") String localRetirada,
            @RequestParam("dataRetirada") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataRetirada,
            @RequestParam("dataDevolu") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataDevolu) {
        
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
        reserva.setDataRetirada(Date.valueOf(dataRetirada));
        reserva.setDataDevolu(Date.valueOf(dataDevolu));

        // Salvar a reserva
        reservasRepository.save(reserva);

        return "redirect:/sucesso";
    }

    @GetMapping("/calcularReceita")
    public String calcularReceita(
            @RequestParam("dataInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
            @RequestParam("dataFim") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim,
            Model model) {

        if (dataInicio.isAfter(dataFim)) {
            model.addAttribute("mensagemErro", "A data de início não pode ser posterior à data de fim.");
            return "pesquisarreservas";
        }

        // Converter LocalDate para java.sql.Date
        Date dataInicioSql = Date.valueOf(dataInicio);
        Date dataFimSql = Date.valueOf(dataFim);

        // Buscar reservas dentro do período
        List<Reservas> reservas = reservasRepository.findByDataRetiradaBetween(dataInicioSql, dataFimSql);

        // Calcular a receita total com base no valor dos carros
        int receitaTotal = reservas.stream()
                .mapToInt(reserva -> reserva.getCarro().getValor())
                .sum();

        // Adicionar atributos ao modelo
        model.addAttribute("receitaTotal", receitaTotal);
        model.addAttribute("dataInicio", dataInicio);
        model.addAttribute("dataFim", dataFim);

        return "pesquisarreservas";
    }

    @GetMapping("/limparPesquisa")
    public String limparPesquisa() {
        // Redirecionar para a página de pesquisa sem parâmetros
        return "redirect:/pesquisarreservas";
    }
}
