package org.example;

import org.example.model.Categoria;
import org.example.services.CategoriaService;

import java.util.List;

public class TesteCategoria {
    public static void main(String[] args) {
//        Categoria categoria = new Categoria("Marketing", "Categoria de produtos de markenting");
//        Categoria categoria1 = new Categoria("Finanças", "finanças é aqui");
//
        CategoriaService categoriaService = new CategoriaService();
//        categoriaService.cadastrarCategoria(categoria);
//        categoriaService.cadastrarCategoria(categoria1);

        List<Categoria> categoriaList =  categoriaService.listarCategorias();
        for(Categoria c: categoriaList){
            System.out.println(c);
        }

        System.out.println();

        List<String> categoriaNome =  categoriaService.consultarCategoriasPorNome();
        for(String c: categoriaNome){
            System.out.println(c);
        }

        Categoria alterando = categoriaService.alterarCategoria(1L, "Plásticos", "Apenas produtos de plasticos");
        System.out.println(alterando);

        Boolean removendo = categoriaService.removerCategoria(2L);
        if(removendo){
            System.out.println("Categoria removida com sucesso");
        }
    }
}
