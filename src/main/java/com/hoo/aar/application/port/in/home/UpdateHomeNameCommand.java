package com.hoo.aar.application.port.in.home;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UpdateHomeNameCommand(
        @NotEmpty @Size(max = 50) String newName
) {
}
