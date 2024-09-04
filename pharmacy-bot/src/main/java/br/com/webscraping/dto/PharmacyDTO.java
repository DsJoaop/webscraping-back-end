package br.com.webscraping.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PharmacyDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String address;
    private String phone;
    private String city;
    private String state;
    private String zipCode;
    private String url;
    private String imgUrl;
    private List<CategoryResponseDTO> categories = new ArrayList<>();
}
