package lld.messageQueue;

import java.util.List;

public class Topic {
    private String name;
    private List<Partition> partitions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Partition> setPartitions(List<Partition> partitions) {
        return this.partitions = partitions;
    }

    public List<Partition> getPartitions() {
        return this.partitions;
    }
}
