package fr.fms.business;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IJob<Obj> {

    List<Obj> findByAttribute(Long id);

    Optional<Obj> getOne(Long id);

    Page<Obj> getAll(String kw, int page);

    List<Obj> getAll();

    void deleteOne(Long id);

    void createOne(Obj obj);
}
