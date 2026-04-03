package mk.ukim.finki.backend.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.backend.model.dto.CheckedApiDto;
import mk.ukim.finki.backend.model.dto.CreateApiDto;
import mk.ukim.finki.backend.model.dto.DisplayApiDto;
import mk.ukim.finki.backend.model.dto.DisplayCheckDto;
import mk.ukim.finki.backend.service.application.ApiApplicationService;
import mk.ukim.finki.backend.service.application.ChecksApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final ApiApplicationService apiApplicationService;
    private final ChecksApplicationService checksApplicationService;

    @PostMapping("/create")
    public ResponseEntity<DisplayApiDto> createApi(@RequestBody @Valid CreateApiDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(apiApplicationService.createApi(dto));
    }

    @GetMapping
    public ResponseEntity<Page<CheckedApiDto>> getCheckedApis(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(apiApplicationService.getAllApisWithStatus(page, size));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<DisplayApiDto> updateApi(@PathVariable Long id, @RequestBody @Valid CreateApiDto dto) {
        return ResponseEntity.ok(apiApplicationService.updateApi(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<DisplayApiDto> deleteApi(@PathVariable Long id) {
        return ResponseEntity.ok(apiApplicationService.deleteById(id));
    }

    @GetMapping("/{id}/checks")
    public ResponseEntity<Page<DisplayCheckDto>> getAllChecksForApi(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(checksApplicationService.findAllChecksForApi(id, page, size));
    }
}
