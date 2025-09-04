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
            if (rs.next()) {
                Carteira c = new Carteira();
                c.setIdCarteira(rs.getInt("id_carteira"));
                c.setSaldoCarteira(rs.getDouble("saldo_em_real"));
                return c;
            }
        }
        return null;
    }

    public Carteira buscarPorUsuario(int id_usuario) throws SQLException {
        String sql = "SELECT id_carteira, saldo_em_real, id_usuario FROM T_CARTEIRA WHERE id_usuario = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id_usuario);
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
        String sql = "SELECT saldo_em_real FROM T_CARTEIRA WHERE id_carteira = ?";
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
        try (Connection conn = ConnectionFactory.getConnection()) {
            // 1. Pega o próximo valor da sequence
            int idCarteira = 0;
            String seqSql = "SELECT SEQ_CARTEIRA.NEXTVAL FROM dual";
            try (PreparedStatement stmtSeq = conn.prepareStatement(seqSql);
                 ResultSet rs = stmtSeq.executeQuery()) {
                if (rs.next()) {
                    idCarteira = rs.getInt(1);
                }
            }

            // 2. Insere a carteira já usando o id gerado
            String sql = "INSERT INTO T_CARTEIRA (id_carteira, saldo_em_real, id_usuario) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idCarteira);
                stmt.setDouble(2, carteira.getSaldo());
                stmt.setInt(3, carteira.getUsuario().getId());
                stmt.executeUpdate();
            }

            // 3. Atualiza o objeto em memória
            carteira.setIdCarteira(idCarteira);
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