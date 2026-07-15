package br.com.ifsertao.apicemiterio.exception;
import br.com.ifsertao.apicemiterio.ApiCemiterioApplication;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.HashMap;
import java.util.Map;


//funciona para ps 4 Controller e todos que vinherem a existir 
@RestControllerAdvice //essa anotação faz parte do spring, ela fica atenta para que sempre que qualquer controller lancar uma excessao,essa classe pode tratar 
public class GlobalExceptionHandler {
    
    //essa anotação informa ao Spring, sempre que uma excessão dessa for lancada por qualquer controller, esse metodo é chamado e executado
    private final ApiCemiterioApplication apiCemiterioApplication;


    GlobalExceptionHandler(ApiCemiterioApplication apiCemiterioApplication) {
        this.apiCemiterioApplication = apiCemiterioApplication;
    }


    @ExceptionHandler(RuntimeException.class)
    //cria o metodo publico que cria um objeto e que representa a exceção lançada
        public ResponseEntity<String> tratarRunTimeException(RuntimeException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        //e retorna a mensagem sepultura ocupada e o codigo http atraves do responseEntity
        
    }

    

    //caso alguem procure um falecido no endpoint sem informar o id, e informe uma string
    // vai dar erro, porque o metodo espera um id do tipo long
    //se alguem digitar GET http://localhost:8080/falecidos/abc o retorno é o bad request e a mensagem no bory de que o valor informado é invalido
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> tratarTipoInvalido(MethodArgumentTypeMismatchException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O valor informado é inválido!");

    }

    //essa exceção faz parte do bean validation, ela é chamada para tratar quando alguma validação como notBlank falha
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarValidacoes(MethodArgumentNotValidException e) {
    //é usada para montar um json contendo a chave como o nome do campo e o valor com a mensagem de validação
    Map<String, String> erros = new HashMap<>();

    e.getBindingResult().getFieldErrors().forEach(erro -> {
        erros.put(erro.getField(), erro.getDefaultMessage());
    });

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(erros);
}

}
