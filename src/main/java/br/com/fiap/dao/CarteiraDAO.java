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
        UsuarioDAO dao = new UsuarioDAO();
        String sql = "SELECT id_carteira, saldo_em_real, id_usuario FROM T_CARTEIRA WHERE id_carteira = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Carteira c = new Carteira();
                c.setIdCarteira(rs.getInt("id_carteira"));
                c.setSaldoCarteira(rs.getDouble("saldo_em_real"));
                c.setUsuario(dao.buscarPorId(rs.getInt("id_usuario")));
                return c;
            }
        } catch (SQLException e) {
            System.out.println("[Erro] Não foi possível buscar a carteira: " + e.getMessage());
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
        } catch (SQLException e) {
            System.out.println("[Erro] Não foi possível buscar a carteira: " + e.getMessage());
        }
        return null;
    }

    public void atualizarSaldo(Carteira carteira, double novoSaldo) throws SQLException {
        String sql = "UPDATE T_CARTEIRA SET saldo_em_real = ? WHERE id_carteira = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql, new String[]{"saldo_em_real"})) {
            ps.setDouble(1, novoSaldo);
            ps.setInt(2, carteira.getId());
            ps.executeUpdate();

            try (java.sql.ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    double novoSaldoDB  = rs.getInt(1);
                    System.out.println("[Success] Novo saldo: " + novoSaldoDB);


                    carteira.setSaldoCarteira(novoSaldoDB);

                }
            } catch (SQLException e) {
                System.out.println("[Erro] Não foi possível obter o ID da transferência: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("[Erro] Não foi possível atualizar o saldo: " + e.getMessage());
        }


    }

    public void depositar(Carteira carteira, double valor) throws SQLException {
        Carteira c = buscarPorId(carteira.getId());
        if (c != null) {
            double novoSaldo = c.getSaldo() + valor;
            atualizarSaldo(carteira, novoSaldo);
        }

    }

    public void sacar(Carteira carteira, double valor) throws SQLException {
        Carteira c = buscarPorId(carteira.getId());
        if (c != null && c.getSaldo() >= valor) {
            double novoSaldo = c.getSaldo() - valor;
            atualizarSaldo(carteira, novoSaldo);
        } else {
            throw new SQLException("Saldo insuficiente.");
        }
    }

    public double getSaldo(int idUsuario) throws SQLException {
        String sql = "SELECT saldo_em_real FROM T_CARTEIRA WHERE id_carteira = ?";
        double saldo = 0.0;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    saldo = rs.getDouble("saldo_em_real");
                }
            }
        } catch (SQLException e) {
            System.out.println("[Erro] Não foi possível consultar saldo: " + e.getMessage());
        }

        return saldo;
    }

    public void inserir(Carteira carteira) throws SQLException {
        // 1. Pega o próximo valor da sequence
        int idCarteira = 0;
        String seqSql = "SELECT SEQ_CARTEIRA.NEXTVAL FROM dual";
        try (PreparedStatement stmtSeq = conexao.prepareStatement(seqSql);
             ResultSet rs = stmtSeq.executeQuery()) {
            if (rs.next()) {
                idCarteira = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("[Erro] Não foi possível obter o próximo ID da carteira: " + e.getMessage());
            throw e;
        }

        // 2. Insere a carteira já usando o id gerado
        String sql = "INSERT INTO T_CARTEIRA (id_carteira, saldo_em_real, id_usuario) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCarteira);
            stmt.setDouble(2, carteira.getSaldo());
            stmt.setInt(3, carteira.getUsuario().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[Erro] Não foi possível inserir a carteira: " + e.getMessage());
            throw e;
        }

        // 3. Atualiza o objeto em memória
        carteira.setIdCarteira(idCarteira);

    }


    public boolean deletar(int idUsuario) throws SQLException {
        String sql = "DELETE FROM T_CARTEIRA WHERE id_usuario = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("[Erro] Não foi possível deletar a carteira: " + e.getMessage());
        }
        return false;
    }

    public void fecharConexao() throws SQLException {
        conexao.close();
    }

}