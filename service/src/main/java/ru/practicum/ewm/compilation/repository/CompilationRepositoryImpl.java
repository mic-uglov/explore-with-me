package ru.practicum.ewm.compilation.repository;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.QCompilation;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompilationRepositoryImpl implements CompilationRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public List<Compilation> findAllByPinnedIfNeeded(Boolean pinned, int from, int size) {
        JPAQuery<Compilation> query = new JPAQuery<Compilation>(entityManager)
                .from(QCompilation.compilation)
                .offset(from)
                .limit(size);

        if (pinned != null) {
            query.where(QCompilation.compilation.pinned.eq(pinned));
        }

        return query.fetch();
    }
}