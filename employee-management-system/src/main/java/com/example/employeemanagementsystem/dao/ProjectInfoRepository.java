package com.example.employeemanagementsystem.dao;

import com.example.employeemanagementsystem.entity.ProjectInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectInfoRepository extends JpaRepository<ProjectInfo, Integer> {
}
