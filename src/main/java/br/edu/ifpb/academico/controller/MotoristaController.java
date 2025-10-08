package br.edu.ifpb.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.academico.service.MotoristaService;
import br.edu.ifpb.entity.Motorista;

@Controller
@RequestMapping("/motorista")
public class MotoristaController {

    @Autowired
    protected MotoristaService motoristaService;

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("motorista", new Motorista());
        return "cadastrarMotorista";
    }

    @PostMapping("/save")
    public String saveMotorista(@ModelAttribute Motorista motorista, Model model) {

        if (motoristaService.existsByEmail(motorista.getEmail())) {
            model.addAttribute("mensagemErro", "Já existe um motorista com o email " + motorista.getEmail());
            return "cadastrarMotorista";
        }

        if (motorista.getCnh() != null && motoristaService.existsByCnhNumero(motorista.getCnh().getNumeroCnh())) {
            model.addAttribute("mensagemErro", "Já existe um motorista com a CNH número " + motorista.getCnh().getNumeroCnh());
            return "cadastrarMotorista";
        }

        motoristaService.save(motorista);
        model.addAttribute("mensagemSucesso", "Motorista " + motorista.getNome() + " salvo com sucesso");
        return form(model);
    }

    @GetMapping("/delete/{id}")
    public String deleteMotorista(@PathVariable Long id, Model model) {
        try {
            Motorista motorista = motoristaService.findById(id);
            motoristaService.deleteById(id);
            model.addAttribute("motoristas", motoristaService.list());
            model.addAttribute("mensagemSucesso", "Motorista " + motorista.getNome() + " removido com sucesso");
            return "listarMotorista";
        } catch (RuntimeException e) {
            model.addAttribute("mensagemErro", e.getMessage());
            model.addAttribute("motoristas", motoristaService.list());
            return "listarMotorista";
        }
    }

    @GetMapping("/list")
    public String listarMotoristas(Model model) {
        model.addAttribute("motoristas", motoristaService.list());
        return "listarMotorista";
    }

    @GetMapping("/edit/{id}")
    public String editMotorista(@PathVariable Long id, Model model) {
        Motorista motorista = motoristaService.findById(id);
        model.addAttribute("motorista", motorista);
        return "editarMotorista";
    }

    @PostMapping("/update")
    public String updateMotorista(@ModelAttribute Motorista motorista, Model model) {
        Motorista existente = motoristaService.findById(motorista.getId());

        if (!existente.getEmail().equals(motorista.getEmail()) && motoristaService.existsByEmail(motorista.getEmail())) {
            model.addAttribute("mensagemErro", "Já existe um motorista com o email " + motorista.getEmail());
            return "editarMotorista";
        }

        if (motorista.getCnh() != null 
                && existente.getCnh() != null 
                && !existente.getCnh().getNumeroCnh().equals(motorista.getCnh().getNumeroCnh()) 
                && motoristaService.existsByCnhNumero(motorista.getCnh().getNumeroCnh())) {
            model.addAttribute("mensagemErro", "Já existe um motorista com a CNH número " + motorista.getCnh().getNumeroCnh());
            return "editarMotorista";
        }

        if (existente.getCnh() != null) {
            motorista.setCnh(existente.getCnh());
        }

        motoristaService.save(motorista);
        model.addAttribute("mensagemSucesso", "Cadastro do motorista " + motorista.getNome() + " atualizado com sucesso");
        model.addAttribute("motoristas", motoristaService.list());
        return "listarMotorista";
    }

}
