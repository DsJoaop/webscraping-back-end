package br.com.webscraping.utils;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.dto.ProductDTO;


public class MainTest {
    public static void main(String[] args) {
        // Testando a criação de um Product
        ProductDTO productDTO = Factory.createProductDTO();
        System.out.println("ProductDTO: " + productDTO);

        // Testando a criação de uma Category
        CategoryDTO categoryDTO = Factory.createCategoryDTO();
        System.out.println("CategoryDTO: " + categoryDTO);

        // Testando a criação de uma Pharmacy
        PharmacyDTO pharmacyDTO = Factory.createPharmacyDTO();
        System.out.println("PharmacyDTO: " + pharmacyDTO);
    }
}