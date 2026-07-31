// URL base da API de Serviços.
const API = "http://localhost:8080/servicos";

// URL da API de Sepulturas.
// Será utilizada para preencher o select de sepulturas.
const API_SEPULTURAS = "http://localhost:8080/sepulturas";


/*
 * Armazena o ID do serviço que está sendo editado.
 *
 * Quando o valor for null, significa que estamos
 * cadastrando um novo serviço.
 */
let idServicoEdicao = null;


/*
 * Armazena a página que está sendo
 * exibida atualmente.
 *
 * No Spring Boot, a primeira página é 0.
 */
let paginaAtual = 0;


/*
 * Armazena a quantidade total de páginas
 * retornada pela API.
 */
let totalPaginas = 0;



/*
 * Função responsável por buscar um serviço
 * através do seu ID e preencher o formulário
 * para edição.
 */
async function editarServico(id) {

    try {

        /*
         * Busca na API o serviço selecionado.
         */
        const resposta =
            await fetch(`${API}/${id}`);


        /*
         * Verifica se a consulta foi realizada
         * com sucesso.
         */
        if (!resposta.ok) {

            throw new Error(
                "Erro ao buscar serviço."
            );

        }


        /*
         * Converte a resposta para JSON.
         */
        const servico =
            await resposta.json();


        /*
         * Guarda o ID do serviço que está
         * sendo editado.
         */
        idServicoEdicao = id;


        /*
         * Preenche os campos do modal com
         * os dados atuais do serviço.
         */
        document.getElementById("dataServico").value =
            servico.dataServico;

        document.getElementById("tipoServico").value =
            servico.tipoServico;

        document.getElementById("statusServico").value =
            servico.statusServico;

        document.getElementById("notificacaoServico").value =
            servico.notificacaoServico;


        /*
         * Carrega as sepulturas no select.
         */
        await carregarSepulturas();


        /*
         * Caso o serviço possua uma sepultura,
         * seleciona automaticamente a atual.
         */
        if (servico.sepultura) {

            document.getElementById("sepulturaServico").value =
                servico.sepultura.idSepultura;

        }


        /*
         * Altera o título do modal para indicar
         * que estamos realizando uma edição.
         */
        document.getElementById("tituloModal").textContent =
            "Editar Serviço";


        /*
         * Abre o modal.
         */
        const modalElemento =
            document.getElementById("modalServico");

        const modal =
            bootstrap.Modal.getOrCreateInstance(
                modalElemento
            );

        modal.show();


    } catch (erro) {

        console.error(
            "Erro ao carregar serviço:",
            erro
        );

    }

}



/*
 * Função responsável por buscar os serviços
 * cadastrados na API utilizando paginação.
 */
async function listarServicos(page = 0) {

    try {

        /*
         * Realiza uma requisição GET informando
         * a página desejada e a quantidade
         * de registros por página.
         */
        const resposta = await fetch(
            `${API}?page=${page}&size=10`
        );


        /*
         * Verifica se a requisição foi realizada
         * com sucesso.
         */
        if (!resposta.ok) {

            throw new Error(
                "Erro ao buscar serviços."
            );

        }


        /*
         * Converte a resposta para JSON.
         */
        const dados =
            await resposta.json();


        /*
         * Guarda o número da página atual
         * retornado pelo Spring Boot.
         */
        paginaAtual = dados.number;


        /*
         * Guarda a quantidade total de páginas.
         */
        totalPaginas = dados.totalPages;


        /*
         * Preenche a tabela com os registros
         * da página atual.
         */
        preencherTabela(dados.content);


        /*
         * Atualiza as informações e botões
         * da paginação.
         */
        atualizarPaginacao();


    } catch (erro) {

        console.error(
            "Erro ao listar serviços:",
            erro
        );

    }

}



/*
 * Função responsável por atualizar
 * as informações da paginação na tela.
 */
