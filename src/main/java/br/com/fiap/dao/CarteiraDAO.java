package br.com.fiap.dao;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Carteira;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CarteiraDAO {
    private Connection conexao;

    public CarteiraDAO() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public Carteira buscarPorId(int id) throws SQLException {
        String sql = "SELECT id_carteira, saldo_em_real, id_usuario FROM T_CARTEIRA WHERE id_carteira = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                Carteira c = new Carteira();
//                c.setIdCarteira(rs.getInt("id_carteira"));
//                c.setSaldoEmReal(rs.getDouble("saldo_em_real"));
//                return c;
//            }
        }
        return null;
    }

    public void atualizarSaldo(int idCarteira, double novoSaldo) throws SQLException {
        String sql = "UPDATE T_CARTEIRA SET saldo_em_real = ? WHERE id_carteira = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setDouble(1, novoSaldo);
            ps.setInt(2, idCarteira);
            ps.executeUpdate();
        }
    }

    public void depositar(int idCarteira, double valor) throws SQLException {
        Carteira c = buscarPorId(idCarteira);
        if (c != null) {
            double novoSaldo = c.getSaldo() + valor;
            atualizarSaldo(idCarteira, novoSaldo);
        }
    }

    public void sacar(int idCarteira, double valor) throws SQLException {
        Carteira c = buscarPorId(idCarteira);
        if (c != null && c.getSaldo() >= valor) {
            double novoSaldo = c.getSaldo() - valor;
            atualizarSaldo(idCarteira, novoSaldo);
        } else {
            throw new SQLException("Saldo insuficiente.");
        }
    }

    public void fecharConexao() throws SQLException {
        conexao.close();
    }

}