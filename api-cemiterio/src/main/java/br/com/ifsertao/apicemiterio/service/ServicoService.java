package br.com.ifsertao.apicemiterio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ifsertao.apicemiterio.entity.Servico;
import br.com.ifsertao.apicemiterio.repository.ServicoRepository;
import org.springframework.data.domain.PageRequest;


@Service
public class ServicoService {

    private final ServicoRepository repository;

    //construtor que inicializa a variavel repositorio do tipo de servico repositorio
    public ServicoService(ServicoRepository repository){
        this.repository = repository;
    }

    //agora criar o metodo para salvar
    public Servico salvar(Servico servico){
        if(servico.getSepultura()==null){
            throw new RuntimeException("O servico precisa estar relacionado com alguma sepultura!");
        }

       

        return repository.save(servico);
    }

    //agora para listar os servicos criados em pagina
    public Page<Servico> listarTodos(Pageable pageable){
        //método findAll() é fornecido pelo Spring Data JPA. Ele serve para buscar todos os registros de uma entidade no banco de dados e os retorna em formato de List
        return repository.findAll(pageable);
    }

    //recebe o texto digitado pelo usuario e a pagina desejada e a quantidade de registros, cria a pagina
    //cria a pagina usadno esses parametros, e esse objeto de pagina vai ser enviada para o repository
    //retorna o resultado do metodo que foi criado no repository
    public Page<Servico> buscarPorTipo(String tipo, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByTipoServicoContainingIgnoreCase(tipo, pageable);
    }

    //faz a mesma coisa do outro metodo, só que agora busca por status, cria aapgina pra retornar só o desejado
    public Page<Servico> buscarPorStatus(String tipo, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return repository.findByStatusServicoContainingIgnoreCase(tipo, pageable);
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