package com.hoo.aar.application.port.in.home;

import java.util.List;

public record QueryUserHomesResult(
        List<HomeInfo> homes
) {
    public record HomeInfo(
            Long id,
            Long basicImageId,
            String name,
            Boolean isMain
    ) {

    }
}
