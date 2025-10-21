package com.sword.usermanagementsystem.mappers;

import com.sword.usermanagementsystem.dtos.TopicDTO;
import com.sword.usermanagementsystem.dtos.CourseDTO;
import com.sword.usermanagementsystem.entities.Course;
import com.sword.usermanagementsystem.entities.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;


//This mapper is for a ManyToMany relationship between the Course and Topic entities

@Mapper(componentModel = "spring") //Generates spring bean so this class can be Autowired
public interface CourseTopicMapper {


    /* ************* Course Entity to DTO ************* */

    /* The courseToCourseDTO method below maps an entity to DTO and converts the list of Topic objects into a list of
    Integer IDs using helper @Named method */

    /* In the @Mapping tag, the source is getting mapped to the target, so in this case, "topics" (which is a list of
    Topic objects) imported from the Course entity class, is getting mapped to the "topicIds" (which is a list of
    Integer objects) from CourseDTO. Remember, there's only two columns in the newly joined table in a ManyToMany
    relationship between two entities, which is why we are only storing Integers in topicIds, they're the primary keys
    of the topic entity. Notice that even though this is for mapping courses, we're dealing in topics. Go check the
    comment at the bottom of the courseDTO class, and you'll see why we're dealing in topic and not courses here.*/
    @Mapping(source = "topics", target = "topicIds", qualifiedByName = "topicsToIds")
    CourseDTO courseToCourseDTO(Course course);



    /* ************* Course DTO to Entity ************* */

    /* Maps back from DTO to entity, ignoring the List<Topic> topics imported from the Course entity class so that we
    can manually attach topic entities in the service layer instead. If we didn't ignore this here, then an infinite
    loop would happen again */

    //Read the "No need for Topic DTO to Entity" section below for more info
    @Mapping(target = "topics", ignore = true)
    Course courseDTOtoCourse(CourseDTO dto);



    /* ************* Helper method that maps List<Topic> -> List<Integer> ************* */
    @Named("topicsToIds")
    default List<Integer> topicsToIds(List<Topic> topic){
        if(topic == null){
            return null;
        }
        return topic.stream().map(com.sword.usermanagementsystem.entities.Topic::getId).collect(Collectors.toList());
    }






    /* ************* Topic Entity to DTO ************* */

    /* The courseToCourseDTO method below maps an entity to DTO and converts the list of Topic objects into a list of
    Integer IDs using helper @Named method */

    /* In the @Mapping tag, the source is getting mapped to the target, so in this case, "courses" (which is a list of
    Course objects) imported from the Course entity class, is getting mapped to the "courseIds" (which is a list of
    Integer objects) from TopicDTO. Remember, there's only two columns in the newly joined table in a ManyToMany
    relationship between two entities, which is why we are only storing Integers in courseIds, they're the primary keys
    of the course entity. Notice that even though this is for mapping courses, we're dealing in courses. Go check the
    comment at the bottom of the TopicDTO class, and you'll see why we're dealing in courses and not topics here.*/
    @Mapping(source = "courses", target = "courseIds", qualifiedByName = "coursesToIds")
    TopicDTO topicToTopicDTO(Topic topic);



    /* ************* No need for Topic DTO to Entity ************* */
    /* In this particular case there is no need because we don't run the risk of an infinite loop. This is because
     MapStruct interface matches variables with the same name in the entity and DTO class. Basically, MapStruct will
     ignore courseIds because there’s no courseIds field in Topic entity like there is in the DTO. However, since there
     is a topicId in both the Course entity and DTO, it would have mapped them, causing that recurring reference,
     causing that infinite loop, which is why we have to explicitly ignore it.*/



    /* ************* Helper method that maps List<Course> -> List<Course> ************* */
    @Named("coursesToIds")
    default List<Integer> coursesToIds(List<Course> courses){
        if(courses == null){
            return null;
        }
        return courses.stream().map(Course::getId).collect(Collectors.toList());
    }
}
