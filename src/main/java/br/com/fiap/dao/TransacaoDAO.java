package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {

    public int inserir(Transacao transacao) throws SQLException {
        String sql = "INSERT INTO T_TRANSACAO (id_transacao, id_carteira, id_investimento, id_moeda, tipo_operacao, " +
                "valor_total_transacao, valor_liquido_transacao, valor_taxa_transacao, quantidade_moeda, status_transacao) " +
                "VALUES (SEQ_TRANSACAO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"id_transacao"})) {

            stmt.setInt(1, transacao.getCarteira().getId());
            stmt.setInt(2, getInvestimentoId(transacao.getInvestimento()));
            stmt.setInt(3, getMoedaId(transacao.getMoeda()));
            stmt.setString(4, transacao.getTipoOperacao().name());
            stmt.setDouble(5, transacao.getValorTotalTransacao());
            stmt.setDouble(6, transacao.getValorLiquidoTransacao());
            stmt.setDouble(7, transacao.getValorTaxaTransacao());
            stmt.setDouble(8, transacao.getQuantidadeMoeda());
            stmt.setString(9, transacao.getStatus().name());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        System.out.println("[Success] Transação inserida com ID: " + id);
                        return id;
                    }
                }
            }
        }
        return -1;
    }

    public Transacao buscarPorId(int id) throws SQLException {
        String sql = "SELECT id_transacao, id_carteira, id_investimento, id_moeda, tipo_operacao, " +
                "valor_total_transacao, valor_liquido_transacao, valor_taxa_transacao, quantidade_moeda, " +
                "status_transacao, data_transacao, hora_transacao FROM T_TRANSACAO WHERE id_transacao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarTransacaoFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Transacao> buscarPorCarteira(int idCarteira) throws SQLException {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT id_transacao, id_carteira, id_investimento, id_moeda, tipo_operacao, " +
                "valor_total_transacao, valor_liquido_transacao, valor_taxa_transacao, quantidade_moeda, " +
                "status_transacao, data_transacao, hora_transacao FROM T_TRANSACAO WHERE id_carteira = ? " +
                "ORDER BY data_transacao DESC, hora_transacao DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCarteira);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transacoes.add(criarTransacaoFromResultSet(rs));
                }
            }
        }
        return transacoes;
    }

    public List<Transacao> listarTodas() throws SQLException {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT id_transacao, id_carteira, id_investimento, id_moeda, tipo_operacao, " +
                "valor_total_transacao, valor_liquido_transacao, valor_taxa_transacao, quantidade_moeda, " +
                "status_transacao, data_transacao, hora_transacao FROM T_TRANSACAO " +
                "ORDER BY data_transacao DESC, hora_transacao DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                transacoes.add(criarTransacaoFromResultSet(rs));
            }
        }
        return transacoes;
    }

    public void atualizar(int idTransacao, Status novoStatus) throws SQLException {
        String sql = "UPDATE T_TRANSACAO SET status_transacao = ? WHERE id_transacao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatus.name());
            stmt.setInt(2, idTransacao);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Transação atualizada. Novo status: " + novoStatus);
            } else {
                System.out.println("[Warning] Nenhuma transação encontrada com ID: " + idTransacao);
            }
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM T_TRANSACAO WHERE id_transacao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Transação excluída com ID: " + id);
            } else {
                System.out.println("[Warning] Nenhuma transação encontrada com ID: " + id);
            }
        }
    }

    public int contarTransacoes() throws SQLException {
        String sql = "SELECT COUNT(*) FROM T_TRANSACAO";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public List<Transacao> buscarPorStatus(Status status) throws SQLException {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT id_transacao, id_carteira, id_investimento, id_moeda, tipo_operacao, " +
                "valor_total_transacao, valor_liquido_transacao, valor_taxa_transacao, quantidade_moeda, " +
                "status_transacao, data_transacao, hora_transacao FROM T_TRANSACAO WHERE status_transacao = ? " +
                "ORDER BY data_transacao DESC, hora_transacao DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transacoes.add(criarTransacaoFromResultSet(rs));
                }
            }
        }
        return transacoes;
    }

    private Transacao criarTransacaoFromResultSet(ResultSet rs) throws SQLException {
        int idCarteira = rs.getInt("id_carteira");
        int idInvestimento = rs.getInt("id_investimento");
        int idMoeda = rs.getInt("id_moeda");
        String tipoOp = rs.getString("tipo_operacao");
        double valorTotal = rs.getDouble("valor_total_transacao");
        double valorLiquido = rs.getDouble("valor_liquido_transacao");
        double valorTaxa = rs.getDouble("valor_taxa_transacao");
        double quantidade = rs.getDouble("quantidade_moeda");
        String statusStr = rs.getString("status_transacao");
        Date dataTransacao = rs.getDate("data_transacao");
        Timestamp horaTransacao = rs.getTimestamp("hora_transacao");

        // Buscar objetos relacionados
        CarteiraDAO carteiraDAO = new CarteiraDAO();
        InvestimentoDAO investimentoDAO = new InvestimentoDAO();
        MoedaDAO moedaDAO = new MoedaDAO();

        Carteira carteira = carteiraDAO.buscarPorId(idCarteira);
        Investimento investimento = investimentoDAO.buscarPorId(idInvestimento);
        Moeda moeda = moedaDAO.buscarPorId(idMoeda);
        TipoOperacao tipoOperacao = TipoOperacao.valueOf(tipoOp);
        Status status = Status.valueOf(statusStr);

        return new Transacao(
                carteira,
                investimento,
                moeda,
                tipoOperacao,
                valorTotal,
                valorLiquido,
                valorTaxa,
                quantidade,
                status,
                dataTransacao != null ? dataTransacao.toString() : "",
                horaTransacao != null ? horaTransacao.toString() : ""
        );
    }

    private int getInvestimentoId(Investimento investimento) throws SQLException {
        // Buscar ID do investimento no banco baseado na moeda e carteira
        String sql = "SELECT id_investimento FROM T_INVESTIMENTO WHERE id_moeda = ? AND id_carteira = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, getMoedaId(investimento.getMoeda()));
            stmt.setInt(2, investimento.getCarteira().getId());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_investimento");
                }
            }
        }
        return -1;
    }

    private int getMoedaId(Moeda moeda) throws SQLException {
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
        return -1;
    }
}