function atualizarPaginacao() {

    /*
     * Obtém os elementos da paginação.
     */
    const btnAnterior =
        document.getElementById("btnAnterior");

    const btnProxima =
        document.getElementById("btnProxima");

    const infoPagina =
        document.getElementById("infoPagina");


    /*
     * No back-end, as páginas começam em 0.
     *
     * Para o usuário, exibimos começando em 1.
     */
    if (totalPaginas > 0) {

        infoPagina.textContent =
            `Página ${paginaAtual + 1} de ${totalPaginas}`;

    } else {

        infoPagina.textContent =
            "Página 1 de 1";

    }


    /*
     * Desabilita o botão "Anterior"
     * quando já estamos na primeira página.
     */
    btnAnterior.disabled =
        paginaAtual === 0;


    /*
     * Desabilita o botão "Próxima"
     * quando chegamos à última página.
     */
    btnProxima.disabled =
        totalPaginas === 0 ||
        paginaAtual >= totalPaginas - 1;

}



/*
 * Função responsável por preencher
 * a tabela de serviços.
 */
function preencherTabela(lista) {

    const tbody =
        document.getElementById("tbodyServicos");


    tbody.innerHTML = "";


    /*
     * Caso nenhum serviço seja encontrado.
     */
    if (lista.length === 0) {

        tbody.innerHTML = `
            <tr>
                <td colspan="7" class="text-center">
                    Nenhum registro encontrado.
                </td>
            </tr>
        `;

        return;

    }


    /*
     * Percorre os serviços encontrados
     * e cria uma linha para cada registro.
     */
    lista.forEach(servico => {

        tbody.innerHTML += `
            <tr>

                <td>${servico.idServico}</td>

                <td>${servico.dataServico}</td>

                <td>${servico.tipoServico}</td>

                <td>${servico.statusServico}</td>

                <td>
                    ${
                        servico.sepultura
                            ? servico.sepultura.idSepultura
                            : "-"
                    }
                </td>

                <td>${servico.notificacaoServico}</td>

                <td>

                    <button
                        class="btn btn-warning btn-sm"
                        onclick="editarServico(${servico.idServico})">

                        Editar

                    </button>

                    <button
                        class="btn btn-danger btn-sm"
                        onclick="excluirServico(${servico.idServico})">

                        Excluir

                    </button>

                </td>

            </tr>
        `;

    });

}



/*
 * Função responsável por pesquisar serviços
 * utilizando os filtros disponibilizados pela API.
 */
async function pesquisarServicos() {

    /*
     * Obtém o tipo de filtro selecionado.
     *
     * Os valores possíveis são:
     * id, tipo e status.
     */
    const tipoFiltro =
        document.getElementById("tipoFiltro").value;


    /*
     * Obtém o valor digitado pelo usuário.
     */
    const valorPesquisa =
        document.getElementById("txtPesquisa")
            .value
            .trim();


    /*
     * Caso o campo esteja vazio,
     * volta a exibir todos os serviços.
     */
    if (valorPesquisa === "") {

        listarServicos();

        return;

    }


    /*
     * Variável que armazenará a URL
     * que será consultada.
     */
    let url;


    /*
     * Monta a URL de acordo com
     * o filtro selecionado.
     */
    switch (tipoFiltro) {

        case "id":

            url =
                `${API}/${valorPesquisa}`;

            break;


        case "tipo":

            url =
                `${API}/buscar/tipo?tipo=${encodeURIComponent(valorPesquisa)}`;

            break;


        case "status":

            url =
                `${API}/buscar/status?status=${encodeURIComponent(valorPesquisa)}`;

            break;


        default:

            return;

    }


    try {

        /*
         * Realiza a requisição GET.
         */
        const resposta =
            await fetch(url);


        /*
         * Verifica se a requisição foi
         * realizada com sucesso.
         */
        if (!resposta.ok) {

            throw new Error(
                `Erro ao pesquisar serviço. Status: ${resposta.status}`
            );

        }


        /*
         * Converte a resposta para JSON.
         */
        const dados =
            await resposta.json();


        /*
         * A pesquisa por ID retorna apenas
         * um objeto Serviço.
         *
         * Por isso colocamos dentro de um array.
         */
        if (tipoFiltro === "id") {

            preencherTabela([dados]);

        } else {

            /*
             * Tipo e status retornam Page<Servico>.
             */
            if (dados.content) {

                preencherTabela(
                    dados.content
                );

            } else {

                preencherTabela(dados);
            }
        }


    } catch (erro) {

        console.error(
            "Erro ao pesquisar serviço:",
            erro
        );
    }
}

