package com.smart.water.plan.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int getRainThreshold(UUID id) {
        return findUserById(id).orElseThrow(() -> new RuntimeException("User not found")).getThreshold();
    }

    // Create
    public User createUser(User user) {
        user.setId(UUID.randomUUID());
        return userRepository.save(user);
    }

    // Read
    public Optional<User> findUserById(UUID id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Update
    public User updateUser(UUID id, User userUpdates) {
        User user = findUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userUpdates.getName());
        user.setEmail(userUpdates.getEmail());
        user.setPhoneNumber(userUpdates.getPhoneNumber());
        user.setThreshold(userUpdates.getThreshold());
        user.setZipcode(userUpdates.getZipcode());
        user.setLat(userUpdates.getLat());
        user.setLon(userUpdates.getLon());
        user.setAddress(userUpdates.getAddress());
        return userRepository.save(user);
    }

    // Delete
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public User getUser(UUID userId) {
        return findUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }
}