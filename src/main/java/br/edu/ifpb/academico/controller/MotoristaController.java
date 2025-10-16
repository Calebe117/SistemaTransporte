package br.edu.ifpb.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import br.edu.ifpb.academico.service.MotoristaService;
import br.edu.ifpb.entity.Motorista;

@Controller
@RequestMapping("/motorista")
public class MotoristaController {

    @Autowired
    private MotoristaService motoristaService;

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("motorista", new Motorista());
        return "cadastrarMotorista";
    }

    @PostMapping("/save")
    public String saveMotorista(@ModelAttribute Motorista motorista, Model model) {

        // Verifica se já existe email
        if (motoristaService.existsByEmail(motorista.getEmail())) {
            model.addAttribute("mensagemErro", "Já existe um motorista com o email " + motorista.getEmail());
            return "cadastrarMotorista";
        }

        // Verifica se já existe CNH
        if (motorista.getCnh() != null && motoristaService.existsByCnhNumero(motorista.getCnh().getNumeroCnh())) {
            model.addAttribute("mensagemErro", "Já existe um motorista com a CNH número " + motorista.getCnh().getNumeroCnh());
            return "cadastrarMotorista";
        }

        // 🔎 Valida idade mínima de 18 anos
        if (motorista.getDataNascimento() != null) {
            long idadeEmMilissegundos = new java.util.Date().getTime() - motorista.getDataNascimento().getTime();
            long anos = idadeEmMilissegundos / (1000L * 60 * 60 * 24 * 365);
            if (anos < 18) {
                model.addAttribute("mensagemErro", "O motorista deve ter pelo menos 18 anos.");
                return "cadastrarMotorista";
            }
        }

        motoristaService.save(motorista);
        model.addAttribute("mensagemSucesso", "Motorista salvo com sucesso!");
        model.addAttribute("motoristas", motoristaService.list());
        return "listarMotorista";
    }


    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("motoristas", motoristaService.list());
        return "listarMotorista";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("motorista", motoristaService.findById(id));
        return "editarMotorista";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Motorista motorista, Model model) {
        Motorista existente = motoristaService.findById(motorista.getId());

        if (!existente.getEmail().equals(motorista.getEmail()) && motoristaService.existsByEmail(motorista.getEmail())) {
            model.addAttribute("mensagemErro", "Já existe um motorista com o email " + motorista.getEmail());
            return "editarMotorista";
        }

        if (motorista.getCnh() != null && existente.getCnh() != null &&
            !existente.getCnh().getNumeroCnh().equals(motorista.getCnh().getNumeroCnh()) &&
            motoristaService.existsByCnhNumero(motorista.getCnh().getNumeroCnh())) {
            model.addAttribute("mensagemErro", "Já existe um motorista com a CNH número " + motorista.getCnh().getNumeroCnh());
            return "editarMotorista";
        }

        motoristaService.save(motorista);
        model.addAttribute("mensagemSucesso", "Motorista atualizado com sucesso!");
        model.addAttribute("motoristas", motoristaService.list());
        return "listarMotorista";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        motoristaService.deleteById(id);
        model.addAttribute("mensagemSucesso", "Motorista removido com sucesso!");
        model.addAttribute("motoristas", motoristaService.list());
        return "listarMotorista";
    }
}
