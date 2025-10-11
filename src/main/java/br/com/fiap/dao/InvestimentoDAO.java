package br.com.fiap.dao;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Carteira;
import br.com.fiap.model.Investimento;
import br.com.fiap.model.Moeda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InvestimentoDAO {
    private Connection conexao;
    public InvestimentoDAO() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void atualizarQuantidadeMoeda (Investimento investimento) throws SQLException {
        String sql = "UPDATE T_INVESTIMENTO SET quantidade_moeda = ? WHERE id_investimento = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            System.out.println(investimento.getQuantidadeMoeda() + " qunatidade");
            ps.setDouble(1, investimento.getQuantidadeMoeda()); // Substitua pelo valor real da quantidade de moeda
            ps.setInt(2, investimento.getId()); // Substitua pelo ID real do investimento
            ps.executeUpdate();
            System.out.println("[Log] Quantidade de moeda atualizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("[Erro] Não foi possível atualizar a quantidade de moeda: " + e.getMessage());
        }
    }

    public int inserir(Investimento investimento) throws SQLException {

        String sql = "INSERT INTO T_INVESTIMENTO (id_investimento, id_moeda, id_carteira, quantidade_moeda) VALUES (SEQ_INVESTIMENTO.NEXTVAL, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql, new String[]{"id_investimento"})) {
            ps.setInt(1, investimento.getMoeda().getId());
            ps.setInt(2, investimento.getCarteira().getId());
            ps.setDouble(3, 0.0 ); // Inicializa com 0.0, a quantidade será atualizada posteriormente
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("[Success] Investimento inserido com ID: " + id);
                    investimento.setIdInvestimento(id);
                    return id;
                }
            } catch (SQLException e) {
                System.out.println("[Erro] Não foi possível obter o ID do investimento: " + e.getMessage());
            }


        } catch (SQLException e) {
            System.out.println("[Erro] Não foi possível inserir o investimento: " + e.getMessage());
        }

        return -1;
    }

    public Investimento consultarInvestimento (Moeda moeda, Carteira carteira) {
        String sql = "SELECT id_investimento, quantidade_moeda FROM T_INVESTIMENTO WHERE id_moeda = ? AND id_carteira = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, moeda.getId());
            ps.setInt(2, carteira.getId());
            var rs = ps.executeQuery();
            if (rs.next()) {
                Investimento i = new Investimento();
                i.setQuantidadeMoeda(rs.getDouble("quantidade_moeda"));
                i.setIdInvestimento(rs.getInt("id_investimento"));
                i.setMoeda(moeda);
                i.setCarteira(carteira);
                return i;
            }
        } catch (SQLException e) {
            System.out.println("[Erro] Não foi possível consultar o investimento: " + e.getMessage());
        }

        return  null;

    }

}
