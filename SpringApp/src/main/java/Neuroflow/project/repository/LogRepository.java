package Neuroflow.project.repository;

import Neuroflow.project.dto.Process;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository {
    public void saveLog(Process process);
}
