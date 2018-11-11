package com.biricutico.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.biricutico.cursomc.domain.Categoria;
import com.biricutico.cursomc.domain.Cidade;
import com.biricutico.cursomc.domain.Cliente;
import com.biricutico.cursomc.domain.Endereco;
import com.biricutico.cursomc.domain.Estado;
import com.biricutico.cursomc.domain.Produto;
import com.biricutico.cursomc.domain.enums.TipoCliente;
import com.biricutico.cursomc.repositories.CategoriaRepository;
import com.biricutico.cursomc.repositories.CidadeRepository;
import com.biricutico.cursomc.repositories.ClienteRepository;
import com.biricutico.cursomc.repositories.EnderecoRepository;
import com.biricutico.cursomc.repositories.EstadoRepository;
import com.biricutico.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;	
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
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
		
		////
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		//no próprio construtor já fazemos a associação de muitos pra um
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		//primeiro salva estado, pois o estado tem várias cidades
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "mariasilva@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27789865", "95986636"));
		
		Endereco e1 = new Endereco(null, "Rua Flores Camelia", "300", "Apto 303", "Jardimn", "26532600", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "56260655" ,cli1, c2);
		
		//cliente tem que conhecer os enderecos dele
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
	}
}
