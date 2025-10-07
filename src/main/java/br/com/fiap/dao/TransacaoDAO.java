package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Carteira;
import br.com.fiap.model.Investimento;
import br.com.fiap.model.Moeda;
import br.com.fiap.model.Status;
import br.com.fiap.model.TipoOperacao;
import br.com.fiap.model.Transacao;

public class TransacaoDAO {

    public int inserir(Transacao transacao) throws SQLException {
        String sql = "INSERT INTO T_TRANSACAO (id_transacao, id_carteira, id_investimento, id_moeda, tipo_operacao, valor_total_transacao, valor_liquido_transacao, valor_taxa_transacao, quantidade_moeda, status_transacao, data_transacao, hora_transacao) VALUES (SEQ_TRANSACAO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"id_transacao"})) {

            stmt.setInt(1, transacao.getCarteira().getId());
            stmt.setInt(2, transacao.getInvestimento().getId());
            stmt.setInt(3, transacao.getMoeda().getId());
            stmt.setString(4, transacao.getTipoOperacao().name());
            stmt.setDouble(5, transacao.getValorTotalTransacao());
            stmt.setDouble(6, transacao.getValorLiquidoTransacao());
            stmt.setDouble(7, transacao.getValorTaxaTransacao());
            stmt.setDouble(8, transacao.getQuantidadeMoeda());
            stmt.setString(9, transacao.getStatus().name());
            stmt.setDate(10, Date.valueOf(transacao.getDataTransacao().toLocalDate()));
            stmt.setTimestamp(11, Timestamp.valueOf(transacao.getDataTransacao()));

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        System.out.println("[Success] Transação inserida com ID: " + id);
                        transacao.setId(id);
                        return id;
                    }
                }
            }
        }
        return -1;
    }

    public Transacao buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM T_TRANSACAO WHERE id_transacao = ?";

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

    public List<Transacao> listarTodas() throws SQLException {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT * FROM T_TRANSACAO ORDER BY data_transacao DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                transacoes.add(criarTransacaoFromResultSet(rs));
            }
        }
        return transacoes;
    }

    public List<Transacao> buscarPorCarteira(int idCarteira) throws SQLException {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT * FROM T_TRANSACAO WHERE id_carteira = ? ORDER BY data_transacao DESC";

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

    public List<Transacao> buscarPorMoeda(int idMoeda) throws SQLException {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT * FROM T_TRANSACAO WHERE id_moeda = ? ORDER BY data_transacao DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMoeda);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transacoes.add(criarTransacaoFromResultSet(rs));
                }
            }
        }
        return transacoes;
    }

    public List<Transacao> buscarPorStatus(Status status) throws SQLException {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT * FROM T_TRANSACAO WHERE status_transacao = ? ORDER BY data_transacao DESC";

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

    public List<Transacao> buscarPorTipoOperacao(TipoOperacao tipoOperacao) throws SQLException {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT * FROM T_TRANSACAO WHERE tipo_operacao = ? ORDER BY data_transacao DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipoOperacao.name());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transacoes.add(criarTransacaoFromResultSet(rs));
                }
            }
        }
        return transacoes;
    }

    public List<Transacao> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) throws SQLException {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT * FROM T_TRANSACAO WHERE data_transacao BETWEEN ? AND ? ORDER BY data_transacao DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(dataInicio));
            stmt.setTimestamp(2, Timestamp.valueOf(dataFim));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transacoes.add(criarTransacaoFromResultSet(rs));
                }
            }
        }
        return transacoes;
    }

    public boolean atualizar(Transacao transacao) throws SQLException {
        String sql = "UPDATE T_TRANSACAO SET id_carteira = ?, id_investimento = ?, id_moeda = ?, tipo_operacao = ?, valor_total_transacao = ?, valor_liquido_transacao = ?, valor_taxa_transacao = ?, quantidade_moeda = ?, status_transacao = ?, data_transacao = ?, hora_transacao = ? WHERE id_transacao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transacao.getCarteira().getId());
            stmt.setInt(2, transacao.getInvestimento().getId());
            stmt.setInt(3, transacao.getMoeda().getId());
            stmt.setString(4, transacao.getTipoOperacao().name());
            stmt.setDouble(5, transacao.getValorTotalTransacao());
            stmt.setDouble(6, transacao.getValorLiquidoTransacao());
            stmt.setDouble(7, transacao.getValorTaxaTransacao());
            stmt.setDouble(8, transacao.getQuantidadeMoeda());
            stmt.setString(9, transacao.getStatus().name());
            stmt.setDate(10, Date.valueOf(transacao.getDataTransacao().toLocalDate()));
            stmt.setTimestamp(11, Timestamp.valueOf(transacao.getDataTransacao()));
            stmt.setInt(12, transacao.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Transação atualizada com ID: " + transacao.getId());
                return true;
            } else {
                System.out.println("[Warning] Nenhuma transação encontrada com ID: " + transacao.getId());
                return false;
            }
        }
    }

    public boolean atualizarStatus(int id, Status novoStatus) throws SQLException {
        String sql = "UPDATE T_TRANSACAO SET status_transacao = ? WHERE id_transacao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatus.name());
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Status da transação atualizado para: " + novoStatus);
                return true;
            } else {
                System.out.println("[Warning] Nenhuma transação encontrada com ID: " + id);
                return false;
            }
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM T_TRANSACAO WHERE id_transacao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Transação excluída com ID: " + id);
                return true;
            } else {
                System.out.println("[Warning] Nenhuma transação encontrada com ID: " + id);
                return false;
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

    public double calcularVolumeTotal() throws SQLException {
        String sql = "SELECT SUM(valor_total_transacao) FROM T_TRANSACAO WHERE status_transacao = 'CONCLUIDA'";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0.0;
    }

    private Transacao criarTransacaoFromResultSet(ResultSet rs) throws SQLException {
        // Buscar objetos relacionados
        CarteiraDAO carteiraDAO = new CarteiraDAO();
        MoedaDAO moedaDAO = new MoedaDAO();

        Carteira carteira = carteiraDAO.buscarPorId(rs.getInt("id_carteira"));
        Moeda moeda = moedaDAO.buscarPorId(rs.getInt("id_moeda"));
        
        // Criar investimento básico
        Investimento investimento = new Investimento();
        investimento.setIdInvestimento(rs.getInt("id_investimento"));

        // Criar transação
        Transacao transacao = new Transacao(
                carteira,
                investimento,
                moeda,
                TipoOperacao.valueOf(rs.getString("tipo_operacao")),
                rs.getDouble("valor_total_transacao"),
                rs.getDouble("valor_liquido_transacao"),
                rs.getDouble("valor_taxa_transacao"),
                rs.getDouble("quantidade_moeda"),
                Status.valueOf(rs.getString("status_transacao")),
                rs.getTimestamp("data_transacao").toLocalDateTime()
        );

        transacao.setId(rs.getInt("id_transacao"));
        return transacao;
    }

    public java.util.Map<Status, java.util.List<Transacao>> agruparTransacoesPorStatus() throws SQLException {
        java.util.Map<Status, java.util.List<Transacao>> transacoesPorStatus = new java.util.HashMap<>();
        List<Transacao> todasTransacoes = listarTodas();
        
        for (Transacao transacao : todasTransacoes) {
            Status status = transacao.getStatus();
            transacoesPorStatus.computeIfAbsent(status, k -> new java.util.ArrayList<>()).add(transacao);
        }
        
        return transacoesPorStatus;
    }

    public java.util.List<Transacao> buscarTransacoesOrdenadasPorValor() throws SQLException {
        List<Transacao> transacoes = listarTodas();
        
        // Ordenação usando Collections.sort com lambda
        transacoes.sort((t1, t2) -> Double.compare(t2.getValorTotalTransacao(), t1.getValorTotalTransacao()));
        
        return transacoes;
    }

    public java.util.Map<TipoOperacao, Integer> contarTransacoesPorTipo() throws SQLException {
        java.util.Map<TipoOperacao, Integer> contagem = new java.util.HashMap<>();
        List<Transacao> todasTransacoes = listarTodas();
        
        for (Transacao transacao : todasTransacoes) {
            TipoOperacao tipo = transacao.getTipoOperacao();
            contagem.put(tipo, contagem.getOrDefault(tipo, 0) + 1);
        }
        
        return contagem;
    }

    public java.util.List<Transacao> filtrarTransacoesPorValorMinimo(double valorMinimo) throws SQLException {
        List<Transacao> todasTransacoes = listarTodas();
        java.util.List<Transacao> transacoesFiltradas = new java.util.ArrayList<>();
        
        for (Transacao transacao : todasTransacoes) {
            if (transacao.getValorTotalTransacao() >= valorMinimo) {
                transacoesFiltradas.add(transacao);
            }
        }
        
        return transacoesFiltradas;
    }
}
