package org.example.model;

import org.example.model.enums.TipoDescontoProduto;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "item_de_pedidos")
public class ItemDePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantidade;
    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario;
    private BigDecimal desconto;
    @ManyToOne(fetch = FetchType.LAZY)
    private Produto produto;
    @ManyToOne(fetch = FetchType.LAZY)
    private Pedido pedido;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_desconto")
    private TipoDescontoProduto tipoDesconto;

    public ItemDePedido(int quantidade, BigDecimal precoUnitario, BigDecimal desconto, Produto produto, Pedido pedido, TipoDescontoProduto tipoDesconto) {
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.desconto = desconto;
        this.produto = produto;
        this.pedido = pedido;
        this.tipoDesconto = tipoDesconto;
    }

    public ItemDePedido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public TipoDescontoProduto getTipoDesconto() {
        return tipoDesconto;
    }

    public void setTipoDesconto(TipoDescontoProduto tipoDesconto) {
        this.tipoDesconto = tipoDesconto;
    }

    public BigDecimal getTotal() {
        BigDecimal total = precoUnitario.multiply(new BigDecimal(quantidade));
        //total - ((total * desconto) / 100)
        return total.subtract((total.multiply(desconto)).divide(new BigDecimal(100)));
    }

    @Override
    public String toString() {
        return "ItemDePedido{" +
                "id=" + id +
                ", quantidade=" + quantidade +
                ", precoUnitario=" + precoUnitario +
                ", desconto=" + desconto +
                ", produto=" + produto +
                ", pedido=" + pedido +
                ", tipoDesconto=" + tipoDesconto +
                '}';
    }
}
