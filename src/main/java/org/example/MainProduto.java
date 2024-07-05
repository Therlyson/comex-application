package org.example;

import  org.example.model.Categoria;
import org.example.model.Produto;
import org.example.services.CategoriaService;
import org.example.services.ProdutoService;
import org.example.utils.JPAutil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainProduto {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManager manager = JPAutil.getEntityManager();
        CategoriaService categoriaService = new CategoriaService(manager);
        ProdutoService produtoService = new ProdutoService(manager);

        while (true) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Consultar produtos por ordem de nome");
            System.out.println("4. Alterar produto");
            System.out.println("5. Remover Produto");
            System.out.println("6. Sair");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do produto: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite a descrição do produto: ");
                    String descricao = scanner.nextLine();
                    System.out.print("Digite o preço do produto: ");
                    BigDecimal preco = scanner.nextBigDecimal();

                    boolean cadastrar = true;
                    List<Categoria> categoriasEscolhidas = new ArrayList<>();
                    while(cadastrar){
                        List<Categoria> categoriasExistentes = categoriaService.listarCategorias();
                        if(categoriasExistentes.isEmpty()){
                            System.out.println("Não existem categorias cadastradas no momento, impossivel cadastrar um produto!");
                            break;
                        }
                        System.out.println("\nLista de categorias disponiveis:");
                        for(Categoria c: categoriasExistentes){
                            System.out.println(c);
                        }

                        System.out.print("\nDigite o ID da categoria do novo produto: ");
                        long categoriaId = scanner.nextLong();
                        scanner.nextLine();
                        Categoria categoria = categoriaService.buscarCategoriaId(categoriaId);
                        categoriasEscolhidas.add(categoria);

                        System.out.println("Deseja adicionar mais uma categoria(N/S): ");
                        String adicionar = scanner.nextLine();
                        if(adicionar.equalsIgnoreCase("N")){
                            cadastrar = false;
                        }
                    }

                    if(!cadastrar){
                        Produto produto = new Produto(nome, descricao, preco, categoriasEscolhidas);
                        produtoService.cadastrarProduto(produto);
                    }
                    break;

                case 2:
                    List<Produto> produtos = produtoService.listarProdutos();
                    for (Produto p : produtos) {
                        System.out.println(p);
                    }
                    break;

                case 3:
                    List<String> produtosPorNome = produtoService.consultarProdutosPorNome();
                    for (String ps : produtosPorNome) {
                        System.out.println(ps);
                    }
                    break;

                case 4:
                    System.out.print("Digite o ID do produto a ser alterado: ");
                    long idAlterar = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print("Digite o novo nome do produto: ");
                    String novoNome = scanner.nextLine();
                    System.out.print("Digite a nova descrição do produto: ");
                    String novaDescricao = scanner.nextLine();
                    System.out.print("Digite o novo preço do produto: ");
                    BigDecimal novoPreco = scanner.nextBigDecimal();

                    Produto produtoAlterado = produtoService.alterarProduto(idAlterar, novoNome, novaDescricao, novoPreco);
                    System.out.println(produtoAlterado);
                    break;

                case 5:
                    System.out.println("Digite o ID do produto a ser removido:");
                    long idRemover = scanner.nextLong();
                    scanner.nextLine();

                    produtoService.removerProduto(idRemover);
                    break;

                case 6:
                    System.out.println("Saindo...");
                    scanner.close();
                    manager.close();
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
