package ru.rivc_pulkovo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.rivc_pulkovo.domain.Department;
import ru.rivc_pulkovo.repository.DepartmentRepository;
import ru.rivc_pulkovo.service.dto.DepartmentDTO;
import ru.rivc_pulkovo.service.mapper.DepartmentMapper;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentMapper departmentMapper;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    @DisplayName("Tests that a department is savable.")
    public void testSave_whenDepartmentDetailsProvided_returnsDepartmentObject() {
        //Given
        DepartmentDTO departmentDTO = new DepartmentDTO();
        Department department = new Department();

        Mockito.when(departmentMapper.toEntity(Mockito.any(DepartmentDTO.class))).thenReturn(department);
        Mockito.when(departmentRepository.save(Mockito.any(Department.class))).thenReturn(department);
        Mockito.when(departmentMapper.toDto(Mockito.any(Department.class))).thenReturn(departmentDTO);

        //When
        departmentDTO = this.departmentService.save(departmentDTO);

        //Then
        assertNotNull(departmentDTO);
        Mockito.verify(departmentRepository, Mockito.times(1)).save(Mockito.any(Department.class));
    }

    @Test
    @DisplayName("Tests that a department tree cannot have more than one root.")
    public void testSave_whenSecondDepartmentRootDetailsProvided_returnsIllegalArgumentException() {
        //Given
        DepartmentDTO departmentDTO = new DepartmentDTO();
        Mockito.when(departmentRepository.findDepartmentRootId()).thenReturn(new Random().nextLong());

        //When && Then
        assertThrows(IllegalArgumentException.class, () -> departmentService.save(departmentDTO));
    }

    @Test
    @DisplayName("Tests that a department with sub-departments is not deletable.")
    public void testDelete_whenDepartmentIsNotLeafNode_returnsIllegalArgumentException() {
        //Given
        int childrenCount = 1 + new Random().nextInt(Integer.MAX_VALUE);
        Mockito.when(departmentRepository.countDepartmentByParentId(Mockito.any(Long.class))).thenReturn(childrenCount);

        //When && Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.departmentService.delete(1L));
    }

    @Test
    @DisplayName("Tests whether a department without any sub-departments is deletable.")
    public void testDelete_whenDepartmentIsLeafNode_doesNotThrowIllegalArgumentException() {
        //Given
        Mockito.when(departmentRepository.countDepartmentByParentId(Mockito.any(Long.class))).thenReturn(0);

        //When && Then
        Assertions.assertDoesNotThrow(() -> this.departmentService.delete(1L));
    }
}
