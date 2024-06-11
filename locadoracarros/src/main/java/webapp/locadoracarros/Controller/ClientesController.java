package webapp.locadoracarros.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import webapp.locadoracarros.Model.Clientes;
import webapp.locadoracarros.Repository.ClientesRepository;

@Controller
public class ClientesController {

    private final ClientesRepository clientesRepository;

    public ClientesController(ClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    // Cadastrar Cliente
    @PostMapping("/cadastrarClientes")
    public ModelAndView cadastrarCliente(@ModelAttribute Clientes cliente) {
        clientesRepository.save(cliente);
        return new ModelAndView("redirect:/sucessocli");
    }

    // Listar Clientes
    @GetMapping("/listarclientes")
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clientesRepository.findAll());
        return "listarclientes";
    }

    // Editar Cliente
    @GetMapping("/editarcliente/{idCliente}")
    public String editarCliente(@PathVariable Long idCliente, Model model) {
        Clientes cliente = clientesRepository.findById(idCliente).orElseThrow();
        model.addAttribute("cliente", cliente);
        return "editarcliente";
    }

    @PostMapping("/editarcliente/{idCliente}")
    public String editarClientePost(@PathVariable Long idCliente, @ModelAttribute Clientes cliente) {
        Clientes clienteToUpdate = clientesRepository.findById(idCliente).orElseThrow();
        clienteToUpdate.setNome(cliente.getNome());
        clienteToUpdate.setCpf(cliente.getCpf());
        clienteToUpdate.setEmail(cliente.getEmail());
        clienteToUpdate.setEndereco(cliente.getEndereco());
        clienteToUpdate.setTelefone(cliente.getTelefone());
        clienteToUpdate.setDataNas(cliente.getDataNas());
        clientesRepository.save(clienteToUpdate);
        return "redirect:/listarclientes";
    }

    // Deletar Cliente
    @GetMapping("/deletarcliente/{idCliente}")
    public String deletarCliente(@PathVariable Long idCliente) {
        clientesRepository.deleteById(idCliente);
        return "redirect:/listarclientes";
    }
}
