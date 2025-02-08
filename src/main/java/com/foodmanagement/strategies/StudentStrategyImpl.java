package com.foodmanagement.strategies;

import com.foodmanagement.Entity.Role;
import com.foodmanagement.Entity.StudentMoreInfo;
import com.foodmanagement.Entity.User;
import com.foodmanagement.Repository.RoleRepository;
import com.foodmanagement.Repository.StudentMoreInfoRepository;
import com.foodmanagement.Repository.UsersRepository;
import com.foodmanagement.dto.CommonResponse;
import com.foodmanagement.dto.RegisterDto;
import com.foodmanagement.dto.StudentMoreInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Component("student")
@RequiredArgsConstructor
public class StudentStrategyImpl implements UserStrategies {

    private final RoleRepository roleRepository;
    private final UsersRepository usersRepository;
    private final StudentMoreInfoRepository studentMoreInfoRepository;
    @Override
    public CommonResponse<User> saveUser(RegisterDto registerDto) {
        if(Boolean.TRUE.equals(usersRepository.existsByUsername(registerDto.getPhoneNumber()))){
            return new CommonResponse<User>(0001,"User already exists",null);
        }
        Role role = roleRepository.findByName("ROLE_STUDENT");
        if(ObjectUtils.isEmpty(role)){
            role = new Role();
            role.setName("ROLE_STUDENT");
            roleRepository.save(role);
        }
        User user = new User();
        user.setUsername(registerDto.getPhoneNumber());
        user.setName(registerDto.getName());
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy(registerDto.getCreatedBy());
        user.setMail(registerDto.getMail());
        user.setPassword(registerDto.getPassword());
        user.setStatus("ACTIVE");
        user.setRoles(Collections.singletonList(role));

        usersRepository.save(user);
        return new CommonResponse<User>(0000,"Successful",user);
    }
    @Override
    public CommonResponse saveMoreDetails(StudentMoreInfoDto studentMoreInfoDto) {
        StudentMoreInfo studentMoreInfo = new StudentMoreInfo();
        studentMoreInfo.setStudentId(studentMoreInfoDto.getStudentId());
        studentMoreInfo.setBatch(studentMoreInfoDto.getBatch());
        studentMoreInfo.setFaculty(studentMoreInfoDto.getFaculty());
        studentMoreInfo.setGender(studentMoreInfoDto.getGender());
        studentMoreInfo.setStream(studentMoreInfoDto.getStream());

        Optional<User> optionalUserPresent =usersRepository.findById(studentMoreInfoDto.getUserId());
        if(optionalUserPresent.isPresent()){
            studentMoreInfo.setUser(optionalUserPresent.get());
        }else{
            return new CommonResponse(0001,"User not found",null);
        }
        StudentMoreInfo studentMoreInfoSaved =studentMoreInfoRepository.save(studentMoreInfo);
        if(studentMoreInfoSaved.getStudentId()!=null){
            return new CommonResponse(0000,"Successful",studentMoreInfoSaved);
        }
        try{
        usersRepository.deleteAllById(Collections.singleton(studentMoreInfoDto.getUserId()));
        }catch (Exception e){
            return new CommonResponse(0003,"RollBack Failed",null);
        }
        return new CommonResponse(0004,"Failed Rollback Success",null);
    }
    @Override
    public void updateUser() {
        System.out.println("Student updated");
    }

    @Override
    public void deleteUser() {
        System.out.println("Student deleted");
    }

    @Override
    public void getUser() {
        System.out.println("Student retrieved");
    }

    @Override
    public Page<User> getAllUsers(String status, Pageable pageable) {
        return usersRepository.findAllByStatusAndRole(status,"ROLE_STUDENT",pageable);
    }
}
