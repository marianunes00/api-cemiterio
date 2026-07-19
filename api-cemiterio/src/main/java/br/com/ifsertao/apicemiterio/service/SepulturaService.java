package br.com.ifsertao.apicemiterio.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.ifsertao.apicemiterio.entity.Sepultura;
import br.com.ifsertao.apicemiterio.repository.SepulturaRepository;

@Service
public class SepulturaService {

    private final SepulturaRepository repository;

    public SepulturaService(SepulturaRepository repository) {
        this.repository = repository;
    }

    public Sepultura salvar(Sepultura sepultura){
        //regra de negocio que verifica o status da sepultura antes de salvar
       if (!sepultura.getStatusSepultura().equals("DISPONIVEL") &&
            !sepultura.getStatusSepultura().equals("OCUPADA")) {
            throw new RuntimeException("Status da sepultura inválido.");
        }
        return repository.save(sepultura);
    }

    public Page<Sepultura> listarTodos(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Sepultura buscarPorId(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Sepultura não encontrada."));
    }

    public Sepultura atualizar(Long id, Sepultura sepultura){
        buscarPorId(id);

        sepultura.setIdSepultura(id);

        return repository.save(sepultura);
    }

    // - Verificar se há falecidos vinculados à sepultura antes da exclusão.
    public void deletar(Long id){
       
        //não permite excluir uma sepultura que tenha falecido nela
        Sepultura sepultura = buscarPorId(id);
        //faz a verificacao se a sepultura não está vazia, se não estiver, lança a exceção informando
        if (!sepultura.getFalecidos().isEmpty()) {
        throw new RuntimeException("Não é possível excluir uma sepultura que possui falecidos cadastrados.");
}       
        repository.deleteById(id);
        }
    }

// - Impedir o cadastro de duas sepulturas com a mesma identificação.
