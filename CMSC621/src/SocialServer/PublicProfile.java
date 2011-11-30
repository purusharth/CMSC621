package SocialServer;

import java.util.Arrays;

public class PublicProfile {
	private String gid=null;
	private String name=null;
	private Integer age=null;
	private String[] hobbies=null;
	private String sex=null;
	
	public String getGID() { return gid;}
	public String getName() { return name;}
	public Integer getAge() { return age;}
	public String[] getHobbies() { return hobbies;}
	public String getSex() {return sex;}
	
	public void setGID(String gid) { this.gid = gid;}
	public void setName(String name) { this.name = name;}
	public void setAge(Integer age) { this.age = age;}
	public void setHobbies(String[] hobbies) { this.hobbies = hobbies;}
	public void setSex(String sex) { this.sex = sex;}
	
	public String toString() {
        return String.format("gid=%s, name=%s, age=%d, sex=%s hobbies=[%s]", gid, name, age, sex, Arrays.toString(hobbies));
    }


}
