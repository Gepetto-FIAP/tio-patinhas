package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();

        String sqlUsuarios = "SELECT * FROM T_USUARIO";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlUsuarios);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String tipo = rs.getString("tipo");

                // 1️⃣ Criar o usuário genérico com os campos comuns
                Usuario usuario = null;
                String email = rs.getString("email");
                String senha = rs.getString("senha");
                String pais = rs.getString("pais");
                String estado = rs.getString("estado");
                String cidade = rs.getString("cidade");
                String rua = rs.getString("rua");
                String numero = rs.getString("numero_imovel");


                if ("PF".equalsIgnoreCase(tipo)) {
                    String sqlPf = "SELECT * FROM T_PF WHERE id_usuario = ?";
                    try (PreparedStatement stmtPf = conn.prepareStatement(sqlPf)) {
                        stmtPf.setInt(1, id);
                        try (ResultSet rsPf = stmtPf.executeQuery()) {
                            if (rsPf.next()) {
                                usuario = new PessoaFisica(
                                        id,
                                        tipo, email, senha, pais, estado, cidade, "", rua, numero,
                                        rsPf.getString("cpf"),
                                        rsPf.getString("genero"),
                                        rsPf.getInt("idade"),
                                        rsPf.getString("nome"),
                                        rsPf.getString("sobrenome")
                                );
                            }
                        }
                    }
                } else if ("PJ".equalsIgnoreCase(tipo)) {
                    String sqlPj = "SELECT * FROM T_PJ WHERE id_usuario = ?";
                    try (PreparedStatement stmtPj = conn.prepareStatement(sqlPj)) {
                        stmtPj.setInt(1, id);
                        try (ResultSet rsPj = stmtPj.executeQuery()) {
                            if (rsPj.next()) {
                                usuario = new PessoaJuridica(
                                        id,
                                        tipo, email, senha, pais, estado, cidade, "", rua, numero,
                                        rsPj.getString("cnpj"),
                                        rsPj.getString("ramo"),
                                        rsPj.getString("nome_fantasia")
                                );
                            }
                        }
                    }
                }

                if (usuario != null) {
                    CarteiraDAO carteiraDAO = new CarteiraDAO();
                    Carteira carteira = carteiraDAO.buscarPorId(id);
                    usuario.setCarteira(carteira);
                    usuarios.add(usuario);
                }
            }
        }
        return usuarios;
    }


    public Usuario buscarPorId(int idUsuario) throws SQLException {
        Usuario usuario = null;

        String sqlUsuarios = "SELECT * FROM T_USUARIO WHERE id_usuario = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlUsuarios)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String tipo = rs.getString("tipo");

                    // Campos comuns
                    String email = rs.getString("email");
                    String senha = rs.getString("senha");
                    String pais = rs.getString("pais");
                    String estado = rs.getString("estado");
                    String cidade = rs.getString("cidade");
                    String rua = rs.getString("rua");
                    String numero = rs.getString("numero_imovel");

                    if ("PF".equalsIgnoreCase(tipo)) {
                        String sqlPf = "SELECT * FROM T_PF WHERE id_usuario = ?";
                        try (PreparedStatement stmtPf = conn.prepareStatement(sqlPf)) {
                            stmtPf.setInt(1, idUsuario);
                            try (ResultSet rsPf = stmtPf.executeQuery()) {
                                if (rsPf.next()) {
                                    usuario = new PessoaFisica(
                                            idUsuario,
                                            tipo, email, senha, pais, estado, cidade, "", rua, numero,
                                            rsPf.getString("cpf"),
                                            rsPf.getString("genero"),
                                            rsPf.getInt("idade"),
                                            rsPf.getString("nome"),
                                            rsPf.getString("sobrenome")
                                    );
                                }
                            }
                        }
                    } else if ("PJ".equalsIgnoreCase(tipo)) {
                        String sqlPj = "SELECT * FROM T_PJ WHERE id_usuario = ?";
                        try (PreparedStatement stmtPj = conn.prepareStatement(sqlPj)) {
                            stmtPj.setInt(1, idUsuario);
                            try (ResultSet rsPj = stmtPj.executeQuery()) {
                                if (rsPj.next()) {
                                    usuario = new PessoaJuridica(
                                            idUsuario,
                                            tipo, email, senha, pais, estado, cidade, "", rua, numero,
                                            rsPj.getString("cnpj"),
                                            rsPj.getString("ramo"),
                                            rsPj.getString("nome_fantasia")
                                    );
                                }
                            }
                        }
                    }

                    // Carregar a carteira se o usuário foi encontrado
                    if (usuario != null) {
                        CarteiraDAO carteiraDAO = new CarteiraDAO();
                        Carteira carteira = carteiraDAO.buscarPorId(idUsuario);
                        usuario.setCarteira(carteira);
                        carteira.setUsuario(usuario);
                    }
                }
            }
        }
        return usuario;
    }




}
