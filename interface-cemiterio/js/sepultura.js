// URL base da API de Sepulturas.
const API = "http://localhost:8080/sepulturas";

/*
 * Armazena o ID da sepultura que está sendo editada.
 *
 * Quando o valor for null, significa que estamos
 * cadastrando uma nova sepultura.
 */
let idSepulturaEdicao = null;

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
 * Função responsável por buscar uma sepultura
 * através do seu ID e preencher o formulário
 * para edição.
 */

async function editarSepultura(id) {

    try {

        /*
         * Busca na API a sepultura selecionada.
         */
        const resposta = await fetch(`${API}/${id}`);


        /*
         * Verifica se a consulta foi realizada
         * com sucesso.
         */
        if (!resposta.ok) {

            throw new Error(
                "Erro ao buscar sepultura."
            );

        }


        /*
         * Converte a resposta para JSON.
         */
        const sepultura =
            await resposta.json();


        /*
         * Guarda o ID da sepultura que está
         * sendo editada.
         */
        idSepulturaEdicao = id;


        /*
         * Preenche os campos do modal com os
         * dados atuais da sepultura.
         */
        document.getElementById("lote").value =
            sepultura.lote;

        document.getElementById("tipoSepultura").value =
            sepultura.tipoSepultura;

        document.getElementById("statusSepultura").value =
            sepultura.statusSepultura;

        document.getElementById("familiarResponsavel").value =
            sepultura.familiarResponsavel;

        document.getElementById("dataCriacao").value =
            sepultura.dataCriacao;


        /*
         * Altera o título do modal para indicar
         * que estamos realizando uma edição.
         */
        document.getElementById("tituloModal").textContent =
            "Editar Sepultura";


        /*
         * Abre o modal.
         */
        const modalElemento =
            document.getElementById("modalSepultura");

        const modal =
            bootstrap.Modal.getOrCreateInstance(
                modalElemento
            );

        modal.show();


    } catch (erro) {

        console.error(
            "Erro ao carregar sepultura:",
            erro
        );

    }

}

/*
 * Função responsável por buscar as sepulturas
 * cadastradas na API utilizando paginação.
 */
