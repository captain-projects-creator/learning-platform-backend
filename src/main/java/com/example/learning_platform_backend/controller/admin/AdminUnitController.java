package com.example.learning_platform_backend.controller.admin;

import com.example.learning_platform_backend.dto.*;
import com.example.learning_platform_backend.entity.Standard;
import com.example.learning_platform_backend.entity.Subject;
import com.example.learning_platform_backend.entity.Unit;
import com.example.learning_platform_backend.repository.StandardRepository;
import com.example.learning_platform_backend.repository.SubjectRepository;
import com.example.learning_platform_backend.repository.UnitRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/units")
@RequiredArgsConstructor
public class AdminUnitController {

    private final StandardRepository standardRepository;
    private final SubjectRepository subjectRepository;
    private final UnitRepository unitRepository;

    // =============================================
    // GET BY STANDARD (Tree Structure)
    // =============================================

    @GetMapping("/by-standard/{standardId}")
    public StandardWithSubjectsUnitsDTO getUnitsByStandard(@PathVariable Long standardId) {

        Standard standard = standardRepository.findById(standardId)
                .orElseThrow(() -> new RuntimeException("Standard not found"));

        StandardWithSubjectsUnitsDTO standardDTO = new StandardWithSubjectsUnitsDTO();
        standardDTO.setId(standard.getId());
        standardDTO.setName(standard.getName());

        List<SubjectWithUnitsDTO> subjectDTOList =
                standard.getSubjects().stream().map(subject -> {

                    SubjectWithUnitsDTO subjectDTO = new SubjectWithUnitsDTO();
                    subjectDTO.setId(subject.getId());
                    subjectDTO.setName(subject.getName());

                    List<UnitSimpleDTO> unitDTOList =
                            unitRepository.findBySubjectIdOrderByOrderIndexAsc(subject.getId())
                                    .stream()
                                    .map(unit -> {
                                        UnitSimpleDTO dto = new UnitSimpleDTO();
                                        dto.setId(unit.getId());
                                        dto.setName(unit.getName());
                                        return dto;
                                    }).toList();

                    subjectDTO.setUnits(unitDTOList);
                    return subjectDTO;

                }).toList();

        standardDTO.setSubjects(subjectDTOList);

        return standardDTO;
    }

    @GetMapping("/by-subject/{subjectId}")
    public List<Unit> getBySubject(@PathVariable Long subjectId) {
        return unitRepository.findBySubjectId(subjectId);
    }

    // =============================================
// CREATE UNIT (JSON BODY)
// =============================================
    @PostMapping
    public Unit create(@RequestBody UnitCreateDTO dto) {

        if (dto.getSubjectId() == null) {
            throw new RuntimeException("Subject ID required");
        }

        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Unit unit = new Unit();
        unit.setName(dto.getName());
        unit.setSubject(subject);

        return unitRepository.save(unit);
    }

    // =============================================
// UPDATE UNIT (JSON BODY)
// =============================================
    @PutMapping("/{id}")
    public Unit update(@PathVariable Long id,
                       @RequestBody UnitCreateDTO dto) {

        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        unit.setName(dto.getName());

        return unitRepository.save(unit);
    }

    // =============================================
    // DELETE UNIT
    // =============================================

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        unitRepository.deleteById(id);
    }
}