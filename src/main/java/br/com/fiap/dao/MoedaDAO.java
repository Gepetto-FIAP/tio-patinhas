package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Moeda;

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

    public java.util.Map<String, Moeda> buscarMoedasPorSimbolo() throws SQLException {
        java.util.Map<String, Moeda> moedasPorSimbolo = new java.util.HashMap<>();
        String sql = "SELECT id_moeda, nome, simbolo, cotacao_para_real FROM T_MOEDA ORDER BY nome";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Moeda moeda = criarMoedaFromResultSet(rs);
                moedasPorSimbolo.put(moeda.getSimbolo(), moeda);
            }
        }
        return moedasPorSimbolo;
    }

    public java.util.List<Moeda> buscarMoedasOrdenadasPorCotacao() throws SQLException {
        java.util.List<Moeda> moedas = new java.util.ArrayList<>();
        String sql = "SELECT id_moeda, nome, simbolo, cotacao_para_real FROM T_MOEDA ORDER BY cotacao_para_real DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                moedas.add(criarMoedaFromResultSet(rs));
            }
        }
        
        // Ordenação adicional usando Collections
        moedas.sort((m1, m2) -> Double.compare(m2.getCotacaoParaReal(), m1.getCotacaoParaReal()));
        return moedas;
    }

    public java.util.Map<String, Integer> contarMoedasPorNome() throws SQLException {
        java.util.Map<String, Integer> contagem = new java.util.HashMap<>();
        String sql = "SELECT nome FROM T_MOEDA";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                contagem.put(nome, contagem.getOrDefault(nome, 0) + 1);
            }
        }
        return contagem;
    }

    private Moeda criarMoedaFromResultSet(ResultSet rs) throws SQLException {
        String nome = rs.getString("nome");
        String simbolo = rs.getString("simbolo");
        double cotacao = rs.getDouble("cotacao_para_real");

        return new Moeda(nome, simbolo, cotacao);
    }
}