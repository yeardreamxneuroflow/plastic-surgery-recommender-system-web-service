package Neuroflow.project.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository {

    public int getSequenceCodeNumber(String code);

    public void saveSequenceCodeNumber(String code, int sequence_number);

}
