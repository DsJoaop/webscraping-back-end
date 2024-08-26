package br.com.webscraping.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CategoryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String url;
    private List<ProductDTO> products = new ArrayList<>();
    private List<CategoryDTO> subcategories = new ArrayList<>();


    public CategoryDTO(String name, String url, List<CategoryDTO> subcategories) {
        this.name = name;
        this.url = url;
        this.subcategories = subcategories;
    }
}
