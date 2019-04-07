package com.github.rogeryk.charity.repository;

import com.github.rogeryk.charity.domain.Category;
import com.github.rogeryk.charity.domain.Project;
import com.github.rogeryk.charity.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findByCategory(Category category, Pageable pageable);

    int countProjectByAuthor(User user);

    Page<Project> findAllByNameLike(String name, Pageable pageable);

    Page<Project> findAllByFavorUsersContains(User user, Pageable pageable);

    Page<Project> findAllByAuthor(User user, Pageable pageable);
}
