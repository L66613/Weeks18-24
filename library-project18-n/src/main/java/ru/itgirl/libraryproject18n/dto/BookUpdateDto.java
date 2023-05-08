package ru.itgirl.libraryproject18n.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookUpdateDto {
    @NotNull
    private Long id;
    @Size(min = 1, max = 30)
    @NotBlank(message = "Необходимо указать название книги")
    private String name;
    @NotBlank(message = "Необходимо указать жанр книги")
    private String genre;
}
