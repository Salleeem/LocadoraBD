package webapp.locadoracarros.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import webapp.locadoracarros.Model.Clientes;
import webapp.locadoracarros.Repository.ClientesRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClientesController {

    private final ClientesRepository clientesRepository;

    public ClientesController(ClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    @PostMapping("/cadastrarClientes")
    public ModelAndView cadastrarCliente(@ModelAttribute Clientes cliente) {
        clientesRepository.save(cliente); // Should work now!
        return new ModelAndView("redirect:/sucesso");
    }
    
}