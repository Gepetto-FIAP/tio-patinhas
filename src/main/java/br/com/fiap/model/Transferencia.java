package br.com.fiap.model;


public class Transferencia {
    private static int contadorId = 0;
    private int id;
    private Carteira carteiraRemetente;
    private Carteira carteiraDestinatario;
    private double valorTransferencia;
    private Status status;
    private String data;
    private String hora;


    public Transferencia(Carteira carteiraRemetente, Carteira carteiraDestinatario, double valorTransferencia, Status status, String data, String hora) {
        this.id = contadorId++;
        this.carteiraRemetente = carteiraRemetente;
        this.carteiraDestinatario = carteiraDestinatario;
        this.valorTransferencia = valorTransferencia;
        this.status = status;
        this.data = data;
        this.hora = hora;
    }

    public void validarTransferencia(Carteira carteiraRemetente, Carteira carteiraDestinatario, double valorTransferencia) {
        if (carteiraRemetente.removerSaldoCarteira(valorTransferencia, carteiraRemetente)) {

            carteiraDestinatario.adicionarSaldoCarteira(valorTransferencia,  carteiraDestinatario);
            this.status = Status.CONCLUIDA;


            System.out.printf("\n[Resumo] TRANSFERENCIA REALIZADA\n Usuário: %s\n Destino: %s\n Valor: R$ %.2f\n Saldo restante: R$ %.2f\n",
                    carteiraRemetente.getNomeUsuario(),
                    carteiraDestinatario.getNomeUsuario(),
                    valorTransferencia,
                    carteiraRemetente.getSaldoCarteira(carteiraRemetente.getId())
            );
        }
        else {
            this.status = Status.ERRO;
            System.out.printf("\n[Resumo] TRANSFERENCIA NÃO REALIZADA\n Motivo: saldo insuficiente\n Usuário: %s\n Destino: %s\n Valor: R$ %.2f\n Saldo restante: R$ %.2f\n",
                    carteiraRemetente.getNomeUsuario(),
                    carteiraDestinatario.getNomeUsuario(),
                    valorTransferencia,
                    carteiraRemetente.getSaldoCarteira(carteiraRemetente.getId())

            );
        }
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

    public double getId(){
        return id;
    }

}
