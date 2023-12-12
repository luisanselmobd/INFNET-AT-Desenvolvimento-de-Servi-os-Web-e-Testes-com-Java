package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UsuarioDTOInput;
import service.UsuarioService;

import static spark.Spark.*;

public class UsuarioController {
    private UsuarioService usuarioService = new UsuarioService();
    private ObjectMapper objectMapper = new ObjectMapper();
    public void respostasRequisicoes() {
        get("/usuarios", (request, response) -> {
            response.type("application/json");
            response.status(200);
            String json = objectMapper.writeValueAsString(usuarioService.listar());
            return json;
        });

        get("/usuarios/:id", (request, response) -> {
            response.type("application/json");
            String idJson = request.params("id");
            int id = Integer.parseInt(idJson);
            String json = objectMapper.writeValueAsString(usuarioService.buscar(id));
            response.status(200);
            return json;
        });

        post("/usuarios", (request, response) -> {
            UsuarioDTOInput usuario = objectMapper.readValue(request.body(), UsuarioDTOInput.class);
            usuarioService.inserir(usuario);
            response.type("application/json");
            response.status(201);
            return "Usuário inserido com sucesso.";
        });

        put("/usuarios", (request, response) -> {
            UsuarioDTOInput usuario = objectMapper.readValue(request.body(), UsuarioDTOInput.class);
            usuarioService.alterar(usuario);
            response.type("application/json");
            response.status(200);
            return "Usuário alterado com sucesso.";
        });

        delete("/usuarios/:id", (request, response) -> {
            response.type("application/json");
            String idJson = request.params("id");
            int id = Integer.parseInt(idJson);
            usuarioService.excluir(id);
            response.status(200);
            return "Usuário excluido com sucesso.";
        });
    }
}
