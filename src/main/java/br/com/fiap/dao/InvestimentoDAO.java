package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Investimento;
import br.com.fiap.model.Moeda;
import br.com.fiap.model.Carteira;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestimentoDAO {

    public int inserir(Investimento investimento) throws SQLException {
        String sql = "INSERT INTO T_INVESTIMENTO (id_investimento, id_moeda, id_carteira, quantidade_moeda) VALUES (SEQ_INVESTIMENTO.NEXTVAL, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"id_investimento"})) {

            stmt.setInt(1, getMoedaId(investimento.getMoeda()));
            stmt.setInt(2, investimento.getCarteira().getId());
            stmt.setDouble(3, investimento.getQuantidadeMoeda());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        System.out.println("[Success] Investimento inserido com ID: " + id);
                        return id;
                    }
                }
            }
        }
        return -1;
    }

    public Investimento buscarPorId(int id) throws SQLException {
        String sql = "SELECT id_investimento, id_moeda, id_carteira, quantidade_moeda FROM T_INVESTIMENTO WHERE id_investimento = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarInvestimentoFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Investimento> buscarPorCarteira(int idCarteira) throws SQLException {
        List<Investimento> investimentos = new ArrayList<>();
        String sql = "SELECT id_investimento, id_moeda, id_carteira, quantidade_moeda FROM T_INVESTIMENTO WHERE id_carteira = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCarteira);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    investimentos.add(criarInvestimentoFromResultSet(rs));
                }
            }
        }
        return investimentos;
    }

    public List<Investimento> listarTodos() throws SQLException {
        List<Investimento> investimentos = new ArrayList<>();
        String sql = "SELECT id_investimento, id_moeda, id_carteira, quantidade_moeda FROM T_INVESTIMENTO ORDER BY id_investimento";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                investimentos.add(criarInvestimentoFromResultSet(rs));
            }
        }
        return investimentos;
    }

    public void atualizar(int idInvestimento, double novaQuantidade) throws SQLException {
        String sql = "UPDATE T_INVESTIMENTO SET quantidade_moeda = ? WHERE id_investimento = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, novaQuantidade);
            stmt.setInt(2, idInvestimento);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Investimento atualizado. Nova quantidade: " + novaQuantidade);
            } else {
                System.out.println("[Warning] Nenhum investimento encontrado com ID: " + idInvestimento);
            }
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM T_INVESTIMENTO WHERE id_investimento = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Investimento excluído com ID: " + id);
            } else {
                System.out.println("[Warning] Nenhum investimento encontrado com ID: " + id);
            }
        }
    }

    public Investimento buscarPorMoedaECarteira(int idMoeda, int idCarteira) throws SQLException {
        String sql = "SELECT id_investimento, id_moeda, id_carteira, quantidade_moeda FROM T_INVESTIMENTO WHERE id_moeda = ? AND id_carteira = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMoeda);
            stmt.setInt(2, idCarteira);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarInvestimentoFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public int contarInvestimentos() throws SQLException {
        String sql = "SELECT COUNT(*) FROM T_INVESTIMENTO";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    private Investimento criarInvestimentoFromResultSet(ResultSet rs) throws SQLException {
        int idMoeda = rs.getInt("id_moeda");
        int idCarteira = rs.getInt("id_carteira");
        double quantidade = rs.getDouble("quantidade_moeda");

        // Buscar moeda e carteira completos
        MoedaDAO moedaDAO = new MoedaDAO();
        CarteiraDAO carteiraDAO = new CarteiraDAO();

        Moeda moeda = moedaDAO.buscarPorId(idMoeda);
        Carteira carteira = carteiraDAO.buscarPorId(idCarteira);

        return new Investimento(moeda, carteira);
    }

    private int getMoedaId(Moeda moeda) throws SQLException {
        MoedaDAO moedaDAO = new MoedaDAO();
        Moeda moedaBanco = moedaDAO.buscarPorSimbolo(moeda.getSimbolo());
        if (moedaBanco != null) {
            // Precisamos ter o ID na classe Moeda - por enquanto vamos buscar do banco
            String sql = "SELECT id_moeda FROM T_MOEDA WHERE simbolo = ?";
            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, moeda.getSimbolo());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id_moeda");
                    }
                }
            }
        }
        return -1;
    }
}
