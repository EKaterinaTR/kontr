package ru.katya.chartographer.repositories;

import ru.katya.chartographer.models.Charta;

import java.util.Optional;

public interface ChartaRepository {
    public Charta save(Charta charta);
    public Optional<Charta> getById(Long id);

    public void deleteById(Long id);
}
