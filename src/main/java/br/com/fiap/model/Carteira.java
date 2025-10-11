package br.com.fiap.model;
import java.sql.SQLException;
import java.util.List;

import br.com.fiap.dao.CarteiraDAO;
import br.com.fiap.dao.InvestimentoDAO;
import br.com.fiap.dao.TransacaoDAO;
import br.com.fiap.dao.TransferenciaDAO;

import java.time.LocalDateTime;

public class Carteira {
    private int id;
    private Usuario usuario;
    private List<Investimento> investimentos;
    private List<Transferencia> transferencias;
    private double saldoEmReal;

    public Carteira() {

    }

    public Carteira( Usuario usuario) {
        this.usuario = usuario;
        this.saldoEmReal = 0;
    }

    public void adicionarSaldo(double valor) throws SQLException {
        CarteiraDAO dao = new CarteiraDAO();
        dao.depositar(this, valor);
        this.saldoEmReal += valor;

        System.out.printf("[Log] R$ %.2f foi adicionado na conta de %s \n", valor, this.usuario.getNome());
    }

    public void sacarSaldo (double valor, String contaBancaria) {
        System.out.printf("\n[Log] %s está realizando saque...\n", this.usuario.getNome());
        if (valor <= this.saldoEmReal) {
            this.saldoEmReal -= valor;
            System.out.printf("[Log] O valor de R$ %.2f foi enviado a conta %s \n", valor, contaBancaria);
        }
        else {
            System.out.printf("[Log] %s, saldo suficiente para realizar este saque. \n", usuario.getNome());
        }
    }

    public void comprarMoeda(Moeda moeda, double quantidadeMoeda) throws SQLException {
        System.out.printf("\n[Log] %s está realizando compra de moeda...\n", this.usuario.getNome());
        double valorTaxa = (quantidadeMoeda*moeda.cotacaoParaReal) * usuario.getTaxaTransacao();
        double valorLiquido = (quantidadeMoeda*moeda.cotacaoParaReal) ;

        Investimento investimento = consultarInvestimentos(moeda, this);

        if (investimento == null) {
            investimento = new Investimento(moeda, this);
            InvestimentoDAO dao = new InvestimentoDAO();
            int idInvestimento = dao.inserir(investimento);
            investimento.setId( idInvestimento  );
            System.out.println(idInvestimento + " id investimento");
            //this.investimentos.add(investimento);
        }

        Transacao novaTransacao = new Transacao(
                this,
                investimento,
                investimento.getMoeda(),
                TipoOperacao.COMPRA,
                valorLiquido+valorTaxa,
                valorLiquido,
                valorTaxa,
                quantidadeMoeda,
                Status.PENDENTE,
                LocalDateTime.now()
        );

        TransacaoDAO transacaoDAO = new TransacaoDAO();
        transacaoDAO.inserir(novaTransacao);

        //investimento.getTransacoes().add(novaTransacao);
        investimento.validarTransacao(quantidadeMoeda, TipoOperacao.COMPRA, novaTransacao, valorLiquido, valorTaxa);
    }

    public void venderMoeda(Moeda moeda, double quantidadeMoeda) throws SQLException {
        System.out.printf("\n[Log] %s está realizando venda de moeda...\n", this.usuario.getNome());

        double valorTaxa = (quantidadeMoeda * moeda.cotacaoParaReal) * usuario.getTaxaTransacao();
        double valorLiquido = (quantidadeMoeda * moeda.cotacaoParaReal) - valorTaxa;

        Investimento investimento = consultarInvestimentos(moeda, this);

        if (investimento == null) {
            System.out.print("[Log] Usuario não possui esta moeda. \n");
            return;
        }

        Transacao novaTransacao = new Transacao(
                this,
                investimento,
                investimento.getMoeda(),
                TipoOperacao.VENDA,
                valorLiquido+valorTaxa,
                valorLiquido,
                valorTaxa,
                quantidadeMoeda,
                Status.PENDENTE,
                LocalDateTime.now()
        );

        TransacaoDAO transacaoDAO = new TransacaoDAO();
        transacaoDAO.inserir(novaTransacao);

        //investimento.getTransacoes().add(novaTransacao);
        investimento.validarTransacao(quantidadeMoeda, TipoOperacao.VENDA, novaTransacao, valorLiquido, valorTaxa);
    }

