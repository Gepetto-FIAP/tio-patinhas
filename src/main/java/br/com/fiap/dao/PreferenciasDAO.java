package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Preferencias;
import br.com.fiap.model.Usuario;

public class PreferenciasDAO {

    public int inserir(Preferencias preferencias) throws SQLException {
        String sql = "INSERT INTO T_PREFERENCIAS (id_preferencias, id_usuario, tema, idioma, receber_notificacoes) VALUES (SEQ_PREFERENCIAS.NEXTVAL, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"id_preferencias"})) {

            stmt.setInt(1, preferencias.getUsuario().getId());
            stmt.setString(2, preferencias.getTema());
            stmt.setString(3, preferencias.getIdioma());
            stmt.setInt(4, preferencias.isReceberNotificacoes() ? 1 : 0);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        System.out.println("[Success] Preferências inseridas com ID: " + id);
                        preferencias.setId(id);
                        return id;
                    }
                }
            }
        }
        return -1;
    }

    public Preferencias buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM T_PREFERENCIAS WHERE id_preferencias = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarPreferenciasFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public Preferencias buscarPorUsuario(int idUsuario) throws SQLException {
        String sql = "SELECT * FROM T_PREFERENCIAS WHERE id_usuario = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarPreferenciasFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Preferencias> listarTodas() throws SQLException {
        List<Preferencias> preferencias = new ArrayList<>();
        String sql = "SELECT * FROM T_PREFERENCIAS ORDER BY id_preferencias";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                preferencias.add(criarPreferenciasFromResultSet(rs));
            }
        }
        return preferencias;
    }

    public List<Preferencias> buscarPorTema(String tema) throws SQLException {
        List<Preferencias> preferencias = new ArrayList<>();
        String sql = "SELECT * FROM T_PREFERENCIAS WHERE tema = ? ORDER BY id_preferencias";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tema);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    preferencias.add(criarPreferenciasFromResultSet(rs));
                }
            }
        }
        return preferencias;
    }

    public List<Preferencias> buscarPorIdioma(String idioma) throws SQLException {
        List<Preferencias> preferencias = new ArrayList<>();
        String sql = "SELECT * FROM T_PREFERENCIAS WHERE idioma = ? ORDER BY id_preferencias";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idioma);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    preferencias.add(criarPreferenciasFromResultSet(rs));
                }
            }
        }
        return preferencias;
    }

    public List<Preferencias> buscarPorNotificacoes(boolean receberNotificacoes) throws SQLException {
        List<Preferencias> preferencias = new ArrayList<>();
        String sql = "SELECT * FROM T_PREFERENCIAS WHERE receber_notificacoes = ? ORDER BY id_preferencias";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, receberNotificacoes ? 1 : 0);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    preferencias.add(criarPreferenciasFromResultSet(rs));
                }
            }
        }
        return preferencias;
    }

    public boolean atualizar(Preferencias preferencias) throws SQLException {
        String sql = "UPDATE T_PREFERENCIAS SET tema = ?, idioma = ?, receber_notificacoes = ? WHERE id_preferencias = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, preferencias.getTema());
            stmt.setString(2, preferencias.getIdioma());
            stmt.setInt(3, preferencias.isReceberNotificacoes() ? 1 : 0);
            stmt.setInt(4, preferencias.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Preferências atualizadas com ID: " + preferencias.getId());
                return true;
            } else {
                System.out.println("[Warning] Nenhuma preferência encontrada com ID: " + preferencias.getId());
                return false;
            }
        }
    }

    public boolean atualizarTema(int id, String novoTema) throws SQLException {
        String sql = "UPDATE T_PREFERENCIAS SET tema = ? WHERE id_preferencias = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoTema);
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Tema atualizado para: " + novoTema);
                return true;
            } else {
                System.out.println("[Warning] Nenhuma preferência encontrada com ID: " + id);
                return false;
            }
        }
    }

    public boolean atualizarIdioma(int id, String novoIdioma) throws SQLException {
        String sql = "UPDATE T_PREFERENCIAS SET idioma = ? WHERE id_preferencias = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoIdioma);
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Idioma atualizado para: " + novoIdioma);
                return true;
            } else {
                System.out.println("[Warning] Nenhuma preferência encontrada com ID: " + id);
                return false;
            }
        }
    }

    public boolean atualizarNotificacoes(int id, boolean receberNotificacoes) throws SQLException {
        String sql = "UPDATE T_PREFERENCIAS SET receber_notificacoes = ? WHERE id_preferencias = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, receberNotificacoes ? 1 : 0);
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Configuração de notificações atualizada para: " + (receberNotificacoes ? "Ativo" : "Inativo"));
                return true;
            } else {
                System.out.println("[Warning] Nenhuma preferência encontrada com ID: " + id);
                return false;
            }
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM T_PREFERENCIAS WHERE id_preferencias = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Preferências excluídas com ID: " + id);
                return true;
            } else {
                System.out.println("[Warning] Nenhuma preferência encontrada com ID: " + id);
                return false;
            }
        }
    }

    public boolean excluirPorUsuario(int idUsuario) throws SQLException {
        String sql = "DELETE FROM T_PREFERENCIAS WHERE id_usuario = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Preferências do usuário excluídas para ID: " + idUsuario);
                return true;
            } else {
                System.out.println("[Warning] Nenhuma preferência encontrada para o usuário ID: " + idUsuario);
                return false;
            }
        }
    }

    public int contarPreferencias() throws SQLException {
        String sql = "SELECT COUNT(*) FROM T_PREFERENCIAS";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public int contarPorTema(String tema) throws SQLException {
        String sql = "SELECT COUNT(*) FROM T_PREFERENCIAS WHERE tema = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tema);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    private Preferencias criarPreferenciasFromResultSet(ResultSet rs) throws SQLException {
        // Buscar usuário relacionado
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.buscarPorId(rs.getInt("id_usuario"));

        // Criar preferências
        Preferencias preferencias = new Preferencias(
                usuario,
                rs.getString("tema"),
                rs.getString("idioma"),
                rs.getInt("receber_notificacoes") == 1
        );

        preferencias.setId(rs.getInt("id_preferencias"));
        return preferencias;
    }
}
