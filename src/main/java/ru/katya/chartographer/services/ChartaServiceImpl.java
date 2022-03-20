package ru.katya.chartographer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.katya.chartographer.dto.ChartaDTO;
import ru.katya.chartographer.dto.ChartaPartDTO;
import ru.katya.chartographer.models.Charta;
import ru.katya.chartographer.models.ChartaPart;
import ru.katya.chartographer.repositories.ChartaRepository;
import ru.katya.chartographer.services.errors.BadCoordinateException;
import ru.katya.chartographer.services.errors.NoSuchChartaException;
import ru.katya.chartographer.utils.StorageCharta;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

@Service
public class ChartaServiceImpl implements ChartaService {
    @Autowired
    StorageCharta storageCharta;
    @Autowired
    ChartaRepository repository;

    @Override
    public String createCharta(ChartaDTO chartaDTO) {
        Charta charta = storageCharta.createCharta(Charta.builder()
                .height(chartaDTO.getHeight())
                .width(chartaDTO.getWidth())
                .build());
        charta = repository.save(charta);
        return charta.getId().toString();
    }

    @Override
    public void getPartCharta(ChartaPartDTO chartaPartDTO, OutputStream stream) {
        Charta charta = repository.getById(chartaPartDTO.getIdCharta()).orElseThrow(NoSuchChartaException::new);
        if (chartaPartDTO.getX() > charta.getWidth() || chartaPartDTO.getY() > charta.getHeight()) {
            throw new BadCoordinateException();
        }
        storageCharta.getChartaPart(
                ChartaPart.builder()
                        .charta(charta)
                        .width(Math.min(chartaPartDTO.getWidth(), charta.getWidth() - chartaPartDTO.getX()))
                        .height(Math.min(chartaPartDTO.getHeight(), charta.getHeight() - chartaPartDTO.getY()))
                        .x(chartaPartDTO.getX())
                        .y(chartaPartDTO.getY())
                        .build(),
                stream);
    }


    @Override
    public void changeCharta(ChartaPartDTO chartaPartDTO, BufferedImage image) {
        Charta charta = repository.getById(chartaPartDTO.getIdCharta()).orElseThrow(NoSuchChartaException::new);
        if (chartaPartDTO.getX() > charta.getWidth() || chartaPartDTO.getY() > charta.getHeight()) {
            throw new BadCoordinateException();
        }
        storageCharta.changeCharte(
                ChartaPart.builder()
                        .charta(charta)
                        .width(Math.min(chartaPartDTO.getWidth(), charta.getWidth() - chartaPartDTO.getX()))
                        .height(Math.min(chartaPartDTO.getHeight(), charta.getHeight() - chartaPartDTO.getY()))
                        .x(chartaPartDTO.getX())
                        .y(chartaPartDTO.getY())
                        .build(),
                image);

    }

    @Override
    public void deleteCharta(Long id) {
        Charta charta = repository.getById(id).orElseThrow(NoSuchChartaException::new);
        storageCharta.delete(charta);
        repository.deleteById(id);
    }
}
