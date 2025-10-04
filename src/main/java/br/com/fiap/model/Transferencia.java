package br.com.fiap.model;

import java.sql.SQLException;
import java.time.LocalDateTime;
import br.com.fiap.dao.TransferenciaDAO;


public class Transferencia {
    private int id;
    private Carteira carteiraRemetente;
    private Carteira carteiraDestinatario;
    private double valorTransferencia;
    private Status status;
    private LocalDateTime dataHora;

    public Transferencia(Carteira carteiraRemetente, Carteira carteiraDestinatario, double valorTransferencia, Status status, LocalDateTime dataHora) {
        this.carteiraRemetente = carteiraRemetente;
        this.carteiraDestinatario = carteiraDestinatario;
        this.valorTransferencia = valorTransferencia;
        this.status = status;
        this.dataHora = dataHora;
    }

    public void validarTransferencia(Carteira carteiraRemetente, Carteira carteiraDestinatario, double valorTransferencia) throws SQLException {
        TransferenciaDAO dao = new TransferenciaDAO();
        dao.inserir(this);

        if (carteiraRemetente.getSaldo() >= valorTransferencia) {
            carteiraRemetente.removerSaldoCarteira(valorTransferencia, carteiraRemetente);
            carteiraDestinatario.adicionarSaldoCarteira(valorTransferencia, carteiraDestinatario);
            this.status = Status.CONCLUIDA;

            System.out.printf("\n[Resumo] TRANSFERENCIA REALIZADA\n Id: %d\n Usuário: %s\n Destino: %s\n Valor: R$ %.2f\n Saldo restante: R$ %.2f\n",
                    this.id,
                    carteiraRemetente.getNomeUsuario(),
                    carteiraDestinatario.getNomeUsuario(),
                    valorTransferencia,
                    carteiraRemetente.getSaldoCarteira(carteiraRemetente.getId())
            );
        }
        else {
            this.status = Status.ERRO;
            System.out.printf("\n[Resumo] TRANSFERENCIA NÃO REALIZADA\n Id: %d\n Motivo: saldo insuficiente\n Usuário: %s\n Destino: %s\n Valor: R$ %.2f\n Saldo restante: R$ %.2f\n",
                    this.id,
                    carteiraRemetente.getNomeUsuario(),
                    carteiraDestinatario.getNomeUsuario(),
                    valorTransferencia,
                    carteiraRemetente.getSaldoCarteira(carteiraRemetente.getId())

            );
        }

        dao.atualizar(this);
    }


    public void exibir() {
        System.out.printf("\n[Resumo] DADOS DA TRANSFERENCIA #%d:\n Usuário: %s\n Destino: %s\n Valor: R$ %.2f\n Status: %s\n",
                this.id,
                this.carteiraRemetente.getNomeUsuario(),
                this.carteiraDestinatario.getNomeUsuario(),
                valorTransferencia,
                this.status
        );
    }

    public Carteira getCarteiraRemetente() {
        return carteiraRemetente;
    }

    public Carteira getCarteiraDestinatario() {
        return carteiraDestinatario;
    }

    public LocalDateTime getDataTransferencia() {
        return dataHora;
    }

    public Status getStatus() {
        return status;
    }

    public double getValor() {
        return valorTransferencia;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

}
