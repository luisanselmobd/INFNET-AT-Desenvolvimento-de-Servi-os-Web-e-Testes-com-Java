package service;

import dto.UsuarioDTOInput;
import dto.UsuarioDTOOutput;
import model.Usuario;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    public List<Usuario> listaUsuarios;
    private ModelMapper modelMapper = new ModelMapper();

    // Construtor para inicializar a lista
    public UsuarioService() {
        this.listaUsuarios = new ArrayList<>();
    }

    public List<UsuarioDTOOutput> listar() {
        List<UsuarioDTOOutput> lista = new ArrayList<>();
        for (Usuario usuario : this.listaUsuarios) {
            lista.add(modelMapper.map(usuario, UsuarioDTOOutput.class));
        }
        return lista;
    }

    public void inserir(UsuarioDTOInput novoUsuario) {
        Usuario usuario = modelMapper.map(novoUsuario, Usuario.class);
        listaUsuarios.add(usuario);
    }

    public void alterar(UsuarioDTOInput novoUsuario) {
        Usuario usuario = modelMapper.map(novoUsuario, Usuario.class);
        listaUsuarios.set(usuario.getId(), usuario);
    }

    public UsuarioDTOOutput buscar(int id) {
        UsuarioDTOOutput usuario = modelMapper.map(listaUsuarios.get(id), UsuarioDTOOutput.class);
        return usuario;
    }

    public void excluir(int id) {
        listaUsuarios.remove(id);
    }
}
