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
                String bairro = rs.getString("bairro");
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
                                        tipo, email, senha, pais, estado, cidade, bairro, rua, numero,
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
                                        tipo, email, senha, pais, estado, cidade, bairro, rua, numero,
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
                    String bairro = rs.getString("bairro");
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
                                            tipo, email, senha, pais, estado, cidade, bairro, rua, numero,
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
                                            tipo, email, senha, pais, estado, cidade, bairro, rua, numero,
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

    public int inserir(Usuario usuario) throws SQLException {
        int userId = -1;

        try (Connection conn = ConnectionFactory.getConnection()) {

            // 🔹 Inserir usuário sem passar ID diretamente
            String sqlUsuario = "INSERT INTO T_USUARIO " +
                    "(id_usuario, tipo, email, senha, pais, estado, cidade, bairro, rua, numero_imovel) " +
                    "VALUES (SEQ_USUARIO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, usuario.tipo);
                stmt.setString(2, usuario.email);
                stmt.setString(3, usuario.senha);
                stmt.setString(4, usuario.pais);
                stmt.setString(5, usuario.estado);
                stmt.setString(6, usuario.cidade);
                stmt.setString(7, usuario.bairro);
                stmt.setString(8, usuario.rua);
                stmt.setString(9, usuario.numero);

                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    // 🔹 Recuperar o ID gerado pela sequence
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            userId = generatedKeys.getInt(1);
                            usuario.id = userId;
                        }
                    }

                    // 🔹 Inserir dados específicos PF/PJ
                    if (usuario instanceof PessoaFisica pf) {
                        inserirPessoaFisica(pf);
                    } else if (usuario instanceof PessoaJuridica pj) {
                        inserirPessoaJuridica(pj);
                    }

                    // 🔹 Criar carteira
                    CarteiraDAO carteiraDAO = new CarteiraDAO();
                    Carteira carteira = new Carteira(usuario);
                    usuario.setCarteira(carteira);
                    carteiraDAO.inserir(carteira);
                }
            }
        }

        return userId;
    }


    private void inserirPessoaFisica(PessoaFisica pf) throws SQLException {
        String sqlPf = "INSERT INTO T_PF (id_usuario, cpf, genero, idade, nome, sobrenome) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlPf)) {

            stmt.setInt(1, pf.getId());
            stmt.setString(2, pf.getCpf());
            stmt.setString(3, pf.getGenero());
            stmt.setInt(4, pf.getIdade());
            stmt.setString(5, pf.getNome());
            stmt.setString(6, pf.getSobrenome());

            stmt.executeUpdate();
        }
    }
    
    private void inserirPessoaJuridica(PessoaJuridica pj) throws SQLException {
        String sqlPj = "INSERT INTO T_PJ (id_usuario, cnpj, ramo, nome_fantasia) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlPj)) {
            
            stmt.setInt(1, pj.getId());
            stmt.setString(2, pj.getCnpj());
            stmt.setString(3, pj.getRamo());
            stmt.setString(4, pj.getNome());
            
            stmt.executeUpdate();
        }
    }

    public boolean atualizar(Usuario usuario) throws SQLException {
        String sqlUsuario = "UPDATE T_USUARIO SET tipo = ?, email = ?, senha = ?, pais = ?, estado = ?, cidade = ?, rua = ?, numero_imovel = ? WHERE id_usuario = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlUsuario)) {
            
            stmt.setString(1, usuario.tipo);
            stmt.setString(2, usuario.email);
            stmt.setString(3, usuario.senha);
            stmt.setString(4, usuario.pais);
            stmt.setString(5, usuario.estado);
            stmt.setString(6, usuario.cidade);
            stmt.setString(7, usuario.rua);
            stmt.setString(8, usuario.numero);
            stmt.setInt(9, usuario.getId());
            
            int rowsUpdated = stmt.executeUpdate();
            
            if (rowsUpdated > 0) {
                if (usuario instanceof PessoaFisica) {
                    return atualizarPessoaFisica((PessoaFisica) usuario);
                } else if (usuario instanceof PessoaJuridica) {
                    return atualizarPessoaJuridica((PessoaJuridica) usuario);
                }
                return true;
            }
        }
        return false;
    }
    
    private boolean atualizarPessoaFisica(PessoaFisica pf) throws SQLException {
        String sqlPf = "UPDATE T_PF SET cpf = ?, genero = ?, idade = ?, nome = ?, sobrenome = ? WHERE id_usuario = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlPf)) {
            
            stmt.setString(1, pf.getCpf());
            stmt.setString(2, pf.getGenero());
            stmt.setInt(3, pf.getIdade());
            stmt.setString(4, pf.getNome());
            stmt.setString(5, pf.getSobrenome());
            stmt.setInt(6, pf.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    private boolean atualizarPessoaJuridica(PessoaJuridica pj) throws SQLException {
        String sqlPj = "UPDATE T_PJ SET cnpj = ?, ramo = ?, nome_fantasia = ? WHERE id_usuario = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlPj)) {
            
            stmt.setString(1, pj.getCnpj());
            stmt.setString(2, pj.getRamo());
            stmt.setString(3, pj.getNome());
            stmt.setInt(4, pj.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deletar(int idUsuario) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            
            try {
                String sqlDeletePf = "DELETE FROM T_PF WHERE id_usuario = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sqlDeletePf)) {
                    stmt.setInt(1, idUsuario);
                    stmt.executeUpdate();
                }
                
                String sqlDeletePj = "DELETE FROM T_PJ WHERE id_usuario = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sqlDeletePj)) {
                    stmt.setInt(1, idUsuario);
                    stmt.executeUpdate();
                }
                
                CarteiraDAO carteiraDAO = new CarteiraDAO();
                carteiraDAO.deletar(idUsuario);
                
                String sqlDeleteUsuario = "DELETE FROM T_USUARIO WHERE id_usuario = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sqlDeleteUsuario)) {
                    stmt.setInt(1, idUsuario);
                    int rowsDeleted = stmt.executeUpdate();
                    
                    if (rowsDeleted > 0) {
                        conn.commit();
                        return true;
                    }
                }
                
                conn.rollback();
                return false;
                
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}
