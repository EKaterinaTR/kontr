package ru.katya.chartographer.services;

import org.springframework.web.multipart.MultipartFile;
import ru.katya.chartographer.dto.ChartaDTO;
import ru.katya.chartographer.dto.ChartaPartDTO;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

public interface ChartaService {
    public String createCharta(ChartaDTO chartaDTO);
    public void getPartCharta(ChartaPartDTO partChartaDTO, OutputStream stream);
    public void changeCharta(ChartaPartDTO partChartaDTO, BufferedImage image);
    public void deleteCharta(Long id);

}
