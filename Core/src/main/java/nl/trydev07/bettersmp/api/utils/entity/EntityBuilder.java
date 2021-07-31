package nl.trydev07.bettersmp.api.utils.entity;

import lombok.Getter;
import nl.trydev07.bettersmp.api.utils.location.EasyLocation;
import nl.trydev07.bettersmp.api.utils.text.TextUtil;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.*;

public class EntityBuilder {

    @Getter private Entity entity;

    private EntityType entityType;
    private String customName;
    private Location location;
    private boolean customNameVisible;
    private boolean mobAI;
    private int noDamageTicks;
    private int amount;

    public EntityBuilder() {
    }

    public EntityBuilder(EntityTypes entityType, int amount) {
        this("", entityType, null, false, false, 0, amount);
    }

    public EntityBuilder(EntityTypes entityType, Location location) {
        this("", entityType, location, false, false, 0, 1);
    }

    public EntityBuilder(String customName, EntityTypes entityType, Location location) {
        this(customName, entityType, location, false, false, 0, 1);
    }

    public EntityBuilder(String customName, EntityTypes entityType, Location location, boolean customNameVisible) {
        this(customName, entityType, location, customNameVisible, false, 0, 1);
    }

    public EntityBuilder(String customName, EntityTypes entityType, Location location, boolean customNameVisible, boolean mobAI) {
        this(customName, entityType, location, customNameVisible, mobAI, 0, 1);
    }

    public EntityBuilder(String customName, EntityTypes entityType, Location location, boolean customNameVisible, boolean mobAI, int setNoDamageTicks, int amount) {
        this.customName = TextUtil.coloredText(customName);
        this.entityType = entityType.getType();
        this.location = location;
        this.customNameVisible = customNameVisible;
        this.mobAI = mobAI;
        this.noDamageTicks = setNoDamageTicks;
        this.amount = amount;
    }

    public void build() {
        LivingEntity livingEntity = (LivingEntity) Objects.requireNonNull(this.location.getWorld()).spawnEntity(this.location, this.entityType);

        if (!(this.customName.length() == 0)) livingEntity.setCustomName(this.customName);
        if (this.noDamageTicks > 0) livingEntity.setNoDamageTicks(this.noDamageTicks);
        livingEntity.setCustomNameVisible(this.customNameVisible);
        livingEntity.setAI(this.mobAI);
        this.entity = livingEntity;
    }

    public void spawnRandom(List<EasyLocation> locations) {
        if (locations == null || locations.isEmpty()) return;
        Random random = new Random();
        for (int i = this.amount; i > 0; i--) {
            int randomLocation = random.nextInt(locations.size());
            locations.get(randomLocation).getWorld().spawnEntity(new Location(locations.get(randomLocation).getWorld(), locations.get(randomLocation).getBlockX(), locations.get(randomLocation).getBlockY(), locations.get(randomLocation).getBlockZ()), this.entityType);
        }
    }

    public Entity getEntity() {
        return entity;
    }


}
