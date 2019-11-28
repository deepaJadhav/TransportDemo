package com.example.transportDemo.Service;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.transportDemo.Model.FileReadLine;


public interface FileInputLineRepository extends 
JpaRepository<FileReadLine, Long>{


}
