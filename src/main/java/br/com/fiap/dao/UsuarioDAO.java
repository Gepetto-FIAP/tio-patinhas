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
                    usuario = new PessoaFisica(
                            tipo, email, senha, pais, estado, cidade, "", rua, numero,
                            rs.getString("cpf"),
                            rs.getString("genero"),
                            rs.getInt("idade"),
                            rs.getString("nome"),
                            rs.getString("sobrenome")
                    );
                } else if ("PJ".equalsIgnoreCase(tipo)) {
                    usuario = new PessoaJuridica(
                            tipo, email, senha, pais, estado, cidade, "", rua, numero,
                            rs.getString("cnpj"),
                            rs.getString("ramo"),
                            rs.getString("nome_fantasia")
                    );
                }

                if (usuario != null) {

                    // Carteira
                    CarteiraDAO carteiraDAO = new CarteiraDAO();
                    Carteira carteira = carteiraDAO.buscarPorId(id);
                    usuario.setCarteira(carteira);

                    usuarios.add(usuario);
                }
            }
        }

        return usuarios;
    }



}
