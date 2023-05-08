package ru.itgirl.libraryproject18n.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenreWithBookInfoDto {
    private Long id;
    private String name;
    private List<BookWithAuthorDto> books;
}
