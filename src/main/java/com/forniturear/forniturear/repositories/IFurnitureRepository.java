package com.forniturear.forniturear.repositories;

import com.forniturear.forniturear.models.Furniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFurnitureRepository extends JpaRepository<Furniture, Long> {
}
