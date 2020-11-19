package ru.simbirsoft.homework.genre.service;

import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simbirsoft.homework.genre.model.GenreEntity;
import ru.simbirsoft.homework.genre.repository.GenreRepo;
import ru.simbirsoft.homework.genre.view.GenreView;

import java.util.List;

/**
 * {@inheritDoc}
 */
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepo genreRepo;
    private final MapperFactory mapperFactory;

    @Autowired
    public GenreServiceImpl(GenreRepo genreRepo, MapperFactory mapperFactory) {
        this.genreRepo = genreRepo;
        this.mapperFactory =mapperFactory;
    }

    @Override
    public List<GenreView> all() {
        List<GenreEntity> genreEntityList = genreRepo.findAll();
        return mapperFactory.getMapperFacade()
                .mapAsList(genreEntityList,GenreView.class);
    }

    @Override
    public void add(GenreView genreView) {
        genreRepo.save(mapperFactory.getMapperFacade().
                map(genreView,GenreEntity.class)
        );
    }

    @Override
    public Long stats(GenreView genreView) {
        return (long) genreRepo.countAllBooksByGenre(genreView.getName());
    }
}
