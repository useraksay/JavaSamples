package sort;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

public class LocaleSort {
    public static void main(String[] args) {
        DTO dto1 = new DTO("123", "وداعًا", "BT", "BT");
        DTO dto2 = new DTO("456", "اختبار", "BT", "BT");
        DTO dto3 = new DTO("789", "عالم", "BT", "BT");
        DTO dto4 = new DTO("789", "مرحبا", "BT", "BT");
        DTO dto5 = new DTO("789", null, "BT", "BT");
        DTO dto6 = new DTO("789", "عالم", "BT", "BT");

        ArrayList<DTO> dtos = new ArrayList<DTO>();
        dtos.add(dto1);
        dtos.add(dto2);
        dtos.add(dto3);
        dtos.add(dto4);
        dtos.add(dto5);
        dtos.add(dto6);

        Collator collator = Collator.getInstance(new Locale("ar")); //Your locale
        collator.setStrength(Collator.PRIMARY);
//        dtos.sort(Comparator.nullsLast((o1, o2) -> collator.compare(o1.getName(), o2.getName())));
        dtos.sort(Comparator.comparing(DTO::getName, Comparator.nullsLast(collator)));


        dtos.forEach(dto -> System.out.println(dto.getName()));

        /*List<DTO> sorted = dtos.stream()
            .sorted(Comparator.comparing(DTO::getName, collator))
            .toList();

        sorted.forEach(dto -> System.out.println(dto.getName()));*/
//        codeDTOList.sort(comparator.thenComparing(ClassificationLocalizedCodeDTO::getName, coll))
        /*Comparator<DTO> nameComparator = (o1, o2) -> o1.getName().compareTo(o2.getName());
        dtos.sort(nameComparator.thenComparing(DTO::getName, collator));
        dtos.forEach(dto -> System.out.println(dto.getName()));*/

       /* dtos.sort((o1, o2) -> {
            if (o1.getName() == null) {
                return (o2.getName() == null) ? 0 : -1;
            }
            if (o2.getName() == null) {
                return 1;
            }
            return collator.compare(o1.getName(), o2.getName());
        });*/
    }
}


class DTO {
    private String code;
    private String name;
    private String value;
    private String type;

    public DTO(String code, String name, String value, String type) {
        this.code = code;
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
