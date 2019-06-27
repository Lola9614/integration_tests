package edu.iis.mto.blog.domain;

import edu.iis.mto.blog.domain.errors.DomainError;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.repository.BlogPostRepository;
import edu.iis.mto.blog.domain.repository.LikePostRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.api.request.UserRequest;
import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;
import edu.iis.mto.blog.domain.repository.UserRepository;
import edu.iis.mto.blog.mapper.BlogDataMapper;
import edu.iis.mto.blog.services.BlogService;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class) @SpringBootTest public class BlogManagerTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    BlogPostRepository postRepository;

    @MockBean
    LikePostRepository likeRepository;

    @Autowired
    BlogDataMapper dataMapper;

    @Autowired
    BlogService blogService;

    private User ownerOfPost;
    private User userHowLikesPost;
    private BlogPost blogPost;

    @Before
    public void setUp() {
        ownerOfPost = new User();
        ownerOfPost.setId(0L);
        ownerOfPost.setAccountStatus(AccountStatus.CONFIRMED);

        userHowLikesPost = new User();
        userHowLikesPost.setId(1L);
        userHowLikesPost.setAccountStatus(AccountStatus.CONFIRMED);

        blogPost = new BlogPost();
        blogPost.setId(0L);
        blogPost.setUser(userHowLikesPost);
        blogPost.setEntry("entry");
    }

    @Test public void creatingNewUserShouldSetAccountStatusToNEW() {
        blogService.createUser(new UserRequest("John", "Steward", "john@domain.com"));
        ArgumentCaptor<User> userParam = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userParam.capture());
        User user = userParam.getValue();
        Assert.assertThat(user.getAccountStatus(), Matchers.equalTo(AccountStatus.NEW));
    }

    @Test(expected = DomainError.class)
    public void shouldFail_whenUserIsRemoved() {
        userHowLikesPost.setAccountStatus(AccountStatus.REMOVED);
        Mockito.when(userRepository.findById(userHowLikesPost.getId())).thenReturn(Optional.of(userHowLikesPost));
        Mockito.when(postRepository.findById(blogPost.getId())).thenReturn(Optional.of(blogPost));

        blogService.addLikeToPost(userHowLikesPost.getId(), blogPost.getId());
    }

    @Test(expected = DomainError.class)
    public void shouldFail_whenUserIsNew() {
        userHowLikesPost.setAccountStatus(AccountStatus.REMOVED);
        Mockito.when(userRepository.findById(userHowLikesPost.getId())).thenReturn(Optional.of(userHowLikesPost));
        Mockito.when(postRepository.findById(blogPost.getId())).thenReturn(Optional.of(blogPost));

        blogService.addLikeToPost(userHowLikesPost.getId(), blogPost.getId());
    }

    @Test
    public void shouldAddPost_whenUserIsConfirmed() {
        Mockito.when(userRepository.findById(ownerOfPost.getId())).thenReturn(Optional.of(ownerOfPost));
        Mockito.when(userRepository.findById(userHowLikesPost.getId())).thenReturn(Optional.of(userHowLikesPost));
        Mockito.when(postRepository.findById(blogPost.getId())).thenReturn(Optional.of(blogPost));

        boolean expected = blogService.addLikeToPost(ownerOfPost.getId(), blogPost.getId());
        assertThat(expected, is(true));
    }

}
