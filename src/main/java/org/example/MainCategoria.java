package org.example;

import org.example.model.Categoria;
import org.example.model.Produto;
import org.example.services.CategoriaService;
import org.example.services.ProdutoService;
import org.example.utils.JPAutil;

import javax.persistence.EntityManager;
import java.util.List;

public class MainCategoria {
    public static void main(String[] args) {
        Categoria categoria = new Categoria("Marketing", "Categoria de produtos de markenting");
        Categoria categoria1 = new Categoria("Finanças", "finanças é aqui");
        Categoria categoria2 = new Categoria("Perfumes", "Os melhores perfumes");

        EntityManager em = JPAutil.getEntityManager();
        CategoriaService categoriaService = new CategoriaService(em);
        categoriaService.cadastrarCategoria(categoria);
        categoriaService.cadastrarCategoria(categoria1);
        categoriaService.cadastrarCategoria(categoria2);

        List<Categoria> categoriaList =  categoriaService.listarCategorias();
        for(Categoria c: categoriaList){
            System.out.println(c);
        }

        System.out.println();

        List<String> categoriaNome =  categoriaService.consultarCategoriasPorNome();
        for(String c: categoriaNome){
            System.out.println(c);
        }

        Categoria categoriaBusca = categoriaService.buscarCategoriaId(1L);
        categoriaBusca.setNome("Category");
        Categoria alterando = categoriaService.alterarCategoria(categoriaBusca);
        System.out.println(alterando);



        //ideia para quando for remover uma categoria mas ela está relacionada com um produto
        Long id = 2l;
        ProdutoService ps = new ProdutoService(em);
        List<Produto> produtos = ps.listarProdutos();
        for (Produto produto : produtos) {
            for(Categoria categoriateste: produto.getCategoria()){
                if(categoriateste.getId() == id){
                    System.out.println("Impossivel remover categoria, remova primeiro o produto");
                }
            }
        }
        categoriaService.removerCategoria(2L);
        em.close();
    }
}
