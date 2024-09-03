package br.com.webscraping.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryScrapingDTO extends CategoryDTO {
    private PharmacyDTO pharmacy;


}
