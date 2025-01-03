package fr.fms.business;

import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryIJob implements IJob<Category> {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public Page<Category> getAll(String kw, int page) {
        return null;
    }

    @Override
    public List<Category> findByAttribute(Long id) {
        return null;
    }

    @Override
    public Optional<Category> getOne(Long id) {
        return null;
    }

    @Override
    public void deleteOne(Long id) {
    }

    @Override
    public void createOne(Category category) {
    }
}
