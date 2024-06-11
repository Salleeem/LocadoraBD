package webapp.locadoracarros.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webapp.locadoracarros.Repository.ReservasRepository;

@Controller
public class PesquisaController {
    @Autowired
    private ReservasRepository reservasRepository;

    @GetMapping("/pesquisarreservas")
    public String mostrarFormularioPesquisa() {
        return "pesquisarreservas";
    }

    @PostMapping("/pesquisarreservas")
    public String pesquisarReservas(@RequestParam(name = "cpf", required = false) String cpf,
                                    @RequestParam(name = "modelo", required = false) String modelo,
                                    Model model) {
        if (cpf != null && !cpf.isEmpty()) {
            model.addAttribute("reservasPorCpf", reservasRepository.findByClienteCpf(cpf));
            model.addAttribute("cpfPesquisado", cpf);
        }
        if (modelo != null && !modelo.isEmpty()) {
            model.addAttribute("reservasPorModelo", reservasRepository.findByCarroModelo(modelo));
            model.addAttribute("modeloPesquisado", modelo);
        }
        return "pesquisarreservas";
    }
}
