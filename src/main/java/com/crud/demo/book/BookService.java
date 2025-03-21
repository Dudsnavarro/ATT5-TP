package com.crud.demo.book;

import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class BookService {

    @Autowired
     private BookRepository bookRepository;


    public List<BookModel> findAll(){
        return  bookRepository.findAll();
    }

    public BookModel criarLivro(BookModel bookModel){
        return bookRepository.save(bookModel);
    }

    public void deletarLivro(Long id){
        bookRepository.deleteById(id);
    }

    public BookModel update(Long id, BookModel bookMode){
       BookModel newbook =  bookRepository.findById(id).get();
       newbook.setCategoria(bookMode.getCategoria());
       newbook.setNome(bookMode.getNome());
       return bookRepository.save(newbook);

    }

    public boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }

}
