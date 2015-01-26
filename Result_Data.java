package sirfireys.rana.verifiem;

/**
 * Created by root on 11/1/15.
 */
public class Result_Data {


    private String password;
    private String enroll_no;
    private String sem;
    private String _id;
    private String dob;
    private String name;
    private String father_name;
    private String course;
    private String marks;
    private String prf;
    private String pulish;
    private String extra;

    public Result_Data(String[] string) {

        enroll_no = string[0];
        sem = string[1];
        _id = string[2];
        dob = string[3];
        name = string[4];
        father_name = string[5];
        course = string[6];
        marks = string[7];
        prf = string[8];
        pulish = string[9];
        extra = string[10];
        password = string[11];

    }


    public String getEnroll_no() {
        return enroll_no;
    }

    public String getSem() {
        return sem;
    }

    public String get_id() {
        return _id;
    }

    public String getDob() {
        return dob;
    }

    public String getName() {
        return name;
    }

    public String getFather_name() {
        return father_name;
    }

    public String getCourse() {
        return course;
    }

    public String getMarks() {
        return marks;
    }

    public String getPrf() {
        return prf;
    }

    public String getPulish() {
        return pulish;
    }

    public String getExtra() {
        return extra;
    }

    public String getPassword() {
        return password;
    }
}