/*
 * Função responsável por excluir um serviço.
 */
async function excluirServico(id) {

    /*
     * Solicita confirmação antes da exclusão.
     */
    const confirmar = confirm(
        "Tem certeza que deseja excluir este serviço?"
    );

    if (!confirmar) {

        return;
    }

    try {

        /*
         * Envia uma requisição DELETE para a API.
         */
        const resposta = await fetch(
            `${API}/${id}`,
            {
                method: "DELETE"
            }
        );


        /*
         * Verifica se a exclusão foi realizada
         * com sucesso.
         */
        if (!resposta.ok) {

            const erroServidor =
                await resposta.text();

            console.error(
                "Resposta da API:",
                erroServidor
            );

            throw new Error(
                `Erro ao excluir serviço. Status: ${resposta.status}`
            );
        }

        /*
         * Informa que a exclusão foi concluída.
         */
        alert(
            "Serviço excluído com sucesso!"
        );


        /*
         * Atualiza a tabela.
         */
        listarServicos();


    } catch (erro) {

        console.error(
            "Erro ao excluir serviço:",
            erro
        );
    }
}


/*
 * Função responsável por buscar as sepulturas
 * cadastradas para preencher o select.
 *
 * Esta é a única parte extra em relação
 * ao JavaScript de Sepulturas, porque um
 * Serviço possui relacionamento com Sepultura.
 */
async function carregarSepulturas() {

    try {

        /*
         * Busca as sepulturas cadastradas.
         */
        const resposta = await fetch(
            `${API_SEPULTURAS}?page=0&size=100`
        );


        if (!resposta.ok) {

            throw new Error(
                "Erro ao buscar sepulturas."
            );

        }

        const dados =
            await resposta.json();

        /*
         * Obtém o select do modal.
         */
        const select =
            document.getElementById(
                "sepulturaServico"
            );


        /*
         * Limpa as opções anteriores.
         */
        select.innerHTML = `
            <option value="">
                Selecione a sepultura
            </option>
        `;

        /*
         * Adiciona cada sepultura encontrada
         * como uma opção.
         */
        dados.content.forEach(sepultura => {

            select.innerHTML += `
                <option value="${sepultura.idSepultura}">
                    ID ${sepultura.idSepultura} - Lote ${sepultura.lote}
                </option>
            `;

        });


    } catch (erro) {

        console.error(
            "Erro ao carregar sepulturas:",
            erro
        );

    }

}


/*
 * Função responsável por abrir o modal
 * para cadastrar um novo serviço.
 */
async function abrirModal() {

    /*
     * Remove qualquer ID utilizado
     * anteriormente em uma edição.
     */
    idServicoEdicao = null;


    /*
     * Define novamente o título de cadastro.
     */
    document.getElementById("tituloModal").textContent =
        "Novo Serviço";


    /*
     * Limpa os campos do formulário.
     */
    document.getElementById("dataServico").value = "";
    document.getElementById("tipoServico").value = "";
    document.getElementById("statusServico").value = "";
    document.getElementById("notificacaoServico").value = "";


    /*
     * Carrega as sepulturas cadastradas.
     */
    await carregarSepulturas();
    document.getElementById("sepulturaServico").value = "";

    /*
     * Abre o modal.
     */
    const modalElemento =
        document.getElementById("modalServico");

    const modal =
        bootstrap.Modal.getOrCreateInstance(
            modalElemento
        );

    modal.show();
}

/*
 * Função responsável por salvar um serviço.
 *
 * Caso não exista um ID em edição, será realizado
 * um cadastro através do POST.
 *
 * Caso exista um ID em edição, será realizada
 * uma atualização através do PUT.
 */
