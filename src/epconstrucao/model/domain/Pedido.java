/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.model.domain;

/**
 *
 * @author Jair
 */
public class Pedido {
    private int idPedido;
    private int quantidade;
    private float valor;
    private Material material;
    private Venda venda;
    //private float precoTotal;

//    public float getPrecoTotal() {
//        return precoTotal;
//    }
//
//    public void setPrecoTotal(float precoTotal) {
//        this.precoTotal = precoTotal;
//    }    
    

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }
    
    
}
