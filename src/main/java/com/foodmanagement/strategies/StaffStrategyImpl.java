package com.foodmanagement.strategies;

import com.foodmanagement.Entity.MoreEmpInfo;
import com.foodmanagement.Entity.Role;
import com.foodmanagement.Entity.StudentMoreInfo;
import com.foodmanagement.Entity.User;
import com.foodmanagement.Repository.MoreEmpInfoRepository;
import com.foodmanagement.Repository.RoleRepository;
import com.foodmanagement.Repository.UsersRepository;
import com.foodmanagement.dto.CommonResponse;
import com.foodmanagement.dto.EmployeeMoreInfoDto;
import com.foodmanagement.dto.RegisterDto;
import com.foodmanagement.dto.StudentMoreInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Component("staff")
@RequiredArgsConstructor
public class StaffStrategyImpl implements UserStrategies {

    private final RoleRepository roleRepository;
    private final UsersRepository usersRepository;
    private final MoreEmpInfoRepository moreEmpInfoRepository;

    @Override
    public CommonResponse<User> saveUser(RegisterDto registerDto) {
        if(Boolean.TRUE.equals(usersRepository.existsByUsername(registerDto.getPhoneNumber()))){
            return new CommonResponse<User>(0001,"User already exists",null);
        }
        Role role = roleRepository.findByName("ROLE_STAFF");
        if(ObjectUtils.isEmpty(role)){
            role = new Role();
            role.setName("ROLE_STAFF");
            roleRepository.save(role);
        }
        User user = new User();
        user.setUsername(registerDto.getPhoneNumber());
        user.setName(registerDto.getName());
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy(registerDto.getCreatedBy());
        user.setMail(registerDto.getMail());
        user.setPassword(registerDto.getPassword());
        user.setStatus("PENDING");
        user.setRoles(Collections.singletonList(role));


        usersRepository.save(user);
        return new CommonResponse<User>(0000,"Successful",user);
    }

    @Override
    public CommonResponse saveMoreDetails(StudentMoreInfoDto studentMoreInfoDto) {
        return null;
    }

    @Override
    public void updateUser() {

    }

    @Override
    public void deleteUser() {

    }

    @Override
    public void getUser() {

    }

    @Override
    public Page<User> getAllUsers(String status, Pageable pageable) {
        return usersRepository. findAllByStatusAndRole(status,"ROLE_STAFF",pageable);
    }

    @Override
    public CommonResponse saveMoreDetailsEmp(EmployeeMoreInfoDto employeeMoreInfoDto) {
        MoreEmpInfo moreEmpInfo = new MoreEmpInfo();
        moreEmpInfo.setDepartment(employeeMoreInfoDto.getDepartment());
        moreEmpInfo.setNic(employeeMoreInfoDto.getNic());
        moreEmpInfo.setEmail(employeeMoreInfoDto.getEmail());
        moreEmpInfo.setInternal(employeeMoreInfoDto.isInternal());
        moreEmpInfo.setGender(employeeMoreInfoDto.getGender());

        Optional<User> optionalUserPresent =usersRepository.findById(employeeMoreInfoDto.getUserId());
        if(optionalUserPresent.isPresent()){
            moreEmpInfo.setUser(optionalUserPresent.get());
        }else{
            return new CommonResponse(0001,"User not found",null);
        }

        MoreEmpInfo empInfo =moreEmpInfoRepository.save(moreEmpInfo);
        if(empInfo.getNic()!=null){
            return new CommonResponse(0000,"Successful",empInfo);
        }
        try{
            usersRepository.deleteAllById(Collections.singleton(employeeMoreInfoDto.getUserId()));
            return new CommonResponse(0004,"Rollback Success",null);
        }catch (Exception e){
            return new CommonResponse(0003,"RollBack Failed",null);
        }
    }
}