async function listarSepulturas(page = 0) {

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
                "Erro ao buscar sepulturas."
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
            "Erro ao listar sepulturas:",
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
     *
     * Exemplo:
     *
     * Spring -> página 0
     * Tela   -> Página 1
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

// Função responsável por preencher a tabela.
function preencherTabela(lista) {

    const tbody =
        document.getElementById("tbodySepulturas");

    tbody.innerHTML = "";


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


    lista.forEach(sepultura => {

        tbody.innerHTML += `
            <tr>

                <td>${sepultura.idSepultura}</td>

                <td>${sepultura.lote}</td>

                <td>${sepultura.tipoSepultura}</td>

                <td>${sepultura.statusSepultura}</td>

                <td>${sepultura.familiarResponsavel}</td>

                <td>${sepultura.dataCriacao}</td>

                <td>
                    <button
                    class="btn btn-warning btn-sm"
                    onclick="editarSepultura(${sepultura.idSepultura})">

                        Editar

                    </button>

                    <button
                        class="btn btn-danger btn-sm"
                        onclick="excluirSepultura(${sepultura.idSepultura})">

                        Excluir

                    </button>

                </td>

            </tr>
        `;

    });

}

/*
 * Função responsável por pesquisar sepulturas
 * utilizando os filtros disponibilizados pela API.
 */
async function pesquisarSepulturas() {

    /*
     * Obtém o tipo de filtro selecionado.
     *
     * Os valores possíveis são:
     * id, lote, tipo e status.
     */
    const tipoFiltro =
        document.getElementById("tipoFiltro").value;


    /*
     * Obtém o valor digitado pelo usuário.
     *
     * trim() remove espaços desnecessários
     * no início e no final do texto.
     */
    const valorPesquisa =
        document.getElementById("txtPesquisa")
            .value
            .trim();


    /*
     * Caso o campo esteja vazio,
     * volta a exibir todas as sepulturas.
     */
    if (valorPesquisa === "") {

        listarSepulturas();

        return;

    }


    /*
     * Variável que armazenará a URL
     * que será consultada.
     */
    let url;


    /*
     * Monta a URL de acordo com o
     * filtro selecionado.
     */
    switch (tipoFiltro) {

        case "id":

            url = `${API}/${valorPesquisa}`;

            break;


        case "lote":

            url =
                `${API}/buscar/lote?lote=${encodeURIComponent(valorPesquisa)}`;

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
         * Realiza a requisição GET para
         * o endpoint correspondente.
         */
        const resposta =
            await fetch(url);


        /*
         * Verifica se a requisição foi
         * realizada com sucesso.
         */
        if (!resposta.ok) {

            throw new Error(
                `Erro ao pesquisar sepultura. Status: ${resposta.status}`
            );

        }


        /*
         * Converte a resposta para JSON.
         */
        const dados =
            await resposta.json();


        /*
         * A pesquisa por ID retorna apenas
         * uma Sepultura.
         *
         * Como preencherTabela() espera uma lista,
         * colocamos o objeto dentro de um array.
         */
        if (tipoFiltro === "id") {

            preencherTabela([dados]);

        } else {

            /*
             * Os demais filtros podem retornar
             * diretamente uma lista ou uma página.
             *
             * Caso exista "content", utilizamos
             * os dados da paginação.
             *
             * Caso contrário, utilizamos
             * diretamente a resposta recebida.
             */
            if (dados.content) {

                preencherTabela(dados.content);

            } else {

                preencherTabela(dados);

            }

        }


    } catch (erro) {

        console.error(
            "Erro ao pesquisar sepultura:",
            erro
        );

    }

}

/*
 * Função responsável por excluir uma sepultura.
 *
 * Recebe como parâmetro o ID da sepultura
 * selecionada na tabela.
 */
async function excluirSepultura(id) {

    /*
     * Solicita uma confirmação antes da exclusão.
     *
     * Caso o usuário clique em "Cancelar",
     * a função será encerrada.
     */
    const confirmar = confirm(
        "Tem certeza que deseja excluir esta sepultura?"
    );


    if (!confirmar) {
        return;
    }


    try {

        /*
         * Envia uma requisição DELETE para a API,
         * informando o ID da sepultura.
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
                `Erro ao excluir sepultura. Status: ${resposta.status}`
            );

        }


        /*
         * Informa ao usuário que a exclusão
         * foi realizada com sucesso.
         */
        alert(
            "Sepultura excluída com sucesso!"
        );


        /*
         * Atualiza a tabela após a exclusão.
         */
        listarSepulturas();


    } catch (erro) {

        /*
         * Exibe possíveis erros no console.
         */
        console.error(
            "Erro ao excluir sepultura:",
            erro
        );

    }

}

/*
 * Função responsável por abrir o modal
 * para cadastrar uma nova sepultura.
 */
function abrirModal() {

    /*
     * Remove qualquer ID que tenha sido
     * utilizado anteriormente em uma edição.
     */
    idSepulturaEdicao = null;


    /*
     * Define novamente o título de cadastro.
     */
    document.getElementById("tituloModal").textContent =
        "Nova Sepultura";


    /*
     * Limpa os campos do formulário.
     */
    document.getElementById("lote").value = "";

    document.getElementById("tipoSepultura").value = "";

    document.getElementById("statusSepultura").value = "";

    document.getElementById("familiarResponsavel").value = "";

    document.getElementById("dataCriacao").value = "";


    /*
     * Abre o modal.
     */
    const modalElemento =
        document.getElementById("modalSepultura");

    const modal =
        bootstrap.Modal.getOrCreateInstance(
            modalElemento
        );

    modal.show();

}

/*
 * Função responsável por salvar uma sepultura.
 *
 * Caso não exista um ID em edição, será realizado
 * um cadastro através do POST.
 *
 * Caso exista um ID em edição, será realizada
 * uma atualização através do PUT.
 */
async function salvarSepultura() {

    /*
     * Cria o objeto com os dados informados
     * no formulário.
     */
    const sepultura = {

        lote:
            document.getElementById("lote").value,

        tipoSepultura:
            document.getElementById("tipoSepultura").value,

        statusSepultura:
            document.getElementById("statusSepultura").value,

        familiarResponsavel:
            document.getElementById("familiarResponsavel").value,

        dataCriacao:
            document.getElementById("dataCriacao").value

    };


    /*
     * Define a URL da requisição.
     *
     * Cadastro:
     * POST /sepulturas
     *
     * Edição:
     * PUT /sepulturas/{id}
     */
    const url = idSepulturaEdicao === null
        ? API
        : `${API}/${idSepulturaEdicao}`;


    /*
     * Define o método HTTP.
     */
    const metodo = idSepulturaEdicao === null
        ? "POST"
        : "PUT";


    try {

        /*
         * Envia os dados para a API.
         */
        const resposta = await fetch(url, {

            method: metodo,

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(sepultura)

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
                `Erro ao salvar sepultura. Status: ${resposta.status}`
            );

        }


        /*
         * Exibe uma mensagem diferente dependendo
         * da operação realizada.
         */
        if (idSepulturaEdicao === null) {

            alert(
                "Sepultura cadastrada com sucesso!"
            );

        } else {

            alert(
                "Sepultura atualizada com sucesso!"
            );

        }


        /*
         * Finalizada a operação, volta ao modo
         * de cadastro.
         */
        idSepulturaEdicao = null;


        /*
         * Fecha o modal.
         */
        const modalElemento =
            document.getElementById("modalSepultura");

        const modal =
            bootstrap.Modal.getInstance(
                modalElemento
            );

        modal.hide();


        /*
         * Atualiza os registros exibidos
         * na tabela.
         */
        listarSepulturas();


    } catch (erro) {

        console.error(
            "Erro ao salvar sepultura:",
            erro
        );

    }

}


/*
 * Obtém o botão "Nova".
 */
const btnNovo =
    document.getElementById("btnNovo");


/*
 * Ao clicar no botão "Nova",
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
 * executa a função de cadastro.
 */
btnSalvar.addEventListener(
    "click",
    salvarSepultura
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

        /*
         * Só volta uma página caso
         * não esteja na primeira.
         */
        if (paginaAtual > 0) {

            listarSepulturas(
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

        /*
         * Só avança caso ainda exista
         * uma próxima página.
         */
        if (
            paginaAtual <
            totalPaginas - 1
        ) {

            listarSepulturas(
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
    pesquisarSepulturas
);

// Carrega as sepulturas ao abrir a página.
listarSepulturas();