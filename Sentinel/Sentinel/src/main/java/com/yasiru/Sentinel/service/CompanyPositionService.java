package com.yasiru.Sentinel.service;

import com.yasiru.Sentinel.dto.request.CompanyPositionsRequest;
import com.yasiru.Sentinel.dto.response.CompanyPositionResponse;
import com.yasiru.Sentinel.entity.CompanyPositions;
import com.yasiru.Sentinel.entity.User;
import com.yasiru.Sentinel.repository.CompanyPositionRepository;
import com.yasiru.Sentinel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyPositionService {

    private final CompanyPositionRepository companyPositionRepository;
    private final UserRepository userRepository;

    public Page<CompanyPositionResponse> getAllPositions(Pageable pageable) {

        return  companyPositionRepository.findAll(pageable).map(CompanyPositionResponse::from);

    }

    @Transactional
    public CompanyPositionResponse createPosition(CompanyPositionsRequest request,Long adminId){
        if (companyPositionRepository.existsByTitle(request.title())){
            throw new IllegalArgumentException("A position with this title already exists");
        }

        User admin = userRepository.findById(adminId)
                .orElseThrow(()->new IllegalArgumentException("Admin not found"));

        CompanyPositions position = CompanyPositions.builder()
                .title(request.title())
                .hourlyRate(request.hourly_rate())
                .createdByAdmin(admin)
                .build();


        return CompanyPositionResponse.from(
                companyPositionRepository.save(position)
        );


    }


    @Transactional
    public CompanyPositionResponse getPosition(Long id) {
        return companyPositionRepository.findById(id)
                .map(CompanyPositionResponse::from)
                .orElseThrow(()->new IllegalArgumentException("Position not found id: "+id));
    }

    public CompanyPositionResponse updatePosition(Long id, CompanyPositionsRequest request) {
        CompanyPositions positions = companyPositionRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Position not found with id: "+id));




    }
}
