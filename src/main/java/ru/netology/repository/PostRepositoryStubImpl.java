package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PostRepositoryStubImpl implements PostRepository {
    private List<Post> posts = new ArrayList<>();

    private AtomicLong idCounter = new AtomicLong(0L); // 0 в типе long

    public List<Post> all() {

        return posts.stream().filter(p -> !p.isRemoved()).collect(Collectors.toList());
    }

    public Optional<Post> getById(long id) {
        Optional<Post> optionalPost = null;
        for (Post post : posts) {
            if (post.getId() == (id)) {
                if (!post.isRemoved()) {
                    optionalPost = Optional.of(post);
                } else {
                    throw new NotFoundException("Нет такого id");
                }

            }
        }
        return optionalPost;
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(idCounter.incrementAndGet());
            posts.add(post);
            return post;
        } else {
            for (Post postPast : posts) {
                if (postPast.getId() == (post.getId())) {
                    if (!postPast.isRemoved()) {
                        postPast.setContent(post.getContent());
                    } else {
                        throw new NotFoundException("Нет такого id");
                    }


                    return postPast;
                }
            }

            posts.add(post);

        }
        return post;
    }

    public void removeById(long id) {
        for (Post post : posts) {
            if (post.getId() == (id)) {
                post.setRemoved(true);
                break;
            }
        }
    }
}