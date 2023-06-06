package ru.itgirl.libraryproject18n.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GenreCreateDto {
    @Size(min = 1, max = 30)
    @NotNull
    private String name;
}
