package com.github.daniel.shuy.openapi.generator.examples.spring.service;

import com.github.daniel.shuy.openapi.generator.examples.spring.dto.TagDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.mapper.TagMapper;
import com.github.daniel.shuy.openapi.generator.examples.spring.model.Tag;
import com.github.daniel.shuy.openapi.generator.examples.spring.repository.TagRepository;
import com.github.daniel.shuy.openapi.generator.examples.spring.util.exception.NotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {
  private final TagMapper tagMapper;
  private final TagRepository tagRepository;

  public Tag addTag(TagDto tagDto) {
    var tag = tagMapper.fromDto(tagDto);
    return tagRepository.save(tag);
  }

  public Set<Tag> upsertTags(Collection<TagDto> tagDtos) {
    if (tagDtos == null || tagDtos.isEmpty()) {
      return Collections.emptySet();
    }

    var tagIds = tagDtos.stream()
        .map(TagDto::getId)
        .filter(Objects::nonNull)
        .toList();

    var tagsById = getTagsByIds(tagIds)
        .stream()
        .collect(Collectors.toMap(Tag::getId, Function.identity()));

    return tagDtos.stream()
        .map(tagDto -> {
          var tagId = tagDto.getId();
          if (tagId == null) {
            return addTag(tagDto);
          }

          var tag = tagsById.get(tagId);
          if (tag == null) {
            throw new NotFoundException("Tag not found: " + tagId);
          }

          tagMapper.updateFromDto(tagDto, tag);
          return tag;
        })
        .collect(Collectors.toSet());
  }

  public Set<Tag> getTagsByIds(Collection<Long> tagIds) {
    return tagRepository.findByIdIn(tagIds);
  }
}
