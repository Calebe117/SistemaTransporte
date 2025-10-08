package br.edu.ifpb.academico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.academico.service.CnhService;
import br.edu.ifpb.entity.Cnh;

@Controller
@RequestMapping("/cnh")
public class CnhController {

    @Autowired
    protected CnhService cnhService;

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("cnh", new Cnh());
        return "cadastrarCnh"; 
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Cnh cnh, Model model) {
        if (cnhService.existsByNumeroCnh(cnh.getNumeroCnh())) {
            model.addAttribute("mensagemErro", "Já existe uma CNH com número " + cnh.getNumeroCnh());
            return "cadastrarCnh";
        }
        cnhService.save(cnh);
        model.addAttribute("mensagemSucesso", "CNH salva com sucesso");
        return list(model);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        Cnh cnh = cnhService.findById(id);
        cnhService.deleteById(id);
        model.addAttribute("mensagemSucesso", "CNH removida com sucesso");
        return list(model);
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("cnhs", cnhService.list());
        return "listarCnh";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Cnh cnh = cnhService.findById(id);
        model.addAttribute("cnh", cnh);
        return "editarCnh";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Cnh cnh, Model model) {
        Cnh existente = cnhService.findById(cnh.getId());
        if (!existente.getNumeroCnh().equals(cnh.getNumeroCnh()) && cnhService.existsByNumeroCnh(cnh.getNumeroCnh())) {
            model.addAttribute("mensagemErro", "Já existe uma CNH com número " + cnh.getNumeroCnh());
            return "editarCnh";
        }
        cnhService.save(cnh);
        model.addAttribute("mensagemSucesso", "CNH atualizada com sucesso");
        return list(model);
    }

}
