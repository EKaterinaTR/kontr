package ru.katya.chartographer.models;

import lombok.*;



@Builder
@Getter
public class Charta {
    @Setter
    private Long id;
    @Setter
    private String name;
    private int width;
    private int height;
}
