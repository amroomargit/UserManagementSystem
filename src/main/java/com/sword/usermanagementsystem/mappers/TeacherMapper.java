package com.sword.usermanagementsystem.mappers;

import com.sword.usermanagementsystem.dtos.TeacherDTO;
import com.sword.usermanagementsystem.dtos.TopicDTO;
import com.sword.usermanagementsystem.entities.Teacher;
import com.sword.usermanagementsystem.entities.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring") //This tag makes this mapper class into a bean so that we can inject anywhere using @Autowired
public interface TeacherMapper {

    //Entities to DTOs (for a list of objects)
    List<TeacherDTO> toDTOs(List<Teacher> teacher);

    //DTOs to Entities (for a list of objects)
    List<Teacher> toEntities(List<TeacherDTO> teacherDTO);







    //Entity to DTO (for a single object)

    /* We are converting from Entity to DTO. In the TeacherDTO, we have a list of Integers (holding the ids of the TopicDTOs)
      called topicIds, and in the Teacher Entity, we have a list of Topic Entities called topics. Our target is what we are
      converting our list from the Entity into, which is taken from the source, so topics -> topicIds.
    * qualifiedByName tells MapStruct which custom helper method (defined with @Named) should be used for this specific mapping. */

    //NOTE: @Mapping here is for the ManyToMany for Teacher and Topic, and only activates when it's a ManyToMany relationship calling the mapper. If there was no ManyToMany, we wouldn't need this, but the method call below would stay the same
    //ALSO: The paragraph above is describing the ManyToMany relationship again, the method toDTO alone doesn't do this if it was another type of relationship
    @Mapping(target = "topicIds", source = "topics", qualifiedByName = "topicsToIds")
    TeacherDTO toDTO(Teacher teacher);



    //DTO to Entity (for a single object)

    /* When mapping from a DTO to an Entity, the list inside the DTO only holds Integers, but the list inside the Entity
     holds Topic Entities, which has way more instance variable to fill out in that class. However, since we only have
     an Integer for the id, and not anything else, we say ignore = true so that those fields aren't attempted to be filled
     out automatically, causing possible recursion or incomplete data problems */

    //NOTE: @Mapping here is for the ManyToMany for Teacher and Topic, and only activates when it's a ManyToMany relationship calling the mapper. If there was no ManyToMany, we wouldn't need this, but the method call below would stay the same
    //ALSO: The paragraph above is describing the ManyToMany relationship again, the method toEntity alone doesn't do this if it was another type of relationship
    @Mapping(target = "topics", ignore = true)
    Teacher toEntity(TeacherDTO teacherDTO);



    //Helper method

    /*This line is a helper method specifically for the ManyToMany relationship between Teacher and Topic. All that
    * @Named("topicsToIds") does is name the method below, so that when the @Mapping tag in the Entity to DTO starts
    * looking for the method that we named in qualifiedByName, it will know it is this one */
    @Named("topicsToIds")
    default List<Integer> mapTopicsToIds(List<Topic> topics){
        return topics != null ? topics.stream().map(Topic::getId).collect(Collectors.toList()) : null;
    }
    /*This method is a custom mapping method that transforms the List<Topic> from the Entity, to the List<Integer>
    * of topic ids in the DTO. It uses Java's Stream API, then it maps each Topic object to its id with (Topic::getId).
    * It collects them into a list of integers with .collect(Collectors.toList()), and then it returns null if the
    * list is empty or missing. */
}
