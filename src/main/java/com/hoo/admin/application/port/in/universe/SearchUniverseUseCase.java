package com.hoo.admin.application.port.in.universe;

public interface SearchUniverseUseCase {
    SearchUniverseResult search(SearchUniverseCommand command);
    SearchUniverseResult.UniverseDetailInfo search(Long universeId);
}
