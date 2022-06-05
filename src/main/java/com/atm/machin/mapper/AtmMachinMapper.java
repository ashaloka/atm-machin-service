package com.atm.machin.mapper;


public class AtmMachinMapper {
/*
    public static EmployeeDTO mapToDto(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setUsername(employee.getUsername());
//        employeeDTO.setPassword(employee.getPassword());
        employeeDTO.setRole(employee.getRole());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setMiddleName(employee.getMiddleName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setPhoneNumber((employee.getPhoneNumber()));
        employeeDTO.setCreatedAt(employee.getCreatedAt());
        employeeDTO.setStatus(String.valueOf(employee.getStatus()));
        employeeDTO.setDob(employee.getDob());
        employeeDTO.setCertificationDob(employee.getCertificationDob());
        employeeDTO.setQualification(employee.getQualification());
        employeeDTO.setBloodGroup(employee.getBloodGroup());
        employeeDTO.setPersonalEmail(employee.getPersonalEmail());
        employeeDTO.setEmployeeNumber(employee.getEmployeeNumber());
        employeeDTO.setGenderName(employee.getGender());
        return employeeDTO;
    }

    private static Gender getGender(String genderName) {
        Gender gender = new Gender();
        gender.setGenderName(genderName);
        return gender;
    }

    public static Employee mapToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDTO.getEmployeeId());
        employee.setUsername(employeeDTO.getUsername());
        employee.setPassword(employeeDTO.getPassword());
        employee.setRole(employeeDTO.getRole());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setMiddleName(employeeDTO.getMiddleName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setCreatedAt(employeeDTO.getCreatedAt());
        employee.setStatus(StatusMapper.mapEmployeeStatus(employeeDTO.getStatus()));
        employee.setEmployeeNumber(employeeDTO.getEmployeeNumber());
        employee.setDob(employeeDTO.getDob());
        employee.setCertificationDob(employeeDTO.getCertificationDob());
        employee.setQualification(employeeDTO.getQualification());
        employee.setBloodGroup(employeeDTO.getBloodGroup());
        employee.setPersonalEmail(employeeDTO.getPersonalEmail());
        employee.setEmployeeNumber(employeeDTO.getEmployeeNumber());
        employee.setGender(employeeDTO.getGenderName());
        return employee;
    }

    public static EmployeeDTO mapToDTOWithSupervisor(Employee employee) {
        EmployeeDTO employeeDTO = mapToDto(employee);
        if (employee.getSupervisor() != null) {
            employeeDTO.setSupervisor(com.vinci.employee.lm.mapper.AtmMachinMapper.mapToDto(employee.getSupervisor()));
        }
        return employeeDTO;
    }

    public static Employee mapToEntityWithSupervisor(EmployeeDTO employeeDTO) {
        Employee employee = mapToEntity(employeeDTO);
        if (employeeDTO.getSupervisor() != null) {
            employee.setSupervisor(com.vinci.employee.lm.mapper.AtmMachinMapper.mapToEntity(employeeDTO.getSupervisor()));
        }
        return employee;
    }*/
}
