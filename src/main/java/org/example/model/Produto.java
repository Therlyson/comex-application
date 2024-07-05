package org.example.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    @ManyToMany
    @JoinTable(name = "produto_categorias", //nome da tabela de junção
            joinColumns = @JoinColumn(name = "produto_fk"), //nome da tabela principal na tabela de junção
            inverseJoinColumns = @JoinColumn(name = "categoria_fk")) //nome da outra tabela na tabela de junção
    private List<Categoria> categorias;

    public Produto(String nome, String descricao, BigDecimal preco, List<Categoria> categorias) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categorias = categorias;
    }

    public Produto(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public List<Categoria> getCategoria() {
        return categorias;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id = ").append(id)
            .append("\nnome = ").append(nome)
            .append("\ndescricao = ").append(descricao)
            .append("\npreco = ").append(preco)
            .append("\ncategorias = ").append(categorias)
                .append("\n");
        return sb.toString();
    }
}
