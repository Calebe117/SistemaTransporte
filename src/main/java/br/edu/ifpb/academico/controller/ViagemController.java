package br.edu.ifpb.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.academico.service.MotoristaService;
import br.edu.ifpb.academico.service.ViagemService;
import br.edu.ifpb.entity.Motorista;
import br.edu.ifpb.entity.Viagem;

@Controller
@RequestMapping("/viagem")
public class ViagemController {

    @Autowired
    private ViagemService viagemService;

    @Autowired
    private MotoristaService motoristaService;

    private void motoristas(Model model) {
        List<Motorista> lista = motoristaService.list();
        model.addAttribute("motoristas", lista);
    }

    @GetMapping("/form")
    public String formViagem(Model model) {
        model.addAttribute("viagem", new Viagem());
        motoristas(model);
        return "cadastrarViagem";
    }

    @PostMapping("/save")
    public String saveViagem(@ModelAttribute Viagem viagem, Model model) {
        viagemService.save(viagem);
        model.addAttribute("mensagemSucesso", "Viagem salva com sucesso");
        motoristas(model);
        return "cadastrarViagem";
    }

    @GetMapping("/list")
    public String listarViagens(Model model) {
        model.addAttribute("viagens", viagemService.list());
        return "listarViagem";
    }

    @GetMapping("/delete/{id}")
    public String deleteViagem(@PathVariable Long id, Model model) {
        try {
            Viagem viagem = viagemService.findById(id);
            viagemService.deleteById(id);
            model.addAttribute("viagens", viagemService.list());
            model.addAttribute("mensagemSucesso", "Viagem removida com sucesso");
        } catch (RuntimeException e) {
            model.addAttribute("mensagemErro", e.getMessage());
            model.addAttribute("viagens", viagemService.list());
        }
        return "listarViagem";
    }

    @GetMapping("/edit/{id}")
    public String editarViagem(@PathVariable Long id, Model model) {
        Viagem viagem = viagemService.findById(id);
        model.addAttribute("viagem", viagem);
        motoristas(model);
        return "editarViagem";
    }

    @PostMapping("/update")
    public String updateViagem(@ModelAttribute Viagem viagem, Model model) {
        viagemService.save(viagem);
        model.addAttribute("mensagemSucesso", "Viagem atualizada com sucesso");
        model.addAttribute("viagens", viagemService.list());
        return "listarViagem";
    }
}
