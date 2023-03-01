package ru.practicum.ewm.user;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public List<User> get(@Nullable List<Long> ids, int from, int size) {
        JPAQuery<User> query = new JPAQuery<User>(entityManager)
                .from(QUser.user).orderBy(QUser.user.id.asc()).offset(from).limit(size);

        if (ids != null && !ids.isEmpty()) {
            query = query.where(QUser.user.id.in(ids));
        }

        return query.fetch();
    }
}