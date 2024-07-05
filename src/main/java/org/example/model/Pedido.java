package org.example.model;

import org.example.model.enums.TipoDesconto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;
    private BigDecimal desconto;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_desconto")
    private TipoDesconto tipoDesconto;
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cLiente;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemDePedido> itens;

    public Pedido(BigDecimal desconto, TipoDesconto tipoDesconto, Cliente cLiente) {
        this.dataCadastro = LocalDate.now();
        this.desconto = desconto;
        this.tipoDesconto = tipoDesconto;
        this.cLiente = cLiente;
        this.itens = new ArrayList<>();
    }

    public Pedido(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return dataCadastro;
    }

    public void setData(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public TipoDesconto getTipoDesconto() {
        return tipoDesconto;
    }

    public void setTipoDesconto(TipoDesconto tipoDesconto) {
        this.tipoDesconto = tipoDesconto;
    }

    public Cliente getcLiente() {
        return cLiente;
    }

    public void setcLiente(Cliente cLiente) {
        this.cLiente = cLiente;
    }

    public List<ItemDePedido> getItens() {
        return itens;
    }

    public void adicionarItem(ItemDePedido item){
        item.setPedido(this);
        this.itens.add(item);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", dataCadastro=" + dataCadastro +
                ", desconto=" + desconto +
                ", tipoDesconto=" + tipoDesconto +
                ", cliente=" + (cLiente != null ? cLiente.getId() : null) +
                ", itens=" + itens.stream().map(ItemDePedido::getId).toList() +
                '}';
    }
}
