package org.example;

import org.example.model.Categoria;
import org.example.services.CategoriaService;
import org.example.utils.JPAutil;

import javax.persistence.EntityManager;
import java.util.List;

public class MainCategoria {
    public static void main(String[] args) {
        Categoria categoria = new Categoria("Marketing", "Categoria de produtos de markenting");
        Categoria categoria1 = new Categoria("Finanças", "finanças é aqui");

        EntityManager em = JPAutil.getEntityManager();
        CategoriaService categoriaService = new CategoriaService(em);
        categoriaService.cadastrarCategoria(categoria);
        categoriaService.cadastrarCategoria(categoria1);

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

        categoriaService.removerCategoria(2L);
        em.close();
    }
}
