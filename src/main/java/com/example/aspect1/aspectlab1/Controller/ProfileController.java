package com.example.aspect1.aspectlab1.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private static final List<Map<String, Object>> profiles = new ArrayList<>();
    private static int currentProfileId = 1; // Auto-incrementing profile ID

    // 🟢 Create a new profile
    @PostMapping
    public ResponseEntity<Map<String, Object>> createProfile(@RequestBody Map<String, Object> profileData) {
        profileData.put("profileId", currentProfileId++);
        profiles.add(profileData);
        return ResponseEntity.ok(Map.of("message", "Profile created successfully", "profile", profileData));
    }

    // 🟢 Get all profiles
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllProfiles() {
        return ResponseEntity.ok(profiles);
    }

    // 🟢 Get profile by ID
    @GetMapping("/{profileId}")
    public ResponseEntity<?> getProfileById(@PathVariable int profileId) {
        return profiles.stream()
                .filter(profile -> (int) profile.get("profileId") == profileId)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(Map.of("message", "Profile not found")));
    }

    // 🟢 Update a profile by ID
    @PutMapping("/{profileId}")
    public ResponseEntity<Map<String, Object>> updateProfile(@PathVariable int profileId, @RequestBody Map<String, Object> updatedData) {
        for (Map<String, Object> profile : profiles) {
            if ((int) profile.get("profileId") == profileId) {
                profile.putAll(updatedData);
                return ResponseEntity.ok(Map.of("message", "Profile updated successfully", "profile", profile));
            }
        }
        return ResponseEntity.status(404).body(Map.of("message", "Profile not found"));
    }

    // 🟢 Delete a profile by ID
    @DeleteMapping("/{profileId}")
    public ResponseEntity<Map<String, Object>> deleteProfile(@PathVariable int profileId) {
        Iterator<Map<String, Object>> iterator = profiles.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> profile = iterator.next();
            if ((int) profile.get("profileId") == profileId) {
                iterator.remove();
                return ResponseEntity.ok(Map.of("message", "Profile deleted successfully"));
            }
        }
        return ResponseEntity.status(404).body(Map.of("message", "Profile not found"));
    }
}
