package ru.practicum.ewm.category.repository;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.category.model.QCategory;
import ru.practicum.ewm.category.model.Category;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@AllArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public List<Category> getAll(int from, int size) {
        return new JPAQuery<Category>(entityManager)
                .from(QCategory.category)
                .orderBy(QCategory.category.id.asc())
                .offset(from)
                .limit(size)
                .fetch();
    }
}