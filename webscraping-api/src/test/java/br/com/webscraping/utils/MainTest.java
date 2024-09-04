package br.com.webscraping.utils;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.dto.ProductDTO;


public class MainTest {
    public static void main(String[] args) {
        Factory factory = new Factory();
        // Testando a criação de um Product
        ProductDTO productDTO = factory.createProductDTO();
        System.out.println("ProductDTO: " + productDTO);

        // Testando a criação de uma Category
        CategoryDTO categoryDTO = factory.createCategoryDTO();
        System.out.println("CategoryDTO: " + categoryDTO);

        // Testando a criação de uma Pharmacy
        PharmacyDTO pharmacyDTO = factory.createPharmacyDTO();
        System.out.println("PharmacyDTO: " + pharmacyDTO);
    }
}