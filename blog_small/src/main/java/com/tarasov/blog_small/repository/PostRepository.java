package com.tarasov.blog_small.repository;

import com.tarasov.blog_small.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
