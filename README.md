Atividade Prática: Analisando e Corrigindo Erros no Código do Controller
Objetivo: Analisar o código do BookController e identificar os erros, realizando as correções necessárias para garantir que o código funcione corretamente e de forma robusta.
Descrição do Código:
O código fornecido é parte de uma aplicação Spring Boot responsável pelo CRUD (Create, Read, Update, Delete) de livros. A classe BookController contém os métodos para listar, criar, atualizar e deletar livros, mas há alguns erros e boas práticas que não foram seguidas. Sua tarefa é analisar o código, identificar os erros e corrigi-los.
@RestController @RequestMapping(path = "/books") public class BookController {

@Autowired
private BookService bookService;

@GetMapping
private ResponseEntity<List<BookModel>> listarLivros(){
    //Verifica se a lista de livros está vazia
    List<BookModel> list = bookService.findAll();
    return ResponseEntity.ok().body(list);  
}

@PostMapping
private ResponseEntity<BookModel> criarLivro(@RequestBody BookModel bookModel){
    // verificar se o bookModel é válido ou se o ID está correto
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(bookModel.getId()).toUri();
    
    BookModel response = bookService.criarLivro(bookModel); 
    return ResponseEntity.created(uri).body(response);  // Erro: Falta tratar falhas de criação
}

@DeleteMapping("/{id}")
private ResponseEntity<?> deletarLivro(@PathVariable Long id){
    // verificar se o livro existe antes de excluir
    bookService.deletarLivro(id); 
    return ResponseEntity.noContent().build();  // Erro: Falta tratamento de erro
}

@PutMapping("/{id}")
private ResponseEntity<BookModel> update(@PathVariable Long id, @RequestBody BookModel bookModel){
    //  verificar se o livro existe antes de tentar atualizar
    BookModel response  = bookService.update(id, bookModel); 
    return ResponseEntity.ok().body(response);  // Erro: Falta tratar o caso onde o livro não é encontrado
}
