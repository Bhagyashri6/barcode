package atminfotech.com.kesclg;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by ABC on 10/30/2017.
 */

public class Bean implements KvmSerializable {
    public String Group;
    public String Dept;
    public String Location;
    public String Assetname;
    public String Serialno;

    public Bean(String group, String dept, String loc, String asset, String serial) {
        this.Group = group;
        this.Dept = dept;
        this.Location = loc;
        this.Assetname = asset;
        this.Serialno = serial;

    }

    public Bean(){}


    public Object getProperty(int arg0) {
        switch(arg0)
        {
            case 0:
                return Group;
            case 1:
                return Dept;
            case 2:
                return Location;
            case 3:
                return Assetname;
            case 4:
                return Serialno;

        }
        return null;
    }

    public int getPropertyCount() {
        return 5;
    }

    @SuppressWarnings("rawtypes")
    public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
        switch(index)
        {
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Group";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Dept";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Location";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Assetname";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Serialno";
                break;

            default:
                break;
        }
    }

    public void setProperty(int index, Object value) {
        switch(index)
        {
            case 0:
                Group = value.toString();
                break;
            case 1:
                Dept = value.toString();
                break;
            case 2:
                Location = value.toString();
                break;
            case 3:
                Assetname = value.toString();
                break;
            case 4:
                Serialno = value.toString();
                break;

            default:
                break;
        }
    }
}
