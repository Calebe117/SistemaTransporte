package br.edu.ifpb.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.academico.entity.Role;
import br.edu.ifpb.academico.entity.Usuario;
import br.edu.ifpb.academico.service.RoleService;
import br.edu.ifpb.academico.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/form")
    public String home(Model model) {
        roles(model);
        model.addAttribute("usuario", new Usuario());
        return "cadastrarUsuario";
    }

    private void roles(Model model) {
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
    }

    @PostMapping("/save")
    public String saveUsuario(@ModelAttribute Usuario usuario, Model model) {
        if (usuarioService.findAll().stream().anyMatch(u -> u.getLogin().equals(usuario.getLogin()))) {
            model.addAttribute("mensagemErro", "Usuário com login " + usuario.getLogin() + " já cadastrado.");
            roles(model);
            return "cadastrarUsuario";
        } else {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            usuarioService.save(usuario);
        }
        model.addAttribute("mensagemSucesso", "Usuário com login " + usuario.getLogin() + " cadastrado com sucesso!");
        roles(model);
        return "cadastrarUsuario";
    }

    @GetMapping("/edit/{id}")
    public String editUsuario(@PathVariable Long id, Model model) {
        roles(model);
        Usuario usuario = usuarioService.findById(id);
        model.addAttribute("usuario", usuario);
        return "editarUsuario";
    }

    @PostMapping("/edit")
    public String editUsuarioPost(@ModelAttribute Usuario usuario, Model model) {
        Usuario usuarioExistente = usuarioService.findById(usuario.getId());

        if (!usuarioExistente.getLogin().equals(usuario.getLogin())
                && usuarioService.findAll().stream().anyMatch(u -> u.getLogin().equals(usuario.getLogin()))) {
            model.addAttribute("mensagemErro", "Usuário com login " + usuario.getLogin() + " já existe.");
            return listUsuarios(model);
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioService.save(usuario);

        model.addAttribute("mensagemSucesso", "Usuário com login " + usuario.getLogin() + " atualizado com sucesso!");
        return listUsuarios(model);
    }

    @GetMapping("/list")
    public String listUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "listarUsuario";
    }

    @GetMapping("/delete/{id}")
    public String deleteUsuario(@PathVariable Long id, Model model) {
        usuarioService.deleteById(id);
        model.addAttribute("mensagemSucesso", "Usuário deletado com sucesso!");
        return listUsuarios(model);
    }
}
