package ru.katya.chartographer.repositories;

import org.springframework.stereotype.Repository;
import ru.katya.chartographer.models.Charta;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ChartaRepositoryImp implements ChartaRepository {
    static Map<Long,Charta> chartas;
    Long nextId;

    ChartaRepositoryImp(){
        chartas = new HashMap();
        nextId = 1L;
    }

    @Override
    public Charta save(Charta charta) {
        charta.setId(nextId);
        chartas.put(charta.getId(),charta);
        nextId++;
        return charta;
    }

    @Override
    public Optional<Charta> getById(Long id) {
        return  Optional.of(chartas.get(id));
    }

    @Override
    public void deleteById(Long id) {
        chartas.remove(id);
    }
}
