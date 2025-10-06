package br.com.fiap.dao;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Status;
import br.com.fiap.model.Transferencia;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;


public class TransferenciaDAO {
    private Connection conexao;

    public TransferenciaDAO() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public int inserir(Transferencia transferencia) throws SQLException {
        String sql = "INSERT INTO T_TRANSFERENCIA (id_transferencia, id_carteira_remetente, id_carteira_destinatario, valor_transferencia, status_transferencia, timestamp_transferencia) VALUES (SEQ_TRANSFERENCIA.NEXTVAL, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql, new String[]{"id_transferencia"})) {
            ps.setInt(1, transferencia.getCarteiraRemetente().getId());
            ps.setInt(2, transferencia.getCarteiraDestinatario().getId());
            ps.setDouble(3, transferencia.getValor());
            ps.setString(4, transferencia.getStatus().name());
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(transferencia.getDataTransferencia()));

            ps.executeUpdate();
            try (java.sql.ResultSet rs = ps.getGeneratedKeys()) {
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
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
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

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, idCarteiraEnvolvida);
            ps.setInt(3, idCarteiraEnvolvida);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
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


}
