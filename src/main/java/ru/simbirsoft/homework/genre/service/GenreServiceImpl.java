package ru.simbirsoft.homework.genre.service;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.homework.book.model.BookEntity;
import ru.simbirsoft.homework.exception.DataNotFound;
import ru.simbirsoft.homework.genre.model.GenreEntity;
import ru.simbirsoft.homework.genre.repository.GenreRepo;
import ru.simbirsoft.homework.genre.view.GenreView;

import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Service
@Transactional
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepo genreRepo;
    private final MapperFactory mapperFactory;


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
    public Integer stats(String name) {
        return genreRepo.countAllBooksByGenre(name);
    }

    @Override
    public void remove(String name) {
        GenreEntity genre = genreRepo.findByName(name);
        if(genre==null){
            throw new DataNotFound("этого жанра");
        }
        List<BookEntity> books = new ArrayList<>(genre.getBooks());
        books.forEach(genre::removeBook);
        genreRepo.delete(genre);
    }
}
