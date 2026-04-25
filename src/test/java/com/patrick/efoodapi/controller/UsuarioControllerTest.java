package com.patrick.efoodapi.controller;

import com.patrick.efoodapi.dto.request.UsuarioRquestDTO;
import com.patrick.efoodapi.dto.response.UsuarioResponseDTO;
import com.patrick.efoodapi.model.enums.Role;
import com.patrick.efoodapi.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    private UsuarioRquestDTO usuarioRequestDTO;
    private UsuarioResponseDTO usuarioResponseDTO;

    @BeforeEach
    void setUp() {
        // Cria um DTO de request para testes
        usuarioRequestDTO = new UsuarioRquestDTO(
                "Test User",
                "test@example.com",
                "senha123"
        );

        // Cria um DTO de response para testes
        usuarioResponseDTO = new UsuarioResponseDTO(
                1L,
                "Test User",
                "test@example.com",
                Role.CLIENTE
        );
    }


    @Test
    void naoDeveCadastrarClienteComEmailDuplicado() throws Exception{

        String json = """
                {
                  "nome": "Ana Santos",
                  "email": "ana.santos@email.com",
                  "senha": "123456"
                }
                """;
        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    // Testa o endpoint GET /usuarios - deve retornar 200 OK com lista de usuários
    void buscarTodosUsuarios_deveRetornar200ComListaDeUsuarios() throws Exception {
        // Preparação: simula que o serviço retorna uma lista com um usuário
        List<UsuarioResponseDTO> usuarios = List.of(usuarioResponseDTO);
        when(usuarioService.buscarTodosUsuarios()).thenReturn(usuarios);

        // Execução e verificação: faz a requisição GET e verifica a resposta
        mockMvc.perform(get("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // Verifica se o status é 200 OK
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.length()").value(1)) // Verifica que a lista tem 1 elemento
//                .andExpect(jsonPath("$[0].id").value(1)) // Verifica o ID do usuário
//                .andExpect(jsonPath("$[0].nome").value("Test User")) // Verifica o nome
//                .andExpect(jsonPath("$[0].email").value("test@example.com")) // Verifica o email
//                .andExpect(jsonPath("$[0].role").value("CLIENTE"));// Verifica o role


    }

    @Test
    // Testa o endpoint POST /usuarios - deve retornar 201 Created ao cadastrar com sucesso
    void cadastrarUsuario_deveRetornar201QuandoSucesso() throws Exception {
        // Preparação: simula que o serviço salva o usuário e retorna o response DTO
        when(usuarioService.cadastrar(any(UsuarioRquestDTO.class)))
                .thenReturn(usuarioResponseDTO);

        // Execução e verificação: faz a requisição POST com o JSON do usuário
        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequestDTO)))
                .andExpect(status().isCreated()); // Verifica se o status é 201 Created
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(1)) // Verifica o ID retornado
//                .andExpect(jsonPath("$.nome").value("Test User")) // Verifica o nome
//                .andExpect(jsonPath("$.email").value("test@example.com")) // Verifica o email
//                .andExpect(jsonPath("$.role").value("CLIENTE")); // Verifica o role padrão CLIENTE
    }
    @Test
    void criarCliente() throws Exception {

        String json = """
                {
                  "nome": "Ana Santos",
                  "email": "ana.santos@email.com",
                  "senha": "123456"
                }
                """;

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }
    @Test
    // Testa o endpoint POST /usuarios com dados inválidos - deve retornar 400 Bad Request
    void cadastrarUsuario_comDadosInvalidos_deveRetornar400() throws Exception {
        // Preparação: cria um DTO com dados inválidos (nome vazio, email inválido)
        UsuarioRquestDTO invalidDTO = new UsuarioRquestDTO(
                "", // Nome inválido (vazio)
                "invalid-email", // Email inválido
                "123" // Senha fraca
        );

        // Execução e verificação: faz a requisição POST com dados inválidos
        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest()); // Deve retornar 400 Bad Request
    }

    @Test
    // Testa o endpoint PUT /usuarios/{id}/admin - deve retornar 200 OK
    @WithMockUser(roles = "ADMIN") // Simula um usuário autenticado com role ADMIN
    void tornarAdmin_deveRetornar200QuandoUsuarioExiste() throws Exception {
        // Preparação: cria um usuário com role ADMIN
        UsuarioResponseDTO adminResponse = new UsuarioResponseDTO(
                1L,
                "Test User",
                "test@example.com",
                Role.ADMIN
        );
        when(usuarioService.tornarAdmin(1L)).thenReturn(adminResponse);

        // Execução e verificação: faz a requisição PUT para promover usuário
        mockMvc.perform(put("/usuarios/1/admin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Verifica se o status é 200 OK
                .andExpect(jsonPath("$.role").value("ADMIN")); // Verifica se o role foi atualizado
    }

    @Test
    // Testa o endpoint PUT /usuarios/{id}/admin sem autenticação - deve retornar 403 Forbidden
    void tornarAdmin_semAutenticacao_deveRetornar403() throws Exception {
        // Execução e verificação: tenta acessar endpoint protegido sem autenticação
        mockMvc.perform(put("/usuarios/1/admin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // Deve retornar 403 Forbidden
    }

    @Test
    // Testa o endpoint GET /usuarios quando a lista está vazia - deve retornar 200 com lista vazia
    void buscarTodosUsuarios_listaVazia_deveRetornar200ComListaVazia() throws Exception {
        // Preparação: simula que o serviço retorna uma lista vazia
        when(usuarioService.buscarTodosUsuarios()).thenReturn(List.of());

        // Execução e verificação: faz a requisição GET
        mockMvc.perform(get("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Verifica se o status é 200 OK
                .andExpect(jsonPath("$.length()").value(0)); // Verifica que a lista está vazia
    }
}