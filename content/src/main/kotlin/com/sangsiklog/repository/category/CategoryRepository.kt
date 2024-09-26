package com.sangsiklog.repository.category

import com.sangsiklog.domain.category.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: JpaRepository<Category, Long>, CategoryRepositoryCustom {
}