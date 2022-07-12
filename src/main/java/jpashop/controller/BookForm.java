package jpashop.controller;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter @Service
public class BookForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;
}
