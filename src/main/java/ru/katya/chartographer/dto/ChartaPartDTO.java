package ru.katya.chartographer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class ChartaPartDTO {
    long idCharta;
    int width;
    int height;
    int x;
    int y;
}
