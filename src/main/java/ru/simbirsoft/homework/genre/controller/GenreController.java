package ru.simbirsoft.homework.genre.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.homework.genre.service.GenreService;
import ru.simbirsoft.homework.genre.view.GenreView;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Api(value = "GenreController", description = "Управление жанрами книг")
@RestController
@RequestMapping(value = "/genre", produces = APPLICATION_JSON_VALUE)
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping("/")
    @ApiOperation(value = "Добавить жанр", httpMethod = "POST")
    public ResponseEntity<GenreView> addGenre(@Valid @RequestBody GenreView genreView){
        genreService.add(genreView);
        return ResponseEntity.ok(genreView);
    }

    @GetMapping("/")
    @ApiOperation(value = "Список жанров", httpMethod = "GET")
    public ResponseEntity<List<GenreView>> getAll(){
        List<GenreView> genreViewList = genreService.all();
        return ResponseEntity.ok(genreViewList);
    }

    @GetMapping("/count")
    @ApiOperation(value = "Количество книг по жанру в базе", httpMethod = "GET")
    public ResponseEntity<Long> getCount(GenreView genreView){
        return ResponseEntity.ok(genreService.stats(genreView));
    }

}
