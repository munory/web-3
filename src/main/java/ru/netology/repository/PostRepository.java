package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {

    private final AtomicLong idGenerator = new AtomicLong(0);

    private final Map<Long, Post> posts = new ConcurrentHashMap<>();

    public List<Post> all() {
        return Collections.unmodifiableList(new ArrayList<>(posts.values()));
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) throws Exception {
        if (post.getId() == 0L) {
            post.setId(idGenerator.incrementAndGet());
        } else {
            if (!posts.containsKey(post.getId())) {
                throw new Exception("Post not found");
            }
        }
        posts.put(post.getId(), post);
        return post;
    }

    public void removeById(long id) throws Exception {
        if (!posts.containsKey(id)) {
            throw new Exception("Post not found");
        }
        posts.remove(id);
    }

}
