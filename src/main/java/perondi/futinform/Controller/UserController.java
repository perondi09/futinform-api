    package perondi.futinform.controller;

    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import perondi.futinform.dtos.user.UserPatchDTO;
    import perondi.futinform.dtos.user.UserRequestDTO;
    import perondi.futinform.dtos.user.UserResponseDTO;
    import perondi.futinform.services.UserService;

    import java.util.List;
    import java.util.UUID;

    @RestController
    @RequestMapping("/api/users")
    @RequiredArgsConstructor
    public class UserController {

        private final UserService userService;

        @PostMapping
        public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO request) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
        }

        @GetMapping("/{id}")
        public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID id) {
            return ResponseEntity.ok(userService.findById(id));
        }

        @GetMapping
        public ResponseEntity<List<UserResponseDTO>> findAll() {
            return ResponseEntity.ok(userService.findAll());
        }

        @PatchMapping("/{id}")
        public ResponseEntity<UserResponseDTO> update(@PathVariable UUID id,
                                                     @RequestBody UserPatchDTO request) {
            return ResponseEntity.ok(userService.update(id, request));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable UUID id) {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        }
    }