package com.example.av2.config;

import com.example.av2.model.Produto;
import com.example.av2.model.Usuario;
import com.example.av2.repository.ProdutoRepository;
import com.example.av2.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Criar usuários de teste
        if (usuarioRepository.count() == 0) {
            Usuario admin = Usuario.builder()
                    .username("admin")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Usuario.Role.ADMIN)
                    .enabled(true)
                    .build();
            
            Usuario user = Usuario.builder()
                    .username("user")
                    .email("user@example.com")
                    .password(passwordEncoder.encode("user123"))
                    .role(Usuario.Role.USER)
                    .enabled(true)
                    .build();
            
            usuarioRepository.save(admin);
            usuarioRepository.save(user);
            
            System.out.println("✅ Usuários de teste criados:");
            System.out.println("👤 Admin: username=admin, password=admin123");
            System.out.println("👤 User: username=user, password=user123");
        }
        
        // Criar produtos de teste
        if (produtoRepository.count() == 0) {
            Produto produto1 = new Produto();
            produto1.setNome("Smartphone Samsung Galaxy S21");
            produto1.setDescricao("Smartphone Android com câmera de 64MP e tela de 6.2 polegadas");
            produto1.setPreco(new BigDecimal("2999.99"));
            produto1.setQuantidadeEstoque(50);
            produto1.setCategoria("Eletrônicos");
            
            Produto produto2 = new Produto();
            produto2.setNome("Notebook Dell Inspiron");
            produto2.setDescricao("Notebook com processador Intel i5, 8GB RAM e SSD 256GB");
            produto2.setPreco(new BigDecimal("3499.99"));
            produto2.setQuantidadeEstoque(30);
            produto2.setCategoria("Informática");
            
            Produto produto3 = new Produto();
            produto3.setNome("Fone de Ouvido Bluetooth");
            produto3.setDescricao("Fone sem fio com cancelamento de ruído ativo");
            produto3.setPreco(new BigDecimal("299.99"));
            produto3.setQuantidadeEstoque(100);
            produto3.setCategoria("Acessórios");
            
            produtoRepository.save(produto1);
            produtoRepository.save(produto2);
            produtoRepository.save(produto3);
            
            System.out.println("✅ Produtos de teste criados");
        }
    }
} 