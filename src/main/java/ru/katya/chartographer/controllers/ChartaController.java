package ru.katya.chartographer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.katya.chartographer.dto.ChartaDTO;
import ru.katya.chartographer.dto.ChartaPartDTO;
import ru.katya.chartographer.services.ChartaService;
import ru.katya.chartographer.services.errors.BadCoordinateException;
import ru.katya.chartographer.services.errors.NoSuchChartaException;
import ru.katya.chartographer.utils.NoBMPFileException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;

@RestController
@Validated
public class ChartaController {

    @Autowired
    ChartaService service;

    @PostMapping("/chartas/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public String createCharta(@RequestParam(name = "width") @Min(1) @Max(20_000) int width,
                               @RequestParam(name = "height") @Min(1) @Max(50_000) int height) {
        return service.createCharta(new ChartaDTO(width, height));
    }

//    @PostMapping(value = "/chartas/{id}/")
//    public void changeCharta(@PathVariable("id") Long chartaId,
//                             @RequestParam(name = "x") @Min(0) int x, @RequestParam(name = "y") @Min(0) int y,
//                             @RequestParam(name = "width") @Min(1) int width,
//                             @RequestParam(name = "height") @Min(1)int height,
//                             @RequestBody BufferedImage bufferedImage) throws IOException {
//
////        service.changeCharta(new ChartaPartDTO(chartaId, width, height, x, y), image);
//    }

    @GetMapping("/chartas/{id}/")
    public void getPartCharta(@PathVariable("id") Long chartaId,
                              @RequestParam(name = "x") @Min(0) int x, @RequestParam(name = "y") @Min(0) int y,
                              @RequestParam(name = "width") @Min(1) @Max(20_000) int width,
                              @RequestParam(name = "height") @Min(1) @Max(50_000)int height,
                              HttpServletResponse response) {
        try {
            response.setContentType("image/bmp");
            service.getPartCharta(new ChartaPartDTO(chartaId, width, height, x, y), response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @DeleteMapping("/chartas/{id}/")
    public void deleteCharta(@PathVariable("id") Long chartaId) {
        service.deleteCharta(chartaId);
    }

    @ExceptionHandler({ConstraintViolationException.class, BadCoordinateException.class, NoSuchChartaException.class, NoBMPFileException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationException(ConstraintViolationException e) {
        return e.getMessage();
    }

}
