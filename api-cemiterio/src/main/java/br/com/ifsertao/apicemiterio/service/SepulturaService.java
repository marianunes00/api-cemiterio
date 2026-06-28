package br.com.ifsertao.apicemiterio.service;

import java.util.List;

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
        return repository.save(sepultura);
    }

    public List<Sepultura> listarTodos(){
        return repository.findAll();
    }

    public Sepultura buscarPorId(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Sepultura não encontrada."));
    }

    public Sepultura atualizar(Long id, Sepultura sepultura){
        buscarPorId(id);

        sepultura.setIdSepultura(id);

        return repository.save(sepultura);
    }

    public void deletar(Long id){
        buscarPorId(id);

        repository.deleteById(id);
    }
    }
    //Falta adicionar as regras de negócio, tipo:antes de excluir uma sepultura deve-se verificar
    //se há falecido vinculado a ela.
// - Verificar se há falecidos vinculados à sepultura antes da exclusão.
// - Impedir o cadastro de duas sepulturas com a mesma identificação.
// - Validar o status da sepultura.
