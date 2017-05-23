package br.com.timberforest.pcp;

/**
 * Created by sander on 22/05/17.
 */

public class Pcp {

        private String ordemServico;
        private String maquina;
        private String cliente;
        private String dtChegada;
        private String dtSaida;
        private String dtPrevisao;

        public Pcp() {
        }

        public Pcp(String ordemServico, String maquina, String cliente, String dtChegada, String dtSaida, String dtPrevisao) {
            this.ordemServico = ordemServico;
            this.maquina = maquina;
            this.cliente = cliente;
            this.dtChegada = dtChegada;
            this.dtSaida = dtSaida;
            this.dtPrevisao = dtPrevisao;
        }

        public String getOrdemServico() {
            return ordemServico;
        }

        public void setOrdemServico(String ordemServico) {
            this.ordemServico = ordemServico;
        }

        public String getMaquina() {
            return maquina;
        }

        public void setMaquina(String maquina) {
            this.maquina = maquina;
        }

        public String getCliente() {
            return cliente;
        }

        public void setCliente(String cliente) {
            this.cliente = cliente;
        }

        public String getDtChegada() {
            return dtChegada;
        }

        public void setDtChegada(String dtChegada) {
            this.dtChegada = dtChegada;
        }

        public String getDtSaida() {
            return dtSaida;
        }

        public void setDtSaida(String dtSaida) {
            this.dtSaida = dtSaida;
        }

        public String getDtPrevisao() {
            return dtPrevisao;
        }

        public void setDtPrevisao(String dtPrevisao) {
            this.dtPrevisao = dtPrevisao;
        }
}