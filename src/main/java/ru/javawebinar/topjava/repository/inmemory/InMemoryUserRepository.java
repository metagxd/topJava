package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    {
        List<User> userList = Arrays.asList(
                new User(0, "Test3", "qw@fg.com", "123", Role.USER),
                new User(0, "Test2", "qw@fg.com", "123", Role.USER),
                new User(0, "Test5", "qw@fg.com", "123", Role.USER),
                new User(0, "Test4", "qw@fg.com", "123", Role.USER)
        );
        userList.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        Optional<User> optionalUser = Optional.ofNullable(repository.remove(id));
        return optionalUser.isPresent() && optionalUser.get().getId() == id;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(id.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        } else {
            log.info("update {}", user);
            return repository.replace(user.getId(), user);
        }
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return repository.values().stream()
                .parallel()
                .sorted(Comparator.comparing(AbstractNamedEntity::getName))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values().stream()
                .parallel()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElseThrow(() -> new NotFoundException(email));
    }
}
