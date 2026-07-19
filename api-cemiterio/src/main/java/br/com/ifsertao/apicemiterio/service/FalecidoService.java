package br.com.ifsertao.apicemiterio.service;g

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.ifsertao.apicemiterio.entity.Falecido;
import br.com.ifsertao.apicemiterio.entity.Sepultura;
import br.com.ifsertao.apicemiterio.repository.FalecidoRepository;
import br.com.ifsertao.apicemiterio.repository.SepulturaRepository;

@Service
public class FalecidoService {

    private final FalecidoRepository repository;
    private final SepulturaRepository sepulturaRepository;

    public FalecidoService(FalecidoRepository repository, SepulturaRepository sepulturaRepository) {
        this.repository = repository;
        this.sepulturaRepository = sepulturaRepository;
    }

//método para salvar(cadastrar) um falecido no sistema
// /*Nessa condicional é onde adicionamos a regra de negócio: atráves de falecido é possivel acessar a sepultura a qual ele está vinculado, acessando o status da sepultura verificamos se ela está disponível.Se ocupada a exceção é lançada e o falecido não é cadastrado.*/
    public Falecido salvar(Falecido falecido){

    if (falecido.getSepultura() == null) {
        throw new RuntimeException("É necessário informar uma sepultura.");
    }

    // Busca a sepultura no banco pelo ID
    Sepultura sepultura = sepulturaRepository.findById(
            falecido.getSepultura().getIdSepultura())
            .orElseThrow(() -> new RuntimeException("Sepultura não encontrada."));

    // Verifica se ela está ocupada
    if (sepultura.getStatusSepultura().equals("OCUPADA")) {
        throw new RuntimeException("Sepultura ocupada.");
    }

    // Associa a sepultura encontrada ao falecido
    falecido.setSepultura(sepultura);

    // (Opcional) muda o status da sepultura para ocupada
    sepultura.setStatusSepultura("OCUPADA");
    sepulturaRepository.save(sepultura);

    // Salva o falecido
    return repository.save(falecido);
}

//Método que lista todos os falecidos cadastrados no sistema, chamando o método findAll() do Repository.
    public Page<Falecido> listarTodos(Pageable pageable){
        return repository.findAll(pageable);
}
//Método que busca um falecido pelo ID informado, se ele existir vai devolver o falecido referente, se não encontrar lança uma exceção.
    public Falecido buscarPorId(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Falecido não encontrado."));
    }
// Método responsável por atualizar os dados de um falecido.
    public Falecido atualizar(Long id, Falecido falecido){

//Utilizando o método buscaPorId que criamos vamos verificar se o ID passado realmentre pertence a um falecido cadastrado no banco.
// se não, o método buscarPorId() lançará uma exceção.
        buscarPorId(id);

        //Se o ID corresponder a um falecido vamos definir esse id para o objeto ser atualizado 
        // corretamente e não correr o risco de uma tentativa de inserção pelo método save() e sim de atualização.
        falecido.setIdFalecido(id);

        //Por fim vamos atualizar os dados do falecido com o método save() também.
        return repository.save(falecido);
    }
    public void deletar(Long id){
        //Verificamos se o falecido realmente existe antes de deletar buscando pelo id.
        buscarPorId(id);
        //ao confirmar o falecido é excluido.
        repository.deleteById(id);
    }
}
//OBS: lembrar de adicioar regras de negócio ao service, tipo:ao atualizar um falecido só podemos atualizar a seepultura por 
// uma que estiver disponivel,além disso se uma sepultura está disponivel e e um falecido for cadastrado nela ,automaticamente o status
//deve mudar para ocupado ou vice versa.