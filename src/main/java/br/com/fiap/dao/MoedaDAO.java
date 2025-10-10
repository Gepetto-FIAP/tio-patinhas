package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Moeda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoedaDAO {

    public void inserir(Moeda moeda) throws SQLException {
        String sql = "INSERT INTO T_MOEDA (id_moeda, nome, simbolo, cotacao_para_real) VALUES (SEQ_MOEDA.NEXTVAL, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"id_moeda"})) {

            stmt.setString(1, moeda.getNome());
            stmt.setString(2, moeda.getSimbolo());
            stmt.setDouble(3, moeda.getCotacaoParaReal());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        System.out.println("[Success] Moeda inserida com ID: " + rs.getInt(1));
                    }
                }
            }
        }
    }

    public Moeda buscarPorId(int id) throws SQLException {
        String sql = "SELECT id_moeda, nome, simbolo, cotacao_para_real FROM T_MOEDA WHERE id_moeda = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarMoedaFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public Moeda buscarPorSimbolo(String simbolo) throws SQLException {
        String sql = "SELECT id_moeda, nome, simbolo, cotacao_para_real FROM T_MOEDA WHERE simbolo = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, simbolo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarMoedaFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Moeda> listarTodas() throws SQLException {
        List<Moeda> moedas = new ArrayList<>();
        String sql = "SELECT id_moeda, nome, simbolo, cotacao_para_real FROM T_MOEDA ORDER BY nome";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                moedas.add(criarMoedaFromResultSet(rs));
            }
        }
        return moedas;
    }

    public void atualizar(Moeda moeda, int id) throws SQLException {
        String sql = "UPDATE T_MOEDA SET nome = ?, simbolo = ?, cotacao_para_real = ? WHERE id_moeda = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, moeda.getNome());
            stmt.setString(2, moeda.getSimbolo());
            stmt.setDouble(3, moeda.getCotacaoParaReal());
            stmt.setInt(4, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Moeda atualizada: " + moeda.getNome());
            } else {
                System.out.println("[Warning] Nenhuma moeda encontrada com ID: " + id);
            }
        }
    }

    public void atualizarCotacao(String simbolo, double novaCotacao) throws SQLException {
        String sql = "UPDATE T_MOEDA SET cotacao_para_real = ? WHERE simbolo = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, novaCotacao);
            stmt.setString(2, simbolo);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Cotação atualizada para " + simbolo + ": R$ " + novaCotacao);
            } else {
                System.out.println("[Warning] Moeda não encontrada: " + simbolo);
            }
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM T_MOEDA WHERE id_moeda = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Moeda excluída com ID: " + id);
            } else {
                System.out.println("[Warning] Nenhuma moeda encontrada com ID: " + id);
            }
        }
    }

    public boolean existePorSimbolo(String simbolo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM T_MOEDA WHERE simbolo = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, simbolo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public int contarMoedas() throws SQLException {
        String sql = "SELECT COUNT(*) FROM T_MOEDA";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public List<Moeda> buscarPorNome(String nome) throws SQLException {
        List<Moeda> moedas = new ArrayList<>();
        String sql = "SELECT id_moeda, nome, simbolo, cotacao_para_real FROM T_MOEDA WHERE UPPER(nome) LIKE UPPER(?) ORDER BY nome";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    moedas.add(criarMoedaFromResultSet(rs));
                }
            }
        }
        return moedas;
    }

    private Moeda criarMoedaFromResultSet(ResultSet rs) throws SQLException {
        String nome = rs.getString("nome");
        String simbolo = rs.getString("simbolo");
        double cotacao = rs.getDouble("cotacao_para_real");

        return new Moeda(nome, simbolo, cotacao);
    }
}
