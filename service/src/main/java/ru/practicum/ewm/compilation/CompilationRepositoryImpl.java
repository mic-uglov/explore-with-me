package ru.practicum.ewm.compilation;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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