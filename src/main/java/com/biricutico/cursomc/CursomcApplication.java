package com.biricutico.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.biricutico.cursomc.domain.Categoria;
import com.biricutico.cursomc.domain.Produto;
import com.biricutico.cursomc.repositories.CategoriaRepository;
import com.biricutico.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Pescaria");
		Categoria cat2 = new Categoria(null, "Esportes");
		
		Produto p1 = new Produto(null, "Bicicleta", 528.00);
		Produto p2 = new Produto(null, "Carro", 50000.00);
		Produto p3 = new Produto(null, "Lancha", 85000.00);
		
		//ao se "cadastrar" os produtos, é preciso dizer às categorias quais produtos elas tem, de acordo com o diagrama de planejamento:
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		//agora vamos fazer os produtos saberem à quais categorias eles pertencem
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		//salva as categorias e os produtos
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
	}
}
