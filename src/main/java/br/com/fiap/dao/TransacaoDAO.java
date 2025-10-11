package br.com.fiap.dao;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Transacao;
import br.com.fiap.model.Transferencia;

import java.sql.*;

public class TransacaoDAO {
    private Connection conexao;

    public TransacaoDAO() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public int inserir(Transacao transacao) throws SQLException {
        String sql = "INSERT INTO T_TRANSACAO (id_transacao, id_carteira, id_investimento, id_moeda, tipo_operacao, valor_total_transacao, valor_liquido_transacao, valor_taxa_transacao, quantidade_moeda, status_transacao, timestamp_transacao) VALUES (SEQ_TRANSACAO.NEXTVAL,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexao.prepareStatement(sql, new String[]{"id_transacao"})) {
            ps.setInt(1, transacao.getCarteira().getId());
            ps.setInt(2, transacao.getInvestimento().getId());
            ps.setInt(3, transacao.getMoeda().getId());
            ps.setString(4, transacao.getTipoOperacao().name());
            ps.setDouble(5, transacao.getValorTotalTransacao());
            ps.setDouble(6, transacao.getValorLiquidoTransacao());
            ps.setDouble(7, transacao.getValorTaxaTransacao());
            ps.setDouble(8, transacao.getQuantidadeMoeda());
            ps.setString(9, transacao.getStatusTransacao().name());
            ps.setTimestamp(10, java.sql.Timestamp.valueOf(transacao.getDataTransacao() ));

            ps.executeUpdate();
            try (java.sql.ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("[Success] Transacao inserida com ID: " + id);
                    transacao.setId(id);
                    return id;
                }
            } catch (SQLException e) {
                System.out.println("[Erro] Não foi possível obter o ID da transacao: " + e.getMessage());
            }
        }
        return -1;

    }


    public void atualizar(Transacao transacao) throws SQLException {
        String sql = "UPDATE T_TRANSACAO SET status_transacao = ? WHERE id_transacao = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, transacao.getStatus().name());
            ps.setInt(2, transacao.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[Erro] Não foi possível atualizar transacao: " + e.getMessage());
        }
    }

}
