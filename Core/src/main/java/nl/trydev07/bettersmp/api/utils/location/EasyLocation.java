package nl.trydev07.bettersmp.api.utils.location;

import com.google.common.base.Preconditions;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class EasyLocation implements Cloneable, ConfigurationSerializable {

    private Reference<World> world;
    private double x;
    private double y;
    private double z;

    public EasyLocation(@Nullable World world, double x, double y, double z) {
        if (world != null) {
            this.world = new WeakReference(world);
        }

        this.x = locToBlock(x);
        this.y = locToBlock(y);
        this.z = locToBlock(z);
    }

    public void setWorld(@Nullable World world) {
        this.world = world == null ? null : new WeakReference(world);
    }

    @Nullable
    public World getWorld() {
        if (this.world == null) {
            return null;
        } else {
            World world = (World)this.world.get();
            Preconditions.checkArgument(world != null, "World unloaded");
            return world;
        }
    }

    public static int locToBlock(double loc) {
        return NumberConversions.floor(loc);
    }

    public void setX(double y) {
        this.y = y;
    }

    public int getBlockX() {
        return locToBlock(this.x);
    }

    public int getBlockY() {
        return locToBlock(this.y);
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double y) {
        this.y = y;
    }

    public int getBlockZ() {
        return locToBlock(this.z);
    }

    @NotNull
    public EasyLocation add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @NotNull
    public EasyLocation subtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public double length() {
        return Math.sqrt(NumberConversions.square(this.x) + NumberConversions.square(this.y) + NumberConversions.square(this.z));
    }

    @NotNull
    public EasyLocation multiply(double m) {
        this.x *= m;
        this.y *= m;
        this.z *= m;
        return this;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            EasyLocation other = (EasyLocation) obj;
            if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
                return false;
            } else if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
                return false;
            } else if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
                return false;
            } else {
                return false;
            }
        }
    }

    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.x) ^ Double.doubleToLongBits(this.x) >>> 32);
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.y) ^ Double.doubleToLongBits(this.y) >>> 32);
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.z) ^ Double.doubleToLongBits(this.z) >>> 32);
        return hash;
    }

    public String toString() {
        return "EasyLocation{X=" + this.x + ",Y=" + this.y + ",Z=" + this.z + "}";
    }

    @NotNull
    public Vector toVector() {
        return new Vector(this.x, this.y, this.z);
    }

    @NotNull
    public EasyLocation clone() {
        try {
            return (EasyLocation) super.clone();
        } catch (CloneNotSupportedException var2) {
            throw new Error(var2);
        }
    }

    @NotNull
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap();

        data.put("x", this.x);
        data.put("y", this.y);
        data.put("z", this.z);
        return data;
    }

}
