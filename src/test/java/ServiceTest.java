import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UsuarioDTOInput;
import model.Usuario;
import org.junit.Test;
import service.UsuarioService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class ServiceTest {
    @Test
    public void testeInsercao() {
        UsuarioService usuarioService = new UsuarioService();
        UsuarioDTOInput usuario = new UsuarioDTOInput();
        usuario.setNome("Menino Ney");
        usuario.setSenha("brunameuamor");
        usuarioService.inserir(usuario);
        assertEquals(1,usuarioService.listar().size());
    }

    @Test
    public void testeListagem() throws IOException {
        URL url = new URL("http://127.0.0.1:4567/usuarios");
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");
        int codigoResposta = conexao.getResponseCode();
        assertEquals(200,codigoResposta);
    }

    @Test
    public void testeInsercaoAPI() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = retornarJson("https://randomuser.me/api/");

        String nome = jsonNode.get("results").get(0).get("name").get("first").asText();
        String sobrenome = jsonNode.get("results").get(0).get("name").get("last").asText();
        String senha = jsonNode.get("results").get(0).get("login").get("password").asText();
        Usuario usuario = new Usuario();
        usuario.setNome(nome + " " + sobrenome);
        usuario.setSenha(senha);

        URL urlUsuarios = new URL("http://127.0.0.1:4567/usuarios");
        HttpURLConnection conexaoUsuarios = (HttpURLConnection) urlUsuarios.openConnection();
        conexaoUsuarios.setRequestMethod("POST");
        conexaoUsuarios.setDoOutput(true);

        try (DataOutputStream dataOutputStream = new DataOutputStream(conexaoUsuarios.getOutputStream())) {
            String body = objectMapper.writeValueAsString(usuario);
            dataOutputStream.writeBytes(body);
            dataOutputStream.flush();
        }

        int codigoResposta = conexaoUsuarios.getResponseCode();
        assertEquals(201,codigoResposta);
    }

    private JsonNode retornarJson(String urlParametro) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        URL url = new URL(urlParametro);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        String jsonString = reader.readLine();
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        return jsonNode;

    }

}
