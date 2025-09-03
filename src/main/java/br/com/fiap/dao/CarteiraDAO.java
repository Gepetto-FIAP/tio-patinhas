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
        String sql = "SELECT id_carteira, saldo_em_real, id_usuario FROM T_CARTEIRA WHERE id_usuario = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Carteira c = new Carteira();
                c.setIdCarteira(rs.getInt("id_carteira"));
                c.setSaldoCarteira(rs.getDouble("saldo_em_real"));
                return c;
            }
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

    public double getSaldo(int idUsuario) throws SQLException {
        String sql = "SELECT saldo_em_real FROM T_CARTEIRA WHERE id_usuario = ?";
        double saldo = 0.0;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    saldo = rs.getDouble("saldo_em_real");
                }
            }
        }

        return saldo;
    }

    public void inserir(Carteira carteira) throws SQLException {
        String sql = "INSERT INTO T_CARTEIRA (id_carteira, saldo_em_real, id_usuario) " +
                "VALUES (SEQ_CARTEIRA.NEXTVAL, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            stmt.setDouble(1, carteira.getSaldo());
            stmt.setInt(2, carteira.getUsuario().getId());
            
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        carteira.setIdCarteira(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    public boolean deletar(int idUsuario) throws SQLException {
        String sql = "DELETE FROM T_CARTEIRA WHERE id_usuario = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        }
    }

    public void fecharConexao() throws SQLException {
        conexao.close();
    }

}