package br.com.fiap.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Status;
import br.com.fiap.model.Transferencia;


public class TransferenciaDAO {

    public TransferenciaDAO() {
        // Não manter conexão aberta - usar try-with-resources
    }

    public int inserir(Transferencia transferencia) throws SQLException {
        String sql = "INSERT INTO T_TRANSFERENCIA (id_transferencia, id_carteira_remetente, id_carteira_destinatario, valor_transferencia, status_transferencia, timestamp_transferencia) VALUES (SEQ_TRANSFERENCIA.NEXTVAL, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id_transferencia"})) {
            ps.setInt(1, transferencia.getCarteiraRemetente().getId());
            ps.setInt(2, transferencia.getCarteiraDestinatario().getId());
            ps.setDouble(3, transferencia.getValor());
            ps.setString(4, transferencia.getStatus().name());
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(transferencia.getDataTransferencia()));

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("[Success] Transferencia inserida com ID: " + id);
                    transferencia.setId(id);
                    return id;
                }
            } catch (SQLException e) {
                System.out.println("[Erro] Não foi possível obter o ID da transferência: " + e.getMessage());
            }
        }
        return -1;
    }

    public void atualizar(Transferencia transferencia) throws SQLException {
        String sql = "UPDATE T_TRANSFERENCIA SET status_transferencia = ? WHERE id_transferencia = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            System.out.println(transferencia.getStatus().name());
            System.out.println(transferencia.getId());
            ps.setString(1, transferencia.getStatus().name());
            ps.setInt(2, transferencia.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[Erro] Não foi possível atualizar transferência: " + e.getMessage());
        }
    }


    public Transferencia consultar(int id, int idCarteiraEnvolvida) throws SQLException {
        String sql = "SELECT id_transferencia, id_carteira_remetente, id_carteira_destinatario, valor_transferencia, status_transferencia, timestamp_transferencia FROM T_TRANSFERENCIA WHERE id_transferencia = ? AND (id_carteira_remetente = ? OR id_carteira_destinatario = ?)";
        CarteiraDAO carteiraDAO = new CarteiraDAO();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, idCarteiraEnvolvida);
            ps.setInt(3, idCarteiraEnvolvida);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Transferencia transferencia = new Transferencia(
                        carteiraDAO.buscarPorId(rs.getInt("id_carteira_remetente")),
                        carteiraDAO.buscarPorId(rs.getInt("id_carteira_destinatario")),
                        rs.getDouble("valor_transferencia"),
                        Status.valueOf(rs.getString("status_transferencia")),
                        rs.getTimestamp("timestamp_transferencia").toLocalDateTime()
                    );

                    transferencia.setId(rs.getInt("id_transferencia"));
                    return transferencia;
                }
            } catch (SQLException e) {
                System.out.println("[Erro] Não foi possível consultar transferência: " + e.getMessage());
            }
        }
        return null;
    }

    public List<Transferencia> listarTodas() throws SQLException {
        List<Transferencia> transferencias = new ArrayList<>();
        String sql = "SELECT * FROM T_TRANSFERENCIA ORDER BY timestamp_transferencia DESC";
        CarteiraDAO carteiraDAO = new CarteiraDAO();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Transferencia transferencia = new Transferencia(
                        carteiraDAO.buscarPorId(rs.getInt("id_carteira_remetente")),
                        carteiraDAO.buscarPorId(rs.getInt("id_carteira_destinatario")),
                        rs.getDouble("valor_transferencia"),
                        Status.valueOf(rs.getString("status_transferencia")),
                        rs.getTimestamp("timestamp_transferencia").toLocalDateTime()
                );

                transferencia.setId(rs.getInt("id_transferencia"));
                transferencias.add(transferencia);
            }
        }
        return transferencias;
    }

    public List<Transferencia> buscarPorRemetente(int idCarteiraRemetente) throws SQLException {
        List<Transferencia> transferencias = new ArrayList<>();
        String sql = "SELECT * FROM T_TRANSFERENCIA WHERE id_carteira_remetente = ? ORDER BY timestamp_transferencia DESC";
        CarteiraDAO carteiraDAO = new CarteiraDAO();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCarteiraRemetente);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transferencia transferencia = new Transferencia(
                            carteiraDAO.buscarPorId(rs.getInt("id_carteira_remetente")),
                            carteiraDAO.buscarPorId(rs.getInt("id_carteira_destinatario")),
                            rs.getDouble("valor_transferencia"),
                            Status.valueOf(rs.getString("status_transferencia")),
                            rs.getTimestamp("timestamp_transferencia").toLocalDateTime()
                    );

                    transferencia.setId(rs.getInt("id_transferencia"));
                    transferencias.add(transferencia);
                }
            }
        }
        return transferencias;
    }

    public List<Transferencia> buscarPorDestinatario(int idCarteiraDestinatario) throws SQLException {
        List<Transferencia> transferencias = new ArrayList<>();
        String sql = "SELECT * FROM T_TRANSFERENCIA WHERE id_carteira_destinatario = ? ORDER BY timestamp_transferencia DESC";
        CarteiraDAO carteiraDAO = new CarteiraDAO();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCarteiraDestinatario);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transferencia transferencia = new Transferencia(
                            carteiraDAO.buscarPorId(rs.getInt("id_carteira_remetente")),
                            carteiraDAO.buscarPorId(rs.getInt("id_carteira_destinatario")),
                            rs.getDouble("valor_transferencia"),
                            Status.valueOf(rs.getString("status_transferencia")),
                            rs.getTimestamp("timestamp_transferencia").toLocalDateTime()
                    );

                    transferencia.setId(rs.getInt("id_transferencia"));
                    transferencias.add(transferencia);
                }
            }
        }
        return transferencias;
    }

    public List<Transferencia> buscarPorStatus(Status status) throws SQLException {
        List<Transferencia> transferencias = new ArrayList<>();
        String sql = "SELECT * FROM T_TRANSFERENCIA WHERE status_transferencia = ? ORDER BY timestamp_transferencia DESC";
        CarteiraDAO carteiraDAO = new CarteiraDAO();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status.name());
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transferencia transferencia = new Transferencia(
                            carteiraDAO.buscarPorId(rs.getInt("id_carteira_remetente")),
                            carteiraDAO.buscarPorId(rs.getInt("id_carteira_destinatario")),
                            rs.getDouble("valor_transferencia"),
                            Status.valueOf(rs.getString("status_transferencia")),
                            rs.getTimestamp("timestamp_transferencia").toLocalDateTime()
                    );

                    transferencia.setId(rs.getInt("id_transferencia"));
                    transferencias.add(transferencia);
                }
            }
        }
        return transferencias;
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM T_TRANSFERENCIA WHERE id_transferencia = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Transferência excluída com ID: " + id);
                return true;
            } else {
                System.out.println("[Warning] Nenhuma transferência encontrada com ID: " + id);
                return false;
            }
        }
    }

    public int contarTransferencias() throws SQLException {
        String sql = "SELECT COUNT(*) FROM T_TRANSFERENCIA";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public double calcularVolumeTotalTransferencias() throws SQLException {
        String sql = "SELECT SUM(valor_transferencia) FROM T_TRANSFERENCIA WHERE status_transferencia = 'CONCLUIDA'";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0.0;
    }


}
