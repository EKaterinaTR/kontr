package ru.katya.chartographer.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChartaPart {
    Charta charta;
    int width;
    int height;
    int x;
    int y;
}
