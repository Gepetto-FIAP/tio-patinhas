package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Preferencias;
import br.com.fiap.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreferenciasDAO {

    public int inserir(Preferencias preferencias) throws SQLException {
        String sql = "INSERT INTO T_PREFERENCIAS (id_preferencias, id_usuario, tema, idioma, receber_notificacoes) " +
                "VALUES (SEQ_PREFERENCIAS.NEXTVAL, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"id_preferencias"})) {

            stmt.setInt(1, getUsuarioId(preferencias));
            stmt.setString(2, preferencias.getTema());
            stmt.setString(3, preferencias.getIdioma());
            stmt.setInt(4, preferencias.isReceberNotificacoes() ? 1 : 0);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        System.out.println("[Success] Preferências inseridas com ID: " + id);
                        return id;
                    }
                }
            }
        }
        return -1;
    }

    public Preferencias buscarPorId(int id) throws SQLException {
        String sql = "SELECT id_preferencias, id_usuario, tema, idioma, receber_notificacoes " +
                "FROM T_PREFERENCIAS WHERE id_preferencias = ?";

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
        String sql = "SELECT id_preferencias, id_usuario, tema, idioma, receber_notificacoes " +
                "FROM T_PREFERENCIAS WHERE id_usuario = ?";

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
        String sql = "SELECT id_preferencias, id_usuario, tema, idioma, receber_notificacoes " +
                "FROM T_PREFERENCIAS ORDER BY id_preferencias";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                preferencias.add(criarPreferenciasFromResultSet(rs));
            }
        }
        return preferencias;
    }

    public void atualizar(Preferencias preferencias, int idPreferencias) throws SQLException {
        String sql = "UPDATE T_PREFERENCIAS SET tema = ?, idioma = ?, receber_notificacoes = ? " +
                "WHERE id_preferencias = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, preferencias.getTema());
            stmt.setString(2, preferencias.getIdioma());
            stmt.setInt(3, preferencias.isReceberNotificacoes() ? 1 : 0);
            stmt.setInt(4, idPreferencias);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Preferências atualizadas");
            } else {
                System.out.println("[Warning] Nenhuma preferência encontrada com ID: " + idPreferencias);
            }
        }
    }

    public void atualizarPorUsuario(Preferencias preferencias, int idUsuario) throws SQLException {
        String sql = "UPDATE T_PREFERENCIAS SET tema = ?, idioma = ?, receber_notificacoes = ? " +
                "WHERE id_usuario = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, preferencias.getTema());
            stmt.setString(2, preferencias.getIdioma());
            stmt.setInt(3, preferencias.isReceberNotificacoes() ? 1 : 0);
            stmt.setInt(4, idUsuario);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Preferências atualizadas para usuário ID: " + idUsuario);
            } else {
                System.out.println("[Warning] Nenhuma preferência encontrada para usuário ID: " + idUsuario);
            }
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM T_PREFERENCIAS WHERE id_preferencias = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Preferências excluídas com ID: " + id);
            } else {
                System.out.println("[Warning] Nenhuma preferência encontrada com ID: " + id);
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

    public List<Preferencias> buscarPorTema(String tema) throws SQLException {
        List<Preferencias> preferencias = new ArrayList<>();
        String sql = "SELECT id_preferencias, id_usuario, tema, idioma, receber_notificacoes " +
                "FROM T_PREFERENCIAS WHERE tema = ? ORDER BY id_preferencias";

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

    private Preferencias criarPreferenciasFromResultSet(ResultSet rs) throws SQLException {
        int idUsuario = rs.getInt("id_usuario");
        String tema = rs.getString("tema");
        String idioma = rs.getString("idioma");
        boolean receberNotificacoes = rs.getInt("receber_notificacoes") == 1;

        // Buscar usuário
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

        return new Preferencias(usuario, tema, idioma, receberNotificacoes);
    }

    private int getUsuarioId(Preferencias preferencias) throws SQLException {
        // Como Preferencias tem um objeto Usuario, pegamos o ID dele
        if (preferencias.getUsuario() != null) {
            return preferencias.getUsuario().getId();
        }
        return -1;
    }
}
