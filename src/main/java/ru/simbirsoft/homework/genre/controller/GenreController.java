package ru.simbirsoft.homework.genre.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.homework.genre.service.GenreService;
import ru.simbirsoft.homework.genre.view.GenreView;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Api(value = "GenreController", description = "Управление жанрами книг")
@RestController
@RequestMapping(value = "/genre", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping("/")
    @Secured(value = {"ROLE_ADMIN"})
    @ApiOperation(value = "Добавить жанр", httpMethod = "POST")
    public ResponseEntity<GenreView> addGenre(@Valid @RequestBody GenreView genreView){
        genreService.add(genreView);
        return ResponseEntity.ok(genreView);
    }

    @GetMapping("/")
    @ApiOperation(value = "Список жанров", httpMethod = "GET")
    public ResponseEntity<List<GenreView>> getAll(){
        List<GenreView> genreViewList = genreService.getAllGenres();
        return ResponseEntity.ok(genreViewList);
    }

    @GetMapping("/{name}")
    @ApiOperation(value = "Количество книг по жанру в базе", httpMethod = "GET")
    public ResponseEntity<Integer> getCount(@PathVariable String name){
        return ResponseEntity.ok(genreService.getBooksCountByGenre(name));
    }

    @DeleteMapping("/")
    @Secured(value = {"ROLE_ADMIN"})
    @ApiOperation(value = "Удалить жанр", httpMethod = "DELETE")
    public ResponseEntity<List<GenreView>> removeGenre(@RequestParam String name){
        genreService.remove(name);
        List<GenreView> genreViewList = genreService.getAllGenres();
        return ResponseEntity.ok(genreViewList);
    }
}
