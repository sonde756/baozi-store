package br.com.baozi.controller;

import br.com.baozi.model.Cliente;
import br.com.baozi.model.Pedido;
import br.com.baozi.model.Produto;
import br.com.baozi.repository.ClienteRepository;
import br.com.baozi.repository.PedidoRepository;
import br.com.baozi.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(PedidoRepository pedidoRepository,
                            ClienteRepository clienteRepository,
                            ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    /**
     * Corpo esperado:
     * { "cliente": {"id": 1}, "produto": {"id": 1}, "quantidade": 10 }
     */
    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody Pedido pedido) {
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null
                || pedido.getProduto() == null || pedido.getProduto().getId() == null
                || pedido.getQuantidade() == null || pedido.getQuantidade() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Cliente cliente = clienteRepository.findById(pedido.getCliente().getId()).orElse(null);
        Produto produto = produtoRepository.findById(pedido.getProduto().getId()).orElse(null);
        if (cliente == null || produto == null) {
            return ResponseEntity.badRequest().build();
        }

        pedido.setId(null);
        pedido.setCliente(cliente);
        pedido.setProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoRepository.save(pedido));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @RequestBody Pedido dados) {
        return pedidoRepository.findById(id).map(p -> {
            if (dados.getQuantidade() != null && dados.getQuantidade() > 0) {
                p.setQuantidade(dados.getQuantidade());
            }
            if (dados.getCliente() != null && dados.getCliente().getId() != null) {
                clienteRepository.findById(dados.getCliente().getId()).ifPresent(p::setCliente);
            }
            if (dados.getProduto() != null && dados.getProduto().getId() != null) {
                produtoRepository.findById(dados.getProduto().getId()).ifPresent(p::setProduto);
            }
            return ResponseEntity.ok(pedidoRepository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        if (!pedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
