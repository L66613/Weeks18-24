package ru.itgirl.libraryproject18n.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookWithAuthorDto {
    private Long id;
    private String name;
    private String author;
}
