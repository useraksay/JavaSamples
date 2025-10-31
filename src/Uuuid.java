import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
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
    uuidGenerate();
  }

  private static void uuidGenerate() {
    UUID uuid = UUID.randomUUID();
    uuid = UUID.nameUUIDFromBytes("9147178_103858_ANTransaction (1).xml".getBytes(StandardCharsets.UTF_8)); //fd32c23b-5578-35c2-9bbc-b91cd00e3038
    System.out.println(uuid.toString());
  }
}
