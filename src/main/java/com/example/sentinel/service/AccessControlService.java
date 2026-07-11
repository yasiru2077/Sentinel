package com.example.sentinel.service;

import com.example.sentinel.entity.*;
import com.example.sentinel.exception.UnauthorizedException;
import com.example.sentinel.repository.ProjectMemberRepository;
import com.example.sentinel.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessControlService {

    private final RoleRepository roleRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public Role requireMember(Company company, User user){
        return roleRepository.findByUserAndCompany(user,company)
                .orElseThrow(()->new UnauthorizedException("You are not a member of this company"));
    }

    public Role requireAdmin(Company company,User user){
        Role role = requireMember(company, user);
        if (role.getRoleType() != RoleType.ADMIN){
            throw new UnauthorizedException("Admin access required");
        }
        return role;
    }

    public boolean isAdmin(Company company,User user){
        return roleRepository.findByUserAndCompany(user,company)
                .map(role -> role.getRoleType() == RoleType.ADMIN)
                .orElse(false);
    }

    public ProjectMember requireProjectMember(Project project, User user){
        return projectMemberRepository.findByProjectAndUser(project,user)
                .orElseThrow(()->new UnauthorizedException("You are not assigned to this project"));
    }

    public void requireProjectAccess(Project project,User user){

        if (isAdmin(project.getCompany(),user)){
            return;
        }

        requireProjectMember(project,user);

    }




}
