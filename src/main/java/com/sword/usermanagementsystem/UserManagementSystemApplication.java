package com.sword.usermanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sword.usermanagementsystem.dtos.EventDTO;
import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.entities.Event;
import com.sword.usermanagementsystem.entities.Teacher;
import com.sword.usermanagementsystem.mappers.EntityMapper;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class UserManagementSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(UserManagementSystemApplication.class, args);

		//Create Teacher entity
		Teacher teacher = new Teacher();
		teacher.setId(1);
		teacher.setName("Mr. Joestar");


		//Create an Event entity and link it to the teacher
		Event event = new Event();
		event.setId(10);
		event.setCourseid(123);
		event.setStarttime(LocalDateTime.now());
		event.setEndtime(LocalDateTime.now().plusHours(2));
		event.setTeacher(teacher);

		//Add event to teacher’s list
		teacher.setEvents(Arrays.asList(event));

		//Map Teacher → TeacherDTO
		TeacherDTO teacherDto = EntityMapper.toTeacherDto(teacher);

		System.out.println("Teacher DTO: " + teacherDto);
		System.out.println("Events inside TeacherDTO: " + teacherDto.getEvents());
		System.out.println("TeacherID inside EventDTO: " + teacherDto.getEvents().get(0).getTeacherid());


	}

}
