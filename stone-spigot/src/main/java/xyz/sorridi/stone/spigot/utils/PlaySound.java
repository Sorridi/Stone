package xyz.sorridi.stone.spigot.utils;

import lombok.NonNull;
import me.lucko.helper.utils.Players;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import xyz.sorridi.stone.common.immutable.Err;

import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Sound utilities.
 *
 * @author Sorridi
 * @since 1.0
 */
public class PlaySound
{

    /**
     * Plays a sound to a player.
     *
     * @param player Player to play the sound to.
     * @param sound  Sound to play.
     */
    public static void play(@NonNull Player player, @NonNull Sound sound)
    {
        play(player, sound, 1, 1, player.getLocation());
    }

    /**
     * Plays a sound to a player.
     *
     * @param player Player to play the sound to.
     * @param sound  Sound to play.
     * @param volume Volume of the sound.
     */
    public static void play(@NonNull Player player, @NonNull Sound sound, int volume)
    {
        play(player, sound, volume, 1, player.getLocation());
    }

    /**
     * Plays a sound to a player.
     *
     * @param player Player to play the sound to.
     * @param sound  Sound to play.
     * @param volume Volume of the sound.
     * @param pitch  Pitch of the sound.
     */
    public static void play(@NonNull Player player, @NonNull Sound sound, int volume, int pitch)
    {
        play(player, sound, volume, pitch, player.getLocation());
    }

    /**
     * Plays a sound to a player.
     *
     * @param player   Player to play the sound to.
     * @param sound    Sound to play.
     * @param location Location to play the sound at.
     */
    public static void play(@NonNull Player player,
                            @NonNull Sound sound,
                            @NonNull Location location)
    {
        play(player, sound, 1, 1, location);
    }

    /**
     * Plays a sound to a player.
     *
     * @param player   Player to play the sound to.
     * @param sound    Sound to play.
     * @param volume   Volume of the sound.
     * @param pitch    Pitch of the sound.
     * @param location Location to play the sound at.
     */
    public static void play(@NonNull Player player,
                            @NonNull Sound sound,
                            int volume,
                            int pitch,
                            @NonNull Location location)
    {
        checkArgument(volume >= 0, Err.NEGATIVE.expect("volume"));
        checkArgument(pitch >= 0, Err.NEGATIVE.expect("pitch"));

        player.playSound(location, sound, volume, pitch);
    }

    /**
     * Plays a sound to players nearby an entity.
     *
     * @param entity Entity to play the sound to.
     * @param sound  Sound to play.
     * @param range  Range of the sound.
     */
    public static void playNearby(@NonNull Entity entity, @NonNull Sound sound, int range)
    {
        playNearby(entity, sound, 1, 1, range);
    }

    /**
     * Plays a sound to players nearby an entity.
     *
     * @param entity Entity to play the sound to.
     * @param sound  Sound to play.
     * @param volume Volume of the sound.
     * @param pitch  Pitch of the sound.
     * @param range  Range of the sound.
     */
    public static void playNearby(@NonNull Entity entity, @NonNull Sound sound, int volume, int pitch, int range)
    {
        playNearby(entity, sound, volume, pitch, range, entity.getLocation());
    }

    /**
     * Plays a sound to players nearby an entity.
     *
     * @param entity   Entity to play the sound to.
     * @param sound    Sound to play.
     * @param range    Range of the sound.
     * @param location Location to play the sound at.
     */
    public static void playNearby(@NonNull Entity entity, @NonNull Sound sound, int range, @NonNull Location location)
    {
        playNearby(entity, sound, 1, 1, range, location);
    }

    /**
     * Plays a sound to players nearby an entity.
     *
     * @param entity   Entity to play the sound to.
     * @param sound    Sound to play.
     * @param volume   Volume of the sound.
     * @param pitch    Pitch of the sound.
     * @param range    Range of the sound.
     * @param location Location to play the sound at.
     */
    public static void playNearby(@NonNull Entity entity,
                                  @NonNull Sound sound,
                                  int volume,
                                  int pitch,
                                  int range,
                                  @NonNull Location location)
    {
        toPlayers(entity, range).forEach(p -> play(p, sound, volume, pitch, location));
    }

    /**
     * Gets the players within a certain radius of an entity.
     *
     * @param entity Entity to get the players around.
     * @param radius Radius to get the players in.
     * @return Stream of players within the range.
     */
    private static Stream<Player> toPlayers(Entity entity, int radius)
    {
        return Players.streamInRange(entity.getLocation(), radius);
    }

}
