package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.controller.dto.EmployeeRequest;
import com.rest.springbootemployee.controller.dto.EmployeeResponse;
import com.rest.springbootemployee.controller.mapper.EmployeeMapper;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;
    private EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public List<EmployeeResponse> getAll() {
        return employeeService
                .findAll()
                .stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EmployeeResponse getById(@PathVariable String id) {
        return employeeMapper.toResponse(employeeService.findById(id));
    }

    @GetMapping(params = {"gender"})
    public List<EmployeeResponse> getByGender(@RequestParam String gender) {
        return employeeService
                .findByGender(gender)
                .stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse add(@RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.toEntity(employeeRequest);
        Employee savedEmployee = employeeService.create(employee);
        return employeeMapper.toResponse(savedEmployee);
    }
    @PutMapping("/{id}")
    public EmployeeResponse update(@PathVariable String id, @RequestBody EmployeeRequest employeeRequest) {
        return employeeMapper.toResponse(employeeService.update(id, employeeMapper.toEntity(employeeRequest)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        employeeService.delete(id);
    }


    @GetMapping(params = {"page", "pageSize"})
    public List<EmployeeResponse> getByPage(int page, int pageSize) {
        return employeeService
                .findByPage(page, pageSize)
                .stream().map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
    }

}
