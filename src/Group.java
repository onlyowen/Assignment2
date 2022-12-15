public class Group {
    private String name;//组名
    String creatTime;

    public Group(String name, String creatTime) {
        this.name = name;
        this.creatTime = creatTime;
    }

    public Group() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
