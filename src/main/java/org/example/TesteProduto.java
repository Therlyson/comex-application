package org.example;

import org.example.model.Produto;
import org.example.services.ProdutoService;

import java.util.List;

public class TesteProduto {
    public static void main(String[] args) {
        Produto produto1 = new Produto("Lille", "Perfume Lille boticário", 200.0);
        Produto produto2 = new Produto("Garrafa Flamengo", "Garrafa da loja oficial do mengão", 130.0);

        ProdutoService produtoService = new ProdutoService();

//        produtoService.cadastrarProduto(produto1);
//        produtoService.cadastrarProduto(produto2);

        List<Produto> produtos = produtoService.listarProdutos();
        for(Produto p: produtos){
            System.out.println(p);
        }

        System.out.println();
        List<String> produtosPorNome = produtoService.consultarProdutosPorNome();
        for(String ps: produtosPorNome){
            System.out.println(ps);
        }

        System.out.println();
        Produto pt = produtoService.alterarProduto(2L,"Malbec", "Uma fragância maravilhosa", 250.0);
        System.out.println(pt);

        produtoService.removerProduto(2L);
    }

}