async function salvarServico() {

    /*
     * Obtém o ID da sepultura selecionada.
     */
    const idSepultura =
        document.getElementById(
            "sepulturaServico"
        ).value;


    /*
     * Cria o objeto com os dados informados
     * no formulário.
     */
    const servico = {

        dataServico:
            document.getElementById("dataServico").value,

        tipoServico:
            document.getElementById("tipoServico").value,

        statusServico:
            document.getElementById("statusServico").value,

        sepultura: {
            idSepultura:
                Number(idSepultura)
        },

        notificacaoServico:
            document.getElementById("notificacaoServico").value
    };


    /*
     * Define a URL.
     *
     * Cadastro:
     * POST /servicos
     *
     * Edição:
     * PUT /servicos/{id}
     */
    const url =
        idServicoEdicao === null
            ? API
            : `${API}/${idServicoEdicao}`;

    /*
     * Define o método HTTP.
     */
    const metodo =
        idServicoEdicao === null
            ? "POST"
            : "PUT";

    try {

        /*
         * Envia os dados para a API.
         */
        const resposta =
            await fetch(url, {

                method: metodo,

                headers: {

                    "Content-Type":
                        "application/json"
                },

                body:
                    JSON.stringify(servico)
            });


        /*
         * Caso o servidor retorne algum erro,
         * mostra a resposta no console.
         */
        if (!resposta.ok) {

            const erroServidor =
                await resposta.text();

            console.error(
                "Resposta da API:",
                erroServidor
            );

            throw new Error(
                `Erro ao salvar serviço. Status: ${resposta.status}`
            );
        }

        /*
         * Exibe mensagem diferente dependendo
         * da operação realizada.
         */
        if (idServicoEdicao === null) {

            alert(
                "Serviço cadastrado com sucesso!"
            );

        } else {

            alert(
                "Serviço atualizado com sucesso!"
            );
        }

        /*
         * Finalizada a operação,
         * volta ao modo de cadastro.
         */
        idServicoEdicao = null;

        /*
         * Fecha o modal.
         */
        const modalElemento =
            document.getElementById(
                "modalServico"
            );

        const modal =
            bootstrap.Modal.getInstance(
                modalElemento
            );

        modal.hide();

        /*
         * Atualiza os registros da tabela.
         */
        listarServicos();

    } catch (erro) {

        console.error(
            "Erro ao salvar serviço:",
            erro
        );
    }
}


/*
 * Obtém o botão "Novo".
 */
const btnNovo =
    document.getElementById("btnNovo");


/*
 * Ao clicar no botão "Novo",
 * abre o modal de cadastro.
 */
btnNovo.addEventListener(
    "click",
    abrirModal
);


/*
 * Obtém o botão "Salvar".
 */
const btnSalvar =
    document.getElementById("btnSalvar");


/*
 * Ao clicar no botão "Salvar",
 * executa a função de salvar.
 */
btnSalvar.addEventListener(
    "click",
    salvarServico
);


/*
 * Obtém o botão Pesquisar.
 */
const btnPesquisar =
    document.getElementById("btnPesquisar");


/*
 * Obtém os botões responsáveis
 * pela navegação entre páginas.
 */
const btnAnterior =
    document.getElementById("btnAnterior");

const btnProxima =
    document.getElementById("btnProxima");

/*
 * Exibe a página anterior.
 */
btnAnterior.addEventListener(
    "click",
    function () {

        if (paginaAtual > 0) {

            listarServicos(
                paginaAtual - 1
            );
        }
    }
);

/*
 * Exibe a próxima página.
 */
btnProxima.addEventListener(
    "click",
    function () {

        if (
            paginaAtual <
            totalPaginas - 1
        ) {

            listarServicos(
                paginaAtual + 1
            );
        }
    }
);

/*
 * Executa a pesquisa quando
 * o botão for clicado.
 */
btnPesquisar.addEventListener(
    "click",
    pesquisarServicos
);

// Carrega os serviços ao abrir a página.
listarServicos();