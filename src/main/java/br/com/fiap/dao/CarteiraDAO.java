package br.com.fiap.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Carteira;
import br.com.fiap.model.Usuario;

public class CarteiraDAO {
    
    public CarteiraDAO() {
        // Não manter conexão aberta - usar try-with-resources
    }

    public Carteira buscarPorId(int id) throws SQLException {
        UsuarioDAO dao = new UsuarioDAO();
        String sql = "SELECT id_carteira, saldo_em_real, id_usuario FROM T_CARTEIRA WHERE id_carteira = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Carteira c = new Carteira();
                    c.setIdCarteira(rs.getInt("id_carteira"));
                    c.setSaldoCarteira(rs.getDouble("saldo_em_real"));
                    c.setUsuario(dao.buscarPorId(rs.getInt("id_usuario")));
                    return c;
                }
            }
        } catch (SQLException e) {
            System.out.println("[Erro] Não foi possível buscar a carteira: " + e.getMessage());
        }
        return null;
    }

    public Carteira buscarPorUsuario(int id_usuario) throws SQLException {
        String sql = "SELECT id_carteira, saldo_em_real, id_usuario FROM T_CARTEIRA WHERE id_usuario = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id_usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Carteira c = new Carteira();
                    c.setIdCarteira(rs.getInt("id_carteira"));
                    c.setSaldoCarteira(rs.getDouble("saldo_em_real"));
                    return c;
                }
            }
        } catch (SQLException e) {
            System.out.println("[Erro] Não foi possível buscar a carteira: " + e.getMessage());
        }
        return null;
    }

    public void atualizarSaldo(Carteira carteira, double novoSaldo) throws SQLException {
        String sql = "UPDATE T_CARTEIRA SET saldo_em_real = ? WHERE id_carteira = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, novoSaldo);
            ps.setInt(2, carteira.getId());
            ps.executeUpdate();
            carteira.setSaldoCarteira(novoSaldo);
            System.out.println("[Success] Saldo atualizado para: " + novoSaldo);
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

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmtSeq = conn.prepareStatement(seqSql);
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
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("[Erro] Não foi possível deletar a carteira: " + e.getMessage());
        }
        return false;
    }

    public List<Carteira> listarTodas() throws SQLException {
        List<Carteira> carteiras = new ArrayList<>();
        String sql = "SELECT id_carteira, saldo_em_real, id_usuario FROM T_CARTEIRA ORDER BY id_carteira";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Carteira carteira = new Carteira();
                carteira.setIdCarteira(rs.getInt("id_carteira"));
                carteira.setSaldoCarteira(rs.getDouble("saldo_em_real"));
                
                // Buscar usuário relacionado
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                Usuario usuario = usuarioDAO.buscarPorId(rs.getInt("id_usuario"));
                carteira.setUsuario(usuario);
                
                carteiras.add(carteira);
            }
        }
        return carteiras;
    }

    public List<Carteira> buscarPorSaldoRange(double saldoMinimo, double saldoMaximo) throws SQLException {
        List<Carteira> carteiras = new ArrayList<>();
        String sql = "SELECT id_carteira, saldo_em_real, id_usuario FROM T_CARTEIRA WHERE saldo_em_real BETWEEN ? AND ? ORDER BY saldo_em_real DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, saldoMinimo);
            stmt.setDouble(2, saldoMaximo);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Carteira carteira = new Carteira();
                    carteira.setIdCarteira(rs.getInt("id_carteira"));
                    carteira.setSaldoCarteira(rs.getDouble("saldo_em_real"));
                    
                    // Buscar usuário relacionado
                    UsuarioDAO usuarioDAO = new UsuarioDAO();
                    Usuario usuario = usuarioDAO.buscarPorId(rs.getInt("id_usuario"));
                    carteira.setUsuario(usuario);
                    
                    carteiras.add(carteira);
                }
            }
        }
        return carteiras;
    }

    public List<Carteira> buscarCarteirasComSaldoMaiorQue(double saldoMinimo) throws SQLException {
        List<Carteira> carteiras = new ArrayList<>();
        String sql = "SELECT id_carteira, saldo_em_real, id_usuario FROM T_CARTEIRA WHERE saldo_em_real > ? ORDER BY saldo_em_real DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, saldoMinimo);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Carteira carteira = new Carteira();
                    carteira.setIdCarteira(rs.getInt("id_carteira"));
                    carteira.setSaldoCarteira(rs.getDouble("saldo_em_real"));
                    
                    // Buscar usuário relacionado
                    UsuarioDAO usuarioDAO = new UsuarioDAO();
                    Usuario usuario = usuarioDAO.buscarPorId(rs.getInt("id_usuario"));
                    carteira.setUsuario(usuario);
                    
                    carteiras.add(carteira);
                }
            }
        }
        return carteiras;
    }

    public double calcularSaldoTotal() throws SQLException {
        String sql = "SELECT SUM(saldo_em_real) FROM T_CARTEIRA";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0.0;
    }

    public int contarCarteiras() throws SQLException {
        String sql = "SELECT COUNT(*) FROM T_CARTEIRA";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    // Método removido - não é mais necessário com try-with-resources

}