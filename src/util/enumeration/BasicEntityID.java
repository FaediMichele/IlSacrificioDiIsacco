package util.enumeration;
import java.util.UUID;

/**
 * ID for entity for statsus component.
 *
 */
public class BasicEntityID implements ValuesMapStatusEnum {
    private final UUID uuid;

    /**
     * 
     * @param uuid is {@link UUID} for entity for status Component.
     */
    public BasicEntityID(final UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return this.uuid.toString();
    }
}
