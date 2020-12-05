package ru.simbirsoft.homework.genre.service;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.simbirsoft.homework.exception.DataNotFoundException;
import ru.simbirsoft.homework.genre.model.GenreEntity;
import ru.simbirsoft.homework.genre.repository.GenreRepo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class GenreServiceImplTest {

    @Mock
    GenreRepo genreRepo;
    @Mock
    MapperFactory mapperFactory;
    @InjectMocks
    GenreServiceImpl genreService;

    @Test
    void remove_Success() {
        GenreEntity genreEntity = new GenreEntity();
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        Mockito.when(genreRepo.findByName(Mockito.anyString()))
                .thenReturn(Optional.of(genreEntity));
        genreService.remove("Test");
        Mockito.verify(genreRepo, Mockito.times(1))
                .findByName(Mockito.anyString());
        Mockito.verify(genreRepo, Mockito.times(1))
                .delete(Mockito.any(GenreEntity.class));
    }

    @Test
    void remove_GenreNotFound() {
        Mockito.when(genreRepo.findByName(Mockito.anyString()))
                .thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, ()-> genreService.remove("Test"));
        Mockito.verify(genreRepo, Mockito.times(1))
                .findByName(Mockito.anyString());
        Mockito.verify(genreRepo, Mockito.times(0))
                .delete(Mockito.any(GenreEntity.class));
    }
}