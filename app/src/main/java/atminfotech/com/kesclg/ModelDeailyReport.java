package atminfotech.com.kesclg;

/**
 * Created by ABC on 09/21/2017.
 */

public class ModelDeailyReport {
    public String Group;
    public String Dept;
    public String Location;
    public String Assetname;
    public String Serialno;

    public ModelDeailyReport(String group, String dept, String location, String assetname, String serialno) {
        Group = group;
        Dept = dept;
        Location = location;
        Assetname = assetname;
        Serialno = serialno;
    }

    public String getGroup() {
        return Group;
    }

    public void setGroup(String group) {
        Group = group;
    }

    public String getDept() {
        return Dept;
    }

    public void setDept(String dept) {
        Dept = dept;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getAssetname() {
        return Assetname;
    }

    public void setAssetname(String assetname) {
        Assetname = assetname;
    }

    public String getSerialno() {
        return Serialno;
    }

    public void setSerialno(String serialno) {
        Serialno = serialno;
    }
}
