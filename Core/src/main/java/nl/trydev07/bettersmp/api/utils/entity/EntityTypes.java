package nl.trydev07.bettersmp.api.utils.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.EntityType;

@AllArgsConstructor
@Getter
public enum EntityTypes {

    PIG(EntityType.PIG),
    COW(EntityType.PIG),
    SHEEP(EntityType.PIG),
    CHICKEN(EntityType.PIG),
    RABBIT(EntityType.PIG);

    private EntityType type;
}
