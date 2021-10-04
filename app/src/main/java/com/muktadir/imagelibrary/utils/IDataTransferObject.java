package com.muktadir.imagelibrary.utils;

import java.util.List;

public interface IDataTransferObject<Domain,Entity> {
    public Entity toEntity(Domain entity);
    public Domain toDomain(Entity entity);
    public List<Entity> toEntityList(List<Domain> entities);
    public List<Domain> toDomainList(List<Entity> entities);
}
