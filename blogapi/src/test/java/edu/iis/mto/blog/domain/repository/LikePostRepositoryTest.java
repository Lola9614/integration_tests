package edu.iis.mto.blog.domain.repository;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LikePostRepository likePostRepository;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private BlogPost blogPost;
    private LikePost likePost;

    @Before public void setup() {
        user = new User();
        user.setFirstName("Karolina");
        user.setEmail("karolinaplociennik96@wp.pl");
        user.setAccountStatus(AccountStatus.NEW);

        List<LikePost> likePosts = new ArrayList<>();
        blogPost = new BlogPost();
        blogPost.setUser(user);
        blogPost.setEntry("entry");
        blogPost.setLikes(likePosts);

        likePost = new LikePost();
        likePost.setPost(blogPost);
        likePost.setUser(user);

        likePosts.add(likePost);

        userRepository.save(user);
        blogPostRepository.save(blogPost);
        entityManager.persist(likePost);

    }


    @Test
    public void shouldSaveLikePost() {

        List<LikePost> result = likePostRepository.findAll();

        Assert.assertThat(result.size(), is(1));
        Assert.assertThat(result.get(0).getUser(), is(user));
    }


    @Test
    public void shouldEditLikePost() {
        user.setFirstName("Karolina_UPDATE");
        user.setAccountStatus(AccountStatus.CONFIRMED);
        userRepository.save(user);

        entityManager.persist(likePost);

        List<LikePost> likePostsResults = likePostRepository.findAll();

        Assert.assertThat(likePostsResults.size(), is(1));
        Assert.assertThat(likePostsResults.get(0).getUser().getFirstName(), is("Karolina_UPDATE"));
    }


    @Test
    public void shouldFindLikePostByUserAndPost() {

        Optional<LikePost> likePostsResults = likePostRepository.findByUserAndPost(user,blogPost);

        Assert.assertThat(likePostsResults.isPresent(), is(true));
    }


    @Test
    public void shouldNotFindLikePostIfUserIsNotPassed() {

        Optional<LikePost> likePostsResults = likePostRepository
                .findByUserAndPost(null,blogPost);

        Assert.assertThat(likePostsResults.isPresent(), is(false));
    }

    @Test
    public void shouldNotFindLikePostIfPostIsNotPassed() {

        Optional<LikePost> likePostsResults = likePostRepository
                .findByUserAndPost(user,null);

        Assert.assertThat(likePostsResults.isPresent(), is(false));
    }

    @Test
    public void shouldNotFindLikePostIfPostAndUserAreNotPassed() {

        Optional<LikePost> likePostsResults = likePostRepository
                .findByUserAndPost(null,null);

        Assert.assertThat(likePostsResults.isPresent(), is(false));
    }
}