    public void transferirSaldo(Usuario usuarioRemetente, Usuario usuarioDestinatario, double valorTransferencia) throws SQLException {
        System.out.printf("\n[Log] %s está realizando transferencia de saldo...\n", usuarioRemetente.getNome());

        Transferencia novaTransferencia = new Transferencia(
                usuarioRemetente.getCarteira(),
                usuarioDestinatario.getCarteira(),
                valorTransferencia,
                Status.PENDENTE,
                LocalDateTime.now()
        );

        novaTransferencia.validarTransferencia(
            usuarioRemetente.getCarteira(),
            usuarioDestinatario.getCarteira(),
            valorTransferencia
        );

        usuarioRemetente.getCarteira().getTransferencias().add(novaTransferencia);
        usuarioDestinatario.getCarteira().getTransferencias().add(novaTransferencia);
    }

    public void consultarTransacao (int id) {
        System.out.printf("\n[Log] %s está realizando consulta transacao...\n", this.usuario.getNome());
        for (Investimento investimento : this.investimentos) {
//            for (Transacao transacao : investimento.getTransacoes()) {
//                if (transacao.getId() == id) {
//                    transacao.exibir();
//                    return;
//                }
//            }
        }

        System.out.printf("[Log] Não há transação com o id %d para %s.\n", id, this.getNomeUsuario());
    }


    public void consultarTransferencia(int idTransferencia) throws SQLException  {
        System.out.printf("\n[Log] %s está realizando consulta transferencias...\n", this.getNomeUsuario());

        TransferenciaDAO dao = new TransferenciaDAO();
        Transferencia transferencia = dao.consultar(idTransferencia, this.id);

        if (transferencia != null) {
            transferencia.exibir();
        }
        else {
            System.out.printf("[Log] Não há transferência com o id %d para %s.\n", idTransferencia, this.getNomeUsuario());
        }
    }


    private Investimento consultarInvestimentos (Moeda moeda, Carteira carteira) throws SQLException {
        InvestimentoDAO dao = new InvestimentoDAO();


        return dao.consultarInvestimento(moeda, carteira);
    }

    public boolean removerSaldo(double valor) {
        if (valor <= this.saldoEmReal) {
            this.saldoEmReal -= valor;
            System.out.printf("[Log] R$ %.2f foi removido da conta de %s \n", valor, this.usuario.getNome());
            return true;
        }
        return false;
    }

    public void removerSaldoCarteira(double valor, Carteira carteira) {
        try {
            CarteiraDAO dao = new CarteiraDAO();
            dao.sacar(carteira, valor);
            dao.fecharConexao();

            System.out.printf("[Log] R$ %.2f foi removido da conta de %s \n", valor, carteira.usuario.getNome());
        } catch (SQLException e) {
            System.out.println("[Erro] Não foi possível remover saldo: " + e.getMessage());
        }
    }


    public void adicionarSaldoCarteira(double valor, Carteira carteira) {
        try {
            CarteiraDAO dao = new CarteiraDAO();
            dao.depositar(carteira, valor);
            dao.fecharConexao();
            System.out.printf("[Log] R$ %.2f foi adicionado na conta de %s \n", valor, carteira.usuario.getNome());
        } catch (SQLException e) {
            System.out.println("[Erro] Não foi possível adicionar saldo: " + e.getMessage());
        }
    }

    public double getSaldoCarteira(int id) {
        double saldo = 0.0;
        try {
            CarteiraDAO dao = new CarteiraDAO();
            saldo = dao.getSaldo(id);
            dao.fecharConexao();
        } catch (SQLException e) {
            System.out.println("[Erro] Não foi possível consultar saldo: " + e.getMessage());
        }
        return saldo;
    }


    //getters
    public Usuario getUsuario() {
        return this.usuario;
    }

    public int getId() {
        return this.id;
    }

    public List<Transferencia> getTransferencias () {
        return this.transferencias;
    }

    public double getSaldo() {
        return this.saldoEmReal;
    }

    public String getNomeUsuario(){
        return usuario.getNome();
    }

    //setters
    public void setIdCarteira(int id){
        this.id = id;
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public void setSaldoCarteira(double saldo){
        this.saldoEmReal = saldo;
    }
}
