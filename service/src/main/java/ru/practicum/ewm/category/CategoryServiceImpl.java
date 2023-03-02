package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryDto create(NewCategoryDto dto) {
        return CategoryMapper.toDto(categoryRepository.save(CategoryMapper.toCategory(dto)));
    }

    @Override
    @Transactional
    public void delete(long catId) {
        categoryRepository.deleteById(catId);
    }

    @Override
    @Transactional
    public CategoryDto update(long catId, CategoryDto categoryDto) {
        Category category = getCategory(catId);

        category.setName(categoryDto.getName());

        return CategoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> getAll(int from, int size) {
        return categoryRepository.getAll(from, size).stream()
                .map(CategoryMapper::toDto).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public CategoryDto get(long catId) {
        return CategoryMapper.toDto(getCategory(catId));
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategory(long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категория id=" + catId + " не найдена"));
    }
}