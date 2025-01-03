import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class Uuuid {
  public static void main(String[] args) {
    UUID uuid = UUID.fromString("699dcddd-d615-490b-b879-0275ca756865");
    byte[] uuidBytes = new byte[16];
    ByteBuffer.wrap(uuidBytes)
        .order(ByteOrder.BIG_ENDIAN)
        .putLong(uuid.getMostSignificantBits())
        .putLong(uuid.getLeastSignificantBits());

    System.out.println(uuidBytes);
  }
}
