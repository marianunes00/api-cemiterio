package br.com.ifsertao.apicemiterio.service;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.ifsertao.apicemiterio.entity.Sepultura;
import br.com.ifsertao.apicemiterio.entity.Servico;
import br.com.ifsertao.apicemiterio.repository.ServicoRepository;

@Service
public class ServicoService {

    private final ServicoRepository repository;

    //construtor que inicializa a variavel repositorio do tipo de servico repositorio
    public ServicoService(ServicoRepository repository){
        this.repository = repository;
    }

    //agora criar o metodo par salvar
    public Servico salvar(Servico servico){
        if(servico.getSepultura()==null){
            throw new RuntimeException("O servico precisa estar relacionado com alguma sepultura!");
        }

       

        return repository.save(servico);
    }

    //agora para listar os servicos criados
    public List<Servico> listarTodos(){
        //método findAll() é fornecido pelo Spring Data JPA. Ele serve para buscar todos os registros de uma entidade no banco de dados e os retorna em formato de List
        return repository.findAll();
    }

    //buscar por id de servico
    public Servico buscarPorId(Long id){
        //busca um registro específico no banco de dados usando seu identificador único (ID),
        // se não achar lança a excessão
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Servico não encontrado!"));
    }

    //agora pra atualizar o servico
    public Servico atualizar(Long id, Servico servico){
        buscarPorId(id);
        servico.setIdServico(id);
        return repository.save(servico);
    }

    public void deletar(Long id){
        //primeiro procura o id e depois só deleta
        buscarPorId(id);
        repository.deleteById(id);
    }


}
