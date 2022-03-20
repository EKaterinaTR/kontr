package ru.katya.chartographer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChartaDTO {
    @Min(1)
    int width;
    @Min(1)
    int height;
}
