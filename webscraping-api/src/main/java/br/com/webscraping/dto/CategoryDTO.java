package br.com.webscraping.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
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
    private Instant createdAt;
    private Instant updatedAt;

    private Long pharmacyId;
    private List<ProductDTO> products = new ArrayList<>();
    private List<CategoryDTO> subcategories = new ArrayList<>();

    private Long parentCategoryId;

    public CategoryDTO(String name, String url, List<CategoryDTO> subcategories, Long pharmacyId) {
        this.subcategories = subcategories;
        this.url = url;
        this.name = name;
    }
}
