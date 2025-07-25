package com.web.user;


import com.web.exception.ResourceAlreadyExistsException;
import com.web.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDtoResponse createUser(UserDtoRequest userDtoRequest) {

        String traceId = UUID.randomUUID().toString();
        StopWatch watch = new StopWatch();
        watch.start();

        log.info("[{}] START createUser | username={}, email={}",
                traceId, userDtoRequest.getUsername(), userDtoRequest.getEmail());
        // improve
//        List<UserEntity> existingUsers = userRepository.findByUsernameOrEmail(
//                userDtoRequest.getUsername(), userDtoRequest.getEmail());
//
//        for (UserEntity user : existingUsers) {
//            if (user.getUsername().equals(userDtoRequest.getUsername())) {
//                throw new ResourceAlreadyExistsException("Username already exists");
//            }
//            if (user.getEmail().equals(userDtoRequest.getEmail())) {
//                throw new ResourceAlreadyExistsException("Email already exists");
//            }
//        }

        if(userRepository.existsByUsername(userDtoRequest.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already exists!");
        }

        if(userRepository.existsByEmail(userDtoRequest.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists!");
        }


        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDtoRequest.getUsername());
        userEntity.setEmail(userDtoRequest.getEmail());
        userEntity.setPassword(userDtoRequest.getPassword());

        UserEntity saved = userRepository.save(userEntity);

        watch.stop();
        long duration = watch.getTotalTimeMillis();

        log.info("[{}] SUCCESS createUser | id={} | time={}ms", traceId, saved.getId(), duration);

        if (duration > 500) {
            log.warn("[{}] createUser slow execution: {}ms", traceId, duration);
        }

        return new UserDtoResponse(saved.getId(), saved.getUsername(), saved.getEmail());
    }


    @Override
    public List<UserDtoResponse> getAllUsers() {
        String traceId = UUID.randomUUID().toString();
        StopWatch watch = new StopWatch();
        watch.start();

        log.info("[{}] START getAllUsers", traceId);

        List<UserEntity> users = userRepository.findAll();

        List<UserDtoResponse> result = users.stream()
                .map(user -> new UserDtoResponse(user.getId(), user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());

        watch.stop();
        long duration = watch.getTotalTimeMillis();

        log.info("[{}] SUCCESS getAllUsers | totalUsers={} | time={}ms", traceId, result.size(), duration);

        if (duration > 500) {
            log.warn("[{}] getAllUsers slow execution: {}ms", traceId, duration);
        }

        return result;
    }

    @Override
    public UserDtoResponse getUserById(Long id) {
        String traceId = UUID.randomUUID().toString();
        StopWatch watch = new StopWatch();
        watch.start();

        log.info("[{}] START getUserById | id={}", traceId, id);

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        UserDtoResponse response = new UserDtoResponse(user.getId(), user.getUsername(), user.getEmail());

        watch.stop();
        long duration = watch.getTotalTimeMillis();

        log.info("[{}] SUCCESS getUserById | id={} | time={}ms", traceId, user.getId(), duration);

        if (duration > 500) {
            log.warn("[{}] getUserById slow execution: {}ms", traceId, duration);
        }

        return response;
    }


    @Override
    public UserDtoResponse updateUser(Long id, UserDtoRequest userRequest) {
        String traceId = UUID.randomUUID().toString();
        StopWatch watch = new StopWatch();
        watch.start();

        log.info("[{}] START updateUser | id={}, username={}, email={}",
                traceId, id, userRequest.getUsername(), userRequest.getEmail());

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        boolean isChanged =  user.updateViolationUser(userRequest);
        if(isChanged) {
            user.updateFromRequest(userRequest);
            UserEntity updated = userRepository.save(user);
            watch.stop();
            long duration = watch.getTotalTimeMillis();

            log.info("[{}] SUCCESS updateUser | id={}, username={} | time={}ms",
                    traceId, updated.getId(), updated.getUsername(), duration);

            if (duration > 500) {
                log.warn("[{}] updateUser slow execution: {}ms", traceId, duration);
            }

            return new UserDtoResponse(updated.getId(), updated.getUsername(), updated.getEmail());
        } else {
            return new UserDtoResponse(user.getId(), user.getUsername(), user.getEmail());
        }
    }


    @Override
    public UserDtoResponse deleteUser(Long id) {
        String traceId = UUID.randomUUID().toString();
        StopWatch watch = new StopWatch();
        watch.start();

        log.info("[{}] START deleteUser | id={}", traceId, id);

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        userRepository.deleteById(id);

        watch.stop();
        long duration = watch.getTotalTimeMillis();

        log.info("[{}] SUCCESS deleteUser | id={} | username={} | time={}ms",
                traceId, user.getId(), user.getUsername(), duration);

        if (duration > 500) {
            log.warn("[{}] deleteUser slow execution: {}ms", traceId, duration);
        }

        return new UserDtoResponse(user.getId(), user.getUsername(), user.getEmail());
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
