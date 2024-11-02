package com.example.posts_service.services.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.posts_service.services.PostService;


@Service
@RequiredArgsConstructor
public class RabbitReceiverService {

    @Autowired
    private PostService postService;

    @RabbitListener(queues = "${spring.rabbitmq.queue.posts}")
    public void receiveMessage(String userId) {
        userId = userId.replace("\"", "");
        postService.deleteUserPostsAndLikes(userId);
    }
}
