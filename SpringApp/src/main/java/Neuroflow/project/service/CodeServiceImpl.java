package Neuroflow.project.service;

import Neuroflow.project.repository.CodeRepository;
import org.springframework.stereotype.Service;

@Service
public class CodeServiceImpl implements CodeService{

    private final CodeRepository codeRepository;

    public CodeServiceImpl(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @Override
    public String getSequenceNumber(String code) {
        codeRepository.saveSequenceCodeNumber(code, codeRepository.getSequenceCodeNumber(code) + 1);

        return code + codeRepository.getSequenceCodeNumber(code);
    }
}
