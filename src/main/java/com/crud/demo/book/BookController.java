package com.crud.demo.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookModel>> listarLivros(){
       List<BookModel> list = bookService.findAll();
       if (list.isEmpty()){
           return ResponseEntity.noContent().build();
       }
        return ResponseEntity.ok().body(list);
    }


    @PostMapping
    public ResponseEntity<?> criarLivro(@RequestBody BookModel bookModel) {
        if (bookModel == null || bookModel.getId() != null){
            return ResponseEntity.badRequest().body("Id deve ser nulo para a criacao");
        }
        try {
            BookModel response  = bookService.criarLivro(bookModel);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/id").buildAndExpand(response.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(response);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao ciar livro");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarLivro(@PathVariable Long id){
        if (!bookService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro nao encontrado");
        }
        bookService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BookModel bookModel){
         if (!bookService.existsById(id)){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro nao encontrado");
         }
         try{
             BookModel responde = bookService.update(id, bookModel);
             return ResponseEntity.ok().body(responde);
         }catch (Exception e){
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar livro");
         }
    }


}
