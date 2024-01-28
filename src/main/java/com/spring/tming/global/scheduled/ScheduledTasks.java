package com.spring.tming.global.scheduled;

import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.repository.PostRepository;
import com.spring.tming.global.meta.Status;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final PostRepository postRepository;

    @Transactional
    @Scheduled(cron = "0 0 3 * * *")
    public void checkAndHandleExpiredPosts() {
        ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
        ZonedDateTime currentSeoulInstant = ZonedDateTime.now(seoulZoneId);
        List<Post> posts =
                postRepository.findAllByDeadlineBeforeAndStatus(
                        Timestamp.valueOf(currentSeoulInstant.toLocalDateTime()), Status.OPEN);
        List<Post> changedPosts = new ArrayList<>();
        for (Post post : posts) {
            changedPosts.add(
                    Post.builder()
                            .postId(post.getPostId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .deadline(post.getDeadline())
                            .status(Status.CLOSED)
                            .visit(post.getVisit())
                            .imageUrl(post.getImageUrl())
                            .user(post.getUser())
                            .build());
        }
        postRepository.saveAll(changedPosts);
    }
}
